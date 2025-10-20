package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.GouvernanceConformiteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.GouvernanceConformiteMapper;
import com.webgram.dgpsn.models.GouvernanceConformiteDTO;
import com.webgram.dgpsn.repositories.GouvernanceConformiteRepository;
import com.webgram.dgpsn.services.GouvernanceConformiteService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GouvernanceConformiteServiceImpl implements GouvernanceConformiteService {
    private final GouvernanceConformiteRepository gouvernanceConformiteRepository;
    private final GouvernanceConformiteMapper gouvernanceConformiteMapper;

    @Override
    @Journal(actionType = ActionType.ADD_GOUV_CONFORMITE)
    public GouvernanceConformiteDTO create(GouvernanceConformiteDTO gouvernanceConformiteDTO) {
         GouvernanceConformiteEntity savedEnvConformite = gouvernanceConformiteRepository.save(gouvernanceConformiteMapper.asEntity(gouvernanceConformiteDTO));

        log.info("Conformite gouvernance ajouté avec succès {}", savedEnvConformite);

        return gouvernanceConformiteMapper.asDto(savedEnvConformite);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_GOUV_CONFORMITE)
    public GouvernanceConformiteDTO update(GouvernanceConformiteDTO gouvernanceConformiteDTO) {
        try{
            if(gouvernanceConformiteRepository.existsById(gouvernanceConformiteDTO.getId())) {
                var conformiteEnvir = gouvernanceConformiteMapper.asEntity(gouvernanceConformiteDTO);

                var updatedConformiteEnvir = gouvernanceConformiteMapper.asDto(gouvernanceConformiteRepository.save(conformiteEnvir));

                log.info("Conformite gouvernance modifié avec succès {} ", conformiteEnvir.getId());

                return updatedConformiteEnvir;
            } else {
                throw new ResourceNotFoundException("Conformite gouvernance Id ", gouvernanceConformiteDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Conformite gouvernance Id ", gouvernanceConformiteDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_GOUV_CONFORMITE)
    public GouvernanceConformiteDTO read(Long gouvernanceConformiteId) {
        var conformiteEnvir = gouvernanceConformiteRepository
                .findById(gouvernanceConformiteId)
                .orElseThrow(()-> new ResourceNotFoundException("Conformite gouvernance Id ", gouvernanceConformiteId));

        log.info("reading conformite gouvernance id {}", gouvernanceConformiteId);

        return gouvernanceConformiteMapper.asDto(conformiteEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_GOUV_CONFORMITE)
    public void delete(Long gouvernanceConformiteId) {
        try {
            gouvernanceConformiteRepository.deleteById(gouvernanceConformiteId);
            log.info("Conformite gouvernance avec l'id {} a été supprimé", gouvernanceConformiteId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("gouvernance Conformite Id", gouvernanceConformiteId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_GOUV_CONFORMITE)
    public Page<GouvernanceConformiteDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String etat,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeReferenceId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        return gouvernanceConformiteRepository
                .readAllByFiltering(pageable, code, libelle, source, etat, startDate, endDate, categorieId, typeReferenceId, projetId,sortBy,ascending)
                .map(gouvernanceConformiteMapper::asDto);
    }

}
