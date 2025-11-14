package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QFormationExterieurEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.mappers.FormationExterieurMapper;
import com.webgram.dgpsn.models.FormationExterieurDTO;
import com.webgram.dgpsn.repositories.FormationExterieurRepository;
import com.webgram.dgpsn.services.FormationExterieurService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FormationExterieurServiceImpl implements FormationExterieurService {

    private final FormationExterieurRepository formationExterieurRepository;
    private final FormationExterieurMapper formationExterieurMapper;

    @Override
    public FormationExterieurDTO create(FormationExterieurDTO dto) {
        var entity = formationExterieurMapper.asEntity(dto);
        var entitySave = formationExterieurRepository.save(entity);
        log.info("FormationExterieur saved successfully {}", entitySave.getId());
        return formationExterieurMapper.asDto(entitySave);
    }

    @Override
    public FormationExterieurDTO update(FormationExterieurDTO dto) {
        read(dto.getId()); // Check for existence
        var entityUpdate = formationExterieurMapper.asEntity(dto);
        var updatedEntity = formationExterieurRepository.save(entityUpdate);
        log.info("FormationExterieur updated successfully {}", updatedEntity.getId());
        return formationExterieurMapper.asDto(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        try {
            formationExterieurRepository.deleteById(id);
            log.info("The formationExterieur id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public FormationExterieurDTO read(Long id) {
        return formationExterieurRepository.findById(id)
                .map(formationExterieurMapper::asDto)
                .orElseThrow(() -> new RuntimeException("FormationExterieur not found with id: " + id));
    }

    @Override
    public Page<FormationExterieurDTO> readAll(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return formationExterieurRepository.findAll(booleanBuilder, pageable)
                .map(formationExterieurMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QFormationExterieurEntity.formationExterieurEntity;
            if (searchParams.containsKey("titreFormation"))
                booleanBuilder.and(qEntity.titreFormation.containsIgnoreCase(searchParams.get("titreFormation")));

            if (searchParams.containsKey("organismeFormateur"))
                booleanBuilder.and(qEntity.organismeFormateur.containsIgnoreCase(searchParams.get("organismeFormateur")));

            if (searchParams.containsKey("agentId"))
                    booleanBuilder.and(qEntity.agent.id.eq(Long.valueOf(searchParams.get("agentId"))));



            if (searchParams.containsKey("lieu"))
                booleanBuilder.and(qEntity.lieu.containsIgnoreCase(searchParams.get("lieu")));


            if (searchParams.containsKey("statut"))
                booleanBuilder.and(qEntity.statut.eq(Statut.valueOf(searchParams.get("statut"))));

            if (searchParams.containsKey("dureeJours"))
                booleanBuilder.and(qEntity.dureeJours.eq(Long.valueOf(searchParams.get("dureeJours"))));


            if (searchParams.containsKey("coutTotal"))
                booleanBuilder.and(qEntity.coutTotal.eq(Double.valueOf(searchParams.get("coutTotal"))));

            if (searchParams.containsKey("dateDebut"))
                booleanBuilder.and(qEntity.dateDebut.eq(LocalDateTime.parse(searchParams.get("dateDebut"))));

            if (searchParams.containsKey("dateFin"))
                booleanBuilder.and(qEntity.dateFin.eq(LocalDateTime.parse(searchParams.get("dateFin"))));


        }
        }
}