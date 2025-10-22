package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.MembreEntity;
import com.webgram.dgpsn.entities.QMembreEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.MembreMapper;
import com.webgram.dgpsn.models.MembreDto;
import com.webgram.dgpsn.repositories.MembreRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.MembreExcelDTO;
import com.webgram.dgpsn.services.MembreService;
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
public class MembreServiceImpl implements MembreService {

    private final MembreRepository membreRepository;
    private final MembreMapper membreMapper;

    @Override
    public MembreDto create(MembreDto dto) {
        var entity = membreMapper.asEntity(dto);
        var savedEntity = membreRepository.save(entity);
        return membreMapper.asDto(savedEntity);
    }

    @Override
    public List<MembreDto> readByConseiladministratifId(Long conseiladministratifId) {
        List<MembreEntity> membres = membreRepository.findByConseiladministratifId(conseiladministratifId);
        return membres.stream()
                .map(membreMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public MembreDto update(MembreDto dto) {
        var membre = read(dto.getId());
        var entity = membreMapper.asEntity(dto);
        var updatedEntity = membreRepository.save(entity);
        return membreMapper.asDto(updatedEntity);
    }

    @Override
    public MembreDto read(Long id) {
        var entity = membreRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return membreMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        membreRepository.deleteById(id);
    }

    @Override
    public Page<MembreDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return membreRepository.findAll(booleanBuilder, pageable)
            .map(membreMapper::asDto);
    }

  
    public void exportMembre(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(MembreExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<MembreExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<MembreExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = membreRepository.findAll();
        var dtos = entities.stream().map(membreMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QMembreEntity.membreEntity;
			if (searchParams.containsKey("agent"))
				booleanBuilder.and(qEntity.agent.containsIgnoreCase(searchParams.get("agent")));
			if (searchParams.containsKey("role"))
				booleanBuilder.and(qEntity.role.containsIgnoreCase(searchParams.get("role")));
			if (searchParams.containsKey("mandat"))
				booleanBuilder.and(qEntity.mandat.eq(Integer.valueOf(searchParams.get("mandat"))));
           }
   }
}
