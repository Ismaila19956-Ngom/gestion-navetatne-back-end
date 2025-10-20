package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QDepartementEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.DepartementExcelDTO;
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
import com.webgram.dgpsn.mappers.DepartementMapper;

import com.webgram.dgpsn.models.DepartementDto;
import com.webgram.dgpsn.entities.DepartementEntity;
import com.webgram.dgpsn.repositories.DepartementRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.DepartementService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper departementMapper;

    @Override
    public DepartementDto create(DepartementDto dto) {
        var entity = departementMapper.asEntity(dto);
        var savedEntity = departementRepository.save(entity);
        return departementMapper.asDto(savedEntity);
    }

    @Override
    public List<DepartementDto> readByRegionId(Long regionId) {
        List<DepartementEntity> departements = departementRepository.findByRegionId(regionId);
        return departements.stream()
                .map(departementMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartementDto update(DepartementDto dto) {
        var departement = read(dto.getId());
        var entity = departementMapper.asEntity(dto);
        var updatedEntity = departementRepository.save(entity);
        return departementMapper.asDto(updatedEntity);
    }

    @Override
    public DepartementDto read(Long id) {
        var entity = departementRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return departementMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        departementRepository.deleteById(id);
    }

    @Override
    public Page<DepartementDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return departementRepository.findAll(booleanBuilder, pageable)
            .map(departementMapper::asDto);
    }

  
    public void exportDepartement(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(DepartementExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<DepartementExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<DepartementExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = departementRepository.findAll();
        var dtos = entities.stream().map(departementMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QDepartementEntity.departementEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
