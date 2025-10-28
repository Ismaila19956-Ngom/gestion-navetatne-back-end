package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.models.CandidatDTO;

public class CandidatMapper {

    public static CandidatDTO toDTO(CandidatEntity entity) {
        return CandidatDTO.builder()
                .id(entity.getId())
                .matricule(entity.getMatricule())
                .prenom(entity.getPrenom())
                .nom(entity.getNom())
                .adresse(entity.getAdresse())
                .salaireSouhaite(entity.getSalaireSouhaite())
                .niveauEtude(entity.getNiveauEtude())
                .experienceProfessionnelle(entity.getExperienceProfessionnelle())
                .postePostule(entity.getPostePostule())
                .preselectionneEntretien(entity.isPreselectionneEntretien())
                .selectionne(entity.isSelectionne())
                .competences(entity.getCompetences())
                .cv(entity.getCv())
                .build();
    }

    public static CandidatEntity toEntity(CandidatDTO dto) {
        CandidatEntity entity = new CandidatEntity();
        entity.setId(dto.getId());
        entity.setMatricule(dto.getMatricule());
        entity.setPrenom(dto.getPrenom());
        entity.setNom(dto.getNom());
        entity.setAdresse(dto.getAdresse());
        entity.setSalaireSouhaite(dto.getSalaireSouhaite());
        entity.setNiveauEtude(dto.getNiveauEtude());
        entity.setExperienceProfessionnelle(dto.getExperienceProfessionnelle());
        entity.setPostePostule(dto.getPostePostule());
        entity.setPreselectionneEntretien(dto.isPreselectionneEntretien());
        entity.setSelectionne(dto.isSelectionne());
        entity.setCompetences(dto.getCompetences());
        entity.setCv(dto.getCv());
        return entity;
    }

}
