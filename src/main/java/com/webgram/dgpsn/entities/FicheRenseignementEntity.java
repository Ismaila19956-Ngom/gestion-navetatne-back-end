package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "FicheRenseignement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FicheRenseignementEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fiche_id")
    private Long id;

    // Information sur la compagnie
    @Column(name = "nom_compagnie", nullable = false)
    private String nomCompagnie;

    // Information sur la mission
    @Column(name = "structure_instruction")
    private String structureInstruction;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_mission")
    private Date dateMission;

    @Column(name = "membres_mission")
    private String membresMission;

    @Column(name = "objet_mission")
    private String objetMission;

    // Identification du promoteur
    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "numero_rccm")
    private Integer numeroRCCM;

    @Column(name = "numero_ninea")
    private Integer numeroNINEA;

    @Column(name = "nom_exploitant")
    private String nomExploitant;

    @Column(name = "telephone_exploitant")
    private String telephoneExploitant;

    @Column(name = "adresse_exploitant")
    private String adresseExploitant;

    @Column(name = "nature_activite")
    private String natureActivite;

    // Localisation géographique
//    @Column(name = "region")
//    private String region;
//
//    @Column(name = "departement")
//    private String departement;
//
//    @Column(name = "commune")
//    private String commune;

    @Column(name = "quartier")
    private String quartier;

    @Column(name = "limite_est")
    private String limiteEst;

    @Column(name = "limite_nord")
    private String limiteNord;

    @Column(name = "limite_ouest")
    private String limiteOuest;

    @Column(name = "limite_sud")
    private String limiteSud;

    @Column(name = "localisation_gps")
    private String localisationGPS;

    // Superficie
    @Column(name = "surface_equipee")
    private Double surfaceEquipee;

    @Column(name = "surface_non_equipee")
    private Double surfaceNonEquipee;

    @Column(name = "surface_totale")
    private Double surfaceTotale;

    // Constats généraux
    @Column(name = "composition_etablissement")
    private String compositionEtablissement;

    @Column(name = "liste_equipements")
    private String listeEquipements;

    @Column(name = "salubrite_etablissement")
    private String salubriteEtablissement;

    @Column(name = "moyens_secours")
    private String moyensSecours;

    @Column(name = "formation_extincteurs")
    private Boolean formationExtincteurs;

    @Column(name = "equipements_protection")
    private String equipementsProtection;

    @Column(name = "affichage_consignes")
    private String affichageConsignes;

    @Column(name = "source_electricite")
    private String sourceElectricite;

    @Column(name = "source_eau")
    private String sourceEau;

    // Gestion des rejets
    @Column(name = "dechets_solides")
    private String dechetsSolides;

    @Column(name = "rejets_liquides")
    private String rejetsLiquides;

    @Column(name = "rejets_atmospheriques")
    private String rejetsAtmospheriques;

    // Autres informations
    @Column(name = "autres_constats")
    private String autresConstats;

    @Column(name = "prescription_mission")
    private String prescriptionMission;

    @Column(name = "conclusion_mission")
    private String conclusionMission;

    @Column(name = "signature_chef")
    private String signatureChef;

    @ManyToOne
    @JoinColumn(name = "region")
    private CadreLogiqueEntity region;

    @ManyToOne
    @JoinColumn(name = "departement")
    private CadreLogiqueEntity departement;

    @ManyToOne
    @JoinColumn(name = "commune")
    private CadreLogiqueEntity commune;
}