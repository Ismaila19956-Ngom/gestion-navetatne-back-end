package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.EnvironmentalIndicateurEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EnvironmentalIndicateurMapper;
import com.webgram.dgpsn.models.EnvironmentalIndicateurDTO;
import com.webgram.dgpsn.repositories.EnvironmentalIndicateurRepository;
import com.webgram.dgpsn.services.EnvironmentalIndicateurService;
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
public class EnvironmentalIndicateurServiceImpl implements EnvironmentalIndicateurService {
    private final EnvironmentalIndicateurRepository environmentalIndicateurRepository;
    private final EnvironmentalIndicateurMapper environmentalIndicateurMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ENV_INDICATEUR)
    public EnvironmentalIndicateurDTO create(EnvironmentalIndicateurDTO environmentalIndicateurDTO) {
         EnvironmentalIndicateurEntity savedEnvIndicateur = environmentalIndicateurRepository.save(environmentalIndicateurMapper.asEntity(environmentalIndicateurDTO));

        log.info("Indicateur environnemental ajouté avec succès {}", savedEnvIndicateur);

        return environmentalIndicateurMapper.asDto(savedEnvIndicateur);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ENV_INDICATEUR)
    public EnvironmentalIndicateurDTO update(EnvironmentalIndicateurDTO environmentalIndicateurDTO) {
        try{
            if(environmentalIndicateurRepository.existsById(environmentalIndicateurDTO.getId())) {
                var indicateurEnvir = environmentalIndicateurMapper.asEntity(environmentalIndicateurDTO);

                var updatedIndicateurEnvir = environmentalIndicateurMapper.asDto(environmentalIndicateurRepository.save(indicateurEnvir));

                log.info("Indicateur environnemental modifié avec succès {} ", indicateurEnvir.getId());

                return updatedIndicateurEnvir;
            } else {
                throw new ResourceNotFoundException("Indicateur environnemental Id ", environmentalIndicateurDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Indicateur environnemental Id ", environmentalIndicateurDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_INDICATEUR)
    public EnvironmentalIndicateurDTO read(Long environmentalIndicateurId) {
        var indicateurEnvir = environmentalIndicateurRepository
                .findById(environmentalIndicateurId)
                .orElseThrow(()-> new ResourceNotFoundException("Indicateur environnemental Id ", environmentalIndicateurId));

        log.info("reading indicateur environnemental id {}", environmentalIndicateurId);

        return environmentalIndicateurMapper.asDto(indicateurEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ENV_INDICATEUR)
    public void delete(Long environmentalIndicateurId) {
        try {
            environmentalIndicateurRepository.deleteById(environmentalIndicateurId);
            log.info("Indicateur environnemental avec l'id {} a été supprimé", environmentalIndicateurId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("environmental Indicateur Id", environmentalIndicateurId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_INDICATEUR)
    public Page<EnvironmentalIndicateurDTO> readAll(
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
        return environmentalIndicateurRepository
                .readAllByFiltering(pageable, code, libelle, valeur, source, startDate, endDate, indicateurId, projetId,sortBy,ascending)
                .map(environmentalIndicateurMapper::asDto);
    }

}
