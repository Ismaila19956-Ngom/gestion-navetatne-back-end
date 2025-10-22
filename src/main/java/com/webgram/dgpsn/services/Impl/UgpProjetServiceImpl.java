package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.exceptions.ResourceAlreadyExistException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.UgpProjetMapper;
import com.webgram.dgpsn.models.UgpProjetDTO;
import com.webgram.dgpsn.repositories.UgpProjetRepository;
import com.webgram.dgpsn.services.RoleService;
import com.webgram.dgpsn.services.UgpProjetService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UgpProjetServiceImpl implements UgpProjetService {
    private final UgpProjetRepository ugpProjetRepository;
    private final UgpProjetMapper ugpProjetMapper;
    private final RoleService roleService;

    @Override
    @Journal(actionType = ActionType.CREATE_UGP)
    public UgpProjetDTO create(UgpProjetDTO ugpProjetDTO) {
        var addedRoles = ugpProjetRepository.findByProjet(ManagementUnitEntity.builder().id(ugpProjetDTO.getProjetId()).build())
                .stream()
                .map(udpProjetEntity -> udpProjetEntity.getUgpRole())
                .collect(Collectors.toList());
        var roleToAdd = roleService.readRole(ugpProjetDTO.getUgpRoleId());

        addedRoles.forEach(role -> {
            if (role.getId().equals(roleToAdd.getId())) {
                throw new ResourceAlreadyExistException("Ce role existe déja pour ce projet");
            }
        });

         var savedUgpProjet = ugpProjetRepository
                 .save(ugpProjetMapper.asEntity(ugpProjetDTO));

        log.info("ugpProject successfully added {}", savedUgpProjet);

        return ugpProjetMapper.asDto(savedUgpProjet);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_UGP)
    public UgpProjetDTO update(UgpProjetDTO ugpProjetDTO) {
        var ugpProjectEntity = ugpProjetMapper.asEntity(ugpProjetDTO);

        var updatedUgpProject = ugpProjetMapper.asDto(ugpProjetRepository.save(ugpProjectEntity));

        log.info("ugpProject successfully updated {} ", updatedUgpProject.getId());

        return updatedUgpProject;
    }

    @Override
    @Journal(actionType = ActionType.READ_UGP)
    public UgpProjetDTO read(Long ugpProjetId) {
        var ugpProjet = ugpProjetRepository
                .findById(ugpProjetId)
                .orElseThrow(()-> new ResourceNotFoundException("UgpProjet", ugpProjetId));

        log.info("reading ugpProject id {}", ugpProjetId);

        return ugpProjetMapper.asDto(ugpProjet);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_UGP)
    public void delete(Long ugpProjetId) {
        try {
            ugpProjetRepository.deleteById(ugpProjetId);
            log.info("The actorProject id {} is deleted", ugpProjetId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_UGP)
    public Page<UgpProjetDTO> readAll(Pageable pageable, Boolean existed, Boolean occupied, String status,  Long projectId, Long ugpRoleId) {
        return ugpProjetRepository
                .readAllByFiltering(pageable, projectId, existed, occupied, status, ugpRoleId)
                .map(ugpProjetMapper::asDto);
    }
}
