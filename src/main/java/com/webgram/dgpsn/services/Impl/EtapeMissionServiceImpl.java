package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.EtapeMissionMapper;
import com.webgram.dgpsn.models.EtapeMissionDTO;
import com.webgram.dgpsn.repositories.AssignmentRepository;
import com.webgram.dgpsn.repositories.EtapeMissionRepository;
import com.webgram.dgpsn.services.EtapeMissionService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EtapeMissionServiceImpl implements EtapeMissionService {

    private final EtapeMissionRepository etapeMissionRepository;
    private final EtapeMissionMapper etapeMissionMapper;
    private final AssignmentRepository assignmentRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType= ActionType.ADD_ETAPE)
    public  EtapeMissionDTO create( EtapeMissionDTO etapeMission) {
         var savedEtapeMission = etapeMissionRepository.save(etapeMissionMapper.asEntity(etapeMission));

        log.info("EtapeMission successfully added {}", savedEtapeMission);

        return etapeMissionMapper.asDto(savedEtapeMission);
    }
    @Override
    @Journal(actionType=ActionType.UPDATE_ETAPE)
    public  EtapeMissionDTO update( EtapeMissionDTO etapeMission) {
        try{
            if(etapeMissionRepository.existsById(etapeMission.getId())) {
                var etape = etapeMissionMapper.asEntity(etapeMission);

                var updatedEtape = etapeMissionRepository.save(etape);

                log.info("EtapeMission successfully updated {} ", updatedEtape.getId());

                return etapeMissionMapper.asDto(updatedEtape);
            } else {
                throw new ResourceNotFoundException("EtapeMission", etapeMission.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("EtapeMission", etapeMission.getId());
        }
    }

    @Override
    @Journal(actionType=ActionType.READ_ETAPE)
    public  EtapeMissionDTO read(Long etapeId) {
        var etape = etapeMissionRepository
                .findById(etapeId)
                .orElseThrow(()-> new ResourceNotFoundException("EtapeMission", etapeId));

        log.info("reading EtapeMission id {}", etapeId);

        return etapeMissionMapper.asDto(etape);
    }

    @Override
    @Journal(actionType=ActionType.DELETE_ETAPE)
    public void delete(Long etapeId) {
        try {
            etapeMissionRepository.deleteById(etapeId);
            log.info("The etape id {} is deleted", etapeId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("etapeId", etapeId);
        }
    }
    @Override
    @Journal(actionType=ActionType.READ_ETAPE)
    public Page<EtapeMissionDTO> readAll(
            Pageable pageable,
            String libelle,
            Date plannedStartDate,
            Date planedEndDate,
            Date actualStartDate,
            Date actualEndDate,
            Long statusId,
            Long responsableId,
            Long assignmentId

    ) {
        return etapeMissionRepository
                .readAllByFilters(pageable, libelle
                        , plannedStartDate,
                        planedEndDate,
                        actualStartDate,
                        actualEndDate,
                        statusId,
                        responsableId,
                        assignmentId)
                .map(etapeMissionMapper::asDto);
    }
}
