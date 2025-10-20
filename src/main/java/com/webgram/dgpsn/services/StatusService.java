package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.models.StatusDTO;


public interface StatusService {
    StatusDTO createStatus(StatusDTO statusDTO);
    StatusDTO updateStatus(StatusDTO statusDTO);
    StatusDTO readStatus(Long id);
    StatusDTO readStatusByCode(String code);
    void deleteStatus(Long id);
    Page<StatusDTO> readAllStatus(Pageable pageable, String code, String libelle, StatusType statusType, String sortBy, Boolean ascending);

}
