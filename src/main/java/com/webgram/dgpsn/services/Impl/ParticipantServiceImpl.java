package com.webgram.dgpsn.services.Impl;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QParticipantEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeParticipant;
import com.webgram.dgpsn.mappers.ParticipantMapper;
import com.webgram.dgpsn.models.ParticipantDTO;
import com.webgram.dgpsn.repositories.ParticipantRepository;
import com.webgram.dgpsn.services.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Override
    public ParticipantDTO create(ParticipantDTO dto) {
        log.info("Participant date {}", dto.getDateInscription());
        var entity = participantMapper.asEntity(dto);
        var entitySave = participantRepository.save(entity);
        log.info("Participant saved successfully {}", entitySave.getId());
        return participantMapper.asDto(entitySave);
    }

    @Override
    public ParticipantDTO update(ParticipantDTO dto) {
        read(dto.getId()); // Check for existence
        var entityUpdate = participantMapper.asEntity(dto);
        var updatedEntity = participantRepository.save(entityUpdate);
        log.info("Participant updated successfully {}", updatedEntity.getId());
        return participantMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        try {
            participantRepository.deleteById(id);
            log.info("The participant id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public ParticipantDTO read(Long id) {
        return participantRepository.findById(id)
                .map(participantMapper::asDto)
                .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
    }

    @Override
    public Page<ParticipantDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return participantRepository.findAll(booleanBuilder, pageable)
                .map(participantMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QParticipantEntity.participantEntity;
            if (searchParams.get("dateInscription") != null) {
                 LocalDate dateInscription = LocalDate.parse(searchParams.get("dateInscription"));
                booleanBuilder.and(qEntity.dateInscription.eq(dateInscription));
            }

            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.eq(Statut.valueOf(searchParams.get("statut"))));

            if (searchParams.containsKey("noteEvaluation"))
                booleanBuilder.and(qEntity.noteEvaluation.eq(Double.valueOf(searchParams.get("noteEvaluation"))));

            if (searchParams.containsKey("commentaires"))
                booleanBuilder.and(qEntity.commentaires.containsIgnoreCase(searchParams.get("commentaires")));

            if (searchParams.containsKey("agentId"))
                booleanBuilder.and(qEntity.agent.id.eq(Long.valueOf(searchParams.get("agentId"))));
            if (searchParams.containsKey("formationId"))
                booleanBuilder.and(qEntity.formation.id.eq(Long.valueOf(searchParams.get("formationId"))));

            if (searchParams.containsKey("typeParticipant"))
                booleanBuilder.and(qEntity.typeParticipant.eq(TypeParticipant.valueOf(searchParams.get("typeParticipant"))));


        }
    }
}