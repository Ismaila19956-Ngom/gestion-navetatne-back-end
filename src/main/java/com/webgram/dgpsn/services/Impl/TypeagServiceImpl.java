package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QTypeagEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.TypeagMapper;
import com.webgram.dgpsn.models.TypeagDto;
import com.webgram.dgpsn.repositories.TypeagRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.TypeagExcelDTO;
import com.webgram.dgpsn.services.TypeagService;
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
public class TypeagServiceImpl implements TypeagService {

    private final TypeagRepository typeagRepository;
    private final TypeagMapper typeagMapper;

    @Override
    public TypeagDto create(TypeagDto dto) {
        var entity = typeagMapper.asEntity(dto);
        var savedEntity = typeagRepository.save(entity);
        return typeagMapper.asDto(savedEntity);
    }

    @Override
    public TypeagDto update(TypeagDto dto) {
        var typeag = read(dto.getId());
        var entity = typeagMapper.asEntity(dto);
        var updatedEntity = typeagRepository.save(entity);
        return typeagMapper.asDto(updatedEntity);
    }

    @Override
    public TypeagDto read(Long id) {
        var entity = typeagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return typeagMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        typeagRepository.deleteById(id);
    }

    @Override
    public Page<TypeagDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return typeagRepository.findAll(booleanBuilder, pageable)
            .map(typeagMapper::asDto);
    }

  
    public void exportTypeag(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(TypeagExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<TypeagExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<TypeagExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = typeagRepository.findAll();
        var dtos = entities.stream().map(typeagMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QTypeagEntity.typeagEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
