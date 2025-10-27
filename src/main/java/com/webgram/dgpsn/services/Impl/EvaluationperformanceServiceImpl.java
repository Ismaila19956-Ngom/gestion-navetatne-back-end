package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.EvaluationperformanceEntity;
import com.webgram.dgpsn.entities.QEvaluationperformanceEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EvaluationperformanceMapper;
import com.webgram.dgpsn.models.EvaluationperformanceDto;
import com.webgram.dgpsn.repositories.EvaluationperformanceRepository;
import com.webgram.dgpsn.services.EvaluationperformanceService;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.EvaluationperformanceExcelDTO;
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
public class EvaluationperformanceServiceImpl implements EvaluationperformanceService {

    private final EvaluationperformanceRepository evaluationperformanceRepository;
    private final EvaluationperformanceMapper evaluationperformanceMapper;

    @Override
    public EvaluationperformanceDto create(EvaluationperformanceDto dto) {
        var entity = evaluationperformanceMapper.asEntity(dto);
        var savedEntity = evaluationperformanceRepository.save(entity);
        return evaluationperformanceMapper.asDto(savedEntity);
    }

    @Override
    public List<EvaluationperformanceDto> readByEntrepriseId(Long entrepriseId) {
        List<EvaluationperformanceEntity> evaluationperformances = evaluationperformanceRepository.findByEntrepriseId(entrepriseId);
        return evaluationperformances.stream()
                .map(evaluationperformanceMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public EvaluationperformanceDto update(EvaluationperformanceDto dto) {
        var evaluationperformance = read(dto.getId());
        var entity = evaluationperformanceMapper.asEntity(dto);
        var updatedEntity = evaluationperformanceRepository.save(entity);
        return evaluationperformanceMapper.asDto(updatedEntity);
    }

    @Override
    public EvaluationperformanceDto read(Long id) {
        var entity = evaluationperformanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return evaluationperformanceMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        evaluationperformanceRepository.deleteById(id);
    }

    @Override
    public Page<EvaluationperformanceDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return evaluationperformanceRepository.findAll(booleanBuilder, pageable)
            .map(evaluationperformanceMapper::asDto);
    }

  
    public void exportEvaluationperformance(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(EvaluationperformanceExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<EvaluationperformanceExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<EvaluationperformanceExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = evaluationperformanceRepository.findAll();
        var dtos = entities.stream().map(evaluationperformanceMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QEvaluationperformanceEntity.evaluationperformanceEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("datededbut")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datededbut"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datededbut.eq(date));
			}
			if (searchParams.containsKey("datedefin")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datedefin"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datedefin.eq(date));
			}
			if (searchParams.containsKey("pointsforts"))
				booleanBuilder.and(qEntity.pointsforts.containsIgnoreCase(searchParams.get("pointsforts")));
			if (searchParams.containsKey("pointsfaibles"))
				booleanBuilder.and(qEntity.pointsfaibles.containsIgnoreCase(searchParams.get("pointsfaibles")));
			if (searchParams.containsKey("apprciationgouvernance"))
				booleanBuilder.and(qEntity.apprciationgouvernance.containsIgnoreCase(searchParams.get("apprciationgouvernance")));
			if (searchParams.containsKey("respectprocdures"))
				booleanBuilder.and(qEntity.respectprocdures.containsIgnoreCase(searchParams.get("respectprocdures")));
			if (searchParams.containsKey("statut"))
				booleanBuilder.and(qEntity.statut.containsIgnoreCase(searchParams.get("statut")));
			if (searchParams.containsKey("notation"))
				booleanBuilder.and(qEntity.notation.eq(Integer.valueOf(searchParams.get("notation"))));
			if (searchParams.containsKey("recommandations"))
				booleanBuilder.and(qEntity.recommandations.containsIgnoreCase(searchParams.get("recommandations")));
			if (searchParams.containsKey("plandaction"))
				booleanBuilder.and(qEntity.plandaction.containsIgnoreCase(searchParams.get("plandaction")));
			if (searchParams.containsKey("description"))
				booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
			if (searchParams.containsKey("typedvaluationId"))
				booleanBuilder.and(qEntity.typedvaluation.id.eq(Long.valueOf(searchParams.get("typedvaluationId"))));
			if (searchParams.containsKey("risqueId"))
				booleanBuilder.and(qEntity.risque.id.eq(Long.valueOf(searchParams.get("risqueId"))));
              if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
          }
   }
}
