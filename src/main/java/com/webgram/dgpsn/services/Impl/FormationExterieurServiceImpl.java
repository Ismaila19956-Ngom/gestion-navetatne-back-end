package com.webgram.dgpsn.services.Impl;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.FormationExterieurEntity;
import com.webgram.dgpsn.entities.QFormationExterieurEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.mappers.FormationExterieurMapper;
import com.webgram.dgpsn.models.FormationExterieurDTO;
import com.webgram.dgpsn.repositories.FormationExterieurRepository;
import com.webgram.dgpsn.services.FormationExterieurService;
import com.webgram.dgpsn.services.modelExcel.FormationExterieurExcelDTO;
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
public class FormationExterieurServiceImpl implements FormationExterieurService {

    private final FormationExterieurRepository formationExterieurRepository;
    private final FormationExterieurMapper formationExterieurMapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("titreFormation", "titreFormation"),
            Map.entry("organismeFormateur", "organismeFormateur"),
            Map.entry("dateDebut", "dateDebut"),
            Map.entry("statut", "statut")
    );




    @Override
    public FormationExterieurDTO createFormationExterieur(FormationExterieurDTO dto, Map<String, MultipartFile> files) {
        var entity = formationExterieurMapper.asEntity(dto);
        var entitySave = formationExterieurRepository.save(entity);
        return formationExterieurMapper.asDto(entitySave);
    }


    @Override
    public FormationExterieurDTO updateFormationExterieur(FormationExterieurDTO dto, Map<String, MultipartFile> files) {
        var entityUpdate = formationExterieurMapper.asEntity(dto);
        var updatedEntity = formationExterieurRepository.save(entityUpdate);
        return formationExterieurMapper.asDto(updatedEntity);
    }

    @Override
    public void deleteFormationExterieur(Long id) {
        if (!formationExterieurRepository.existsById(id)) {
            throw new RuntimeException("FormationExterieur not found");
        }
        formationExterieurRepository.deleteById(id);
    }

    @Override
    public FormationExterieurDTO getFormationExterieurById(Long id) {
        return formationExterieurRepository.findById(id)
                .map(formationExterieurMapper::asDto)
                .orElseThrow(() -> new RuntimeException("FormationExterieur not found with id: " + id));
    }

    @Override
    public Page<FormationExterieurDTO> getAllFormationExterieurs(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        Pageable effectivePageable = applySorting(pageable, searchParams);
        return formationExterieurRepository.findAll(booleanBuilder, effectivePageable)
                .map(formationExterieurMapper::asDto);
    }

    @Override
    public void exportFormationExterieurs(PrintWriter writer) {
        var formationExterieurs = formationExterieurRepository.findAll().stream()
                .map(formationExterieurMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            new StatefulBeanToCsvBuilder<FormationExterieurExcelDTO>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build()
                    .write(formationExterieurs);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error exporting to CSV", e);
            throw new RuntimeException("Error exporting to CSV", e);
        }
    }

    @Override
    public List<FormationExterieurDTO> importFormationExterieurs(List<FormationExterieurDTO> dtos) {
        List<FormationExterieurEntity> entities = dtos.stream()
                .map(formationExterieurMapper::asEntity)
                .collect(Collectors.toList());
        List<FormationExterieurEntity> savedEntities = formationExterieurRepository.saveAll(entities);
        return savedEntities.stream()
                .map(formationExterieurMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public FormationExterieurDTO updateStatut(Long id, Statut statut) {
        FormationExterieurEntity entity = formationExterieurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FormationExterieur with ID=" + id + " not found."));
        entity.setStatut(statut);
        entity = formationExterieurRepository.save(entity);
        return formationExterieurMapper.asDto(entity);
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
            var qEntity = QFormationExterieurEntity.formationExterieurEntity;
            if (StringUtils.hasText(searchParams.get("titreFormation")))
                booleanBuilder.and(qEntity.titreFormation.containsIgnoreCase(searchParams.get("titreFormation")));
            if (StringUtils.hasText(searchParams.get("organismeFormateur")))
                booleanBuilder.and(qEntity.organismeFormateur.containsIgnoreCase(searchParams.get("organismeFormateur")));

            if (StringUtils.hasText(searchParams.get("statut"))) {
                try {
                    booleanBuilder.and(qEntity.statut.eq(Statut.valueOf(searchParams.get("statut"))));
                } catch (IllegalArgumentException e) { log.error("Invalid enum value for statut", e); }
            }
        }
    }
}
