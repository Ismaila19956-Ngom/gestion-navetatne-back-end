package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum ReferentielType {
    AXE_PSE("Axe Stratégique", "Paramétrage axe stratégique"),
    ETAT_AVANCEMENT("Etat d'avancement", "Paramétrage etat d'avancement"),
    FONCTION("Fonction", "Paramétrage fonction"),
    CATEGORIE("Categorie", "Paramétrage catégorie"),
    //    CRITICITE("Criticité", "Paramétrage criticité"),
//    DELAY_IMPACT("delay Impact", "Paramétrage delay impact"),
//    FINANCIAL_IMPACT("finacial impact", "Paramétrage financial impact"),
//    CANAL_RESOLUTION("Canal de résolution", "Paramétrage canal de résolution"),
//    FLAG("Flag", "Paramétrage falg"),
//    GROUPE_PARTENAIRE("Groupe de partenaire", "Paramétrage groupe partenaire"),
//    NATURE("Nature", "Paramétrage nature"),
    PAYS("Pays", "Paramétrage pays"),
    //    PHASE("Phase", "Paramétrage phase"),
    SECTEUR("Secteur", "Paramétrage secteur"),
    //    PARTIE_PRENANTE("Partie prenante", "Paramétrage Partie prenante"),
//    SOURCE("Source", "Paramétrage source"),
//    TYPE_FINANCEMENT("Type de financement", "Paramétrage type de financement"),
//    TYPE_CONDITIONNALITE("Type de conditionnalité", "Paramétrage type de conditionnalité"),
//    TYPE_REUNION("Type de reunion", "Paramétrage type de reunion"),
//    TYPE_REFERENCE_REGLEMENTAIRE("Type de référence réglementaire", "Paramétrage type de référence réglementaire"),
    TYPE_DOCUMENT("Type de document", "Paramétrage type de document"),
    //    TAG("Tag", "Paramétrage tag"),
    TYPE_INDICATEUR("Type d'indicateur", "Paramétrage type indicateur"),
    TYPE_MISSION("Type de mission", "Paramétrage type de mission"),
    UNITE("Unite", "Paramétrage unités"),
    ZONE_EXECUTION("Zones d'exécutions", "Paramétrage zones exécutions"),
    BENEFICIAIRE("Beneficiaire", "Paramétrage bénéficiaires"),
    TYPE_PASSATION("Type de passation", "Paramétrage type passation"),
    TYPE_MARCHE("Type de marche", "Paramétrage type de marché"),
    TYPE_DE_CRITERE_EVALUATION_MARCHE("Type de critere d'evaluation des marches", "Paramétrage type de critere d'evaluation des marches"),
    CIBLE("CIBLE", "Paramétrage cible"),

    STATUT_DE_LA_RECEPTION("Status de la réception", "Paramétrage des status de reception"),

    TYPE_BUDGET("Type de budget", "Paramétrage budget activity"),
    CATEGORY_DEPENSE("Categorie dépense ", "Paramétrage dépenses activity"),
    //    TYPE_DEPENSE("Types dépenses","Paramétrage dépenses activity"),
    CATEGORY_REQUETE("Categorie requete", "Paramétrage requete activity"),
    //    TYPE_REQUETE("Types requetes","Paramétrage requete activity"),
    STATUS("Status", "Paramétrage Status"),
    //    CATEGORY_CONFORMITE_ENVIRONNEMENTAL("Categorie conformites environnemental", "Paramétrage conformites reglementaire environnemental"),
//    CATEGORY_IMPACT_SOCIAL("Categorie impact social", "Paramétrage impact social"),
//    TYPE_IMPACT_SOCIAL("Type impact social", "Paramétrage type impact social"),
//    CATEGORY_CONFORMITE_SOCIAL("Categorie conformite social", "Paramétrage conformites reglementaire social"),
//    CATEGORY_INDICATEUR_SOCIAL("Categorie indicateur social", "Paramétrage categorie indicateur social"),
//    CATEGORY_IMPACT_GOUVERNENCE("Categorie impact gouvernance", "Paramétrage impact gouvernance"),
//    CATEGORY_CONFORMITE_GOUVERNENCE("Categorie conformite de gouvernance", "Paramétrage categorie conformite reglementaire de gouvernance"),
//    FORME_JURIDIQUE("Forme juridique", "Paramétrage forme juridique"),
    ROLE_ENTREPRISE("Entreprise role", "Paramétrage Entreprise role"),
    POINT_PRELEVEMENT("point Prelevement", "Paramétrage point Prelevement de la qualite du milieux"),
    CONDITION_METEO("condition Meteo", "Paramétrage condition meteo de la qualite du milieux"),
    ENTREPRISE("Entreprise polluant", "Paramétrage Entreprise polluant"),
    TYPE_PLASTIQUE("type  polluant plastique", "Paramétrage type  polluant plastique"),
    PRODUIT_CHIMIQUE("produit  polluant chimique", "Paramétrage des produits chimiques"),
    TYPE_DECHET("type dechet polluant", "Paramétrage type  dechet polluant"),
    DESTINATION_ORIGINE("lieux destination et origine", "Paramétrage des lieux destination et origine"),
    METHODE_ELIMINATION("methode elimination polluant", "Paramétrage des methodes d'elimination polluant"),
    SITE_ELIMNINATION("site elimination polluant", "Paramétrage des sites elimination polluant"),
    CATEGORY_ICPE("Categorie ICPE", "Paramétrage categorie ICPE"),
    TYPE_ETABLISSEMENT("Type etablissement classe", "Paramétrage type etablissement classe"),
    TYPE_INSPECTION("Type d'inspection", "Paramétrage type d'inspection"),
    ENVIRONMENTAL_RISK("Risque environnemental", "Paramétrage Risque environnemental"),
    COMPLIANCE_LEVEL("Niveau de conformité", "Paramétrage niveau de conformité"),
    TYPE_STATION("Type de station", "Paramétrage type de station"),
    TYPE_POLLUTANT("Type de polluant", "Paramétrage type de polluant"),
    CATEGORIE_TECHONLOGIE("categorie technologie", "evaluation categorie technologie"),
    SOURCE_FINANCEMENT("source financement", "source financement plan passation"),
    MODE_PASSATION("mode passation", "model de  passation"),
    TYPE_ENGAGEMENT("Engagement", "type  engagement"),
    STRUCTURE_BENEFICIAIRE("structure", "structure beneficiaire"),
    MARQUE_PARC("Parc roulant", "marque voiture parc roulant"),
    LOCALISATION_PARC("Parc roulant", "Localisation voiture parc roulant"),
    ETAT_PARC("Parc roulant", "Etat voiture parc roulant"),
    TYPE_INVENTAIRE("Inventaire", "Type Inventaire"),
    FOURNISSEUR("Fournisseur", "Fournisseur Materiel"),
    PROFIL_RH("Profil RH", "Profil Agent RH"),
    CONTRAT_RH("Contrat Rh", "Type Contrat Agent Rh"),
    DIPLOME("Diplome", "Diplome Agent Rh"),
    FRAIS("Frais", "Frais"),
    PRISE_EN_CHARGE("Prise en charge", "Prise en charge"),
    POSTE("Poste", "Poste Agent Rh"),
    TYPE_CONTRAT("Type de contrat", "Paramétrage type de contrat RH"),
    TYPE_COURRIER("Type courrier", "Parametrage type courrier"),
    TYPE_CARACTERISTIQUE("Type caracteristiques recrutement", "Parametrage type caracteristiques recrutement"),
    URGENCE_COURRIER("urgence courrier", "Parametrage urgence"),
    NATURE_COURRIER("nature courrier", "Parametrage nature"),
    STATUT_COURRIER("statut courrier", "Parametrage statut"),
    MODE_ENVOIE_COURRIER("mode envoie courrier", "Parametrage mode envoie");
//    STARTUP("Startup", "Paramétrage startup"),;


    @Getter
    private final String label;
    @Getter
    private final String description;


    ReferentielType(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReferentielType fromValue(Object referentielType) {
        if (referentielType instanceof Map) {
            Map<String, Object> mapReferentielType = (Map<String, Object>) referentielType;
            if (mapReferentielType.containsKey("name")) {
                return ReferentielType.valueOf(mapReferentielType.get("name").toString());
            }
        }
        if (referentielType instanceof String) {
            return ReferentielType.valueOf(referentielType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", ReferentielType.class, referentielType, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "label", label,
                "description", description
        );
    }
}
