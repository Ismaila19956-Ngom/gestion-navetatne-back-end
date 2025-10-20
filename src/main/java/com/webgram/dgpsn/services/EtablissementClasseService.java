package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.EtablissementClasseDTO;

public interface EtablissementClasseService {

    EtablissementClasseDTO create(EtablissementClasseDTO etablissementClasseDTO);

    EtablissementClasseDTO update(EtablissementClasseDTO etablissementClasseDTO);

    EtablissementClasseDTO read(Long etablissementClasseId);

    void delete(Long etablissementClasseId);

    Page<EtablissementClasseDTO> readAll(
            Pageable pageable,
            String code,
            String libelle,
            String latitude,
            String longitude,
            String contactPerson,
            String contactRole,
            String contactInfo,
            String mainActivity,
            Long typeEtablissementId,
            Long regionId,
            Long departementId,
            Long categoryICPEId,
            String sortBy,
            Boolean ascending
    );
}