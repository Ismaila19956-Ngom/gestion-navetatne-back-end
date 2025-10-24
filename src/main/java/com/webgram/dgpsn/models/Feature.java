package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import com.webgram.dgpsn.entities.enums.Module;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public enum Feature {
    TDB_POLLUTION("Pollution", Module.DASHBOARD),
    TDB_ICPE("ICPE", Module.DASHBOARD),
    TDB_QUALITE_AIR("Qualité de l'air", Module.DASHBOARD),
    TDB_EVALUATION("Évaluation Environnementale", Module.DASHBOARD),
    TDB_RESUME("Resumé tableau de bord", Module.DASHBOARD),
    TDB_PROJETS("tableau de bord des projets", Module.DASHBOARD),
    TDB_Financements("tableau de bord des financements", Module.DASHBOARD),
    TDB_PROBLEMES("tableau de bord des problèmes", Module.DASHBOARD),
    TDB_Enttrepsie("tableau de bord des entreprises", Module.DASHBOARD),

    LIST_PROGRAMME("Liste programme", Module.PROGRAMME),
    CONFIG_PROGRAMME("Configuration programme", Module.PROGRAMME),
    PROJECT_LIST("Liste projets", Module.PROJECT_LIST),

//    FORMULAIRE_GENERALE("Formulaire generale", Module.FORMULAIRE),
//    INSPECTION_ICPE("Inspection ICPE", Module.FORMULAIRE),
//    EVALUATION_ENVIRONNEMENTAL("Evaluation environnementale", Module.FORMULAIRE),
//    QUALITE_AIR("Qualite de l'air", Module.FORMULAIRE),
//    GESTION_POLLUTION("Gestion Pollutions", Module.FORMULAIRE),
//    DIRECTION_REGIONALE("Direction Regionales", Module.FORMULAIRE),
//    URGENCE_ENVIRO("Urgence environnementale", Module.FORMULAIRE),
//    EVALUATION_STARTUP("Evaluation startup", Module.FORMULAIRE),
//    BUREAU_AFFAIRES("Administration - Finances", Module.FORMULAIRE),
    PLAN_DE_PASSATION("Administration - Finances", Module.PROJECT_SETTINGS),
//    ETABLISSEMENTS_CLASSES("Etablissements Classés", Module.SITE_STATION),
//    STATIONS("Stations", Module.SITE_STATION),
//    STARTUP("Startup", Module.SITE_STATION),
    ORGANIGRAMME("Organigramme",Module.ORGANIGRAMMER),
    ORDRE_MISSION("Ordre de mission", Module.ORDRE_MISSION),
//    PASSATION_PLAN("Liste plan de passation",Module.PROJECT_CALL_FOR_TENDER),
    UGP("UGP", Module.PROJECT_SETTINGS),
//    DATE_IMPORTANT("date_important",Module.PROJECT_SETTINGS),
//    COMPONENT("Composantes", Module.PROJECT_SETTINGS),
    INTERVENTION_AREA("Zones d'intervention", Module.PROJECT_SETTINGS),
    SUPERVISION_EXECUTION_STRUCTURE("Structure tutelle/exécution", Module.PROJECT_SETTINGS),
    PARTNER("Partenaires", Module.PROJECT_SETTINGS),
    INDICATOR("Indicateurs", Module.PROJECT_SETTINGS),
    DEMANDE_CONGE("Demandes de congés", Module.ACTES_GESTION),
    ACTOR("Acteurs", Module.PROJECT_SETTINGS),
//    PROJET_ENTREPRISE("Acteurs", Module.PROJET_ENTREPRISE),
    MILESTONE("Jalons", Module.PROJECT_SETTINGS),
//    PROGRESS_TRACKING_COMPONENT("Composantes", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    PROGRESS_TRACKING_INDICATOR("Indicateurs", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    RISK("Risques", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    ISSUE_LOG("Problèmes", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    POINT_OF_VIGILANCE("Point de vigilance", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    STATUS("Statut", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    FLAG("Flags", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    FUNDING("Financements", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    MEDIATHEQUE("Médiathèque", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    DOCUMENT("Documents", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    BUDGET("Budget", Module.FINANCEMENT_SETTINGS),
    FUNDING_CONFIG(" besoin financement,Mobilisation et execution", Module.FINANCEMENT_SETTINGS),
    FUNDING_MOBILISATION("Mobilisation", Module.FINANCEMENT_SETTINGS),
    FUNDING_NEED("Besoin financemen", Module.FINANCEMENT_SETTINGS),
    FUNDING_EXECUTION("Execution", Module.FINANCEMENT_SETTINGS),
    FUNDING_SOURCE("Source Financement", Module.FINANCEMENT_SETTINGS),
    BUDGET_ACTIVITY("Source Budget Activity", Module.ACTIVITY_SETTINGS),
    EXPENSE_ACTIVITY("Source Expense Activity", Module.ACTIVITY_SETTINGS),
    ETAPE_MISSION_ACTIVITY("Etape Activity", Module.SETTINGS_ACTIVITE_MISSION),
    DISBURSEMENT_RATE("Taux de decaissement", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    VALUE_INDICATEUR("Indicateur", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    PREPARATION("Préparations", Module.PROJECT_ACTIVITY_MONITORING),
    CONDITIONALITY("Conditionnalités", Module.PROJECT_ACTIVITY_MONITORING),
    ASSIGNMENT("Missions", Module.PROJECT_ACTIVITY_MONITORING),
    REVIEW("Revues", Module.PROJECT_ACTIVITY_MONITORING),
    QUERY("Requêtes", Module.PROJECT_ACTIVITY_MONITORING),
    MEETING("Réunions", Module.PROJECT_ACTIVITY_MONITORING),
    AGENT("Agents", Module.REFERENTIEL),
    STRUCTURE("Structures", Module.REFERENTIEL),
    COMPLETION_RATE("Taux avancement", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
    EVALUATION("evaluation", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
//    FICHE_RENSEIGNEMENT("renseignement", Module.RENSEIGNEMENT_REGIONAL),
    MEETINGTYPE("Réunions", Module.REFERENTIEL),
    PHASE("Phases", Module.REFERENTIEL),
    FUNCTION("Fonctions", Module.REFERENTIEL),
    REF_FLAG("Flags", Module.REFERENTIEL),
    FUNDING_TYPE("Types de financement", Module.REFERENTIEL),
    CONDITIONALITY_TYPE("Types de conditionalité", Module.REFERENTIEL),
    NATURE("Natures", Module.REFERENTIEL),
    NATURE_RECETTE("Natures", Module.REFERENTIEL),
    SPECIFIC_NATURE("Natures spécifiques", Module.REFERENTIEL),
    DOCUMENT_TYPE("Types de document", Module.REFERENTIEL),
    CRITICITY("Criticités", Module.REFERENTIEL),
    PERIODICITY("Périodicités", Module.REFERENTIEL),
    PARTNER_GROUP("Groupes partenaire", Module.REFERENTIEL),
    TAG("Tags", Module.REFERENTIEL),
    PERIOD("Périodes", Module.REFERENTIEL),

    PARTICIPANT_MISSION("Mission Participant", Module.ACTIVITY_SETTINGS),
    STATE_PROGRESS("Etat avancement", Module.REFERENTIEL),
    UNIT("Unités", Module.REFERENTIEL),
    CADRE_LOGIQUE_TYPE("Types cadre logique", Module.REFERENTIEL),
    CADRE_LOGIQUE("Cadre logique", Module.REFERENTIEL),
    COUNTRY("Pays", Module.REFERENTIEL),
    SECTOR("Secteurs", Module.REFERENTIEL),
    SUB_SECTOR("Sous secteurs", Module.REFERENTIEL),
    SUB_CATEGORY("Sous catégorie", Module.REFERENTIEL),
    DELAY_IMPACT("Impacts délai", Module.REFERENTIEL),
    FINANCIAL_IMPACT("Impacts financier", Module.REFERENTIEL),
    REF_STATUS("Statuts", Module.REFERENTIEL),
    REF_STATUS_TYPE("Types Statuts", Module.REFERENTIEL),
    REF_INDICATOR("Indicateurs", Module.REFERENTIEL),
    INDICATOR_TYPE("Types indicateur", Module.REFERENTIEL),
    EXPENSE_TYPE("Types expense", Module.REFERENTIEL),

    REQUETE_TYPE("Types requete", Module.REFERENTIEL),
    ASSIGNMENT_TYPE("Types mission", Module.REFERENTIEL),
    ROLE("Roles", Module.REFERENTIEL),
    AXE_PSE("Axe PSE", Module.REFERENTIEL),
    CATEGORY("Catégorie", Module.REFERENTIEL),
    RESOLVE_CHANNEL("Canal de résolution", Module.REFERENTIEL),
    SOURCE("Sources", Module.REFERENTIEL),

    CASH("Monnaie", Module.REFERENTIEL),
    ECHANGE("Taux echange", Module.REFERENTIEL),
    ALERTE("Alertes", Module.ALERTE),
    TEMPLATE("Templates", Module.ALERTE),
    USER("Utilisateurs", Module.SECURITY),
    PROFILE("Profils", Module.SECURITY),
    LOG("Journal", Module.SECURITY),
    IMPACTSANDOBJECTIVE("Vision des performances", Module.PROJECT_DETAILED_PROGRESS_TRACKING),
//    ENVIRONNEMENTAL("Environnemental", Module.ESG),
//    SOCIAL("Social", Module.ESG),
//    GOUVERNANCE("Gouvernance", Module.ESG),

     ALL_ACCESS("Toutes les permissions", Module.SECURITY),
     WORKFLOW("Workflow", Module.SECURITY),
     JOURNAL("Journal", Module.SECURITY),
//    MESSAGERIE("Messagerie", Module.COMMUNICATION),
//    CHAT("Chat", Module.COMMUNICATION),
    LABEL("Label", Module.REFERENTIEL),
    PROMOTEUR("Promoteur", Module.REFERENTIEL);
//    PASSATION ("Promoteur", Module.BAF),


    @Getter
    private final String description;

    @Getter
    private final Module module;

    Feature(String description, Module module) {
        this.description = description;
        this.module = module;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Feature fromValue(Object feature) {
        if (feature instanceof Map) {
            Map<String, Object> mapFeature = (Map<String, Object>) feature;
            if (mapFeature.containsKey("name")) {
                return Feature.valueOf(mapFeature.get("name").toString());
            }
        }
        if (feature instanceof String) {
            return Feature.valueOf(feature.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Feature.class, feature, values()));
    }

    @JsonValue
    Map<String, Object> getFeature() {
        return Map.of(
                "name", name(),
                "module", module,
                "description", description
        );
    }

public static Set<Feature> readFeatureByModule(String module) {
    if (Objects.isNull(module)) {
        return Arrays.stream(values())
                .filter(securityPermissions -> !securityPermissions.equals(Feature.ALL_ACCESS))
//                .collect(Collectors.toSet());
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    return Arrays.stream(values())
            .filter(securityPermissions -> !securityPermissions.equals(Feature.ALL_ACCESS))
            .filter(securityPermissions -> securityPermissions.module.name().equals(module))
//            .collect(Collectors.toSet());
            .collect(Collectors.toCollection(LinkedHashSet::new));
}

    public static Set<Feature> getAllByModule(String name) {
        return  Arrays
                .stream(values())
                .filter(securityPermissions -> securityPermissions.module.name().equals(name))
                .collect(Collectors.toSet());
    }
}
