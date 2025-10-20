package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.HistoryFlagDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface HistoryFlagService {
    HistoryFlagDTO create(HistoryFlagDTO historyFlagDTO);
    HistoryFlagDTO update(HistoryFlagDTO historyFlagDTO);
    HistoryFlagDTO read(Long historyFlagId);
    void delete(Long historyFlagId);
    Page<HistoryFlagDTO> readAll(
            Pageable pageable,
            String startDate,
            String endDate,
            Long flagId,
            Long projetId
    )throws ParseException;

    Map<String, Long> countByFlagCode();

    List<HistoryFlagDTO> readByFlagCode(String code);
}
