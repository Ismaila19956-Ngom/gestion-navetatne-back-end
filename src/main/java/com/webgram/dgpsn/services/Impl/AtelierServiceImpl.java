package com.webgram.dgpsn.services.Impl;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QAtelierEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.mappers.AtelierMapper;
import com.webgram.dgpsn.models.AtelierDTO;
import com.webgram.dgpsn.repositories.AtelierRepository;
import com.webgram.dgpsn.services.AtelierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AtelierServiceImpl implements AtelierService {

    private final AtelierRepository atelierRepository;
    private final AtelierMapper atelierMapper;

    @Override
    public AtelierDTO create(AtelierDTO dto) {
        var entity = atelierMapper.asEntity(dto);
        var entitySave = atelierRepository.save(entity);
        log.info("Atelier saved successfully {}", entitySave.getId());
        return atelierMapper.asDto(entitySave);
    }

    @Override
    public AtelierDTO update(AtelierDTO dto) {
        read(dto.getId()); // Check for existence
        var entityUpdate = atelierMapper.asEntity(dto);
        var updatedEntity = atelierRepository.save(entityUpdate);
        log.info("Atelier updated successfully {}", updatedEntity.getId());
        return atelierMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        try {
            atelierRepository.deleteById(id);
            log.info("The atelier id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public AtelierDTO read(Long id) {
        return atelierRepository.findById(id)
                .map(atelierMapper::asDto)
                .orElseThrow(() -> new RuntimeException("Atelier not found with id: " + id));
    }

    @Override
    public Page<AtelierDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return atelierRepository.findAll(booleanBuilder, pageable)
                .map(atelierMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QAtelierEntity.atelierEntity;

            if (searchParams.containsKey("titreAtelier"))
                booleanBuilder.and(qEntity.titreAtelier.containsIgnoreCase(searchParams.get("titreAtelier")));

            if (searchParams.containsKey("theme"))
                booleanBuilder.and(qEntity.theme.containsIgnoreCase(searchParams.get("theme")));

            if (searchParams.containsKey("objectif"))
                booleanBuilder.and(qEntity.objectif.containsIgnoreCase(searchParams.get("objectif")));

            if (searchParams.containsKey("dateAtelier"))
                booleanBuilder.and(qEntity.dateAtelier.eq(LocalDateTime.parse(searchParams.get("dateAtelier"))));

            if (searchParams.containsKey("heurDebut"))
                booleanBuilder.and(qEntity.heurDebut.eq(LocalDateTime.parse(searchParams.get("heurDebut"))));

            if (searchParams.containsKey("heurFin"))
                booleanBuilder.and(qEntity.heurFin.eq(LocalDateTime.parse(searchParams.get("heurFin"))));

            if (searchParams.containsKey("lieu"))
                booleanBuilder.and(qEntity.lieu.containsIgnoreCase(searchParams.get("lieu")));

            if (searchParams.containsKey("coutOrganisation"))
                booleanBuilder.and(qEntity.coutOrganisation.eq(Double.valueOf(searchParams.get("coutOrganisation"))));

            if (searchParams.containsKey("nombreParticipantsMax"))
                booleanBuilder.and(qEntity.nombreParticipantsMax.eq(Long.valueOf(searchParams.get("nombreParticipantsMax"))));

            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.eq(Statut.valueOf(searchParams.get("statut").toUpperCase())));
}
}
}