package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.entities.QInformationDeclarationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.InformationDeclarationMapper;
import com.webgram.dgpsn.models.InformationDeclarationDTO;
import com.webgram.dgpsn.repositories.InformationDeclarationRepository;
import com.webgram.dgpsn.services.InformationDeclarationService;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformationDeclarationServiceImpl implements InformationDeclarationService {
    private final InformationDeclarationRepository informationDeclarationRepository;
    private final InformationDeclarationMapper informationDeclarationMapper;

    @Override
    public InformationDeclarationDTO create(InformationDeclarationDTO informationDeclarationDTO) {
        var informationDeclaration = informationDeclarationMapper.asEntity(informationDeclarationDTO);
        var savedInformationDeclaration = informationDeclarationRepository.save(informationDeclaration);
        log.info("InformationDeclaration saved successfully {}", savedInformationDeclaration.getId());
        return informationDeclarationMapper.asDto(savedInformationDeclaration);
    }

    @Override
    public InformationDeclarationDTO update(InformationDeclarationDTO informationDeclarationDTO) {
        var informationDeclaration = informationDeclarationMapper.asEntity(informationDeclarationDTO);
        var updatedInformationDeclaration = informationDeclarationRepository.save(informationDeclaration);
        log.info("InformationDeclaration updated successfully {}", updatedInformationDeclaration.getId());
        return informationDeclarationMapper.asDto(updatedInformationDeclaration);
    }

    @Override
    public InformationDeclarationDTO read(Long informationDeclarationId) {
        var informationDeclaration = informationDeclarationRepository.findById(informationDeclarationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("InformationDeclaration with id [%s] not found", informationDeclarationId)));
        return informationDeclarationMapper.asDto(informationDeclaration);
    }

    @Override
    public void delete(Long informationDeclarationId) {
        read(informationDeclarationId);
        informationDeclarationRepository.deleteById(informationDeclarationId);
    }

    @Override
    public Page<InformationDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return informationDeclarationRepository.findAll(booleanBuilder, pageable)
                .map(informationDeclarationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QInformationDeclarationEntity.informationDeclarationEntity;
            if (searchParams.containsKey("referenceDec"))
                booleanBuilder.and(qEntity.referenceDec.containsIgnoreCase(searchParams.get("referenceDec")));
            if (searchParams.containsKey("traitementInformation"))
                booleanBuilder.and(qEntity.traitementInformation.containsIgnoreCase(searchParams.get("traitementInformation")));
            if (searchParams.containsKey("observation"))
                booleanBuilder.and(qEntity.observation.containsIgnoreCase(searchParams.get("observation")));
            if (searchParams.containsKey("compteRendu"))
                booleanBuilder.and(qEntity.compteRendu.containsIgnoreCase(searchParams.get("compteRendu")));
            if (searchParams.containsKey("evaluation"))
                booleanBuilder.and(qEntity.evaluation.containsIgnoreCase(searchParams.get("evaluation")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }
    }
}