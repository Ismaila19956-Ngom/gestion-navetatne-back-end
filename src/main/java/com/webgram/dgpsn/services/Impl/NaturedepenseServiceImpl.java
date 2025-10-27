package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QNaturedepenseEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.NaturedepenseMapper;
import com.webgram.dgpsn.models.NaturedepenseDto;
import com.webgram.dgpsn.repositories.NaturedepenseRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.NaturedepenseExcelDTO;
import com.webgram.dgpsn.services.NaturedepenseService;
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
public class NaturedepenseServiceImpl implements NaturedepenseService {

    private final NaturedepenseRepository naturedepenseRepository;
    private final NaturedepenseMapper naturedepenseMapper;

    @Override
    public NaturedepenseDto create(NaturedepenseDto dto) {
        var entity = naturedepenseMapper.asEntity(dto);
        var savedEntity = naturedepenseRepository.save(entity);
        return naturedepenseMapper.asDto(savedEntity);
    }

    @Override
    public NaturedepenseDto update(NaturedepenseDto dto) {
        var naturedepense = read(dto.getId());
        var entity = naturedepenseMapper.asEntity(dto);
        var updatedEntity = naturedepenseRepository.save(entity);
        return naturedepenseMapper.asDto(updatedEntity);
    }

    @Override
    public NaturedepenseDto read(Long id) {
        var entity = naturedepenseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return naturedepenseMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        naturedepenseRepository.deleteById(id);
    }

    @Override
    public Page<NaturedepenseDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return naturedepenseRepository.findAll(booleanBuilder, pageable)
            .map(naturedepenseMapper::asDto);
    }

  
    public void exportNaturedepense(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(NaturedepenseExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<NaturedepenseExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<NaturedepenseExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = naturedepenseRepository.findAll();
        var dtos = entities.stream().map(naturedepenseMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QNaturedepenseEntity.naturedepenseEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
