package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.services.CandidatService;

import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.mappers.CandidatMapper;
import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.repositories.CandidatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CandidatServiceImpl implements CandidatService {

    private final CandidatRepository candidatRepository;

    @Override
    public Page<CandidatDTO> getCandidatsFiltered(
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
    ) {
        Page<CandidatEntity> pageEntities = candidatRepository.readAllByFiltering(
                pageable,
                idsToIgnore,
                nom,
                prenom,
                adresse,
                niveauEtude,
                experience,
                preselectionneEntretien,
                selectionne,
                sortBy,
                ascending
        );

        return pageEntities.map(CandidatMapper::toDTO);
    }

    @Override
    public CandidatDTO getCandidatByMatricule(String matricule) {
        CandidatEntity entity = candidatRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé : " + matricule));
        return CandidatMapper.toDTO(entity);
    }


    @Override
    public CandidatDTO saveOrUpdateCandidat(CandidatDTO dto) {
        CandidatEntity entity = CandidatMapper.toEntity(dto);

        if (entity.getId() == null || entity.getMatricule() == null) {
            entity.setMatricule(generateMatricule());
        }

        CandidatEntity saved = candidatRepository.save(entity);
        return CandidatMapper.toDTO(saved);
    }

    @Override
    public void deleteCandidat(Long id) {
        candidatRepository.deleteById(id);
    }

    @Override
    public CandidatDTO getCandidatById(Long id) {
        CandidatEntity entity = candidatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidat non trouvé : " + id));
        return CandidatMapper.toDTO(entity);
    }

    private String generateMatricule() {
        return "CAND-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
