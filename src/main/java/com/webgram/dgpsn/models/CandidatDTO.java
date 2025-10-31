package com.webgram.dgpsn.models;

import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.entities.enums.StatusCadidature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidatDTO {
    private Long id;
    private String matricule;
    private String prenom;
    private String nom;
    private String adresse;
    private Double salaireSouhaite;
    private NiveauEtude niveauEtude;
    private ExperienceProfessionnelle experienceProfessionnelle;
    private String postePostule;
    private StatusCadidature statusCandidature;
    private List<String> competences;
    private String cv;
    private Long recrutementId;
    private List<NotationDTO> notations;
}