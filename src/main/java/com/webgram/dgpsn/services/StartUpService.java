package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.StartUpDTO;

import java.util.Map;

public interface StartUpService {
    StartUpDTO create(StartUpDTO startUpDTO);
    StartUpDTO update(StartUpDTO startUpDTO);
    StartUpDTO read(Long startUpId);
    void delete(Long budgetActivityId);
    Page<StartUpDTO> readAllStartUp(Map<String, String> searchParams, Pageable pageable);

}
