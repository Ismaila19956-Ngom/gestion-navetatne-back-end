package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.EtatFinancierEntity;
import com.webgram.dgpsn.entities.QEtatFinancierEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EtatFinancierMapper;
import com.webgram.dgpsn.models.EtatFinancierDto;
import com.webgram.dgpsn.repositories.EtatFinancierRepository;
import com.webgram.dgpsn.services.EtatFinancierService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EtatFinancierServiceImpl implements EtatFinancierService {

    private final EtatFinancierRepository etatFinancierRepository;
    private final EtatFinancierMapper etatFinancierMapper;

    @Override
    public EtatFinancierDto create(EtatFinancierDto dto) {
        var entity = etatFinancierMapper.asEntity(dto);
        var savedEntity = etatFinancierRepository.save(entity);
        return etatFinancierMapper.asDto(savedEntity);
    }

    @Override
    public List<EtatFinancierDto> readByEntrepriseId(Long entrepriseId) {
        List<EtatFinancierEntity> depenses = etatFinancierRepository.findByEntrepriseId(entrepriseId);
        return depenses.stream()
                .map(etatFinancierMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public EtatFinancierDto update(EtatFinancierDto dto) {
        var etatFinancier = read(dto.getId());
        var entity = etatFinancierMapper.asEntity(dto);
        var updatedEntity = etatFinancierRepository.save(entity);
        return etatFinancierMapper.asDto(updatedEntity);
    }

    @Override
    public EtatFinancierDto read(Long id) {
        var entity = etatFinancierRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return etatFinancierMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        etatFinancierRepository.deleteById(id);
    }

    @Override
    public Page<EtatFinancierDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return etatFinancierRepository.findAll(booleanBuilder, pageable)
            .map(etatFinancierMapper::asDto);
    }

  

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
              var qEntity = QEtatFinancierEntity.etatFinancierEntity;
              if (searchParams.containsKey("annee"))
                  booleanBuilder.and(qEntity.annee.eq(Integer.getInteger(searchParams.get("annee"))));
              if (searchParams.containsKey("entrepriseId"))
                  booleanBuilder.and(qEntity.entreprise.id.eq(Long.valueOf(searchParams.get("entrepriseId"))));
          }
   }
}
