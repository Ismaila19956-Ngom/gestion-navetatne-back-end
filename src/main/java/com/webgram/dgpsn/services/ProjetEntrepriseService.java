package com.webgram.dgpsn.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.webgram.dgpsn.models.ProjetEntrepriseDTO;

public interface ProjetEntrepriseService {
    ProjetEntrepriseDTO create(ProjetEntrepriseDTO projetEntrepriseDTO);
    ProjetEntrepriseDTO update(ProjetEntrepriseDTO projetEntrepriseDTO);
    ProjetEntrepriseDTO read(Long projetEntrepriseId);
    void delete(Long projetEntrepriseId);
    Page<ProjetEntrepriseDTO> readAll(Pageable pageable,Long projectId, Long entrepriseId, Long roleEntrepriseId,Long flagId);

}
