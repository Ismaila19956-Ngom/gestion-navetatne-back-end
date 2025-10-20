package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SocialIndicateurDTO;


public interface SocialIndicateurService {
    SocialIndicateurDTO create(SocialIndicateurDTO socialIndicateurDTO);
    SocialIndicateurDTO update(SocialIndicateurDTO socialIndicateurDTO);
    SocialIndicateurDTO read(Long socialIndicateurId);
    void delete(Long socialIndicateurId);
    Page<SocialIndicateurDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String valeur,
            String source,
            String startDate,
            String endDate,
            Long indicateurId,
            Long projetId,
            String sortBy,
            Boolean ascending
    );

}
