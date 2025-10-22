package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QPeriodeEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PeriodeMapper;
import com.webgram.dgpsn.models.PeriodeDto;
import com.webgram.dgpsn.repositories.PeriodeRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.PeriodeExcelDTO;
import com.webgram.dgpsn.services.PeriodeService;
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
public class PeriodeServiceImpl implements PeriodeService {

    private final PeriodeRepository periodeRepository;
    private final PeriodeMapper periodeMapper;

    @Override
    public PeriodeDto create(PeriodeDto dto) {
        var entity = periodeMapper.asEntity(dto);
        var savedEntity = periodeRepository.save(entity);
        return periodeMapper.asDto(savedEntity);
    }

    @Override
    public PeriodeDto update(PeriodeDto dto) {
        var periode = read(dto.getId());
        var entity = periodeMapper.asEntity(dto);
        var updatedEntity = periodeRepository.save(entity);
        return periodeMapper.asDto(updatedEntity);
    }

    @Override
    public PeriodeDto read(Long id) {
        var entity = periodeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return periodeMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        periodeRepository.deleteById(id);
    }

    @Override
    public Page<PeriodeDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return periodeRepository.findAll(booleanBuilder, pageable)
            .map(periodeMapper::asDto);
    }

  
    public void exportPeriode(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(PeriodeExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<PeriodeExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<PeriodeExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = periodeRepository.findAll();
        var dtos = entities.stream().map(periodeMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QPeriodeEntity.periodeEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
           }
   }
}
