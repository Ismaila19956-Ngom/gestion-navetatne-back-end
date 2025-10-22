package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.AssemblegeneralEntity;
import com.webgram.dgpsn.entities.QAssemblegeneralEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AssemblegeneralMapper;
import com.webgram.dgpsn.models.AssemblegeneralDto;
import com.webgram.dgpsn.repositories.AssemblegeneralRepository;
import com.webgram.dgpsn.services.AssemblegeneralService;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.AssemblegeneralExcelDTO;
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
public class AssemblegeneralServiceImpl implements AssemblegeneralService {

    private final AssemblegeneralRepository assemblegeneralRepository;
    private final AssemblegeneralMapper assemblegeneralMapper;

    @Override
    public AssemblegeneralDto create(AssemblegeneralDto dto) {
        var entity = assemblegeneralMapper.asEntity(dto);
        var savedEntity = assemblegeneralRepository.save(entity);
        return assemblegeneralMapper.asDto(savedEntity);
    }

    @Override
    public List<AssemblegeneralDto> readByEntrepriseId(Long entrepriseId) {
        List<AssemblegeneralEntity> assemblegenerals = assemblegeneralRepository.findByEntrepriseId(entrepriseId);
        return assemblegenerals.stream()
                .map(assemblegeneralMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public AssemblegeneralDto update(AssemblegeneralDto dto) {
        var assemblegeneral = read(dto.getId());
        var entity = assemblegeneralMapper.asEntity(dto);
        var updatedEntity = assemblegeneralRepository.save(entity);
        return assemblegeneralMapper.asDto(updatedEntity);
    }

    @Override
    public AssemblegeneralDto read(Long id) {
        var entity = assemblegeneralRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return assemblegeneralMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        assemblegeneralRepository.deleteById(id);
    }

    @Override
    public Page<AssemblegeneralDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return assemblegeneralRepository.findAll(booleanBuilder, pageable)
            .map(assemblegeneralMapper::asDto);
    }

  
    public void exportAssemblegeneral(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(AssemblegeneralExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<AssemblegeneralExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<AssemblegeneralExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = assemblegeneralRepository.findAll();
        var dtos = entities.stream().map(assemblegeneralMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QAssemblegeneralEntity.assemblegeneralEntity;
			if (searchParams.containsKey("code"))
				booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("dateassemble")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateassemble"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.dateassemble.eq(date));
			}
			if (searchParams.containsKey("ville"))
				booleanBuilder.and(qEntity.ville.containsIgnoreCase(searchParams.get("ville")));
			if (searchParams.containsKey("lieu"))
				booleanBuilder.and(qEntity.lieu.containsIgnoreCase(searchParams.get("lieu")));
			if (searchParams.containsKey("quorum"))
				booleanBuilder.and(qEntity.quorum.eq(Integer.valueOf(searchParams.get("quorum"))));
			if (searchParams.containsKey("quorumen"))
				booleanBuilder.and(qEntity.quorumen.containsIgnoreCase(searchParams.get("quorumen")));
			if (searchParams.containsKey("description"))
				booleanBuilder.and(qEntity.description.containsIgnoreCase(searchParams.get("description")));
			if (searchParams.containsKey("conseiladministratifId"))
				booleanBuilder.and(qEntity.conseiladministratif.id.eq(Long.valueOf(searchParams.get("conseiladministratifId"))));
			if (searchParams.containsKey("typeagId"))
				booleanBuilder.and(qEntity.typeag.id.eq(Long.valueOf(searchParams.get("typeagId"))));
            if (searchParams.containsKey("entrepriseId")) {
                  Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                  booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
              }
          }
   }
}
