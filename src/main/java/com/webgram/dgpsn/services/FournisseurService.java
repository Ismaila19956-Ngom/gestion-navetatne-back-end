package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.FournisseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface FournisseurService {
    FournisseurDTO create(FournisseurDTO fournisseurDTO);
    FournisseurDTO update(Long fournisseurId, FournisseurDTO fournisseurDTO);
    FournisseurDTO read(Long fournisseurId);
    void delete(Long fournisseurId);
    Page<FournisseurDTO> readAll(
            Pageable pageable,
            String raisonSociale,
            String codeFournisseur,
            String ninea,
            String categorieFournisseur,
            String statut,
            String sortBy,
            Boolean ascending
    );

}