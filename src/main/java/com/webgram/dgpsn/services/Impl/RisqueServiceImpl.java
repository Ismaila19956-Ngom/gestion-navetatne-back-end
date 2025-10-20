package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QRisqueEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.RisqueExcelDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.mappers.RisqueMapper;

import com.webgram.dgpsn.models.RisqueDto;
import com.webgram.dgpsn.repositories.RisqueRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.RisqueService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RisqueServiceImpl implements RisqueService {

    private final RisqueRepository risqueRepository;
    private final RisqueMapper risqueMapper;

    @Override
    public RisqueDto create(RisqueDto dto) {
        var entity = risqueMapper.asEntity(dto);
        var savedEntity = risqueRepository.save(entity);
        return risqueMapper.asDto(savedEntity);
    }

    @Override
    public RisqueDto update(RisqueDto dto) {
        var risque = read(dto.getId());
        var entity = risqueMapper.asEntity(dto);
        var updatedEntity = risqueRepository.save(entity);
        return risqueMapper.asDto(updatedEntity);
    }

    @Override
    public RisqueDto read(Long id) {
        var entity = risqueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return risqueMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        risqueRepository.deleteById(id);
    }

    @Override
    public Page<RisqueDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return risqueRepository.findAll(booleanBuilder, pageable)
            .map(risqueMapper::asDto);
    }

  
    public void exportRisque(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(RisqueExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<RisqueExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<RisqueExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = risqueRepository.findAll();
        var dtos = entities.stream().map(risqueMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QRisqueEntity.risqueEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
