package com.webgram.dgpsn.services.Impl;
import com.opencsv.CSVWriter;

import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.querydsl.core.BooleanBuilder;

import com.webgram.dgpsn.entities.MissionEntity;
import com.webgram.dgpsn.entities.QMissionEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.mappers.MissionMapper;
import com.webgram.dgpsn.models.MissionDTO;
import com.webgram.dgpsn.repositories.MissionRepository;
import com.webgram.dgpsn.services.Impl.modelExcelDTO.MissionExcelDTO;
import com.webgram.dgpsn.services.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.StatusTransformer;
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
public class MissionServiceImpl implements MissionService {

    private final MissionRepository missionRepository;
    private final MissionMapper missionMapper;

    private static final Map<String, String> SORT_MAPPING = Map.ofEntries(
            Map.entry("numeroOrdre", "numeroOrdre"),
            Map.entry("type", "type"),
            Map.entry("objet", "objet"),
            Map.entry("destination", "destination"),
            Map.entry("dateDebut", "dateDebut"),
            Map.entry("dateFin", "dateFin"),
            Map.entry("duree", "duree"),
            Map.entry("budget", "budget"),
            Map.entry("responsable", "responsable"),
            Map.entry("statut", "statut"),
            Map.entry("rapport", "rapport"),
            Map.entry("dateRapport", "dateRapport")
    );




    @Override
    public MissionDTO createMission(MissionDTO dto, Map<String, MultipartFile> files) {
        var entity = missionMapper.asEntity(dto);
        var entitySave = missionRepository.save(entity);
        return missionMapper.asDto(entitySave);
    }


    @Override
    public MissionDTO updateMission(MissionDTO dto, Map<String, MultipartFile> files) {
        var entityUpdate = missionMapper.asEntity(dto);
        var updatedEntity = missionRepository.save(entityUpdate);
        return missionMapper.asDto(updatedEntity);
    }

    @Override
    public void deleteMission(Long id) {
        if (!missionRepository.existsById(id)) {
            throw new RuntimeException("Mission not found");
        }
        missionRepository.deleteById(id);
    }

    @Override
    public MissionDTO getMissionById(Long id) {
        return missionRepository.findById(id)
                .map(missionMapper::asDto)
                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + id));
    }

    @Override
    public Page<MissionDTO> getAllMissions(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        Pageable effectivePageable = applySorting(pageable, searchParams);
        return missionRepository.findAll(booleanBuilder, effectivePageable)
                .map(missionMapper::asDto);
    }

    @Override
    public void exportMissions(PrintWriter writer) {
        var missions = missionRepository.findAll().stream()
                .map(missionMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            new StatefulBeanToCsvBuilder<MissionExcelDTO>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator(';')
                    .build()
                    .write(missions);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("Error exporting to CSV", e);
            throw new RuntimeException("Error exporting to CSV", e);
        }
    }

    @Override
    public List<MissionDTO> importMissions(List<MissionDTO> dtos) {
        List<MissionEntity> entities = dtos.stream()
                .map(missionMapper::asEntity)
                .collect(Collectors.toList());
        List<MissionEntity> savedEntities = missionRepository.saveAll(entities);
        return savedEntities.stream()
                .map(missionMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public MissionDTO updateStatut(Long id, Statut statut) {
        MissionEntity entity = missionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mission with ID=" + id + " not found."));
        entity.setStatut(statut);
        entity = missionRepository.save(entity);
        return missionMapper.asDto(entity);
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
            var qEntity = QMissionEntity.missionEntity;

            // Search logic for numeroOrdre
            if (StringUtils.hasText(searchParams.get("numeroOrdreMin"))) {
                try {
                    booleanBuilder.and(qEntity.numeroOrdre.goe((Integer) Integer.valueOf(searchParams.get("numeroOrdreMin"))));
                } catch (NumberFormatException e) { log.error("Cannot parse numeroOrdreMin", e); }
            }
            if (StringUtils.hasText(searchParams.get("numeroOrdreMax"))) {
                try {
                    booleanBuilder.and(qEntity.numeroOrdre.loe((Integer) Integer.valueOf(searchParams.get("numeroOrdreMax"))));
                } catch (NumberFormatException e) { log.error("Cannot parse numeroOrdreMax", e); }
            }
            if (StringUtils.hasText(searchParams.get("numeroOrdre"))) { // Equality
                try {
                    booleanBuilder.and(qEntity.numeroOrdre.eq((Integer) Integer.valueOf(searchParams.get("numeroOrdre"))));
                } catch (NumberFormatException e) { log.error("Cannot parse numeroOrdre for equality", e); }
            }
            if (StringUtils.hasText(searchParams.get("numeroOrdreGt"))) {
                try {
                    booleanBuilder.and(qEntity.numeroOrdre.gt((Integer) Integer.valueOf(searchParams.get("numeroOrdreGt"))));
                } catch (NumberFormatException e) { log.error("Cannot parse numeroOrdreGt", e); }
            }
            if (StringUtils.hasText(searchParams.get("numeroOrdreLt"))) {
                try {
                    booleanBuilder.and(qEntity.numeroOrdre.lt((Integer) Integer.valueOf(searchParams.get("numeroOrdreLt"))));
                } catch (NumberFormatException e) { log.error("Cannot parse numeroOrdreLt", e); }
            }
            if (StringUtils.hasText(searchParams.get("type")))
                booleanBuilder.and(qEntity.type.containsIgnoreCase(searchParams.get("type")));
            if (StringUtils.hasText(searchParams.get("objet")))
                booleanBuilder.and(qEntity.objet.containsIgnoreCase(searchParams.get("objet")));
            if (StringUtils.hasText(searchParams.get("destination")))
                booleanBuilder.and(qEntity.destination.containsIgnoreCase(searchParams.get("destination")));



            // Search logic for duree
            if (StringUtils.hasText(searchParams.get("dureeMin"))) {
                try {
                    booleanBuilder.and(qEntity.duree.goe((Integer) Integer.valueOf(searchParams.get("dureeMin"))));
                } catch (NumberFormatException e) { log.error("Cannot parse dureeMin", e); }
            }
            if (StringUtils.hasText(searchParams.get("dureeMax"))) {
                try {
                    booleanBuilder.and(qEntity.duree.loe((Integer) Integer.valueOf(searchParams.get("dureeMax"))));
                } catch (NumberFormatException e) { log.error("Cannot parse dureeMax", e); }
            }
            if (StringUtils.hasText(searchParams.get("duree"))) { // Equality
                try {
                    booleanBuilder.and(qEntity.duree.eq((Integer) Integer.valueOf(searchParams.get("duree"))));
                } catch (NumberFormatException e) { log.error("Cannot parse duree for equality", e); }
            }
            if (StringUtils.hasText(searchParams.get("dureeGt"))) {
                try {
                    booleanBuilder.and(qEntity.duree.gt((Integer) Integer.valueOf(searchParams.get("dureeGt"))));
                } catch (NumberFormatException e) { log.error("Cannot parse dureeGt", e); }
            }
            if (StringUtils.hasText(searchParams.get("dureeLt"))) {
                try {
                    booleanBuilder.and(qEntity.duree.lt((Integer) Integer.valueOf(searchParams.get("dureeLt"))));
                } catch (NumberFormatException e) { log.error("Cannot parse dureeLt", e); }
            }

            if (StringUtils.hasText(searchParams.get("statut"))) {
                try {
                    booleanBuilder.and(qEntity.statut.eq(Statut.valueOf(searchParams.get("statut"))));
                } catch (IllegalArgumentException e) { log.error("Invalid enum value for statut", e); }
            }

        }
    }
}
