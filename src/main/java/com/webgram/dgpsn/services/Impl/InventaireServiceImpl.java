package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QInventaireEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.InventaireMapper;
import com.webgram.dgpsn.models.InventaireDTO;
import com.webgram.dgpsn.repositories.InventaireRepository;
import com.webgram.dgpsn.services.InventaireService;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventaireServiceImpl implements InventaireService {
    private final InventaireRepository inventaireRepository;
    private final InventaireMapper inventaireMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public InventaireDTO create(InventaireDTO inventaireDTO) {
        var savedInventaire = inventaireRepository.save(inventaireMapper.asEntity(inventaireDTO));
        log.info("Inventaire successfully added {}", savedInventaire);
        return inventaireMapper.asDto(savedInventaire);
    }


    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public InventaireDTO update(InventaireDTO inventaireDTO) {
        var inventaireSaved = inventaireMapper.asEntity(inventaireDTO);
        var updatedInventaire = inventaireMapper.asDto(inventaireRepository.save(inventaireSaved));
        log.info("Inventaire successfully updated {}", updatedInventaire.getId());
        return updatedInventaire;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public InventaireDTO read(Long inventaireId) {
        var inventaire = inventaireRepository
                .findById(inventaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventaire", inventaireId));
        log.info("Reading inventaire id {}", inventaireId);
        return inventaireMapper.asDto(inventaire);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long inventaireId) {
        try {
            inventaireRepository.deleteById(inventaireId);
            log.info("The inventaire id {} is deleted", inventaireId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<InventaireDTO> readAllInventaires(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return inventaireRepository.findAll(booleanBuilder, pageable)
                .map(inventaireMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QInventaireEntity.inventaireEntity;
            if (searchParams.containsKey("nomPeriode"))
                booleanBuilder.and(qEntity.nomPeriode.eq(searchParams.get("nomPeriode")));
            if (searchParams.containsKey("dateDebut"))
                booleanBuilder.and(qEntity.dateDebut.eq(LocalDate.parse(searchParams.get("dateDebut"))));
            if (searchParams.containsKey("dateFin"))
                booleanBuilder.and(qEntity.dateFin.eq(LocalDate.parse(searchParams.get("dateFin"))));
            if (searchParams.containsKey("etat"))
                booleanBuilder.and(qEntity.etat.eq(searchParams.get("etat")));
            if (searchParams.containsKey("commentaires"))
                booleanBuilder.and(qEntity.commentaires.eq(searchParams.get("commentaires")));
        }
    }
}