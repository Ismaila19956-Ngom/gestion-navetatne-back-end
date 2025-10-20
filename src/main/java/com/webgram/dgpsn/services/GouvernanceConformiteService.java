package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.GouvernanceConformiteDTO;


public interface GouvernanceConformiteService {
    GouvernanceConformiteDTO create(GouvernanceConformiteDTO gouvernanceConformiteDTO);
    GouvernanceConformiteDTO update(GouvernanceConformiteDTO gouvernanceConformiteDTO);
    GouvernanceConformiteDTO read(Long gouvernanceConformiteId);
    void delete(Long gouvernanceConformiteId);
    Page<GouvernanceConformiteDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String etat,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeReferenceId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
