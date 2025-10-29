package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String matricule;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String nom;

    private String adresse;

    private Double salaireSouhaite;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude", nullable = false)
    private NiveauEtude niveauEtude;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_professionnelle", nullable = false)
    private ExperienceProfessionnelle experienceProfessionnelle;

    @Column(name = "poste_postule")
    private String postePostule;

    @Column(name = "preselectionne_entretien")
    private boolean preselectionneEntretien = false;

    private boolean selectionne = false;

    @Column(name = "competences", columnDefinition = "text[]")
    private List<String> competences = new ArrayList<>();

    @Column(name = "cv")
    private String cv; // optionnel : peut contenir URL ou chemin du fichier


//    @JoinColumn(name = "recrutement_id")
//    private Recrutement recrutement;

}