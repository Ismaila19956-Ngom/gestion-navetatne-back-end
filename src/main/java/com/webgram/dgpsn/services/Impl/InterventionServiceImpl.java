package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QInterventionEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.InterventionMapper;
import com.webgram.dgpsn.models.InterventionDTO;
import com.webgram.dgpsn.repositories.InterventionRepository;
import com.webgram.dgpsn.services.InterventionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterventionServiceImpl implements InterventionService {
    private final InterventionRepository interventionRepository;
    private final InterventionMapper interventionMapper;

    @Override
    public InterventionDTO create(InterventionDTO interventionDTO) {
        var intervention = interventionMapper.asEntity(interventionDTO);
        var savedIntervention = interventionRepository.save(intervention);
        log.info("Intervention saved successfully {}", savedIntervention.getId());
        return interventionMapper.asDto(savedIntervention);
    }

    @Override
    public InterventionDTO update(InterventionDTO interventionDTO) {
        var intervention = interventionMapper.asEntity(interventionDTO);
        var updatedIntervention = interventionRepository.save(intervention);
        log.info("Intervention updated successfully {}", updatedIntervention.getId());
        return interventionMapper.asDto(updatedIntervention);
    }

    @Override
    public InterventionDTO read(Long interventionId) {
        var intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Intervention with id [%s] not found", interventionId)));
        return interventionMapper.asDto(intervention);
    }

    @Override
    public void delete(Long interventionId) {
        read(interventionId);
        interventionRepository.deleteById(interventionId);
    }

    @Override
    public Page<InterventionDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return interventionRepository.findAll(booleanBuilder, pageable)
                .map(interventionMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QInterventionEntity.interventionEntity;
            if (searchParams.containsKey("reference"))
                booleanBuilder.and(qEntity.reference.containsIgnoreCase(searchParams.get("reference")));
            if (searchParams.containsKey("date"))
                booleanBuilder.and(qEntity.date.eq(LocalDate.parse(searchParams.get("date"))));
            if (searchParams.containsKey("heure"))
                booleanBuilder.and(qEntity.heure.eq(LocalTime.parse(searchParams.get("heure"))));
            if (searchParams.containsKey("contactDeclarant"))
                booleanBuilder.and(qEntity.contactDeclarant.containsIgnoreCase(searchParams.get("contactDeclarant")));
            if (searchParams.containsKey("typeEvenement"))
                booleanBuilder.and(qEntity.typeEvenement.containsIgnoreCase(searchParams.get("typeEvenement")));
            if (searchParams.containsKey("localisation"))
                booleanBuilder.and(qEntity.localisation.containsIgnoreCase(searchParams.get("localisation")));
            if (searchParams.containsKey("etatLieux"))
                booleanBuilder.and(qEntity.etatLieux.containsIgnoreCase(searchParams.get("etatLieux")));
            if (searchParams.containsKey("niveauIntervention"))
                booleanBuilder.and(qEntity.niveauIntervention.containsIgnoreCase(searchParams.get("niveauIntervention")));
            if (searchParams.containsKey("actionsGestionLocale"))
                booleanBuilder.and(qEntity.actionsGestionLocale.containsIgnoreCase(searchParams.get("actionsGestionLocale")));
            if (searchParams.containsKey("declenchementPlanUrgence"))
                booleanBuilder.and(qEntity.declenchementPlanUrgence.containsIgnoreCase(searchParams.get("declenchementPlanUrgence")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }

    }
}
