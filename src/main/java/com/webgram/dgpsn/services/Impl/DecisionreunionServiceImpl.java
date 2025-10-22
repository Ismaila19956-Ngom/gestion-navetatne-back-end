package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.DecisionreunionEntity;
import com.webgram.dgpsn.entities.QDecisionreunionEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.DecisionreunionMapper;
import com.webgram.dgpsn.models.DecisionreunionDto;
import com.webgram.dgpsn.repositories.DecisionreunionRepository;
import com.webgram.dgpsn.services.DecisionreunionService;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DecisionreunionExcelDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DecisionreunionServiceImpl implements DecisionreunionService {

    private final DecisionreunionRepository decisionreunionRepository;
    private final DecisionreunionMapper decisionreunionMapper;

    @Override
    public DecisionreunionDto create(DecisionreunionDto dto) {
        var entity = decisionreunionMapper.asEntity(dto);
        var savedEntity = decisionreunionRepository.save(entity);
        return decisionreunionMapper.asDto(savedEntity);
    }

    @Override
    public List<DecisionreunionDto> readByReunionId(Long reunionId) {
        List<DecisionreunionEntity> decisionreunions = decisionreunionRepository.findByReunionId(reunionId);
        return decisionreunions.stream()
                .map(decisionreunionMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public DecisionreunionDto update(DecisionreunionDto dto) {
        var decisionreunion = read(dto.getId());
        var entity = decisionreunionMapper.asEntity(dto);
        var updatedEntity = decisionreunionRepository.save(entity);
        return decisionreunionMapper.asDto(updatedEntity);
    }

    @Override
    public DecisionreunionDto read(Long id) {
        var entity = decisionreunionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return decisionreunionMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        decisionreunionRepository.deleteById(id);
    }

    @Override
    public Page<DecisionreunionDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return decisionreunionRepository.findAll(booleanBuilder, pageable)
            .map(decisionreunionMapper::asDto);
    }

  
    public void exportDecisionreunion(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(DecisionreunionExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<DecisionreunionExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<DecisionreunionExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = decisionreunionRepository.findAll();
        var dtos = entities.stream().map(decisionreunionMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QDecisionreunionEntity.decisionreunionEntity;
			if (searchParams.containsKey("sujet"))
				booleanBuilder.and(qEntity.sujet.containsIgnoreCase(searchParams.get("sujet")));
			if (searchParams.containsKey("modalitevote"))
				booleanBuilder.and(qEntity.modalitevote.containsIgnoreCase(searchParams.get("modalitevote")));
			if (searchParams.containsKey("resultat"))
				booleanBuilder.and(qEntity.resultat.containsIgnoreCase(searchParams.get("resultat")));
			if (searchParams.containsKey("pourcentage"))
				booleanBuilder.and(qEntity.pourcentage.eq(Integer.valueOf(searchParams.get("pourcentage"))));
           }
   }
}
