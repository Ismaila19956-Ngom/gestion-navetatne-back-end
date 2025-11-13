package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.BudgetDgpsnMapper;
import com.webgram.dgpsn.models.BudgetDgpsnDTO;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.models.RealisationDTO;
import com.webgram.dgpsn.repositories.BudgetDgpsnRepository;
import com.webgram.dgpsn.repositories.LigneBudgetaireRepository;
import com.webgram.dgpsn.services.BudgetDgpsnService;
import com.webgram.dgpsn.services.LigneBudgetaireService;
import com.webgram.dgpsn.services.RealisationService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetDgpsnServiceImpl implements BudgetDgpsnService {
    private final BudgetDgpsnRepository budgetDgpsnRepository;
    private final BudgetDgpsnMapper budgetDgpsnMapper;
    private final LigneBudgetaireService ligneBudgetaireService;
    private final RealisationService realisationService;
    private final LigneBudgetaireRepository ligneBudgetaireRepository;
    private final AlerteServiceImpl alerteService;

//    @Override
//    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
//    public BudgetDgpsnDTO create(BudgetDgpsnDTO budgetDgpsnDTO) {
//        var savedBudget = budgetDgpsnRepository.save(budgetDgpsnMapper.asEntity(budgetDgpsnDTO));
//        log.info("BudgetDgpsn successfully added {}", savedBudget);
//        return budgetDgpsnMapper.asDto(savedBudget);
//    }
//
//    @Override
//    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
//    public BudgetDgpsnDTO update(BudgetDgpsnDTO budgetDgpsnDTO) {
//        try {
//            if (budgetDgpsnRepository.existsById(budgetDgpsnDTO.getId())) {
//                var budgetEntity = budgetDgpsnMapper.asEntity(budgetDgpsnDTO);
//                var updatedBudget = budgetDgpsnMapper.asDto(budgetDgpsnRepository.save(budgetEntity));
//                log.info("BudgetDgpsn successfully updated {}", updatedBudget.getId());
//                return updatedBudget;
//            } else {
//                throw new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnDTO.getId());
//            }
//        } catch (IllegalArgumentException ex) {
//            throw new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnDTO.getId());
//        }
//    }
@Override
@Journal(actionType = ActionType.ADD_BUDGET)
public BudgetDgpsnDTO create(BudgetDgpsnDTO budgetDgpsnDTO) {
    var budgetEntity = budgetDgpsnMapper.asEntity(budgetDgpsnDTO);
    var savedBudget = budgetDgpsnRepository.save(budgetEntity);
    // Calculer le montant total des lignes budgétaires existantes pour ce budget
    Double montantTotalLignes = ligneBudgetaireRepository.findByBudgetId(savedBudget.getId())
            .stream()
            .mapToDouble(ligne -> ligne.getMontant() != null ? ligne.getMontant() : 0.0)
            .sum();
    // Mettre à jour le montantEngage avec le total calculé (sans les 0)
    if (montantTotalLignes > 0) {
        savedBudget.setMontantEngage(montantTotalLignes);
        savedBudget = budgetDgpsnRepository.save(savedBudget);
        log.info("Budget créé avec montantEngage calculé: {}/{}",
                savedBudget.getMontantEngage(), savedBudget.getMontant());
    } else {
        savedBudget.setMontantEngage(0.0);
        log.info("Budget créé sans lignes budgétaires existantes");
    }
       alerteService.generateAlertNouveauBudget(budgetDgpsnMapper.asDto(savedBudget));
         return budgetDgpsnMapper.asDto(savedBudget);
}

    @Override
    @Journal(actionType = ActionType.UPDATE_BUDGET)
    public BudgetDgpsnDTO update(BudgetDgpsnDTO budgetDgpsnDTO) {
        if (!budgetDgpsnRepository.existsById(budgetDgpsnDTO.getId())) {
            throw new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnDTO.getId());
        }

        var budgetEntity = budgetDgpsnMapper.asEntity(budgetDgpsnDTO);

        // Recalculer le montantEngage lors de la mise à jour
        Double montantTotalLignes = ligneBudgetaireRepository.findByBudgetId(budgetEntity.getId())
                .stream()
                .mapToDouble(ligne -> ligne.getMontant() != null ? ligne.getMontant() : 0.0)
                .sum();

        budgetEntity.setMontantEngage(montantTotalLignes);

        var updatedBudget = budgetDgpsnRepository.save(budgetEntity);
        log.info("Budget mis à jour avec montantEngage recalculé: {}/{}",
                updatedBudget.getMontantEngage(), updatedBudget.getMontant());
        alerteService.generateAlertUpdateBudget(budgetDgpsnMapper.asDto(updatedBudget));
        return budgetDgpsnMapper.asDto(updatedBudget);
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public BudgetDgpsnDTO read(Long budgetDgpsnId) {
        var budget = budgetDgpsnRepository
                .findById(budgetDgpsnId)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnId));
        log.info("Reading BudgetDgpsn id {}", budget);
        return budgetDgpsnMapper.asDto(budget);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long budgetDgpsnId) {
        var budget = budgetDgpsnRepository.findById(budgetDgpsnId)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetDgpsn", budgetDgpsnId));
        alerteService.generateAlertDeleteBudget(budgetDgpsnMapper.asDto(budget));
        budgetDgpsnRepository.deleteById(budgetDgpsnId);
        log.info("The BudgetDgpsn id {} is deleted", budgetDgpsnId);
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<BudgetDgpsnDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Double montant,
            Integer annee
    ) {
        return budgetDgpsnRepository
                .readAllByFiltering(pageable, code, libelle, montant, annee)
                .map(budgetDgpsnMapper::asDto);
    }

    @Override
    public Map<String, Object> getSyntheseBudgetaire(Long budgetId, String periode, Integer trimestre, Integer mois) {
        // Récupérer les données de base
        BudgetDgpsnDTO budget = this.read(budgetId);
        List<LigneBudgetaireDTO> lignes = ligneBudgetaireService.findByBudgetId(budgetId);
        List<RealisationDTO> realisations = realisationService.findByBudgetId(budgetId);
        // Filtrer les réalisations selon la période si spécifiée
        if (periode != null && !periode.isEmpty() && realisations != null) {
            realisations = filtrerRealisationsParPeriode(realisations, budget.getAnnee(), periode, trimestre, mois);
        }
        // Construire la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("budget", budget);
        response.put("lignes", lignes);
        response.put("realisations", realisations);
        response.put("periode", periode != null ? periode : "annee");

        if ("trimestre".equals(periode) && trimestre != null) {
            response.put("trimestre", trimestre);
        }
        if ("mois".equals(periode) && mois != null) {
            response.put("mois", mois);
        }

        return response;
    }

    /**
     * Filtre les réalisations selon la période demandée
     */
    private List<RealisationDTO> filtrerRealisationsParPeriode(
            List<RealisationDTO> realisations,
            Integer annee,
            String periode,
            Integer trimestre,
            Integer mois) {

        return realisations.stream()
                .filter(realisation -> {
                    LocalDate dateRealisation = extraireDateRealisation(realisation);

                    if (dateRealisation == null) {
                        return false;
                    }

                    // Vérifier que la date appartient à l'année du budget
                    if (dateRealisation.getYear() != annee) {
                        return false;
                    }

                    // Filtrer selon le type de période
                    return switch (periode) {
                        case "trimestre" -> filtrerParTrimestre(dateRealisation, trimestre);
                        case "mois" -> filtrerParMois(dateRealisation, mois);
                        case "annee" -> true;
                        default -> true;
                    };
                })
                .collect(Collectors.toList());
    }

    /**
     * Extrait la date d'une réalisation
     */
    private LocalDate extraireDateRealisation(RealisationDTO realisation) {
        if (realisation.getDate() == null) {
            return null;
        }

        try {
            // La date est déjà un LocalDate dans le DTO
            return realisation.getDate();
        } catch (Exception e) {
            // Log l'erreur si nécessaire
            return null;
        }
    }

    /**
     * Vérifie si une date appartient au trimestre spécifié
     */
    private boolean filtrerParTrimestre(LocalDate date, Integer trimestre) {
        if (trimestre == null || trimestre < 1 || trimestre > 4) {
            return false;
        }

        int moisDebut = (trimestre - 1) * 3 + 1;
        int moisFin = trimestre * 3;
        int moisDate = date.getMonthValue();

        return moisDate >= moisDebut && moisDate <= moisFin;
    }

    /**
     * Vérifie si une date appartient au mois spécifié
     */
    private boolean filtrerParMois(LocalDate date, Integer mois) {
        if (mois == null || mois < 1 || mois > 12) {
            return false;
        }

        return date.getMonthValue() == mois;
    }

    /**
     * Calcule le trimestre d'une date
     */
    private int getTrimestreFromDate(LocalDate date) {
        return (date.getMonthValue() - 1) / 3 + 1;
    }
}