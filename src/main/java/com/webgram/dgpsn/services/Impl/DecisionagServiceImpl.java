package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QDecisionagEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DecisionagExcelDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.List;
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
import com.webgram.dgpsn.mappers.DecisionagMapper;

import com.webgram.dgpsn.models.DecisionagDto;
import com.webgram.dgpsn.entities.DecisionagEntity;
import com.webgram.dgpsn.repositories.DecisionagRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.DecisionagService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DecisionagServiceImpl implements DecisionagService {

    private final DecisionagRepository decisionagRepository;
    private final DecisionagMapper decisionagMapper;

    @Override
    public DecisionagDto create(DecisionagDto dto) {
        var entity = decisionagMapper.asEntity(dto);
        var savedEntity = decisionagRepository.save(entity);
        return decisionagMapper.asDto(savedEntity);
    }

    @Override
    public List<DecisionagDto> readByAssemblegeneralId(Long assemblegeneralId) {
        List<DecisionagEntity> decisionags = decisionagRepository.findByAssemblegeneralId(assemblegeneralId);
        return decisionags.stream()
                .map(decisionagMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public DecisionagDto update(DecisionagDto dto) {
        var decisionag = read(dto.getId());
        var entity = decisionagMapper.asEntity(dto);
        var updatedEntity = decisionagRepository.save(entity);
        return decisionagMapper.asDto(updatedEntity);
    }

    @Override
    public DecisionagDto read(Long id) {
        var entity = decisionagRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return decisionagMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        decisionagRepository.deleteById(id);
    }

    @Override
    public Page<DecisionagDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return decisionagRepository.findAll(booleanBuilder, pageable)
            .map(decisionagMapper::asDto);
    }

  
    public void exportDecisionag(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(DecisionagExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<DecisionagExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<DecisionagExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = decisionagRepository.findAll();
        var dtos = entities.stream().map(decisionagMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QDecisionagEntity.decisionagEntity;
			if (searchParams.containsKey("sujet"))
				booleanBuilder.and(qEntity.sujet.containsIgnoreCase(searchParams.get("sujet")));
			if (searchParams.containsKey("modalite"))
				booleanBuilder.and(qEntity.modalite.containsIgnoreCase(searchParams.get("modalite")));
			if (searchParams.containsKey("resultat"))
				booleanBuilder.and(qEntity.resultat.containsIgnoreCase(searchParams.get("resultat")));
			if (searchParams.containsKey("pourcentage"))
				booleanBuilder.and(qEntity.pourcentage.eq(Integer.valueOf(searchParams.get("pourcentage"))));
           }
   }
}
