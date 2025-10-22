package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.PeriodiciteEntity;
import com.webgram.dgpsn.entities.QPeriodiciteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PeriodiciteMapper;
import com.webgram.dgpsn.models.PeriodiciteDto;
import com.webgram.dgpsn.repositories.PeriodiciteRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.PeriodiciteExcelDTO;
import com.webgram.dgpsn.services.PeriodiciteService;
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
public class PeriodiciteServiceImpl implements PeriodiciteService {

    private final PeriodiciteRepository periodiciteRepository;
    private final PeriodiciteMapper periodiciteMapper;

    @Override
    public PeriodiciteDto create(PeriodiciteDto dto) {
        var entity = periodiciteMapper.asEntity(dto);
        var savedEntity = periodiciteRepository.save(entity);
        return periodiciteMapper.asDto(savedEntity);
    }

    @Override
    public List<PeriodiciteDto> readByPeriodeId(Long periodeId) {
        List<PeriodiciteEntity> periodicites = periodiciteRepository.findByPeriodeId(periodeId);
        return periodicites.stream()
                .map(periodiciteMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public PeriodiciteDto update(PeriodiciteDto dto) {
        var periodicite = read(dto.getId());
        var entity = periodiciteMapper.asEntity(dto);
        var updatedEntity = periodiciteRepository.save(entity);
        return periodiciteMapper.asDto(updatedEntity);
    }

    @Override
    public PeriodiciteDto read(Long id) {
        var entity = periodiciteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return periodiciteMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        periodiciteRepository.deleteById(id);
    }

    @Override
    public Page<PeriodiciteDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return periodiciteRepository.findAll(booleanBuilder, pageable)
            .map(periodiciteMapper::asDto);
    }

  
    public void exportPeriodicite(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(PeriodiciteExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<PeriodiciteExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<PeriodiciteExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = periodiciteRepository.findAll();
        var dtos = entities.stream().map(periodiciteMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QPeriodiciteEntity.periodiciteEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
