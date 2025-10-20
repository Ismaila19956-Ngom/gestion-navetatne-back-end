package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Table(name = "FicheVisite")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FicheVisiteEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fiche_visite_id")
    private Long id;
    // Structure et visite
    @Column(name = "structure", nullable = false)
    private String structure;
    @Column(name = "date_visite", nullable = false)
    private LocalDate dateVisite;
    // Informations du projet
    @Column(name = "titre", nullable = false)
    private String titre;
    @Column(name = "type_projet", nullable = false)
    private String typeProjet;
    @Column(name = "nom_promoteur", nullable = false)
    private String nomPromoteur;
    @Column(name = "responsables")
    private String responsables;
    // Localisation
//    @Column(name = "region", nullable = false)
//    private String region;
//    @Column(name = "departement", nullable = false)
//    private String departement;
//    @Column(name = "arrondissement")
//    private String arrondissement;
//    @Column(name = "commune")
//    private String commune;
    @Column(name = "village")
    private String village;
    @Column(name = "annee", nullable = false)
    private Integer annee;
    @Column(name = "coord_x")
    private String coordX;
    @Column(name = "coord_y")
    private String coordY;
    // Présentation du site
    @Column(name = "limite_est")
    private String limiteEst;
    @Column(name = "limite_ouest")
    private String limiteOuest;
    @Column(name = "limite_nord")
    private String limiteNord;
    @Column(name = "limite_sud")
    private String limiteSud;
    @Column(name = "statut_juridique")
    private String statutJuridique;
    @Column(name = "superficie")
    private String superficie;
    @Column(name = "utilisation_anterieure")
    private String utilisationAnterieure;
    // Description du projet
    @Column(name = "description_projet", nullable = false)
    private String descriptionProjet;
    @Column(name = "phase_pre_construction")
    private String phasePreConstruction;
    @Column(name = "phase_construction")
    private String phaseConstruction;
    @Column(name = "phase_exploitation")
    private String phaseExploitation;
    // Sensibilités et recommandations
    @Column(name = "sensibilite_environnementale")
    private String sensibiliteEnvironnementale;
    @Column(name = "sensibilite_sociale")
    private String sensibiliteSociale;
    @Column(name = "particularites")
    private String particularites;
    @Column(name = "points_accent")
    private String pointsAccent;
    @Column(name = "recommandations")
    private String recommandations;
    // Chef de division
    @Column(name = "chef_division")
    private String chefDivision;
    // Membres
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fiche_visite_id")
    private List<MembreMissiomEntity> membres;

    @ManyToOne
    @JoinColumn(name = "region")
    private CadreLogiqueEntity region;

    @ManyToOne
    @JoinColumn(name = "departement")
    private CadreLogiqueEntity departement;

    @ManyToOne
    @JoinColumn(name = "arrondissement")
    private CadreLogiqueEntity arrondissement;

    @ManyToOne
    @JoinColumn(name = "commune")
    private CadreLogiqueEntity commune;
}