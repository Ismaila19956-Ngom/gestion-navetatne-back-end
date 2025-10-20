package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.PromoteurDTO;

public interface PromoteurService {
    PromoteurDTO create(PromoteurDTO promoteur);
    PromoteurDTO update(PromoteurDTO promoteurDTO);
    PromoteurDTO read(Long promoteurId);
    void delete(Long promoteurId);
    Page<PromoteurDTO> readAll(Pageable pageable,String nomEntreprise, String personneContact, String fonctionContact, String telephone, String bureauEtudes, String adresseSiege, String adresseSite);
}
