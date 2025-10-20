package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QCategoriebudgetaireEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.CategoriebudgetaireExcelDTO;
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
import com.webgram.dgpsn.mappers.CategoriebudgetaireMapper;

import com.webgram.dgpsn.models.CategoriebudgetaireDto;
import com.webgram.dgpsn.repositories.CategoriebudgetaireRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.CategoriebudgetaireService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoriebudgetaireServiceImpl implements CategoriebudgetaireService {

    private final CategoriebudgetaireRepository categoriebudgetaireRepository;
    private final CategoriebudgetaireMapper categoriebudgetaireMapper;

    @Override
    public CategoriebudgetaireDto create(CategoriebudgetaireDto dto) {
        var entity = categoriebudgetaireMapper.asEntity(dto);
        var savedEntity = categoriebudgetaireRepository.save(entity);
        return categoriebudgetaireMapper.asDto(savedEntity);
    }

    @Override
    public CategoriebudgetaireDto update(CategoriebudgetaireDto dto) {
        var categoriebudgetaire = read(dto.getId());
        var entity = categoriebudgetaireMapper.asEntity(dto);
        var updatedEntity = categoriebudgetaireRepository.save(entity);
        return categoriebudgetaireMapper.asDto(updatedEntity);
    }

    @Override
    public CategoriebudgetaireDto read(Long id) {
        var entity = categoriebudgetaireRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return categoriebudgetaireMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        categoriebudgetaireRepository.deleteById(id);
    }

    @Override
    public Page<CategoriebudgetaireDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return categoriebudgetaireRepository.findAll(booleanBuilder, pageable)
            .map(categoriebudgetaireMapper::asDto);
    }

  
    public void exportCategoriebudgetaire(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(CategoriebudgetaireExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<CategoriebudgetaireExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<CategoriebudgetaireExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = categoriebudgetaireRepository.findAll();
        var dtos = entities.stream().map(categoriebudgetaireMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QCategoriebudgetaireEntity.categoriebudgetaireEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
