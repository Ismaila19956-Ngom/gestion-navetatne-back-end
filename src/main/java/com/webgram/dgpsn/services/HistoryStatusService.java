package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.models.HistoryStatusDTO;
import com.webgram.dgpsn.models.responses.StatisticalDTO;

import java.text.ParseException;
import java.util.List;

public interface HistoryStatusService {
    HistoryStatusDTO create(HistoryStatusDTO historyStatusDTO);
    HistoryStatusDTO update(HistoryStatusDTO historyStatusDTO);
    HistoryStatusDTO read(Long historyStatusId);
    void delete(Long historyStatusId);
    Page<HistoryStatusDTO> readAll(
            Pageable pageable,
            String startDate,
            String endDate,
            Long statusId,
            Long projetId
    )throws ParseException;

    List<StatisticalDTO> countByStatusCode(StatusType statusType);

    List<HistoryStatusDTO> readStatusProjectByLibelle(String libelle);
}
