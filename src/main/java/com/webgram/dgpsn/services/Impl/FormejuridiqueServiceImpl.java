package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QFormejuridiqueEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.FormejuridiqueExcelDTO;
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
import com.webgram.dgpsn.mappers.FormejuridiqueMapper;

import com.webgram.dgpsn.models.FormejuridiqueDto;
import com.webgram.dgpsn.repositories.FormejuridiqueRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.FormejuridiqueService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FormejuridiqueServiceImpl implements FormejuridiqueService {

    private final FormejuridiqueRepository formejuridiqueRepository;
    private final FormejuridiqueMapper formejuridiqueMapper;

    @Override
    public FormejuridiqueDto create(FormejuridiqueDto dto) {
        var entity = formejuridiqueMapper.asEntity(dto);
        var savedEntity = formejuridiqueRepository.save(entity);
        return formejuridiqueMapper.asDto(savedEntity);
    }

    @Override
    public FormejuridiqueDto update(FormejuridiqueDto dto) {
        var formejuridique = read(dto.getId());
        var entity = formejuridiqueMapper.asEntity(dto);
        var updatedEntity = formejuridiqueRepository.save(entity);
        return formejuridiqueMapper.asDto(updatedEntity);
    }

    @Override
    public FormejuridiqueDto read(Long id) {
        var entity = formejuridiqueRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return formejuridiqueMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        formejuridiqueRepository.deleteById(id);
    }

    @Override
    public Page<FormejuridiqueDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return formejuridiqueRepository.findAll(booleanBuilder, pageable)
            .map(formejuridiqueMapper::asDto);
    }

  
    public void exportFormejuridique(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(FormejuridiqueExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<FormejuridiqueExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<FormejuridiqueExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = formejuridiqueRepository.findAll();
        var dtos = entities.stream().map(formejuridiqueMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QFormejuridiqueEntity.formejuridiqueEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
