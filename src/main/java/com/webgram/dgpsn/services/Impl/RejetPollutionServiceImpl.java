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
import com.webgram.dgpsn.entities.QRejetPollutiontEntity;
import com.webgram.dgpsn.entities.RejetPollutiontEntity;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RejetPollutionMapper;
import com.webgram.dgpsn.models.RejetPollutionDTO;
import com.webgram.dgpsn.repositories.RejetPollutionRepository;
import com.webgram.dgpsn.services.RejetPollutionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RejetPollutionServiceImpl implements RejetPollutionService {
    private final RejetPollutionRepository rejetPollutionRepository;
    private final RejetPollutionMapper rejetPollutionMapper;
    private final AlerteServiceImpl alerteService;


    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public RejetPollutionDTO create(RejetPollutionDTO rejetPollution) {
        rejetPollution.setTypeStatut(StatutType.EN_COURS);
        // Appeler la méthode de génération d'alerte
        alerteService.generateAlertForRejet(rejetPollution);
        var savedrejetPollution = rejetPollutionRepository
                .save(rejetPollutionMapper.asEntity(rejetPollution));
        log.info("actorProject successfully added {}", savedrejetPollution);

        return rejetPollutionMapper.asDto(savedrejetPollution);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public RejetPollutionDTO update(RejetPollutionDTO rejetPollution) {
        var actorProjectEntity = rejetPollutionMapper.asEntity(rejetPollution);
        var updatedActorProject = rejetPollutionMapper.asDto(rejetPollutionRepository.save(actorProjectEntity));

        log.info("actorProject successfully updated {} ", updatedActorProject.getId());

        return updatedActorProject;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public RejetPollutionDTO read(Long actorProjectId) {
        var rejetPollution = rejetPollutionRepository
                .findById(actorProjectId)
                .orElseThrow(() -> new ResourceNotFoundException("rejetPollution", actorProjectId));

        log.info("reading actorProject id {}", actorProjectId);

        return rejetPollutionMapper.asDto(rejetPollution);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long rejetPollutionId) {
        try {
            rejetPollutionRepository.deleteById(rejetPollutionId);
            log.info("The actorProject id {} is deleted", rejetPollutionId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<RejetPollutionDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return rejetPollutionRepository.findAll(booleanBuilder, pageable)
                .map(rejetPollutionMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRejetPollutiontEntity.rejetPollutiontEntity;
            if (searchParams.containsKey("ph")) {
                Integer phValue = Integer.parseInt(searchParams.get("ph"));
                booleanBuilder.and(qEntity.ph.eq(phValue));
            }

            if (searchParams.containsKey("dco")) {
                Integer dcoValue = Integer.parseInt(searchParams.get("dco"));
                booleanBuilder.and(qEntity.dco.eq(dcoValue));
            }
            if (searchParams.containsKey("dbo")) {
                Integer dbo = Integer.parseInt(searchParams.get("dco"));
                booleanBuilder.and(qEntity.dbo.eq(dbo));
            }
            if (searchParams.containsKey("volume")) {
                Integer volume = Integer.parseInt(searchParams.get("dco"));
                booleanBuilder.and(qEntity.volume.eq(volume));
            }


            if (searchParams.containsKey("datePrelevement")) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datePrelevement"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                booleanBuilder.and(qEntity.datePrelevement.eq(date));
            }
            if (searchParams.containsKey("comment"))
                booleanBuilder.and(qEntity.comment.containsIgnoreCase(searchParams.get("comment")));

            if (searchParams.containsKey("entrepriseId")) {
                Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
            }
        }
    }

    @Override
    public RejetPollutionDTO updateStatut(Long id, StatutType typeStatut) {
        log.info("Mise à jour du statut du avec ID={} à {}", id, typeStatut);
        if (id == null || typeStatut == null) {
            throw new IllegalArgumentException("L'ID et le typeStatut ne peuvent pas être null.");
        }
        RejetPollutiontEntity entity = rejetPollutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(" avec ID=" + id + " non trouvé."));
        entity.setTypeStatut(typeStatut);
        entity = rejetPollutionRepository.save(entity);
        log.info("Statut du  mis à jour avec succès : ID={}", entity.getId());
        return rejetPollutionMapper.asDto(entity);
    }

}
