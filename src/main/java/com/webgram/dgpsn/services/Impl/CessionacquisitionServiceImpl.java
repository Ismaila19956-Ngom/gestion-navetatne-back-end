package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.CessionacquisitionEntity;
import com.webgram.dgpsn.entities.QCessionacquisitionEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CessionacquisitionMapper;
import com.webgram.dgpsn.models.CessionacquisitionDto;
import com.webgram.dgpsn.repositories.CessionacquisitionRepository;
import com.webgram.dgpsn.services.CessionacquisitionService;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.CessionacquisitionExcelDTO;
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
public class CessionacquisitionServiceImpl implements CessionacquisitionService {

    private final CessionacquisitionRepository cessionacquisitionRepository;
    private final CessionacquisitionMapper cessionacquisitionMapper;

    @Override
    public CessionacquisitionDto create(CessionacquisitionDto dto) {
        var entity = cessionacquisitionMapper.asEntity(dto);
        var savedEntity = cessionacquisitionRepository.save(entity);
        return cessionacquisitionMapper.asDto(savedEntity);
    }

    @Override
    public List<CessionacquisitionDto> readByEntrepriseId(Long entrepriseId) {
        List<CessionacquisitionEntity> cessionacquisitions = cessionacquisitionRepository.findByEntrepriseId(entrepriseId);
        return cessionacquisitions.stream()
                .map(cessionacquisitionMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public CessionacquisitionDto update(CessionacquisitionDto dto) {
        var cessionacquisition = read(dto.getId());
        var entity = cessionacquisitionMapper.asEntity(dto);
        var updatedEntity = cessionacquisitionRepository.save(entity);
        return cessionacquisitionMapper.asDto(updatedEntity);
    }

    @Override
    public CessionacquisitionDto read(Long id) {
        var entity = cessionacquisitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return cessionacquisitionMapper.asDto(entity);
    }

    @Override
    public void delete(Long id) {
        cessionacquisitionRepository.deleteById(id);
    }

    @Override
    public Page<CessionacquisitionDto> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return cessionacquisitionRepository.findAll(booleanBuilder, pageable)
                .map(cessionacquisitionMapper::asDto);
    }


    public void exportCessionacquisition(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(CessionacquisitionExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<CessionacquisitionExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<CessionacquisitionExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var entities = cessionacquisitionRepository.findAll();
        var dtos = entities.stream().map(cessionacquisitionMapper::asExcelDto).collect(Collectors.toList());
        try {
            beanToCsv.write(dtos);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error writing CSV", e);
        }
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QCessionacquisitionEntity.cessionacquisitionEntity;
            if (searchParams.containsKey("code"))
                booleanBuilder.and(qEntity.code.containsIgnoreCase(searchParams.get("code")));
            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
            if (searchParams.containsKey("date")) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("date"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                booleanBuilder.and(qEntity.date.eq(date));
            }
            if (searchParams.containsKey("typeoperation"))
                booleanBuilder.and(qEntity.typeoperation.containsIgnoreCase(searchParams.get("typeoperation")));
            if (searchParams.containsKey("montantoperation"))
                booleanBuilder.and(qEntity.montantoperation.eq(Integer.valueOf(searchParams.get("montantoperation"))));
            if (searchParams.containsKey("prixaction"))
                booleanBuilder.and(qEntity.prixaction.eq(Integer.valueOf(searchParams.get("prixaction"))));
            if (searchParams.containsKey("nombretitre"))
                booleanBuilder.and(qEntity.nombretitre.eq(Integer.valueOf(searchParams.get("nombretitre"))));
            if (searchParams.containsKey("participationoperation"))
                booleanBuilder.and(qEntity.participationoperation.eq(Integer.valueOf(searchParams.get("participationoperation"))));
            if (searchParams.containsKey("vcoperation"))
                booleanBuilder.and(qEntity.vcoperation.eq(Integer.valueOf(searchParams.get("vcoperation"))));
            if (searchParams.containsKey("nombreactioncree"))
                booleanBuilder.and(qEntity.nombreactioncree.eq(Integer.valueOf(searchParams.get("nombreactioncree"))));
            if (searchParams.containsKey("valeurdecotesurcote"))
                booleanBuilder.and(qEntity.valeurdecotesurcote.eq(Integer.valueOf(searchParams.get("valeurdecotesurcote"))));
            if (searchParams.containsKey("decotesurcote"))
                booleanBuilder.and(qEntity.decotesurcote.containsIgnoreCase(searchParams.get("decotesurcote")));
            if (searchParams.containsKey("coment"))
                booleanBuilder.and(qEntity.coment.containsIgnoreCase(searchParams.get("coment")));
            if (searchParams.containsKey("entrepriseId")) {
                Long entrepriseId = Long.parseLong(searchParams.get("entrepriseId"));
                booleanBuilder.and(qEntity.entreprise.id.eq(entrepriseId));
            }
        }
    }
}
