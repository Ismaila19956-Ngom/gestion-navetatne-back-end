package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.entities.QDeclarationEntity;
import com.webgram.dgpsn.entities.enums.Sexe;
import com.webgram.dgpsn.entities.enums.StatutDeclaration;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.DeclarationMapper;
import com.webgram.dgpsn.models.DeclarationDTO;
import com.webgram.dgpsn.repositories.DeclarationRepository;
import com.webgram.dgpsn.services.DeclarationService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeclarationServiceImpl implements DeclarationService {
    private final DeclarationRepository declarationRepository;
    private final DeclarationMapper declarationMapper;

    @Override
    public DeclarationDTO create(DeclarationDTO declarationDTO) {
        var declaration = declarationMapper.asEntity(declarationDTO);
        var savedDeclaration = declarationRepository.save(declaration);
        log.info("Declaration saved successfully {}", savedDeclaration.getId());
        return declarationMapper.asDto(savedDeclaration);
    }

    @Override
    public DeclarationDTO update(DeclarationDTO declarationDTO) {
        var declaration = declarationMapper.asEntity(declarationDTO);
        var updatedDeclaration = declarationRepository.save(declaration);
        log.info("Declaration updated successfully {}", updatedDeclaration.getId());
        return declarationMapper.asDto(updatedDeclaration);
    }

    @Override
    public DeclarationDTO read(Long declarationId) {
        var declaration = declarationRepository.findById(declarationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Declaration with id [%s] not found", declarationId)));
        return declarationMapper.asDto(declaration);
    }

    @Override
    public void delete(Long declarationId) {
        read(declarationId);
        declarationRepository.deleteById(declarationId);
    }

    @Override
    public Page<DeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return declarationRepository.findAll(booleanBuilder, pageable)
                .map(declarationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QDeclarationEntity.declarationEntity;
            if (searchParams.containsKey("reference"))
                booleanBuilder.and(qEntity.reference.containsIgnoreCase(searchParams.get("reference")));
            if (searchParams.containsKey("dateReception"))
                booleanBuilder.and(qEntity.dateReception.eq(LocalDate.parse(searchParams.get("dateReception"))));
            if (searchParams.containsKey("prenom"))
                booleanBuilder.and(qEntity.prenom.containsIgnoreCase(searchParams.get("prenom")));
            if (searchParams.containsKey("nom"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("nom")));
            if (searchParams.containsKey("age"))
                booleanBuilder.and(qEntity.nom.containsIgnoreCase(searchParams.get("age")));
            if (searchParams.containsKey("telephone"))
                booleanBuilder.and(qEntity.telephone.containsIgnoreCase(searchParams.get("telephone")));
            if (searchParams.containsKey("localisation"))
                booleanBuilder.and(qEntity.localisation.containsIgnoreCase(searchParams.get("localisation")));
            if (searchParams.containsKey("description"))
                booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
            if (searchParams.containsKey("operateur"))
                booleanBuilder.and(qEntity.operateur.containsIgnoreCase(searchParams.get("operateur")));
            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.eq(StatutDeclaration.valueOf(searchParams.get("statut"))));
            if (searchParams.containsKey("sexe"))
                booleanBuilder.and(qEntity.sexe.eq(Sexe.valueOf(searchParams.get("sexe"))));

        }

    }

}
