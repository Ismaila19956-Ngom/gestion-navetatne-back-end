package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.entities.QActeurDeclarationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ActeurDeclarationMapper;
import com.webgram.dgpsn.models.ActeurDeclarationDTO;
import com.webgram.dgpsn.repositories.ActeurDeclarationRepository;
import com.webgram.dgpsn.services.ActeurDeclarationService;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActeurDeclarationServiceImpl implements ActeurDeclarationService {
    private final ActeurDeclarationRepository acteurDeclarationRepository;
    private final ActeurDeclarationMapper acteurDeclarationMapper;

    @Override
    public ActeurDeclarationDTO create(ActeurDeclarationDTO acteurDeclarationDTO) {
        var acteurDeclaration = acteurDeclarationMapper.asEntity(acteurDeclarationDTO);
        var savedActeurDeclaration = acteurDeclarationRepository.save(acteurDeclaration);
        log.info("ActeurDeclaration saved successfully {}", savedActeurDeclaration.getId());
        return acteurDeclarationMapper.asDto(savedActeurDeclaration);
    }

    @Override
    public ActeurDeclarationDTO update(ActeurDeclarationDTO acteurDeclarationDTO) {
        var acteurDeclaration = acteurDeclarationMapper.asEntity(acteurDeclarationDTO);
        var updatedActeurDeclaration = acteurDeclarationRepository.save(acteurDeclaration);
        log.info("ActeurDeclaration updated successfully {}", updatedActeurDeclaration.getId());
        return acteurDeclarationMapper.asDto(updatedActeurDeclaration);
    }

    @Override
    public ActeurDeclarationDTO read(Long acteurDeclarationId) {
        var acteurDeclaration = acteurDeclarationRepository.findById(acteurDeclarationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("ActeurDeclaration with id [%s] not found", acteurDeclarationId)));
        return acteurDeclarationMapper.asDto(acteurDeclaration);
    }

    @Override
    public void delete(Long acteurDeclarationId) {
        read(acteurDeclarationId);
        acteurDeclarationRepository.deleteById(acteurDeclarationId);
    }

    @Override
    public Page<ActeurDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return acteurDeclarationRepository.findAll(booleanBuilder, pageable)
                .map(acteurDeclarationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QActeurDeclarationEntity.acteurDeclarationEntity;
            if (searchParams.containsKey("zoneCompetence"))
                booleanBuilder.and(qEntity.zoneCompetence.containsIgnoreCase(searchParams.get("zoneCompetence")));
            if (searchParams.containsKey("service"))
                booleanBuilder.and(qEntity.service.containsIgnoreCase(searchParams.get("service")));
            if (searchParams.containsKey("prenom"))
                booleanBuilder.and(qEntity.prenom.containsIgnoreCase(searchParams.get("prenom")));
            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));
            if (searchParams.containsKey("telBureau"))
                booleanBuilder.and(qEntity.telBureau.containsIgnoreCase(searchParams.get("telBureau")));
            if (searchParams.containsKey("telPortable"))
                booleanBuilder.and(qEntity.telPortable.containsIgnoreCase(searchParams.get("telPortable")));
            if (searchParams.containsKey("email"))
                booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }
    }
}