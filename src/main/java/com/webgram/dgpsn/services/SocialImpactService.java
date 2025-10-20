package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SocialImpactDTO;


public interface SocialImpactService {
    SocialImpactDTO create(SocialImpactDTO socialImpactDTO);
    SocialImpactDTO update(SocialImpactDTO socialImpactDTO);
    SocialImpactDTO read(Long socialImpactId);
    void delete(Long socialImpactId);
    Page<SocialImpactDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            Number nbPersonnesAffectees,
            Number nbMenagesAffectees,
            String source,
            String natureImpact,
            String importanceImpact,
            String startDate,
            String endDate,
            Long categorieId,
            Long typeImpactId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
