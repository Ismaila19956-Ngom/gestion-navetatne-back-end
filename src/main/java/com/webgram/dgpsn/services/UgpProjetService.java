package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.UgpProjetDTO;

public interface UgpProjetService {
    UgpProjetDTO create(UgpProjetDTO ugpProjetDTO);
    UgpProjetDTO update(UgpProjetDTO ugpProjetDTO);
    UgpProjetDTO read(Long ugpProjetId);
    void delete(Long ugpProjetIdId);
    Page<UgpProjetDTO> readAll(Pageable pageable, Boolean existed, Boolean occupied, String status,  Long projectId, Long ugpRoleId);
}
