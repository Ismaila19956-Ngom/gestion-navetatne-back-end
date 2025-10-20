package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.entities.enums.ReferentielType;
import com.webgram.dgpsn.models.LabelDTO;

public interface LabelService {
    LabelDTO create(LabelDTO label);
    LabelDTO update(LabelDTO labelDTO);
    LabelDTO read(Long labelId);
    void delete(Long labelId);
    Page<LabelDTO> readAll(Pageable pageable, ReferentielType referentielType, String label);
    Page<ReferentielType> readAllReferentielType(Pageable pageable, String label, String description);
}
