package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.GouvernanceIndicateurEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.GouvernanceIndicateurMapper;
import com.webgram.dgpsn.models.GouvernanceIndicateurDTO;
import com.webgram.dgpsn.repositories.GouvernanceIndicateurRepository;
import com.webgram.dgpsn.services.GouvernanceIndicateurService;
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
public class GouvernanceIndicateurServiceImpl implements GouvernanceIndicateurService {
    private final GouvernanceIndicateurRepository gouvernanceIndicateurRepository;
    private final GouvernanceIndicateurMapper gouvernanceIndicateurMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ENV_INDICATEUR)
    public GouvernanceIndicateurDTO create(GouvernanceIndicateurDTO gouvernanceIndicateurDTO) {
         GouvernanceIndicateurEntity savedEnvIndicateur = gouvernanceIndicateurRepository.save(gouvernanceIndicateurMapper.asEntity(gouvernanceIndicateurDTO));

        log.info("Indicateur gouvernance ajouté avec succès {}", savedEnvIndicateur);

        return gouvernanceIndicateurMapper.asDto(savedEnvIndicateur);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ENV_INDICATEUR)
    public GouvernanceIndicateurDTO update(GouvernanceIndicateurDTO gouvernanceIndicateurDTO) {
        try{
            if(gouvernanceIndicateurRepository.existsById(gouvernanceIndicateurDTO.getId())) {
                var indicateurEnvir = gouvernanceIndicateurMapper.asEntity(gouvernanceIndicateurDTO);

                var updatedIndicateurEnvir = gouvernanceIndicateurMapper.asDto(gouvernanceIndicateurRepository.save(indicateurEnvir));

                log.info("Indicateur gouvernance modifié avec succès {} ", indicateurEnvir.getId());

                return updatedIndicateurEnvir;
            } else {
                throw new ResourceNotFoundException("Indicateur gouvernance Id ", gouvernanceIndicateurDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Indicateur gouvernance Id ", gouvernanceIndicateurDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_INDICATEUR)
    public GouvernanceIndicateurDTO read(Long gouvernanceIndicateurId) {
        var indicateurEnvir = gouvernanceIndicateurRepository
                .findById(gouvernanceIndicateurId)
                .orElseThrow(()-> new ResourceNotFoundException("Indicateur gouvernance Id ", gouvernanceIndicateurId));

        log.info("reading indicateur gouvernance id {}", gouvernanceIndicateurId);

        return gouvernanceIndicateurMapper.asDto(indicateurEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ENV_INDICATEUR)
    public void delete(Long gouvernanceIndicateurId) {
        try {
            gouvernanceIndicateurRepository.deleteById(gouvernanceIndicateurId);
            log.info("Indicateur gouvernance avec l'id {} a été supprimé", gouvernanceIndicateurId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("gouvernance Indicateur Id", gouvernanceIndicateurId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_INDICATEUR)
    public Page<GouvernanceIndicateurDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String valeur,
            String source,
            String startDate,
            String endDate,
            Long indicateurId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        return gouvernanceIndicateurRepository
                .readAllByFiltering(pageable, code, libelle, valeur, source, startDate, endDate, indicateurId, projetId,sortBy,ascending)
                .map(gouvernanceIndicateurMapper::asDto);
    }

}
