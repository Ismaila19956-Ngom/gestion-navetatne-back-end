package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QRegionEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.RegionMapper;
import com.webgram.dgpsn.models.RegionDto;
import com.webgram.dgpsn.repositories.RegionRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.RegionExcelDTO;
import com.webgram.dgpsn.services.RegionService;
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
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public RegionDto create(RegionDto dto) {
        var entity = regionMapper.asEntity(dto);
        var savedEntity = regionRepository.save(entity);
        return regionMapper.asDto(savedEntity);
    }

    @Override
    public RegionDto update(RegionDto dto) {
        var region = read(dto.getId());
        var entity = regionMapper.asEntity(dto);
        var updatedEntity = regionRepository.save(entity);
        return regionMapper.asDto(updatedEntity);
    }

    @Override
    public RegionDto read(Long id) {
        var entity = regionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return regionMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        regionRepository.deleteById(id);
    }

    @Override
    public Page<RegionDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return regionRepository.findAll(booleanBuilder, pageable)
            .map(regionMapper::asDto);
    }

  
    public void exportRegion(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(RegionExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<RegionExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<RegionExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = regionRepository.findAll();
        var dtos = entities.stream().map(regionMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QRegionEntity.regionEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
