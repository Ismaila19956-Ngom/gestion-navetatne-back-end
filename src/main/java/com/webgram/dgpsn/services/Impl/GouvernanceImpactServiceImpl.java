package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.GouvernanceImpactEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.GouvernanceImpactMapper;
import com.webgram.dgpsn.models.GouvernanceImpactDTO;
import com.webgram.dgpsn.repositories.GouvernanceImpactRepository;
import com.webgram.dgpsn.services.GouvernanceImpactService;
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
public class GouvernanceImpactServiceImpl implements GouvernanceImpactService {
    private final GouvernanceImpactRepository gouvernanceImpactRepository;
    private final GouvernanceImpactMapper gouvernanceImpactMapper;

    @Override
    @Journal(actionType = ActionType.ADD_GOUV_IMPACT)
    public GouvernanceImpactDTO create(GouvernanceImpactDTO gouvernanceImpactDTO) {
         GouvernanceImpactEntity savedEnvImpact = gouvernanceImpactRepository.save(gouvernanceImpactMapper.asEntity(gouvernanceImpactDTO));

        log.info("Impact gouvernance ajouté avec succès {}", savedEnvImpact);

        return gouvernanceImpactMapper.asDto(savedEnvImpact);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_GOUV_IMPACT)
    public GouvernanceImpactDTO update(GouvernanceImpactDTO gouvernanceImpactDTO) {
        try{
            if(gouvernanceImpactRepository.existsById(gouvernanceImpactDTO.getId())) {
                var impactEnvir = gouvernanceImpactMapper.asEntity(gouvernanceImpactDTO);

                var updatedImpactEnvir = gouvernanceImpactMapper.asDto(gouvernanceImpactRepository.save(impactEnvir));

                log.info("Impact gouvernance modifié avec succès {} ", impactEnvir.getId());

                return updatedImpactEnvir;
            } else {
                throw new ResourceNotFoundException("Impact gouvernance Id ", gouvernanceImpactDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Impact gouvernance Id ", gouvernanceImpactDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_GOUV_IMPACT)
    public GouvernanceImpactDTO read(Long gouvernanceImpactId) {
        var impactEnvir = gouvernanceImpactRepository
                .findById(gouvernanceImpactId)
                .orElseThrow(()-> new ResourceNotFoundException("Impact gouvernance Id ", gouvernanceImpactId));

        log.info("reading impact gouvernance id {}", gouvernanceImpactId);

        return gouvernanceImpactMapper.asDto(impactEnvir);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_GOUV_IMPACT)
    public void delete(Long gouvernanceImpactId) {
        try {
            gouvernanceImpactRepository.deleteById(gouvernanceImpactId);
            log.info("Impact gouvernance avec l'id {} a été supprimé", gouvernanceImpactId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("gouvernance Impact Id", gouvernanceImpactId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_GOUV_IMPACT)
    public Page<GouvernanceImpactDTO> readAll(
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
        return gouvernanceImpactRepository
                .readAllByFiltering(pageable, code, libelle, source, natureImpact, importanceImpact, startDate, endDate,categorieId,projetId,sortBy,ascending)
                .map(gouvernanceImpactMapper::asDto);
    }

}
