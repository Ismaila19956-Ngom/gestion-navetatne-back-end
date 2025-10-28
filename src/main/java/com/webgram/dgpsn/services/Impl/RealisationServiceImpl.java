package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RealisationMapper;
import com.webgram.dgpsn.models.LigneBudgetaireDTO;
import com.webgram.dgpsn.models.RealisationDTO;
import com.webgram.dgpsn.repositories.RealisationRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RealisationServiceImpl implements RealisationService {
    private final RealisationRepository realisationRepository;
    private final RealisationMapper realisationMapper;
    private final LigneBudgetaireService ligneBudgetaireService;

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public RealisationDTO create(RealisationDTO realisationDTO) {
        var savedRealisation = realisationRepository.save(realisationMapper.asEntity(realisationDTO));
        log.info("Realisation successfully added {}", savedRealisation);
        return realisationMapper.asDto(savedRealisation);
    }

    @Override
    @Journal(actionType = ActionType.ADD_DATES_IMPORTANTE)
    public List<RealisationDTO> createMultiple(List<RealisationDTO> realisationDTOs) {
        var savedRealisations = realisationRepository.saveAll(
                realisationDTOs.stream()
                        .map(realisationMapper::asEntity)
                        .collect(Collectors.toList())
        );
        log.info("Multiple Realisation entries successfully added, count: {}", savedRealisations.size());
        return savedRealisations.stream()
                .map(realisationMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DATES_IMPORTANTE)
    public RealisationDTO update(RealisationDTO realisationDTO) {
        try {
            if (realisationRepository.existsById(realisationDTO.getId())) {
                var realisationEntity = realisationMapper.asEntity(realisationDTO);
                var updatedRealisation = realisationMapper.asDto(realisationRepository.save(realisationEntity));
                log.info("Realisation successfully updated {}", updatedRealisation.getId());
                return updatedRealisation;
            } else {
                throw new ResourceNotFoundException("Realisation", realisationDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Realisation", realisationDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public RealisationDTO read(Long realisationId) {
        var realisation = realisationRepository
                .findById(realisationId)
                .orElseThrow(() -> new ResourceNotFoundException("Realisation", realisationId));
        log.info("Reading Realisation id {}", realisation);
        return realisationMapper.asDto(realisation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DATES_IMPORTANTE)
    public void delete(Long realisationId) {
        try {
            realisationRepository.deleteById(realisationId);
            log.info("The Realisation id {} is deleted", realisationId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Realisation", realisationId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public Page<RealisationDTO> readAll(
            Pageable pageable,
            String code,
            Long realisationsId,
            Double montant,
            LocalDate date,
            String fournisseur,
            String numeroBon,
            String numeroBE,
            String numeroMandat,
            String description,
            Long ligneBudgetaireId
    ) {
        return realisationRepository
                .readAllByFiltering(pageable, code, realisationsId, montant, date, fournisseur, numeroBon, numeroBE, numeroMandat, description, ligneBudgetaireId)
                .map(realisationMapper::asDto);
    }
    // Add to RealisationServiceImpl
//    @Override
//    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
//    public List<RealisationDTO> findByBudgetId(Long budgetId) {
//        return realisationRepository.findByLigneBudgetaireId(budgetId)
//                .stream()
//                .map(realisationMapper::asDto)
//                .collect(Collectors.toList());
//    }
    @Override
    @Journal(actionType = ActionType.READ_DATES_IMPORTANTE)
    public List<RealisationDTO> findByBudgetId(Long budgetId) {
        // 1. Récupérer toutes les lignes du budget
        List<LigneBudgetaireDTO> lignes = ligneBudgetaireService.findByBudgetId(budgetId);
        // 2. Extraire les IDs des lignes
        List<Long> ligneIds = lignes.stream()
                .map(LigneBudgetaireDTO::getId)
                .collect(Collectors.toList());
        // 3. Chercher les réalisations pour ces ligneIds
        if (ligneIds.isEmpty()) {
            return Collections.emptyList();
        }

        return realisationRepository.findByLigneBudgetaireIdIn(ligneIds)
                .stream()
                .map(realisationMapper::asDto)
                .collect(Collectors.toList());
    }
}