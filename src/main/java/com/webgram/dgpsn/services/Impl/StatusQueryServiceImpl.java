package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.tools.ActionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.StatusQueryMapper;
import com.webgram.dgpsn.models.StatusQueryDTO;
import com.webgram.dgpsn.repositories.StatusQueryRepository;
import com.webgram.dgpsn.services.StatusQueryService;

import java.text.MessageFormat;
import java.util.Date;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class StatusQueryServiceImpl implements StatusQueryService {
    private final StatusQueryRepository statusQueryRepository;
    private final StatusQueryMapper statusQueryMapper;

    String STATUS_QUERY_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id statut query";

    @Override
    @Journal(actionType = ActionType.ADD_STATUS_QUERY)
    public StatusQueryDTO createStatusQuery(StatusQueryDTO statusQueryDTO) {
        var createdSatatusQuery = statusQueryRepository.save(statusQueryMapper.asEntity(statusQueryDTO));
        log.info("createdSatatusQuery ok id {}", createdSatatusQuery.getId());
        log.trace("createdSatatusQuery ok  {}", createdSatatusQuery);
        return statusQueryMapper.asDto(createdSatatusQuery);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_STATUS_QUERY)
    public StatusQueryDTO updateStatusQuery(StatusQueryDTO statusQueryDTO) {
        if(!statusQueryRepository.existsById(statusQueryDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STATUS_QUERY_IDENTIFIER_NOT_FOUND_MESSAGE, statusQueryDTO.getId()));
        }
        var statusQuery = statusQueryMapper.asEntity(statusQueryDTO);
        var updatedSatatusQuery = statusQueryRepository.save(statusQuery);
        log.info("updatedSatatusQuery ok id {}", updatedSatatusQuery.getId());
        log.trace("updatedSatatusQuery ok  {}", updatedSatatusQuery);
        return statusQueryMapper.asDto(updatedSatatusQuery);
    }

    @Override
    public StatusQueryDTO readStatusQuery(Long id) {
        var statusQuery = statusQueryRepository.findById(id)
                .map(statusQueryMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(STATUS_QUERY_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read statusQuery end ok - Id: {}", id);
        log.trace("read statusQuery end ok - status: {}", statusQuery);
        return statusQuery;
    }

    @Override
    @Journal(actionType = ActionType.DELETE_STATUS_QUERY)
    public void deleteStatusQuery(Long id) {
        if(!statusQueryRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(STATUS_QUERY_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        statusQueryRepository.deleteById(id);
        log.info("delete statusQuery ok id {}", id);
    }

    @Override
    @Journal(actionType = ActionType.READ_STATUS_QUERY)
    public Page<StatusQueryDTO> readAllStatusQuery(Pageable pageable, String libelle, String responsable
            , Date identificationDate, Date deadline, Long queryId) {
        var statusQueries = statusQueryRepository.readAllByFilters(pageable, libelle, responsable, identificationDate, deadline, queryId)
                .map(statusQueryMapper::asDto);
        log.trace("list statusQuery get ok {}", statusQueries);
        return statusQueries;
    }
}
