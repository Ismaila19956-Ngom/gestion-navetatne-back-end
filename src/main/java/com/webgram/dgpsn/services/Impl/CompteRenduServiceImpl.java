package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.entities.QCompteRenduEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CompteRenduMapper;
import com.webgram.dgpsn.models.CompteRenduDTO;
import com.webgram.dgpsn.repositories.CompteRenduRepository;
import com.webgram.dgpsn.services.CompteRenduService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompteRenduServiceImpl implements CompteRenduService {
    private final CompteRenduRepository compteRenduRepository;
    private final CompteRenduMapper compteRenduMapper;

    @Override
    public CompteRenduDTO create(CompteRenduDTO compteRenduDTO) {
        var compteRendu = compteRenduMapper.asEntity(compteRenduDTO);
        var savedCompteRendu = compteRenduRepository.save(compteRendu);
        log.info("CompteRendu saved successfully {}", savedCompteRendu.getId());
        return compteRenduMapper.asDto(savedCompteRendu);
    }

    @Override
    public CompteRenduDTO update(CompteRenduDTO compteRenduDTO) {
        var compteRendu = compteRenduMapper.asEntity(compteRenduDTO);
        var updatedCompteRendu = compteRenduRepository.save(compteRendu);
        log.info("CompteRendu updated successfully {}", updatedCompteRendu.getId());
        return compteRenduMapper.asDto(updatedCompteRendu);
    }

    @Override
    public CompteRenduDTO read(Long compteRenduId) {
        var compteRendu = compteRenduRepository.findById(compteRenduId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("CompteRendu with id [%s] not found", compteRenduId)));
        return compteRenduMapper.asDto(compteRendu);
    }

    @Override
    public void delete(Long compteRenduId) {
        read(compteRenduId);
        compteRenduRepository.deleteById(compteRenduId);
    }

    @Override
    public Page<CompteRenduDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return compteRenduRepository.findAll(booleanBuilder, pageable)
                .map(compteRenduMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCompteRenduEntity.compteRenduEntity;
            if (searchParams.containsKey("date"))
                booleanBuilder.and(qEntity.date.eq(LocalDate.parse(searchParams.get("date"))));
            if (searchParams.containsKey("contenu"))
                booleanBuilder.and(qEntity.contenu.containsIgnoreCase(searchParams.get("contenu")));
            if (searchParams.containsKey("declarationId"))
                booleanBuilder.and(qEntity.declaration.id.eq(Long.parseLong(searchParams.get("declarationId"))));
        }

    }

}
