package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.StationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.StationMapper;
import com.webgram.dgpsn.models.StationDTO;
import com.webgram.dgpsn.repositories.StationRepository;
import com.webgram.dgpsn.services.StationService;
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
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    private static final String STATION_NOT_FOUND_MESSAGE = "Station not found with ID: {}";

    @Override
    @Journal(actionType = ActionType.ADD_STATION)
    public StationDTO create(StationDTO stationDTO) {
        StationEntity entity = stationMapper.asEntity(stationDTO);
        StationEntity savedStation = stationRepository.save(entity);
        log.info("Station added successfully: {}", savedStation.getId());
        return stationMapper.asDto(savedStation);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_STATION)
    public StationDTO update(StationDTO stationDTO) {
        if (!stationRepository.existsById(stationDTO.getId())) {
            throw new ResourceNotFoundException("Station Id", stationDTO.getId());
        }
        StationEntity entity = stationMapper.asEntity(stationDTO);
        StationEntity updatedStation = stationRepository.save(entity);
        log.info("Station updated successfully: {}", updatedStation.getId());
        return stationMapper.asDto(updatedStation);
    }

    @Override
    @Journal(actionType = ActionType.READ_STATION)
    public StationDTO read(Long stationId) {
        StationEntity entity = stationRepository
                .findById(stationId)
                .orElseThrow(() -> new ResourceNotFoundException("Station Id", stationId));
        log.info("Reading station ID: {}", stationId);
        return stationMapper.asDto(entity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_STATION)
    public void delete(Long stationId) {
        try {
            stationRepository.deleteById(stationId);
            log.info("Station with ID {} deleted", stationId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Station Id", stationId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_STATION)
    public Page<StationDTO> readAll(
            Pageable pageable,
            String code,
            String name,
            Long typeId,
            Long regionId,
            Long departementId,
            String sortBy,
            Boolean ascending
    ) {
        return stationRepository
                .readAllByFiltering(pageable, code, name, typeId, regionId, departementId, sortBy, ascending)
                .map(stationMapper::asDto);
    }
}