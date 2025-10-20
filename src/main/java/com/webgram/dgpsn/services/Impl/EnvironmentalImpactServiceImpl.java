package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.EnvironmentalImpactEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EnvironmentalImpactMapper;
import com.webgram.dgpsn.models.EnvironmentalImpactDTO;
import com.webgram.dgpsn.repositories.EnvironmentalImpactRepository;
import com.webgram.dgpsn.services.EnvironmentalImpactService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EnvironmentalImpactServiceImpl implements EnvironmentalImpactService {
    private final EnvironmentalImpactRepository environmentalImpactRepository;
    private final EnvironmentalImpactMapper environmentalImpactMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ENV_IMPACT)
    public EnvironmentalImpactDTO create(EnvironmentalImpactDTO environmentalImpactDTO) {
         EnvironmentalImpactEntity savedEnvImpact = environmentalImpactRepository.save(environmentalImpactMapper.asEntity(environmentalImpactDTO));

        log.info("Impact environnemental ajouté avec succès {}", savedEnvImpact);

        return environmentalImpactMapper.asDto(savedEnvImpact);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ENV_IMPACT)
    public EnvironmentalImpactDTO update(EnvironmentalImpactDTO environmentalImpactDTO) {
        try{
            if(environmentalImpactRepository.existsById(environmentalImpactDTO.getId())) {
                var impactEnvir = environmentalImpactMapper.asEntity(environmentalImpactDTO);

                var updatedImpactEnvir = environmentalImpactMapper.asDto(environmentalImpactRepository.save(impactEnvir));

                log.info("Impact environnemental modifié avec succès {} ", impactEnvir.getId());

                return updatedImpactEnvir;
            } else {
                throw new ResourceNotFoundException("Impact environnemental Id ", environmentalImpactDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Impact environnemental Id ", environmentalImpactDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_IMPACT)
    public EnvironmentalImpactDTO read(Long environmentalImpactId) {
        var impactEnvir = environmentalImpactRepository
                .findById(environmentalImpactId)
                .orElseThrow(()-> new ResourceNotFoundException("Impact environnemental Id ", environmentalImpactId));

        log.info("reading impact environnemental id {}", environmentalImpactId);

        return environmentalImpactMapper.asDto(impactEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ENV_IMPACT)
    public void delete(Long environmentalImpactId) {
        try {
            environmentalImpactRepository.deleteById(environmentalImpactId);
            log.info("Impact environnemental avec l'id {} a été supprimé", environmentalImpactId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("environmental Impact Id", environmentalImpactId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ENV_IMPACT)
    public Page<EnvironmentalImpactDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long projetId,
            String sortBy,
            Boolean ascending
    ) {
        return environmentalImpactRepository
                .readAllByFiltering(pageable, code, libelle, source, natureImpact, importanceImpact, startDate, endDate,categorieId,projetId,sortBy,ascending)
                .map(environmentalImpactMapper::asDto);
    }

}
