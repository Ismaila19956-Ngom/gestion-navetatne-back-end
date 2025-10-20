package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QProcedurenominationEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ProcedurenominationExcelDTO;
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
import com.webgram.dgpsn.mappers.ProcedurenominationMapper;

import com.webgram.dgpsn.models.ProcedurenominationDto;
import com.webgram.dgpsn.repositories.ProcedurenominationRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.ProcedurenominationService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProcedurenominationServiceImpl implements ProcedurenominationService {

    private final ProcedurenominationRepository procedurenominationRepository;
    private final ProcedurenominationMapper procedurenominationMapper;

    @Override
    public ProcedurenominationDto create(ProcedurenominationDto dto) {
        var entity = procedurenominationMapper.asEntity(dto);
        var savedEntity = procedurenominationRepository.save(entity);
        return procedurenominationMapper.asDto(savedEntity);
    }

    @Override
    public ProcedurenominationDto update(ProcedurenominationDto dto) {
        var procedurenomination = read(dto.getId());
        var entity = procedurenominationMapper.asEntity(dto);
        var updatedEntity = procedurenominationRepository.save(entity);
        return procedurenominationMapper.asDto(updatedEntity);
    }

    @Override
    public ProcedurenominationDto read(Long id) {
        var entity = procedurenominationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return procedurenominationMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        procedurenominationRepository.deleteById(id);
    }

    @Override
    public Page<ProcedurenominationDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return procedurenominationRepository.findAll(booleanBuilder, pageable)
            .map(procedurenominationMapper::asDto);
    }

  
    public void exportProcedurenomination(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(ProcedurenominationExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ProcedurenominationExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ProcedurenominationExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = procedurenominationRepository.findAll();
        var dtos = entities.stream().map(procedurenominationMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QProcedurenominationEntity.procedurenominationEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
