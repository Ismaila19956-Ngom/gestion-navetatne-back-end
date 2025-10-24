package com.example.demo.services.Impl;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.AtelierEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.mappers.AtelierMapper;
import com.webgram.dgpsn.models.AtelierDTO;
import com.webgram.dgpsn.repositories.AtelierRepository;
import com.webgram.dgpsn.services.AtelierService;
import com.webgram.dgpsn.services.modelExcel.AtelierExcelDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AtelierServiceImpl implements AtelierService {

    private final AtelierRepository atelierRepository;
    private final AtelierMapper atelierMapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("titre", "titre"),
            Map.entry("theme", "theme"),
            Map.entry("objectif", "objectif"),
            Map.entry("date", "date"),
            Map.entry("heurDebut", "heurDebut"),
            Map.entry("heurFin", "heurFin"),
            Map.entry("lieu", "lieu"),
            Map.entry("participants", "participants"),
            Map.entry("cout", "cout")
    );




    @Override
    public AtelierDTO createAtelier(AtelierDTO dto, Map<String, MultipartFile> files) {
        var entity = atelierMapper.asEntity(dto);
        var entitySave = atelierRepository.save(entity);
        return atelierMapper.asDto(entitySave);
    }


    @Override
    public AtelierDTO updateAtelier(AtelierDTO dto, Map<String, MultipartFile> files) {
        var entityUpdate = atelierMapper.asEntity(dto);
        var updatedEntity = atelierRepository.save(entityUpdate);
        return atelierMapper.asDto(updatedEntity);
    }

    @Override
    public void deleteAtelier(Long id) {
        if (!atelierRepository.existsById(id)) {
            throw new RuntimeException("Atelier not found");
        }
        atelierRepository.deleteById(id);
    }

    @Override
    public AtelierDTO getAtelierById(Long id) {
        return atelierRepository.findById(id)
                .map(atelierMapper::asDto)
                .orElseThrow(() -> new RuntimeException("Atelier not found with id: " + id));
    }

    @Override
    public Page<AtelierDTO> getAllAteliers(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        Pageable effectivePageable = applySorting(pageable, searchParams);
        return atelierRepository.findAll(booleanBuilder, effectivePageable)
                .map(atelierMapper::asDto);
    }

    @Override
    public void exportAteliers(PrintWriter writer) {
        var ateliers = atelierRepository.findAll().stream()
                .map(atelierMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            new StatefulBeanToCsvBuilder<AtelierExcelDTO>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build()
                    .write(ateliers);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error exporting to CSV", e);
            throw new RuntimeException("Error exporting to CSV", e);
        }
    }

    @Override
    public List<AtelierDTO> importAteliers(List<AtelierDTO> dtos) {
        List<AtelierEntity> entities = dtos.stream()
                .map(atelierMapper::asEntity)
                .collect(Collectors.toList());
        List<AtelierEntity> savedEntities = atelierRepository.saveAll(entities);
        return savedEntities.stream()
                .map(atelierMapper::asDto)
                .collect(Collectors.toList());
    }


    private Pageable applySorting(Pageable pageable, Map<String, String> params) {
        if (pageable != null && pageable.getSort() != null && pageable.getSort().isSorted()) {
            Sort normalized = normalizeSort(pageable.getSort());
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), normalized);
        }
        String sortBy = params != null ? params.get("sort") : null;
        if (sortBy == null) {
            sortBy = params != null ? params.get("sortBy") : null;
        }
        String direction = params != null ? params.getOrDefault("direction", "asc") : "asc";

        if (sortBy != null) {
            String mapped = SORT_MAPPING.getOrDefault(sortBy.split(",")[0], sortBy.split(",")[0]);
            Sort sort = Sort.by(("desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC), mapped);
            return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("id")));
    }

    private Sort normalizeSort(Sort sort) {
        List<Sort.Order> normalizedOrders = sort.get().map(order -> {
            String mappedProperty = SORT_MAPPING.getOrDefault(order.getProperty(), order.getProperty());
            return new Sort.Order(order.getDirection(), mappedProperty);
        }).collect(Collectors.toList());
        return Sort.by(normalizedOrders);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QAtelierEntity.atelierEntity;
            if (StringUtils.hasText(searchParams.get("titre")))
                booleanBuilder.and(qEntity.titre.containsIgnoreCase(searchParams.get("titre")));
            if (StringUtils.hasText(searchParams.get("theme")))
                booleanBuilder.and(qEntity.theme.containsIgnoreCase(searchParams.get("theme")));

        }
    }
}