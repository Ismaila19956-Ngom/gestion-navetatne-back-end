package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.StatusQueryDTO;

import java.util.Date;


public interface StatusQueryService {
    StatusQueryDTO createStatusQuery(StatusQueryDTO statusQueryDTO);
    StatusQueryDTO updateStatusQuery(StatusQueryDTO statusQueryDTO);
    StatusQueryDTO readStatusQuery(Long id);
    void deleteStatusQuery(Long id);
    Page<StatusQueryDTO> readAllStatusQuery(Pageable pageable, String libelle, String responsable
            , Date identificationDate, Date deadline, Long queryId);

}
