package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QDepenseEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DepenseExcelDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import java.io.PrintWriter;
import java.util.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import com.webgram.dgpsn.mappers.DepenseMapper;

import com.webgram.dgpsn.models.DepenseDto;
import com.webgram.dgpsn.entities.DepenseEntity;
import com.webgram.dgpsn.repositories.DepenseRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.DepenseService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DepenseServiceImpl implements DepenseService {

    private final DepenseRepository depenseRepository;
    private final DepenseMapper depenseMapper;

    @Override
    public DepenseDto create(DepenseDto dto) {
        var entity = depenseMapper.asEntity(dto);
        var savedEntity = depenseRepository.save(entity);
        return depenseMapper.asDto(savedEntity);
    }

    @Override
    public List<DepenseDto> readByEntrepriseId(Long entrepriseId) {
        List<DepenseEntity> depenses = depenseRepository.findByEntrepriseId(entrepriseId);
        return depenses.stream()
                .map(depenseMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepenseDto update(DepenseDto dto) {
        var depense = read(dto.getId());
        var entity = depenseMapper.asEntity(dto);
        var updatedEntity = depenseRepository.save(entity);
        return depenseMapper.asDto(updatedEntity);
    }

    @Override
    public DepenseDto read(Long id) {
        var entity = depenseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return depenseMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        depenseRepository.deleteById(id);
    }

    @Override
    public Page<DepenseDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return depenseRepository.findAll(booleanBuilder, pageable)
            .map(depenseMapper::asDto);
    }

  
    public void exportDepense(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(DepenseExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<DepenseExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<DepenseExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = depenseRepository.findAll();
        var dtos = entities.stream().map(depenseMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QDepenseEntity.depenseEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("datedepense")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datedepense"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datedepense.eq(date));
			}
			if (searchParams.containsKey("description"))
				booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
			if (searchParams.containsKey("naturedepenseId"))
				booleanBuilder.and(qEntity.naturedepense.id.eq(Long.valueOf(searchParams.get("naturedepenseId"))));
              if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
           }
   }
}
