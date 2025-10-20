package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SocialConformiteDTO;


public interface SocialConformiteService {
    SocialConformiteDTO create(SocialConformiteDTO socialConformiteDTO);
    SocialConformiteDTO update(SocialConformiteDTO socialConformiteDTO);
    SocialConformiteDTO read(Long socialConformiteId);
    void delete(Long socialConformiteId);
    Page<SocialConformiteDTO> readAll(
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
