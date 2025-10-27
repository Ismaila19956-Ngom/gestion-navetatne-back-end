package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QTypereunionEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TypereunionMapper;
import com.webgram.dgpsn.models.TypereunionDto;
import com.webgram.dgpsn.repositories.TypereunionRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.TypereunionExcelDTO;
import com.webgram.dgpsn.services.TypereunionService;
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
public class TypereunionServiceImpl implements TypereunionService {

    private final TypereunionRepository typereunionRepository;
    private final TypereunionMapper typereunionMapper;

    @Override
    public TypereunionDto create(TypereunionDto dto) {
        var entity = typereunionMapper.asEntity(dto);
        var savedEntity = typereunionRepository.save(entity);
        return typereunionMapper.asDto(savedEntity);
    }

    @Override
    public TypereunionDto update(TypereunionDto dto) {
        var typereunion = read(dto.getId());
        var entity = typereunionMapper.asEntity(dto);
        var updatedEntity = typereunionRepository.save(entity);
        return typereunionMapper.asDto(updatedEntity);
    }

    @Override
    public TypereunionDto read(Long id) {
        var entity = typereunionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return typereunionMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        typereunionRepository.deleteById(id);
    }

    @Override
    public Page<TypereunionDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return typereunionRepository.findAll(booleanBuilder, pageable)
            .map(typereunionMapper::asDto);
    }

  
    public void exportTypereunion(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(TypereunionExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<TypereunionExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<TypereunionExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = typereunionRepository.findAll();
        var dtos = entities.stream().map(typereunionMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QTypereunionEntity.typereunionEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
