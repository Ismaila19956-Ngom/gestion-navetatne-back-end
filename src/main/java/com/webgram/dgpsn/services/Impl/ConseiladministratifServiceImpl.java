package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QConseiladministratifEntity;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ConseiladministratifExcelDTO;
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
import com.webgram.dgpsn.mappers.ConseiladministratifMapper;

import com.webgram.dgpsn.models.ConseiladministratifDto;
import com.webgram.dgpsn.entities.ConseiladministratifEntity;
import com.webgram.dgpsn.repositories.ConseiladministratifRepository;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.services.ConseiladministratifService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConseiladministratifServiceImpl implements ConseiladministratifService {

    private final ConseiladministratifRepository conseiladministratifRepository;
    private final ConseiladministratifMapper conseiladministratifMapper;

    @Override
    public ConseiladministratifDto create(ConseiladministratifDto dto) {
        var entity = conseiladministratifMapper.asEntity(dto);
        var savedEntity = conseiladministratifRepository.save(entity);
        return conseiladministratifMapper.asDto(savedEntity);
    }

    @Override
    public List<ConseiladministratifDto> readByEntrepriseId(Long entrepriseId) {
        List<ConseiladministratifEntity> conseiladministratifs = conseiladministratifRepository.findByEntrepriseId(entrepriseId);
        return conseiladministratifs.stream()
                .map(conseiladministratifMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConseiladministratifDto update(ConseiladministratifDto dto) {
        var conseiladministratif = read(dto.getId());
        var entity = conseiladministratifMapper.asEntity(dto);
        var updatedEntity = conseiladministratifRepository.save(entity);
        return conseiladministratifMapper.asDto(updatedEntity);
    }

    @Override
    public ConseiladministratifDto read(Long id) {
        var entity = conseiladministratifRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return conseiladministratifMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        conseiladministratifRepository.deleteById(id);
    }

    @Override
    public Page<ConseiladministratifDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return conseiladministratifRepository.findAll(booleanBuilder, pageable)
            .map(conseiladministratifMapper::asDto);
    }

  
    public void exportConseiladministratif(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(ConseiladministratifExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ConseiladministratifExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ConseiladministratifExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = conseiladministratifRepository.findAll();
        var dtos = entities.stream().map(conseiladministratifMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QConseiladministratifEntity.conseiladministratifEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("nomca"))
				booleanBuilder.and(qEntity.nomca.containsIgnoreCase(searchParams.get("nomca")));
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
			if (searchParams.containsKey("nbretotalmembre"))
				booleanBuilder.and(qEntity.nbretotalmembre.containsIgnoreCase(searchParams.get("nbretotalmembre")));
			if (searchParams.containsKey("frequencereunions"))
				booleanBuilder.and(qEntity.frequencereunions.containsIgnoreCase(searchParams.get("frequencereunions")));
			if (searchParams.containsKey("description"))
				booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
			if (searchParams.containsKey("procedurenominationId"))
				booleanBuilder.and(qEntity.procedurenomination.id.eq(Long.valueOf(searchParams.get("procedurenominationId"))));
              if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
          }
   }
}
