package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QRecetteEntity;
import com.webgram.dgpsn.entities.RecetteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RecetteMapper;
import com.webgram.dgpsn.models.RecetteDto;
import com.webgram.dgpsn.repositories.RecetteRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.RecetteExcelDTO;
import com.webgram.dgpsn.services.RecetteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecetteServiceImpl implements RecetteService {

    private final RecetteRepository recetteRepository;
    private final RecetteMapper recetteMapper;

    @Override
    public RecetteDto create(RecetteDto dto) {
        var entity = recetteMapper.asEntity(dto);
        var savedEntity = recetteRepository.save(entity);
        return recetteMapper.asDto(savedEntity);
    }

    @Override
    public List<RecetteDto> readByEntrepriseId(Long entrepriseId) {
        List<RecetteEntity> recettes = recetteRepository.findByEntrepriseId(entrepriseId);
        return recettes.stream()
                .map(recetteMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecetteDto update(RecetteDto dto) {
        var recette = read(dto.getId());
        var entity = recetteMapper.asEntity(dto);
        var updatedEntity = recetteRepository.save(entity);
        return recetteMapper.asDto(updatedEntity);
    }

    @Override
    public RecetteDto read(Long id) {
        var entity = recetteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return recetteMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        recetteRepository.deleteById(id);
    }

    @Override
    public Page<RecetteDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return recetteRepository.findAll(booleanBuilder, pageable)
            .map(recetteMapper::asDto);
    }

  
    public void exportRecette(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(RecetteExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<RecetteExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<RecetteExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = recetteRepository.findAll();
        var dtos = entities.stream().map(recetteMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QRecetteEntity.recetteEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("datedelatransaction")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datedelatransaction"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datedelatransaction.eq(date));
			}
			if (searchParams.containsKey("description"))
				booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
			if (searchParams.containsKey("naturedelarecetteId"))
				booleanBuilder.and(qEntity.naturedelarecette.id.eq(Long.valueOf(searchParams.get("naturedelarecetteId"))));

            if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
          }
   }
}
