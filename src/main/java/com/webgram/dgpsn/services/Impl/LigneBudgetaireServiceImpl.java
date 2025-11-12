package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.BudgetDgpsnEntity;
import com.webgram.dgpsn.entities.LigneBudgetaireEntity;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import com.webgram.dgpsn.exceptions.BudgetDepassementException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.LigneBudgetaireMapper;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.repositories.LigneBudgetaireRepository;
import com.webgram.dgpsn.services.LigneBudgetaireService;
import com.webgram.dgpsn.tools.ActionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LigneBudgetaireServiceImpl implements LigneBudgetaireService {
    private final LigneBudgetaireRepository ligneBudgetaireRepository;
    private final LigneBudgetaireMapper ligneBudgetaireMapper;
    @PersistenceContext
    private EntityManager entityManager;
    private final AlerteServiceImpl alerteService;

    @Override
    @Transactional
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public LigneBudgetaireDTO create(LigneBudgetaireDTO dto) {
        var ligne = ligneBudgetaireMapper.asEntity(dto);
        var budget = ligne.getBudget();
        if (budget == null) {
            throw new IllegalArgumentException("Le budget est requis pour la ligne budgétaire");
        }
        // Charger les lignes existantes pour recalculer
        budget = entityManager.find(BudgetDgpsnEntity.class, budget.getId());
        budget.getLignesBudgetaires().add(ligne);
        validateMontantLigne(ligne, budget);
        updateMontantEngage(budget);
        var saved = ligneBudgetaireRepository.save(ligne);
        entityManager.merge(budget);
        alerteService.generateAlertNouvelleLigneBudget(ligneBudgetaireMapper.asDto(saved));
//        log.info("LigneBudgetaire ajoutée, budget engagé: {}/{}", budget.getMontantEngage(), budget.getMontant());
        return ligneBudgetaireMapper.asDto(saved);
    }

//    @Override
//    @Transactional
//    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
//    public List<LigneBudgetaireDTO> createMultiple(List<LigneBudgetaireDTO> dtos) {
//        if (dtos.isEmpty()) return List.of();
//
//        var budgetId = dtos.get(0).getBudgetId();
//        var budget = entityManager.find(BudgetDgpsnEntity.class, budgetId);
//
//        if (budget == null) {
//            throw new ResourceNotFoundException("Budget", budgetId);
//        }
//
//        double totalAjout = dtos.stream()
//                .mapToDouble(dto -> dto.getMontant() != null ? dto.getMontant() : 0.0)
//                .sum();
//
//        double nouveauTotal = budget.getMontantEngage() + totalAjout;
//
//        if (nouveauTotal > budget.getMontant()) {
//            throw new BudgetDepassementException(
//                    String.format("Dépassement du Budget : le total %.2f FCFA dépasse le budget autorisé de %.2f FCFA. " +
//                                    "Montant actuellement engagé : %.2f FCFA, montant à ajouter : %.2f FCFA",
//                            nouveauTotal, budget.getMontant(), budget.getMontantEngage(), totalAjout)
//            );
//        }
//
//        var entities = dtos.stream()
//                .map(ligneBudgetaireMapper::asEntity)
//                .peek(l -> l.setBudget(budget))
//                .collect(Collectors.toList());
//
//        var saved = ligneBudgetaireRepository.saveAll(entities);
//        budget.getLignesBudgetaires().addAll(entities);
//        updateMontantEngage(budget);
//        entityManager.merge(budget);
//
//        log.info("Multiple lignes budgétaires ajoutées. Nouveau montant engagé: {}/{}",
//                budget.getMontantEngage(), budget.getMontant());
//
//        return saved.stream().map(ligneBudgetaireMapper::asDto).collect(Collectors.toList());
//    }
@Override
@Transactional
@Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
public List<LigneBudgetaireDTO> createMultiple(List<LigneBudgetaireDTO> dtos) {
    if (dtos.isEmpty()) return List.of();
    var budgetId = dtos.get(0).getBudgetId();
    var budget = entityManager.find(BudgetDgpsnEntity.class, budgetId);

    if (budget == null) {
        throw new ResourceNotFoundException("Budget", budgetId);
    }
    double totalAjout = dtos.stream()
            .mapToDouble(dto -> dto.getMontant() != null ? dto.getMontant() : 0.0)
            .sum();
    double nouveauTotal = budget.getMontantEngage() + totalAjout;
    if (nouveauTotal > budget.getMontant()) {
        throw new BudgetDepassementException(
                String.format("Dépassement du Budget : le total %.2f FCFA dépasse le budget autorisé de %.2f FCFA. " +
                                "Montant actuellement engagé : %.2f FCFA, montant à ajouter : %.2f FCFA",
                        nouveauTotal, budget.getMontant(), budget.getMontantEngage(), totalAjout)
        );
    }
    var entities = dtos.stream()
            .map(ligneBudgetaireMapper::asEntity)
            .peek(l -> l.setBudget(budget))
            .collect(Collectors.toList());

    var saved = ligneBudgetaireRepository.saveAll(entities);
    budget.getLignesBudgetaires().addAll(entities);
    updateMontantEngage(budget);
    entityManager.merge(budget);

    log.info("Multiple lignes budgétaires ajoutées. Nouveau montant engagé: {}/{}",
            budget.getMontantEngage(), budget.getMontant());

    // === UNE SEULE ALERTE POUR TOUTES LES LIGNES ===
    var savedDtos = saved.stream()
            .map(ligneBudgetaireMapper::asDto)
            .collect(Collectors.toList());

    alerteService.generateAlertMultipleLignesBudgetAjoutees(savedDtos, budgetId, totalAjout);

    return savedDtos;
}
    @Override
    @Transactional
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public LigneBudgetaireDTO update(LigneBudgetaireDTO dto) {
        var existing = ligneBudgetaireRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("LigneBudgetaire", dto.getId()));

        var budget = existing.getBudget();
        budget = entityManager.find(BudgetDgpsnEntity.class, budget.getId());

        // Calculer la différence
        double ancienMontant = existing.getMontant() != null ? existing.getMontant() : 0.0;
        double nouveauMontant = dto.getMontant() != null ? dto.getMontant() : 0.0;
        double difference = nouveauMontant - ancienMontant;
        double nouveauTotal = budget.getMontantEngage() + difference;

        if (nouveauTotal > budget.getMontant()) {
            throw new BudgetDepassementException(
                    String.format("Mise à jour refusée : le nouveau total %.2f FCFA dépasse le budget de %.2f FCFA. " +
                                    "Montant actuel : %.2f FCFA, différence : %+.2f FCFA",
                            nouveauTotal, budget.getMontant(), budget.getMontantEngage(), difference)
            );
        }

        // Mettre à jour
        existing.setMontant(dto.getMontant());
        existing.setRubrique(entityManager.find(PlanComptableElementEntity.class, dto.getRubriqueId()));
        existing.setTypeLigneBugetaire(dto.getTypeLigneBugetaire());
        existing.setCommentaire(dto.getCommentaire());

        updateMontantEngage(budget);
        entityManager.merge(budget);

        var updated = ligneBudgetaireRepository.save(existing);
        alerteService.generateAlertNouvelleLigneBudget(ligneBudgetaireMapper.asDto(updated));
        log.info("LigneBudgetaire mise à jour. Nouveau montant engagé: {}/{}",
                budget.getMontantEngage(), budget.getMontant());

        return ligneBudgetaireMapper.asDto(updated);
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public LigneBudgetaireDTO read(Long ligneBudgetaireId) {
        var ligne = ligneBudgetaireRepository
                .findById(ligneBudgetaireId)
                .orElseThrow(() -> new ResourceNotFoundException("LigneBudgetaire", ligneBudgetaireId));
        log.info("Reading LigneBudgetaire id {}", ligne);
        return ligneBudgetaireMapper.asDto(ligne);
    }

    @Override
    @Transactional
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long ligneBudgetaireId) {
        var ligne = ligneBudgetaireRepository.findById(ligneBudgetaireId)
                .orElseThrow(() -> new ResourceNotFoundException("LigneBudgetaire", ligneBudgetaireId));

        var budget = ligne.getBudget();
        budget = entityManager.find(BudgetDgpsnEntity.class, budget.getId());
        budget.getLignesBudgetaires().remove(ligne);

        // ALERTE DE SUPPRESSION (avant suppression)
        alerteService.generateAlertDeleteLigneBudget(ligneBudgetaireMapper.asDto(ligne));

        ligneBudgetaireRepository.delete(ligne);
        updateMontantEngage(budget);
        entityManager.merge(budget);

        log.info("LigneBudgetaire supprimée, montant engagé mis à jour: {}", budget.getMontantEngage());
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<LigneBudgetaireDTO> readAll(
            Pageable pageable,
            Long rubriqueId,
            Double montant,
            TypeLigneBugetaire typeLigneBugetaire,
            String commentaire,
            Long budgetId
    ) {
        return ligneBudgetaireRepository
                .readAllByFiltering(pageable, rubriqueId, montant, typeLigneBugetaire, commentaire, budgetId)
                .map(ligneBudgetaireMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public List<LigneBudgetaireDTO> findByBudgetId(Long budgetId) {
        return ligneBudgetaireRepository.findByBudgetId(budgetId)
                .stream()
                .map(ligneBudgetaireMapper::asDto)
                .collect(Collectors.toList());
    }

    private void validateMontantLigne(LigneBudgetaireEntity ligne, BudgetDgpsnEntity budget) {
        double nouveauTotal = budget.getMontantEngage() + ligne.getMontant();
        if (nouveauTotal > budget.getMontant()) {
            throw new BudgetDepassementException(
                    String.format("Dépassement de budget : %.2f FCFA dépasse le montant autorisé de %.2f FCFA",
                            nouveauTotal, budget.getMontant())
            );
        }
    }

    private void updateMontantEngage(BudgetDgpsnEntity budget) {
        double total = budget.getLignesBudgetaires().stream()
                .mapToDouble(l -> l.getMontant() != null ? l.getMontant() : 0.0)
                .sum();
        budget.setMontantEngage(total);
    }
}