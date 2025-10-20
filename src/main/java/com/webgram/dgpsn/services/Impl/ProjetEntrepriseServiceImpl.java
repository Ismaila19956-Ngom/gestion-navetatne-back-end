package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ProjetEntrepriseMapper;
import com.webgram.dgpsn.models.ProjetEntrepriseDTO;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.repositories.ProjetEntrepriseRepository;
import com.webgram.dgpsn.services.ProjetEntrepriseService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProjetEntrepriseServiceImpl implements ProjetEntrepriseService {
    private final ProjetEntrepriseRepository projetEntrepriseRepository;
    private final ProjetEntrepriseMapper projetEntrepriseMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public ProjetEntrepriseDTO create(ProjetEntrepriseDTO projetEntrepriseDTO) {
         var savedProjetEntreprise = projetEntrepriseRepository
                 .save(projetEntrepriseMapper.asEntity(projetEntrepriseDTO));

        log.info("actorProject successfully added {}", savedProjetEntreprise);

        return projetEntrepriseMapper.asDto(savedProjetEntreprise);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public ProjetEntrepriseDTO update(ProjetEntrepriseDTO projetEntrepriseDTO) {
        var ProjectEntrepriseEntity = projetEntrepriseMapper.asEntity(projetEntrepriseDTO);
        var updatedProjectEntrepise = projetEntrepriseMapper.asDto(projetEntrepriseRepository.save(ProjectEntrepriseEntity));

        log.info("actorProject successfully updated {} ", updatedProjectEntrepise.getId());

        return updatedProjectEntrepise;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public ProjetEntrepriseDTO read(Long projetEntrepriseId) {
        var projetEntity = projetEntrepriseRepository
                .findById(projetEntrepriseId)
                .orElseThrow(()-> new ResourceNotFoundException("ProjetEntreprise", projetEntrepriseId));

        log.info("reading Project entreprise id {}", projetEntrepriseId);

        return projetEntrepriseMapper.asDto(projetEntity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long ProjetEntrepriseId) {
        try {
            projetEntrepriseRepository.deleteById(ProjetEntrepriseId);
            log.info("The Project entreprise id {} is deleted", ProjetEntrepriseId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public Page<ProjetEntrepriseDTO> readAll(Pageable pageable, Long projectId, Long entrepriseId, Long roleEntrepriseId,Long flagId) {
        return projetEntrepriseRepository
                .readAllByFiltering(pageable, projectId, entrepriseId,roleEntrepriseId,flagId)
                .map(projetEntrepriseMapper::asDto);
    }


}
