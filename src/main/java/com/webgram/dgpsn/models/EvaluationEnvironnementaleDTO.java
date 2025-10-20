package com.webgram.dgpsn.models;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationEnvironnementaleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long programmeId;

    private Long projetId;

    private Long activiteId;

    private Long directionId;

    private Long promoteurId;

    private String raisonDemande;

    private String autreRaison;

    private String utilisationTerrain;

    private String titreProjet;

    private String typeProjet;

    private String objectifs;

    private String localisation;

    private String carteGeographique;

    private String activitesDescription;

    private String procedeTechnique;

    private String infrastructures;

    private List<Installation> installations = new ArrayList<>();

    private List<Distance> distances = new ArrayList<>();

    private String descriptionGeographique;

    private String composantesEnvironnementales;

    private String typeSols;

    private String contexteGeologique;

    private String eauxSurface;

    private String eauxSouterraines;

    private String pollutionAir;

    private String flore;

    private String faune;

    private String occupationSol;

    private String activiteSocioEconomique;

    private String demographie;

    private String eauPotable;

    private String sante;

    private String education;

    private String modeVie;

    private String hygiene;

    private String assainissementEauxUsees;

    private String assainissementEauxPluviales;

    private String collecteDechets;

    private String patrimoineCulturel;

    private String contraintesHumaines;

    private String contraintesPhysiques;

    private String contraintesSocioEconomiques;

    private List<MatierePrimaire> matieresPrimaires = new ArrayList<>();

    private List<SubstanceDangereuse> substancesDangereuses = new ArrayList<>();

    private List<EauEntrante> eauxEntrantes = new ArrayList<>();

    private Boolean rejetEau;

    private List<EauSortante> eauxSortantes = new ArrayList<>();

    private Boolean rejetAir;

    private List<RejetCanallise> rejetsCanalises = new ArrayList<>();

    private List<RejetDiffus> rejetsDiffus = new ArrayList<>();

    private List<Bruit> bruits = new ArrayList<>();

    private List<Dechet> dechets = new ArrayList<>();

    private List<ExigenceLegale> exigencesLegales = new ArrayList<>();

    private List<Consultation> consultations = new ArrayList<>();

    private List<PlanGestion> planGestion = new ArrayList<>();

    private String planMasse;

    private String planSituation;

    private String planInstallations;

    private String planReseaux;

    private String tdrEtude;

    private String attestationDomaine;

    private String bilanEau;

    private List<String> autresDocuments = new ArrayList<>();

    // Classes internes pour les collections

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Installation {
        private String rubrique;
        private String activite;
        private String niveauActivite;
        private String regime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Distance {
        private String direction;
        private Integer distance;
        private String caractere;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatierePrimaire {
        private String nom;
        private Double quantite;
        private String unite;
        private String stockage;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubstanceDangereuse {
        private String nom;
        private Double quantite;
        private String unite;
        private String stockage;
        private String etatPhysique;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EauEntrante {
        private String source;
        private Double debit;
        private String unite;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EauSortante {
        private String typeEau;
        private String recepteur;
        private String controle;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RejetCanallise {
        private String installation;
        private Double hauteur;
        private String effluents;
        private String epuration;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RejetDiffus {
        private String installation;
        private String nature;
        private String prevention;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bruit {
        private String installation;
        private String horaire;
        private Double niveauSonore;
        private String mesuresReduction;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dechet {
        private String type;
        private String description;
        private Double quantite;
        private String traitement;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExigenceLegale {
        private String secteur;
        private String basesLegales;
        private String references;
        private String contenu;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Consultation {
        private String acteurs;
        private String questionnement;
        private String perceptions;
        private String preoccupations;
        private String attentes;
        private String recommandations;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanGestion {
        private String activites;
        private String impact;
        private String recepteur;
        private String mesures;
        private String indicateurs;
        private String verification;
        private String calendrier;
        private Double couts;
        private String responsableExecution;
        private String responsableSuivi;
        private Double coutsSuivi;
    }
}