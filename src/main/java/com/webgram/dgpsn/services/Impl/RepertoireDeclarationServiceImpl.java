package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QRepertoireDeclarationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RepertoireDeclarationMapper;
import com.webgram.dgpsn.models.RepertoireDeclarationDTO;
import com.webgram.dgpsn.repositories.RepertoireDeclarationRepository;
import com.webgram.dgpsn.services.RepertoireDeclarationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepertoireDeclarationServiceImpl implements RepertoireDeclarationService {
    private final RepertoireDeclarationRepository repertoireDeclarationRepository;
    private final RepertoireDeclarationMapper repertoireDeclarationMapper;

    @Override
    public RepertoireDeclarationDTO create(RepertoireDeclarationDTO repertoireDeclarationDTO) {
        var repertoireDeclaration = repertoireDeclarationMapper.asEntity(repertoireDeclarationDTO);
        var savedRepertoireDeclaration = repertoireDeclarationRepository.save(repertoireDeclaration);
        log.info("RepertoireDeclaration saved successfully {}", savedRepertoireDeclaration.getId());
        return repertoireDeclarationMapper.asDto(savedRepertoireDeclaration);
    }

    @Override
    public RepertoireDeclarationDTO update(RepertoireDeclarationDTO repertoireDeclarationDTO) {
        var repertoireDeclaration = repertoireDeclarationMapper.asEntity(repertoireDeclarationDTO);
        var updatedRepertoireDeclaration = repertoireDeclarationRepository.save(repertoireDeclaration);
        log.info("RepertoireDeclaration updated successfully {}", updatedRepertoireDeclaration.getId());
        return repertoireDeclarationMapper.asDto(updatedRepertoireDeclaration);
    }

    @Override
    public RepertoireDeclarationDTO read(Long repertoireDeclarationId) {
        var repertoireDeclaration = repertoireDeclarationRepository.findById(repertoireDeclarationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("RepertoireDeclaration with id [%s] not found", repertoireDeclarationId)));
        return repertoireDeclarationMapper.asDto(repertoireDeclaration);
    }

    @Override
    public void delete(Long repertoireDeclarationId) {
        read(repertoireDeclarationId);
        repertoireDeclarationRepository.deleteById(repertoireDeclarationId);
    }

    @Override
    public Page<RepertoireDeclarationDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return repertoireDeclarationRepository.findAll(booleanBuilder, pageable)
                .map(repertoireDeclarationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRepertoireDeclarationEntity.repertoireDeclarationEntity;
            if (searchParams.containsKey("mission"))
                booleanBuilder.and(qEntity.mission.containsIgnoreCase(searchParams.get("mission")));
            if (searchParams.containsKey("structure"))
                booleanBuilder.and(qEntity.structure.containsIgnoreCase(searchParams.get("structure")));
            if (searchParams.containsKey("telephone"))
                booleanBuilder.and(qEntity.telephone.containsIgnoreCase(searchParams.get("telephone")));
            if (searchParams.containsKey("email"))
                booleanBuilder.and(qEntity.email.containsIgnoreCase(searchParams.get("email")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }
    }
}