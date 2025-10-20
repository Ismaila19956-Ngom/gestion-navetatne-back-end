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
import com.webgram.dgpsn.entities.MilieuxPollutiontEntity;
import com.webgram.dgpsn.entities.QMilieuxPollutiontEntity;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.MilieuxPollutionMapper;
import com.webgram.dgpsn.models.MilieuxPollutionDTO;
import com.webgram.dgpsn.repositories.MilieuxPollutionRepository;
import com.webgram.dgpsn.services.MilieuxPollutionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MilieuxPollutionServiceImpl implements MilieuxPollutionService {
    private final MilieuxPollutionRepository milieuxPollutionRepository;
    private final MilieuxPollutionMapper milieuxPollutionMapper;
    private final AlerteServiceImpl alerteService;


    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public MilieuxPollutionDTO create(MilieuxPollutionDTO milieuxPollutionDTO) {
        milieuxPollutionDTO.setTypeStatut(StatutType.EN_COURS);
        // Appeler la méthode de génération d'alerte
        alerteService.generateAlertForMilieux(milieuxPollutionDTO);
         var saved = milieuxPollutionRepository
                 .save(milieuxPollutionMapper.asEntity(milieuxPollutionDTO));
        log.info("actorProject successfully added {}", saved);

        return milieuxPollutionMapper.asDto(saved);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public MilieuxPollutionDTO update(MilieuxPollutionDTO milieuxPollutionDTO) {
        var milieux = milieuxPollutionMapper.asEntity(milieuxPollutionDTO);
        var updatedActorProject = milieuxPollutionMapper.asDto(milieuxPollutionRepository.save(milieux));

        log.info("actorProject successfully updated {} ", updatedActorProject.getId());

        return updatedActorProject;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public MilieuxPollutionDTO read(Long milieuxId) {
        var milieuPollution = milieuxPollutionRepository
                .findById(milieuxId)
                .orElseThrow(()-> new ResourceNotFoundException("rejetPollution", milieuxId));

        log.info("reading actorProject id {}", milieuPollution.getId());

        return milieuxPollutionMapper.asDto(milieuPollution);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long milieuxId) {
        try {
            milieuxPollutionRepository.deleteById(milieuxId);
            log.info("The actorProject id {} is deleted", milieuxId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<MilieuxPollutionDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return milieuxPollutionRepository.findAll(booleanBuilder, pageable)
                .map(milieuxPollutionMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QMilieuxPollutiontEntity.milieuxPollutiontEntity;
            if (searchParams.containsKey("turbidite")){
                Integer turbidite = Integer.parseInt(searchParams.get("turbidite"));
                 booleanBuilder.and(qEntity.turbidite.eq(turbidite));}
            if (searchParams.containsKey("oxygene")){
                Integer oxygene = Integer.parseInt(searchParams.get("oxygene"));
            booleanBuilder.and(qEntity.oxygene.eq(oxygene));}
            if (searchParams.containsKey("nitrate")){
                Integer nitrate = Integer.parseInt(searchParams.get("nitrate"));
                booleanBuilder.and(qEntity.oxygene.eq(nitrate));}

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

            if (searchParams.containsKey("pointPrelevementId")) {
                Long pointPrelevementId = Long.parseLong(searchParams.get("pointPrelevementId"));
                booleanBuilder.and(qEntity.pointPrelevement.id.eq(pointPrelevementId));
            }
            if (searchParams.containsKey("condMeteoId")) {
                Long condMeteoId = Long.parseLong(searchParams.get("condMeteoId"));
                booleanBuilder.and(qEntity.condMeteo.id.eq(condMeteoId));
            }
        }
    }

    @Override
    public MilieuxPollutionDTO updateStatut(Long id, StatutType typeStatut) {
        log.info("Mise à jour du statut du SoldeJournalier avec ID={} à {}", id, typeStatut);
        if (id == null || typeStatut == null) {
            throw new IllegalArgumentException("L'ID et le typeStatut ne peuvent pas être null.");
        }
        MilieuxPollutiontEntity entity = milieuxPollutionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SoldeJournalier avec ID=" + id + " non trouvé."));
        entity.setTypeStatut(typeStatut);
        entity = milieuxPollutionRepository.save(entity);
        log.info("Statut du SoldeJournalier mis à jour avec succès : ID={}", entity.getId());
        return milieuxPollutionMapper.asDto(entity);
    }

}
