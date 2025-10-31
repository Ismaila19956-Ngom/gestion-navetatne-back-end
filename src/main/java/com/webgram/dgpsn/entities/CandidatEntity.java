package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.ExperienceProfessionnelle;
import com.webgram.dgpsn.entities.enums.NiveauEtude;
import com.webgram.dgpsn.entities.enums.StatusCadidature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status_candidature", nullable = false)
    private StatusCadidature statusCandidature = StatusCadidature.IN_PROGRESS;

    @Builder.Default
    @Column(name = "competences", columnDefinition = "text[]")
    private List<String> competences = new ArrayList<>();

    @Column(name = "cv")
    private String cv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recrutement_id")
    private RecrutementEntity recrutement;

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotationEntity> notations = new ArrayList<>();

}