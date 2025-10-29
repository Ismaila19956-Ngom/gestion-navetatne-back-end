package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QTacheEntity;
import com.webgram.dgpsn.entities.TacheEntity;
import com.webgram.dgpsn.entities.TacheEntity.StatutTache;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TacheMapper;
import com.webgram.dgpsn.models.TacheDto;
import com.webgram.dgpsn.repositories.TacheRepository;
import com.webgram.dgpsn.services.modelExcel.TacheExcelDTO;
import com.webgram.dgpsn.services.TacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TacheServiceImpl implements TacheService {

    private final TacheRepository tacheRepository;
    private final TacheMapper tacheMapper;

    @Override
    public TacheDto create(TacheDto dto) {
        // Définir le statut par défaut si non fourni
        if (dto.getStatut() == null) {
            dto.setStatut(StatutTache.PLANIFIE);
        }
        var entity = tacheMapper.asEntity(dto);
        var savedEntity = tacheRepository.save(entity);
        return tacheMapper.asDto(savedEntity);
    }

    @Override
    public List<TacheDto> readByActiviteId(Long activiteId) {
        List<TacheEntity> taches = tacheRepository.findByActiviteId(activiteId);
        return taches.stream()
                .map(tacheMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public TacheDto update(TacheDto dto) {
        var tache = read(dto.getId());
        var entity = tacheMapper.asEntity(dto);
        var updatedEntity = tacheRepository.save(entity);
        return tacheMapper.asDto(updatedEntity);
    }

    @Override
    public TacheDto read(Long id) {
        var entity = tacheRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tache not found with id: " + id));
        return tacheMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        if (!tacheRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tache not found with id: " + id);
        }
        tacheRepository.deleteById(id);
    }

    @Override
    public Page<TacheDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return tacheRepository.findAll(booleanBuilder, pageable)
            .map(tacheMapper::asDto);
    }

    @Override
    public TacheDto updateStatut(Long id, StatutTache statut) {
        var entity = tacheRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tache not found with id: " + id));
        
        entity.setStatut(statut);
        var updatedEntity = tacheRepository.save(entity);
        return tacheMapper.asDto(updatedEntity);
    }

    @Override
    public void exportTache(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(TacheExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && 
                           Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<TacheExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<TacheExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = tacheRepository.findAll();
        var dtos = entities.stream().map(tacheMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV for Tache", e);
        }
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QTacheEntity.tacheEntity;
            
            if (searchParams.containsKey("trimestre")) {
                booleanBuilder.and(qEntity.trimestre.eq(Integer.valueOf(searchParams.get("trimestre"))));
            }
            
            if (searchParams.containsKey("mois")) {
                booleanBuilder.and(qEntity.mois.eq(Integer.valueOf(searchParams.get("mois"))));
            }
            
            if (searchParams.containsKey("statut")) {
                try {
                    StatutTache statut = StatutTache.valueOf(searchParams.get("statut"));
                    booleanBuilder.and(qEntity.statut.eq(statut));
                } catch (IllegalArgumentException e) {
                    log.warn("Invalid statut value: {}", searchParams.get("statut"));
                }
            }
            
            if (searchParams.containsKey("commentaire")) {
                booleanBuilder.and(qEntity.commentaire.containsIgnoreCase(searchParams.get("commentaire")));
            }
            
            if (searchParams.containsKey("activiteId")) {
                Long activiteId = Long.parseLong(searchParams.get("activiteId"));
                booleanBuilder.and(qEntity.activite.id.eq(activiteId));
            }

            if (searchParams.containsKey("indicatorId")) {
                Long indicatorId = Long.parseLong(searchParams.get("indicatorId"));
                booleanBuilder.and(qEntity.indicator.id.eq(indicatorId));
            }
        }
    }
}
