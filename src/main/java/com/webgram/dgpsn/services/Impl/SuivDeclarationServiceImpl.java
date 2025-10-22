package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QSuivDeclarationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SuivDeclarationMapper;
import com.webgram.dgpsn.models.SuivDeclarationDTO;
import com.webgram.dgpsn.repositories.SuivDeclarationRepository;
import com.webgram.dgpsn.services.SuivDeclarationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuivDeclarationServiceImpl implements SuivDeclarationService {
    private final SuivDeclarationRepository suivDeclarationRepository;
    private final SuivDeclarationMapper suivDeclarationMapper;

    @Override
    public SuivDeclarationDTO create(SuivDeclarationDTO suivDeclarationDTO) {
        var suivDeclaration = suivDeclarationMapper.asEntity(suivDeclarationDTO);
        var savedSuivDeclaration = suivDeclarationRepository.save(suivDeclaration);
        log.info("SuivDeclaration saved successfully {}", savedSuivDeclaration.getId());
        return suivDeclarationMapper.asDto(savedSuivDeclaration);
    }

    @Override
    public SuivDeclarationDTO update(SuivDeclarationDTO suivDeclarationDTO) {
        var suivDeclaration = suivDeclarationMapper.asEntity(suivDeclarationDTO);
        var updatedSuivDeclaration = suivDeclarationRepository.save(suivDeclaration);
        log.info("SuivDeclaration updated successfully {}", updatedSuivDeclaration.getId());
        return suivDeclarationMapper.asDto(updatedSuivDeclaration);
    }

    @Override
    public SuivDeclarationDTO read(Long suivDeclarationId) {
        var suivDeclaration = suivDeclarationRepository.findById(suivDeclarationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("SuivDeclaration with id [%s] not found", suivDeclarationId)));
        return suivDeclarationMapper.asDto(suivDeclaration);
    }

    @Override
    public void delete(Long suivDeclarationId) {
        read(suivDeclarationId);
        suivDeclarationRepository.deleteById(suivDeclarationId);
    }

    @Override
    public Page<SuivDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return suivDeclarationRepository.findAll(booleanBuilder, pageable)
                .map(suivDeclarationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QSuivDeclarationEntity.suivDeclarationEntity;
            if (searchParams.containsKey("ref"))
                booleanBuilder.and(qEntity.ref.containsIgnoreCase(searchParams.get("ref")));
            if (searchParams.containsKey("date"))
                booleanBuilder.and(qEntity.date.eq(LocalDate.parse(searchParams.get("date"))));
            if (searchParams.containsKey("plaignant"))
                booleanBuilder.and(qEntity.plaignant.containsIgnoreCase(searchParams.get("plaignant")));
            if (searchParams.containsKey("contactPlaignant"))
                booleanBuilder.and(qEntity.contactPlaignant.containsIgnoreCase(searchParams.get("contactPlaignant")));
            if (searchParams.containsKey("evenement"))
                booleanBuilder.and(qEntity.evenement.containsIgnoreCase(searchParams.get("evenement")));
            if (searchParams.containsKey("localisation"))
                booleanBuilder.and(qEntity.localisation.containsIgnoreCase(searchParams.get("localisation")));
            if (searchParams.containsKey("miseEnCause"))
                booleanBuilder.and(qEntity.miseEnCause.containsIgnoreCase(searchParams.get("miseEnCause")));
            if (searchParams.containsKey("contactMiseEnCause"))
                booleanBuilder.and(qEntity.contactMiseEnCause.containsIgnoreCase(searchParams.get("contactMiseEnCause")));
            if (searchParams.containsKey("constat"))
                booleanBuilder.and(qEntity.constat.containsIgnoreCase(searchParams.get("constat")));
            if (searchParams.containsKey("dateConstat"))
                booleanBuilder.and(qEntity.dateConstat.eq(LocalDate.parse(searchParams.get("dateConstat"))));
            if (searchParams.containsKey("mesuresPrises"))
                booleanBuilder.and(qEntity.mesuresPrises.containsIgnoreCase(searchParams.get("mesuresPrises")));
            if (searchParams.containsKey("observations"))
                booleanBuilder.and(qEntity.observations.containsIgnoreCase(searchParams.get("observations")));
            if (searchParams.containsKey("pv"))
                booleanBuilder.and(qEntity.pv.containsIgnoreCase(searchParams.get("pv")));
            if (searchParams.containsKey("autresPj"))
                booleanBuilder.and(qEntity.autresPj.containsIgnoreCase(searchParams.get("autresPj")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }
    }
}