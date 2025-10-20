package com.webgram.dgpsn.services.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.entities.enums.DetailType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ImpactsAndObjectiveMapper;
import com.webgram.dgpsn.models.ImpactsAndObjectiveDTO;
import com.webgram.dgpsn.repositories.ImpactsAndObjectiveRepository;
import com.webgram.dgpsn.services.ImpactsAndObjectiveService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ImpactsAndObjectiveImpl implements ImpactsAndObjectiveService {
    private final ImpactsAndObjectiveRepository impactsAndObjectiveRepository;
    private final ImpactsAndObjectiveMapper impactsAndObjectiveMapper;

    @Override
    public ImpactsAndObjectiveDTO create(ImpactsAndObjectiveDTO impactsAndObjectiveDTO) {
        var impactsAndObjective = impactsAndObjectiveRepository.save(impactsAndObjectiveMapper.asEntity(impactsAndObjectiveDTO));

        log.info("label successfully added {}", impactsAndObjective.getId());

        return impactsAndObjectiveMapper.asDto(impactsAndObjective);
    }

    @Override
    public ImpactsAndObjectiveDTO update(ImpactsAndObjectiveDTO impactsAndObjectiveDTO) {
        var impactsAndObjective = impactsAndObjectiveMapper.asEntity(impactsAndObjectiveDTO);

        var updatedImpactsAndObjective = impactsAndObjectiveMapper.asDto(impactsAndObjectiveRepository.save(impactsAndObjective));

        log.info("label successfully updated {} ", updatedImpactsAndObjective.getId());

        return updatedImpactsAndObjective;
    }

    @Override
    public ImpactsAndObjectiveDTO read(Long impactsAndObjectiveId) {
        var impactsAndObjective = impactsAndObjectiveRepository
                .findById(impactsAndObjectiveId)
                .orElseThrow(()-> new ResourceNotFoundException("impact ou objectif", impactsAndObjectiveId));

        log.info("reading Impact or objective id {}", impactsAndObjectiveId);

        return impactsAndObjectiveMapper.asDto(impactsAndObjective);
    }

    @Override
    public void delete(Long impactsAndObjectiveId) {
        try {
            impactsAndObjectiveRepository.deleteById(impactsAndObjectiveId);
            log.info("The Impact or objective id {} is deleted", impactsAndObjectiveId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<ImpactsAndObjectiveDTO> readAll(Pageable pageable, DetailType detailType, String description, Long managementUnitId) {
        return impactsAndObjectiveRepository
                .readByFiltering(pageable, detailType, description, managementUnitId)
                .map(impactsAndObjectiveMapper::asDto);
    }
}
