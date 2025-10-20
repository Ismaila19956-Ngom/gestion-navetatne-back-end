package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.GouvernanceImpactDTO;


public interface GouvernanceImpactService {
    GouvernanceImpactDTO create(GouvernanceImpactDTO gouvernanceImpactDTO);
    GouvernanceImpactDTO update(GouvernanceImpactDTO gouvernanceImpactDTO);
    GouvernanceImpactDTO read(Long gouvernanceImpactId);
    void delete(Long gouvernanceImpactId);
    Page<GouvernanceImpactDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
