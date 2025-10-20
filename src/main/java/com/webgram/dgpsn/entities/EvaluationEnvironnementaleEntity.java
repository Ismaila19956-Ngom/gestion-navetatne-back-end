package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "evaluation_environnementale")
@Table(name = "evaluation_environnementale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationEnvironnementaleEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private ManagementUnitEntity programme;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private ManagementUnitEntity activite;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private DirectionEntity direction;

    @ManyToOne
    @JoinColumn(name = "promoteur_id")
    private PromoteurEntity promoteur;

    @Column(name = "raison_demande", length = 50)
    private String raisonDemande;

    @Column(name = "autre_raison", length = 50)
    private String autreRaison;

    @Column(name = "utilisation_terrain", columnDefinition = "TEXT", length = 50)
    private String utilisationTerrain;

    @Column(name = "titre_projet", length = 50)
    private String titreProjet;

    @Column(name = "type_projet", columnDefinition = "TEXT", length = 100)
    private String typeProjet;

    @Column(name = "objectifs", columnDefinition = "TEXT", length = 100)
    private String objectifs;

    @Column(name = "localisation", columnDefinition = "TEXT", length = 100)
    private String localisation;

    @Column(name = "carte_geographique", length = 50)
    private String carteGeographique;

    @Column(name = "activites", columnDefinition = "TEXT", length = 50)
    private String activitesDescription;

    @Column(name = "procede_technique", columnDefinition = "TEXT", length = 50)
    private String procedeTechnique;

    @Column(name = "infrastructures", columnDefinition = "TEXT", length = 50)
    private String infrastructures;

    @ElementCollection
    @CollectionTable(name = "evaluation_installations", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<Installation> installations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_distances", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<Distance> distances = new ArrayList<>();

    @Column(name = "description_geographique", columnDefinition = "TEXT")
    private String descriptionGeographique;

    @Column(name = "composantes_environnementales", columnDefinition = "TEXT")
    private String composantesEnvironnementales;

    @Column(name = "type_sols")
    private String typeSols;

    @Column(name = "contexte_geologique")
    private String contexteGeologique;

    @Column(name = "eaux_surface")
    private String eauxSurface;

    @Column(name = "eaux_souterraines")
    private String eauxSouterraines;

    @Column(name = "pollution_air")
    private String pollutionAir;

    @Column(name = "flore")
    private String flore;

    @Column(name = "faune")
    private String faune;

    @Column(name = "occupation_sol")
    private String occupationSol;

    @Column(name = "activite_socio_economique")
    private String activiteSocioEconomique;

    @Column(name = "demographie")
    private String demographie;

    @Column(name = "eau_potable")
    private String eauPotable;

    @Column(name = "sante")
    private String sante;

    @Column(name = "education")
    private String education;

    @Column(name = "mode_vie")
    private String modeVie;

    @Column(name = "hygiene")
    private String hygiene;

    @Column(name = "assainissement_eaux_usees")
    private String assainissementEauxUsees;

    @Column(name = "assainissement_eaux_pluviales")
    private String assainissementEauxPluviales;

    @Column(name = "collecte_dechets")
    private String collecteDechets;

    @Column(name = "patrimoine_culturel")
    private String patrimoineCulturel;

    @Column(name = "contraintes_humaines")
    private String contraintesHumaines;

    @Column(name = "contraintes_physiques")
    private String contraintesPhysiques;

    @Column(name = "contraintes_socio_economiques")
    private String contraintesSocioEconomiques;

    @ElementCollection
    @CollectionTable(name = "evaluation_matieres_primaires", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<MatierePrimaire> matieresPrimaires = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_substances_dangereuses", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<SubstanceDangereuse> substancesDangereuses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_eaux_entrantes", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<EauEntrante> eauxEntrantes = new ArrayList<>();

    @Column(name = "rejet_eau")
    private Boolean rejetEau;

    @ElementCollection
    @CollectionTable(name = "evaluation_eaux_sortantes", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<EauSortante> eauxSortantes = new ArrayList<>();

    @Column(name = "rejet_air")
    private Boolean rejetAir;

    @ElementCollection
    @CollectionTable(name = "evaluation_rejets_canalises", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<RejetCanallise> rejetsCanalises = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_rejets_diffus", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<RejetDiffus> rejetsDiffus = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_bruits", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<Bruit> bruits = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_dechets", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<Dechet> dechets = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_exigences_legales", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<ExigenceLegale> exigencesLegales = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_consultations", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<Consultation> consultations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "evaluation_plan_gestion", joinColumns = @JoinColumn(name = "evaluation_id"))
    private List<PlanGestion> planGestion = new ArrayList<>();

    @Column(name = "plan_masse")
    private String planMasse;

    @Column(name = "plan_situation")
    private String planSituation;

    @Column(name = "plan_installations")
    private String planInstallations;

    @Column(name = "plan_reseaux")
    private String planReseaux;

    @Column(name = "tdr_etude")
    private String tdrEtude;

    @Column(name = "attestation_domaine")
    private String attestationDomaine;

    @Column(name = "bilan_eau")
    private String bilanEau;

    @ElementCollection
    @CollectionTable(name = "evaluation_autres_documents", joinColumns = @JoinColumn(name = "evaluation_id"))
    @Column(name = "document_path", length = 1000)
    private List<String> autresDocuments = new ArrayList<>();

    // Classes Embeddable pour les collections

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Installation {
        @Column(name = "rubrique")
        private String rubrique;

        @Column(name = "activite")
        private String activite;

        @Column(name = "niveau_activite")
        private String niveauActivite;

        @Column(name = "regime")
        private String regime;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Distance {
        @Column(name = "direction")
        private String direction;

        @Column(name = "distance")
        private Integer distance;

        @Column(name = "caractere")
        private String caractere;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatierePrimaire {
        @Column(name = "nom")
        private String nom;

        @Column(name = "quantite")
        private Double quantite;

        @Column(name = "unite")
        private String unite;

        @Column(name = "stockage")
        private String stockage;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubstanceDangereuse {
        @Column(name = "nom")
        private String nom;

        @Column(name = "quantite")
        private Double quantite;

        @Column(name = "unite")
        private String unite;

        @Column(name = "stockage")
        private String stockage;

        @Column(name = "etat_physique")
        private String etatPhysique;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EauEntrante {
        @Column(name = "source")
        private String source;

        @Column(name = "debit")
        private Double debit;

        @Column(name = "unite")
        private String unite;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EauSortante {
        @Column(name = "type_eau")
        private String typeEau;

        @Column(name = "recepteur")
        private String recepteur;

        @Column(name = "controle")
        private String controle;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RejetCanallise {
        @Column(name = "installation")
        private String installation;

        @Column(name = "hauteur")
        private Double hauteur;

        @Column(name = "effluents")
        private String effluents;

        @Column(name = "epuration")
        private String epuration;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RejetDiffus {
        @Column(name = "installation")
        private String installation;

        @Column(name = "nature")
        private String nature;

        @Column(name = "prevention")
        private String prevention;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bruit {
        @Column(name = "installation")
        private String installation;

        @Column(name = "horaire")
        private String horaire;

        @Column(name = "niveau_sonore")
        private Double niveauSonore;

        @Column(name = "mesures_reduction")
        private String mesuresReduction;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Dechet {
        @Column(name = "type")
        private String type;

        @Column(name = "description")
        private String description;

        @Column(name = "quantite")
        private Double quantite;

        @Column(name = "traitement")
        private String traitement;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExigenceLegale {
        @Column(name = "secteur", length = 50)
        private String secteur;

        @Column(name = "bases_legales", length = 50)
        private String basesLegales;

        @Column(name = "ref_legal", length = 50)
        private String references;

        @Column(name = "contenu", length = 50)
        private String contenu;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Consultation {
        @Column(name = "acteurs")
        private String acteurs;

        @Column(name = "questionnement")
        private String questionnement;

        @Column(name = "perceptions")
        private String perceptions;

        @Column(name = "preoccupations")
        private String preoccupations;

        @Column(name = "attentes")
        private String attentes;

        @Column(name = "recommandations")
        private String recommandations;
    }

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlanGestion {
        @Column(name = "activites")
        private String activites;

        @Column(name = "impact")
        private String impact;

        @Column(name = "recepteur")
        private String recepteur;

        @Column(name = "mesures")
        private String mesures;

        @Column(name = "indicateurs")
        private String indicateurs;

        @Column(name = "verification")
        private String verification;

        @Column(name = "calendrier")
        private String calendrier;

        @Column(name = "couts")
        private Double couts;

        @Column(name = "responsable_execution")
        private String responsableExecution;

        @Column(name = "responsable_suivi")
        private String responsableSuivi;

        @Column(name = "couts_suivi")
        private Double coutsSuivi;
    }
}