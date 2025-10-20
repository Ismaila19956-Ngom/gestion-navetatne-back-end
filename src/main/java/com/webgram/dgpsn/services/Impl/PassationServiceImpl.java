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
import com.webgram.dgpsn.entities.QPassationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PassationMapper;
import com.webgram.dgpsn.models.PassationDTO;
import com.webgram.dgpsn.repositories.PassationRepository;
import com.webgram.dgpsn.services.PassationService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PassationServiceImpl implements PassationService {
    private final PassationRepository passationRepository;
    private final PassationMapper passationMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public PassationDTO create(PassationDTO passationDTO) {
        var savedPassation = passationRepository.save(passationMapper.asEntity(passationDTO));
        log.info("Passation successfully added {}", savedPassation);
        return passationMapper.asDto(savedPassation);
    }


    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public PassationDTO update(PassationDTO passationDTO) {
        var passationSaved = passationMapper.asEntity(passationDTO);
        var updatedPassation = passationMapper.asDto(passationRepository.save(passationSaved));
        log.info("Passation successfully updated {}", updatedPassation.getId());
        return updatedPassation;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public PassationDTO read(Long passationId) {
        var passation = passationRepository
                .findById(passationId)
                .orElseThrow(() -> new ResourceNotFoundException("Passation", passationId));
        log.info("Reading passation id {}", passationId);
        return passationMapper.asDto(passation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long passationId) {
        try {
            passationRepository.deleteById(passationId);
            log.info("The passation id {} is deleted", passationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PassationDTO> readAllPassations(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return passationRepository.findAll(booleanBuilder, pageable)
                .map(passationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QPassationEntity.passationEntity;
            if (searchParams.containsKey("reference"))
                booleanBuilder.and(qEntity.reference.eq(searchParams.get("reference")));
            if (searchParams.containsKey("realisation"))
                booleanBuilder.and(qEntity.realisation.eq(searchParams.get("realisation")));
            if (searchParams.containsKey("sourceFinancementId"))
                booleanBuilder.and(qEntity.sourceFinancement.id.eq(Long.valueOf(searchParams.get("sourceFinancementId"))));
            if (searchParams.containsKey("typeMarcheId"))
                booleanBuilder.and(qEntity.typeMarche.id.eq(Long.valueOf(searchParams.get("typeMarcheId"))));
            if (searchParams.containsKey("modePassationId"))
                booleanBuilder.and(qEntity.modePassation.id.eq(Long.valueOf(searchParams.get("modePassationId"))));
            if (searchParams.containsKey("montant"))
                booleanBuilder.and(qEntity.montant.eq(Double.valueOf(searchParams.get("montant"))));
            if (searchParams.containsKey("dateLancement"))
                booleanBuilder.and(qEntity.dateLancement.eq(LocalDate.parse(searchParams.get("dateLancement"))));
            if (searchParams.containsKey("dateAttribution"))
                booleanBuilder.and(qEntity.dateAttribution.eq(LocalDate.parse(searchParams.get("dateAttribution"))));
            if (searchParams.containsKey("dateDemarrage"))
                booleanBuilder.and(qEntity.dateDemarrage.eq(LocalDate.parse(searchParams.get("dateDemarrage"))));
            if (searchParams.containsKey("dateAchevement"))
                booleanBuilder.and(qEntity.dateAchevement.eq(LocalDate.parse(searchParams.get("dateAchevement"))));
        }
    }
}