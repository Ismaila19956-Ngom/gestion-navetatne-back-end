package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.SpecificNatureDTO;

public interface SpecificNatureService {
    SpecificNatureDTO createSpecificNature(SpecificNatureDTO specificNatureDTO);
    SpecificNatureDTO updateSpecificNature(SpecificNatureDTO specificNatureDTO);
    SpecificNatureDTO readSpecificNature(Long id);
    SpecificNatureDTO readSpecificNatureByCode(String code);
    void deleteSpecificNature(Long id);
    Page<SpecificNatureDTO> readAllSpecificNature(Pageable pageable, String code, String libelle, Long natureId);

}
