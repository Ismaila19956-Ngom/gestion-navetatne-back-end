package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QNaturedelarecetteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.NaturedelarecetteMapper;
import com.webgram.dgpsn.models.NaturedelarecetteDto;
import com.webgram.dgpsn.repositories.NaturedelarecetteRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.NaturedelarecetteExcelDTO;
import com.webgram.dgpsn.services.NaturedelarecetteService;
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
public class NaturedelarecetteServiceImpl implements NaturedelarecetteService {

    private final NaturedelarecetteRepository naturedelarecetteRepository;
    private final NaturedelarecetteMapper naturedelarecetteMapper;

    @Override
    public NaturedelarecetteDto create(NaturedelarecetteDto dto) {
        var entity = naturedelarecetteMapper.asEntity(dto);
        var savedEntity = naturedelarecetteRepository.save(entity);
        return naturedelarecetteMapper.asDto(savedEntity);
    }

    @Override
    public NaturedelarecetteDto update(NaturedelarecetteDto dto) {
        var naturedelarecette = read(dto.getId());
        var entity = naturedelarecetteMapper.asEntity(dto);
        var updatedEntity = naturedelarecetteRepository.save(entity);
        return naturedelarecetteMapper.asDto(updatedEntity);
    }

    @Override
    public NaturedelarecetteDto read(Long id) {
        var entity = naturedelarecetteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return naturedelarecetteMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        naturedelarecetteRepository.deleteById(id);
    }

    @Override
    public Page<NaturedelarecetteDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return naturedelarecetteRepository.findAll(booleanBuilder, pageable)
            .map(naturedelarecetteMapper::asDto);
    }

  
    public void exportNaturedelarecette(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(NaturedelarecetteExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<NaturedelarecetteExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<NaturedelarecetteExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = naturedelarecetteRepository.findAll();
        var dtos = entities.stream().map(naturedelarecetteMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QNaturedelarecetteEntity.naturedelarecetteEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
