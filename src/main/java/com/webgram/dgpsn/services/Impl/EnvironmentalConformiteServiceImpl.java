package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.EnvironmentalConformiteEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EnvironmentalConformiteMapper;
import com.webgram.dgpsn.models.EnvironmentalConformiteDTO;
import com.webgram.dgpsn.repositories.EnvironmentalConformiteRepository;
import com.webgram.dgpsn.services.EnvironmentalConformiteService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EnvironmentalConformiteServiceImpl implements EnvironmentalConformiteService {
    private final EnvironmentalConformiteRepository environmentalConformiteRepository;
    private final EnvironmentalConformiteMapper environmentalConformiteMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ENV_CONFORMITE)
    public EnvironmentalConformiteDTO create(EnvironmentalConformiteDTO environmentalConformiteDTO) {
         EnvironmentalConformiteEntity savedEnvConformite = environmentalConformiteRepository.save(environmentalConformiteMapper.asEntity(environmentalConformiteDTO));

        log.info("Conformite environnemental ajouté avec succès {}", savedEnvConformite);

        return environmentalConformiteMapper.asDto(savedEnvConformite);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ENV_CONFORMITE)
    public EnvironmentalConformiteDTO update(EnvironmentalConformiteDTO environmentalConformiteDTO) {
        try{
            if(environmentalConformiteRepository.existsById(environmentalConformiteDTO.getId())) {
                var conformiteEnvir = environmentalConformiteMapper.asEntity(environmentalConformiteDTO);

                var updatedConformiteEnvir = environmentalConformiteMapper.asDto(environmentalConformiteRepository.save(conformiteEnvir));

                log.info("Conformite environnemental modifié avec succès {} ", conformiteEnvir.getId());

                return updatedConformiteEnvir;
            } else {
                throw new ResourceNotFoundException("Conformite environnemental Id ", environmentalConformiteDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Conformite environnemental Id ", environmentalConformiteDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_CONFORMITE)
    public EnvironmentalConformiteDTO read(Long environmentalConformiteId) {
        var conformiteEnvir = environmentalConformiteRepository
                .findById(environmentalConformiteId)
                .orElseThrow(()-> new ResourceNotFoundException("Conformite environnemental Id ", environmentalConformiteId));

        log.info("reading conformite environnemental id {}", environmentalConformiteId);

        return environmentalConformiteMapper.asDto(conformiteEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ENV_CONFORMITE)
    public void delete(Long environmentalConformiteId) {
        try {
            environmentalConformiteRepository.deleteById(environmentalConformiteId);
            log.info("Conformite environnemental avec l'id {} a été supprimé", environmentalConformiteId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("environmental Conformite Id", environmentalConformiteId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_CONFORMITE)
    public Page<EnvironmentalConformiteDTO> readAll(
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
        return environmentalConformiteRepository
                .readAllByFiltering(pageable, code, libelle, source, etat, startDate, endDate, categorieId, typeReferenceId, projetId,sortBy,ascending)
                .map(environmentalConformiteMapper::asDto);
    }

}
