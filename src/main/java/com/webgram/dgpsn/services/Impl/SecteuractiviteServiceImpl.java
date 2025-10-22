package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QSecteuractiviteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SecteuractiviteMapper;
import com.webgram.dgpsn.models.SecteuractiviteDto;
import com.webgram.dgpsn.repositories.SecteuractiviteRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.SecteuractiviteExcelDTO;
import com.webgram.dgpsn.services.SecteuractiviteService;
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
public class SecteuractiviteServiceImpl implements SecteuractiviteService {

    private final SecteuractiviteRepository secteuractiviteRepository;
    private final SecteuractiviteMapper secteuractiviteMapper;

    @Override
    public SecteuractiviteDto create(SecteuractiviteDto dto) {
        var entity = secteuractiviteMapper.asEntity(dto);
        var savedEntity = secteuractiviteRepository.save(entity);
        return secteuractiviteMapper.asDto(savedEntity);
    }

    @Override
    public SecteuractiviteDto update(SecteuractiviteDto dto) {
        var secteuractivite = read(dto.getId());
        var entity = secteuractiviteMapper.asEntity(dto);
        var updatedEntity = secteuractiviteRepository.save(entity);
        return secteuractiviteMapper.asDto(updatedEntity);
    }

    @Override
    public SecteuractiviteDto read(Long id) {
        var entity = secteuractiviteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return secteuractiviteMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        secteuractiviteRepository.deleteById(id);
    }

    @Override
    public Page<SecteuractiviteDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return secteuractiviteRepository.findAll(booleanBuilder, pageable)
            .map(secteuractiviteMapper::asDto);
    }

  
    public void exportSecteuractivite(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(SecteuractiviteExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<SecteuractiviteExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<SecteuractiviteExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = secteuractiviteRepository.findAll();
        var dtos = entities.stream().map(secteuractiviteMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QSecteuractiviteEntity.secteuractiviteEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
