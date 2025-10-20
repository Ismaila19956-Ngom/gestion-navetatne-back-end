package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QEvaluationfinanciereEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.EvaluationfinanciereExcelDTO;
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
import com.webgram.dgpsn.mappers.EvaluationfinanciereMapper;

import com.webgram.dgpsn.models.EvaluationfinanciereDto;
import com.webgram.dgpsn.entities.EvaluationfinanciereEntity;
import com.webgram.dgpsn.repositories.EvaluationfinanciereRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.EvaluationfinanciereService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EvaluationfinanciereServiceImpl implements EvaluationfinanciereService {

    private final EvaluationfinanciereRepository evaluationfinanciereRepository;
    private final EvaluationfinanciereMapper evaluationfinanciereMapper;

    @Override
    public EvaluationfinanciereDto create(EvaluationfinanciereDto dto) {
        var entity = evaluationfinanciereMapper.asEntity(dto);
        var savedEntity = evaluationfinanciereRepository.save(entity);
        return evaluationfinanciereMapper.asDto(savedEntity);
    }

    @Override
    public List<EvaluationfinanciereDto> readByEntrepriseId(Long entrepriseId) {
        List<EvaluationfinanciereEntity> evaluationfinancieres = evaluationfinanciereRepository.findByEntrepriseId(entrepriseId);
        return evaluationfinancieres.stream()
                .map(evaluationfinanciereMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public EvaluationfinanciereDto update(EvaluationfinanciereDto dto) {
        var evaluationfinanciere = read(dto.getId());
        var entity = evaluationfinanciereMapper.asEntity(dto);
        var updatedEntity = evaluationfinanciereRepository.save(entity);
        return evaluationfinanciereMapper.asDto(updatedEntity);
    }

    @Override
    public EvaluationfinanciereDto read(Long id) {
        var entity = evaluationfinanciereRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return evaluationfinanciereMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        evaluationfinanciereRepository.deleteById(id);
    }

    @Override
    public Page<EvaluationfinanciereDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return evaluationfinanciereRepository.findAll(booleanBuilder, pageable)
            .map(evaluationfinanciereMapper::asDto);
    }

  
    public void exportEvaluationfinanciere(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(EvaluationfinanciereExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<EvaluationfinanciereExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<EvaluationfinanciereExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = evaluationfinanciereRepository.findAll();
        var dtos = entities.stream().map(evaluationfinanciereMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QEvaluationfinanciereEntity.evaluationfinanciereEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("datedebut")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datedebut"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datedebut.eq(date));
			}
			if (searchParams.containsKey("datefin")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datefin"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datefin.eq(date));
			}
			if (searchParams.containsKey("commentaire"))
				booleanBuilder.and(qEntity.commentaire.containsIgnoreCase(searchParams.get("commentaire")));
			if (searchParams.containsKey("notation"))
				booleanBuilder.and(qEntity.notation.eq(Integer.valueOf(searchParams.get("notation"))));
              if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
           }
   }
}
