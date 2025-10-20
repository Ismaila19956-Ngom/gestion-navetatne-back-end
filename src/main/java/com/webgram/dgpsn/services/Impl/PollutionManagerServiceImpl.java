package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.PollutiontManagerEntity;
import com.webgram.dgpsn.entities.QPollutiontManagerEntity;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PollutionManagerMapper;
import com.webgram.dgpsn.models.PollutionManagerDTO;
import com.webgram.dgpsn.repositories.PollutionManagerRepository;
import com.webgram.dgpsn.services.PollutionManagerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PollutionManagerServiceImpl implements PollutionManagerService {
    private final PollutionManagerRepository pollutionManagerRepository;
    private final PollutionManagerMapper pollutionManagerMapper;
    private final AlerteServiceImpl alerteService;


    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public PollutionManagerDTO create(PollutionManagerDTO pollutionManagerDTO) {
        pollutionManagerDTO.setTypeStatut(StatutType.EN_COURS);
        // Appeler la méthode de génération d'alerte avec le DTO
        alerteService.generateAlertForPollution(pollutionManagerDTO);
        var saved = pollutionManagerRepository
                .save(pollutionManagerMapper.asEntity(pollutionManagerDTO));
        log.info("actManager successfully added {}", saved);

        return pollutionManagerMapper.asDto(saved);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public PollutionManagerDTO update(PollutionManagerDTO pollutionManagerDTO) {
        var milieux = pollutionManagerMapper.asEntity(pollutionManagerDTO);
        var updatedActorProject = pollutionManagerMapper.asDto(pollutionManagerRepository.save(milieux));

        log.info("actManager successfully updated {} ", updatedActorProject.getId());

        return updatedActorProject;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public PollutionManagerDTO read(Long pollutionManagerId) {
        var milieuPollution = pollutionManagerRepository
                .findById(pollutionManagerId)
                .orElseThrow(() -> new ResourceNotFoundException("rejetPollution", pollutionManagerId));

        log.info("reading actManager id {}", milieuPollution.getId());

        return pollutionManagerMapper.asDto(milieuPollution);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long pollutionManagerId) {
        try {
            pollutionManagerRepository.deleteById(pollutionManagerId);
            log.info("The actManager id {} is deleted", pollutionManagerId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PollutionManagerDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return pollutionManagerRepository.findAll(booleanBuilder, pageable)
                .map(pollutionManagerMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (searchParams == null || searchParams.isEmpty()) {
            log.debug("No search parameters provided; returning all results.");
            return;
        }

        var qEntity = QPollutiontManagerEntity.pollutiontManagerEntity;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Comment filter
        if (searchParams.containsKey("comment")) {
            booleanBuilder.and(qEntity.comment.containsIgnoreCase(searchParams.get("comment")));
        }

        // Date filters
        try {
            if (searchParams.containsKey("dateTransport")) {
                LocalDate dateTransport = LocalDate.parse(searchParams.get("dateTransport"), formatter);
                booleanBuilder.and(qEntity.dateTransport.eq(java.sql.Date.valueOf(dateTransport)));
            }
            if (searchParams.containsKey("dateMouvement")) {
                LocalDate dateMouvement = LocalDate.parse(searchParams.get("dateMouvement"), formatter);
                booleanBuilder.and(qEntity.dateMouvement.eq(java.sql.Date.valueOf(dateMouvement)));
            }
        } catch (Exception e) {
            log.warn("Invalid date format in searchParams: {}", e.getMessage());
        }

        // Numeric filters
        try {
            if (searchParams.containsKey("quantite")) {
                booleanBuilder.and(qEntity.quantite.eq(Double.valueOf(searchParams.get("quantite"))));
            }
            if (searchParams.containsKey("siteEliminationId")) {
                Long siteEliminationId = Long.parseLong(searchParams.get("siteEliminationId"));
                booleanBuilder.and(qEntity.siteElimination.id.eq(siteEliminationId));
            }
            if (searchParams.containsKey("plastiqueId")) {
                Long plastiqueId = Long.parseLong(searchParams.get("plastiqueId"));
                booleanBuilder.and(qEntity.plastique.id.eq(plastiqueId));
            }
            if (searchParams.containsKey("produitId")) {
                Long produitId = Long.parseLong(searchParams.get("produitId"));
                booleanBuilder.and(qEntity.produit.id.eq(produitId));
            }
            if (searchParams.containsKey("methodeEliminationId")) {
                Long methodeEliminationId = Long.parseLong(searchParams.get("methodeEliminationId"));
                booleanBuilder.and(qEntity.methodeElimination.id.eq(methodeEliminationId));
            }
            if (searchParams.containsKey("destinationId")) {
                Long destinationId = Long.parseLong(searchParams.get("destinationId"));
                booleanBuilder.and(qEntity.destination.id.eq(destinationId));
            }
            if (searchParams.containsKey("dechetId")) {
                Long dechetId = Long.parseLong(searchParams.get("dechetId"));
                booleanBuilder.and(qEntity.dechet.id.eq(dechetId));
            }
            if (searchParams.containsKey("origineId")) {
                Long origineId = Long.parseLong(searchParams.get("origineId"));
                booleanBuilder.and(qEntity.origine.id.eq(origineId));
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid numeric value in searchParams: {}", e.getMessage());
        }

        // String filters
        if (searchParams.containsKey("autorisation")) {
            booleanBuilder.and(qEntity.autorisation.containsIgnoreCase(searchParams.get("autorisation")));
        }
        if (searchParams.containsKey("typePollution")) {
            String type = searchParams.get("typePollution");
            if (!type.isEmpty()) {
                booleanBuilder.and(qEntity.typePollution.stringValue().lower().containsIgnoreCase(type.toLowerCase()));
            }
        }
    }

    @Override
    public PollutionManagerDTO updateStatut(Long id, StatutType typeStatut) {
        log.info("Mise à jour avec ID={} à {}", id, typeStatut);
        if (id == null || typeStatut == null) {
            throw new IllegalArgumentException("L'ID et le typeStatut ne peuvent pas être null.");
        }
        PollutiontManagerEntity entity = pollutionManagerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" avec ID=" + id + " non trouvé."));
        entity.setTypeStatut(typeStatut);
        entity = pollutionManagerRepository.save(entity);
        log.info("Statut  mis à jour avec succès : ID={}", entity.getId());
        return pollutionManagerMapper.asDto(entity);
    }

}
