package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.StatusMapper;
import com.webgram.dgpsn.models.StatusDTO;
import com.webgram.dgpsn.repositories.StatusRepository;
import com.webgram.dgpsn.services.StatusService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    String STATUS_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id statut: {0}";

    @Override
    @Journal(actionType = ActionType.ADD_STATUTS)
    public StatusDTO createStatus(StatusDTO statusDTO) {
        var createdSatatus = statusRepository.save(statusMapper.asEntity(statusDTO));
        log.info("createdSatatus ok id {}", createdSatatus.getId());
        log.trace("createdSatatus ok  {}", createdSatatus);
        return statusMapper.asDto(createdSatatus);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_STATUTS)
    public StatusDTO updateStatus(StatusDTO statusDTO) {
        if(!statusRepository.existsById(statusDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STATUS_IDENTIFIER_NOT_FOUND_MESSAGE, statusDTO.getId()));
        }
        var status = statusMapper.asEntity(statusDTO);
        var updatedSatatus = statusRepository.save(status);
        log.info("updatedSatatus ok id {}", updatedSatatus.getId());
        log.trace("updatedSatatus ok  {}", updatedSatatus);
        return statusMapper.asDto(updatedSatatus);
    }

    @Override
    @Journal(actionType = ActionType.READ_STATUTS)
    public StatusDTO readStatus(Long id) {
        var status = statusRepository.findById(id)
                .map(statusMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(STATUS_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read status end ok - Id: {}", id);
        log.trace("read status end ok - status: {}", status);
        return status;
    }

    @Override
    public StatusDTO readStatusByCode(String code) {
        var status = statusRepository.findByCode(code)
                .map(statusMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(STATUS_IDENTIFIER_NOT_FOUND_MESSAGE, code)));
        log.info("read status end ok - Code: {}", code);
        log.trace("read status end ok - status: {}", status);
        return status;
    }

    @Override
    @Journal(actionType = ActionType.DELETE_STATUTS)
    public void deleteStatus(Long id) {
        if(!statusRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(STATUS_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        statusRepository.deleteById(id);
        log.info("delete status ok id {}", id);
    }

    @Override
    @Journal(actionType = ActionType.READ_STATUTS)
    public Page<StatusDTO> readAllStatus(Pageable pageable, String code, String libelle, StatusType statusType,String sortBy, Boolean ascending) {
        var status = statusRepository.readAllByFilters(pageable, code, libelle, statusType,sortBy,ascending)
                .map(statusMapper::asDto);
        log.trace("list status get ok {}", status);
        return status;
    }


}
