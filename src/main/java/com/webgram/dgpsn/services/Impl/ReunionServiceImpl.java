package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QReunionEntity;
import com.webgram.dgpsn.entities.ReunionEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ReunionMapper;
import com.webgram.dgpsn.models.ReunionDto;
import com.webgram.dgpsn.repositories.ReunionRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.ReunionExcelDTO;
import com.webgram.dgpsn.services.ReunionService;
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
public class ReunionServiceImpl implements ReunionService {

    private final ReunionRepository reunionRepository;
    private final ReunionMapper reunionMapper;

    @Override
    public ReunionDto create(ReunionDto dto) {
        dto.setStatut(Statut.EN_COURS);
        var entity = reunionMapper.asEntity(dto);
        var savedEntity = reunionRepository.save(entity);
        return reunionMapper.asDto(savedEntity);
    }

    @Override
    public ReunionDto changeStatus(Long reunionId, Statut statut) {
        var reunion = reunionRepository.findById(reunionId)
                .orElseThrow(() -> new ResourceNotFoundException("la reunion n'existe pas"));
        reunion.setStatut(statut);
        var savedStatut = reunionRepository.save(reunion);
        return reunionMapper.asDto(savedStatut);
    }

    @Override
    public List<ReunionDto> readByConseiladministratifId(Long conseiladministratifId) {
        List<ReunionEntity> reunions = reunionRepository.findByConseiladministratifId(conseiladministratifId);
        return reunions.stream()
                .map(reunionMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReunionDto update(ReunionDto dto) {
        var reunion = read(dto.getId());
        dto.setStatut(reunion.getStatut());
        var entity = reunionMapper.asEntity(dto);
        var updatedEntity = reunionRepository.save(entity);
        return reunionMapper.asDto(updatedEntity);
    }

    @Override
    public ReunionDto read(Long id) {
        var entity = reunionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return reunionMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        reunionRepository.deleteById(id);
    }

    @Override
    public Page<ReunionDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return reunionRepository.findAll(booleanBuilder, pageable)
            .map(reunionMapper::asDto);
    }

  
    public void exportReunion(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(ReunionExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ReunionExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ReunionExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = reunionRepository.findAll();
        var dtos = entities.stream().map(reunionMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
          if (Objects.nonNull(searchParams)) {
               var qEntity = QReunionEntity.reunionEntity;
			if (searchParams.containsKey("libelle"))
				booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
			if (searchParams.containsKey("typereunionId"))
				booleanBuilder.and(qEntity.typereunion.id.eq(Long.valueOf(searchParams.get("typereunionId"))));
			if (searchParams.containsKey("participant"))
				booleanBuilder.and(qEntity.participant.containsIgnoreCase(searchParams.get("participant")));
			if (searchParams.containsKey("dateprevue")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateprevue"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.dateprevue.eq(date));
			}
			if (searchParams.containsKey("datereelle")){
				Date date = null;
				try {
					date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("datereelle"));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				booleanBuilder.and(qEntity.datereelle.eq(date));
			}
			if (searchParams.containsKey("heuredebutprevue"))
				booleanBuilder.and(qEntity.heuredebutprevue.containsIgnoreCase(searchParams.get("heuredebutprevue")));
			if (searchParams.containsKey("heurefinprevue"))
				booleanBuilder.and(qEntity.heurefinprevue.containsIgnoreCase(searchParams.get("heurefinprevue")));
			if (searchParams.containsKey("heuredebutreelle"))
				booleanBuilder.and(qEntity.heuredebutreelle.containsIgnoreCase(searchParams.get("heuredebutreelle")));
			if (searchParams.containsKey("heurefinreelle"))
				booleanBuilder.and(qEntity.heurefinreelle.containsIgnoreCase(searchParams.get("heurefinreelle")));
			if (searchParams.containsKey("coment"))
				booleanBuilder.and(qEntity.coment.containsIgnoreCase(searchParams.get("coment")));
           }
   }
}
