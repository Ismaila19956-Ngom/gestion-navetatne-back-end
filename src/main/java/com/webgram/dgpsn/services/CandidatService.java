package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.models.CandidatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CandidatService {

    Page<CandidatDTO> getCandidatsFiltered(
            Pageable pageable,
            List<Long> idsToIgnore,
            String nom,
            String prenom,
            String adresse,
            NiveauEtude niveauEtude,
            ExperienceProfessionnelle experience,
            Boolean preselectionneEntretien,
            Boolean selectionne,
            String sortBy,
            Boolean ascending
    );

    CandidatDTO getCandidatByMatricule(String matricule);

    CandidatDTO saveOrUpdateCandidat(CandidatDTO dto);

    void deleteCandidat(Long id);


    CandidatDTO getCandidatById(Long id);
}
