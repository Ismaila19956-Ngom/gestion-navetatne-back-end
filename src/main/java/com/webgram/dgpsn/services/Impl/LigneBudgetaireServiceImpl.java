package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.enums.TypeLigneBugetaire;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.LigneBudgetaireMapper;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.repositories.LigneBudgetaireRepository;
import com.webgram.dgpsn.services.LigneBudgetaireService;
import com.webgram.dgpsn.tools.ActionType;
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

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public LigneBudgetaireDTO create(LigneBudgetaireDTO ligneBudgetaireDTO) {
        var savedLigne = ligneBudgetaireRepository.save(ligneBudgetaireMapper.asEntity(ligneBudgetaireDTO));
        log.info("LigneBudgetaire successfully added {}", savedLigne);
        return ligneBudgetaireMapper.asDto(savedLigne);
    }

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public List<LigneBudgetaireDTO> createMultiple(List<LigneBudgetaireDTO> ligneBudgetaireDTOs) {
        var savedLignes = ligneBudgetaireRepository.saveAll(
                ligneBudgetaireDTOs.stream()
                        .map(ligneBudgetaireMapper::asEntity)
                        .collect(Collectors.toList())
        );
        log.info("Multiple LigneBudgetaire entries successfully added, count: {}", savedLignes.size());
        return savedLignes.stream()
                .map(ligneBudgetaireMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public LigneBudgetaireDTO update(LigneBudgetaireDTO ligneBudgetaireDTO) {
        try {
            if (ligneBudgetaireRepository.existsById(ligneBudgetaireDTO.getId())) {
                var ligneEntity = ligneBudgetaireMapper.asEntity(ligneBudgetaireDTO);
                var updatedLigne = ligneBudgetaireMapper.asDto(ligneBudgetaireRepository.save(ligneEntity));
                log.info("LigneBudgetaire successfully updated {}", updatedLigne.getId());
                return updatedLigne;
            } else {
                throw new ResourceNotFoundException("LigneBudgetaire", ligneBudgetaireDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("LigneBudgetaire", ligneBudgetaireDTO.getId());
        }
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
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long ligneBudgetaireId) {
        try {
            ligneBudgetaireRepository.deleteById(ligneBudgetaireId);
            log.info("The LigneBudgetaire id {} is deleted", ligneBudgetaireId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("LigneBudgetaire", ligneBudgetaireId);
        }
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
                .readAllByFiltering(pageable,  rubriqueId, montant,typeLigneBugetaire, commentaire, budgetId)
                .map(ligneBudgetaireMapper::asDto);
    }
    // Add to LigneBudgetaireServiceImpl
    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public List<LigneBudgetaireDTO> findByBudgetId(Long budgetId) {
        return ligneBudgetaireRepository.findByBudgetId(budgetId)
                .stream()
                .map(ligneBudgetaireMapper::asDto)
                .collect(Collectors.toList());
    }
}