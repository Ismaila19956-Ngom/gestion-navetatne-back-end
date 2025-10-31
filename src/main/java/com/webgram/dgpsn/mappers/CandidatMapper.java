package com.webgram.dgpsn.mappers;

import com.webgram.dgpsn.entities.CandidatEntity;
import com.webgram.dgpsn.models.CandidatDTO;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

public class CandidatMapper {

    // Création manuelle de l'instance MapStruct
    private static final NotationMapper notationMapper = Mappers.getMapper(NotationMapper.class);

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
                .statusCandidature(entity.getStatusCandidature())
                .competences(entity.getCompetences())
                .cv(entity.getCv())
                .recrutementId(entity.getRecrutement().getId())
                .notations(
                        entity.getNotations() != null
                                ? entity.getNotations().stream()
                                .map(notationMapper::toDto)
                                .collect(Collectors.toList())
                                : null
                )
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
        entity.setStatusCandidature(dto.getStatusCandidature());
        entity.setCompetences(dto.getCompetences());
        entity.setCv(dto.getCv());
        // Les notations seront gérées dans le service
        return entity;
    }
}
