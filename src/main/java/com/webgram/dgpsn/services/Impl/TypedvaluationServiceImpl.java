package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QTypedvaluationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TypedvaluationMapper;
import com.webgram.dgpsn.models.TypedvaluationDto;
import com.webgram.dgpsn.repositories.TypedvaluationRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.TypedvaluationExcelDTO;
import com.webgram.dgpsn.services.TypedvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TypedvaluationServiceImpl implements TypedvaluationService {

    private final TypedvaluationRepository typedvaluationRepository;
    private final TypedvaluationMapper typedvaluationMapper;

    @Override
    public TypedvaluationDto create(TypedvaluationDto dto) {
        var entity = typedvaluationMapper.asEntity(dto);
        var savedEntity = typedvaluationRepository.save(entity);
        return typedvaluationMapper.asDto(savedEntity);
    }

    @Override
    public TypedvaluationDto update(TypedvaluationDto dto) {
        var typedvaluation = read(dto.getId());
        var entity = typedvaluationMapper.asEntity(dto);
        var updatedEntity = typedvaluationRepository.save(entity);
        return typedvaluationMapper.asDto(updatedEntity);
    }

    @Override
    public TypedvaluationDto read(Long id) {
        var entity = typedvaluationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return typedvaluationMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        typedvaluationRepository.deleteById(id);
    }

    @Override
    public Page<TypedvaluationDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return typedvaluationRepository.findAll(booleanBuilder, pageable)
            .map(typedvaluationMapper::asDto);
    }

  
    public void exportTypedvaluation(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(TypedvaluationExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<TypedvaluationExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<TypedvaluationExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = typedvaluationRepository.findAll();
        var dtos = entities.stream().map(typedvaluationMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QTypedvaluationEntity.typedvaluationEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
