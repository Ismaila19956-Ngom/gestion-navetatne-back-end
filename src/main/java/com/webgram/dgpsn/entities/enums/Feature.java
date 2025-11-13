package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public enum Feature {
    DASHBOARD("Tableau de bord", Module.DASHBOARD),
    RAPPORT("Rapports", Module.DASHBOARD),
    PROJECT_LIST("Projets", Module.PROJECT_LIST),
    UGP("UGP", Module.PROJECT_LIST),
    PLAN_DE_PASSATION("Appel d'offre", Module.PROJECT_LIST),
    INTERVENTION_AREA("Zones d'intervention", Module.PROJECT_LIST),
    PARTNER("Partenaires", Module.PROJECT_LIST),
    INDICATOR("Indicateurs", Module.PROJECT_LIST),
    SUPERVISION_EXECUTION_STRUCTURE("Structure tutelle/exécution", Module.PROJECT_LIST),
    ACTOR("Acteurs", Module.PROJECT_LIST),
    MILESTONE("Jalons", Module.PROJECT_LIST),
    PROGRESS_TRACKING_INDICATOR("Indicateurs", Module.PROJECT_LIST),
    FUNDING("Financements", Module.PROJECT_LIST),

    ORDRE_MISSION("Ordre de mission", Module.ACTES_GESTION),
    CANDIDAT("Candidat", Module.ACTES_GESTION),
    DEMANDE_CONGE("Demandes de congés", Module.ACTES_GESTION),
     SERVICE_EXTERIEUR("Service externe", Module.ACTES_GESTION),
    PARTICIPANT("participant", Module.ACTES_GESTION),
    AGENT("Agents", Module.ACTES_GESTION),
    RECRUTEMENT("Recrutement", Module.ACTES_GESTION),
    CARACTERISTIQUE_RECRUTEMENT("Caracteristique recrutement", Module.ACTES_GESTION),

    COURRIER("Courrier", Module.GESTION_COURRIER),

    DOCUMENT("Documents", Module.DOCUMENT),

    ORGANIGRAMME("Organigramme",Module.ORGANIGRAMMER),
    PTBA("PTBA",Module.FINANCEMENT_SETTINGS),

    BUDGET("Budget", Module.FINANCEMENT_SETTINGS),
    FUNDING_CONFIG(" besoin financement,Mobilisation et execution", Module.FINANCEMENT_SETTINGS),
    FUNDING_MOBILISATION("Mobilisation", Module.FINANCEMENT_SETTINGS),
    FUNDING_NEED("Besoin financemen", Module.FINANCEMENT_SETTINGS),
    FUNDING_EXECUTION("Execution", Module.FINANCEMENT_SETTINGS),
    FUNDING_SOURCE("Source Financement", Module.FINANCEMENT_SETTINGS),
    BUDGET_ACTIVITY("Source Budget Activity", Module.FINANCEMENT_SETTINGS),
    EXPENSE_ACTIVITY("Source Expense Activity", Module.FINANCEMENT_SETTINGS),
    ETAPE_MISSION_ACTIVITY("Etape Activity", Module.FINANCEMENT_SETTINGS),
    VALUE_INDICATEUR("Indicateur", Module.FINANCEMENT_SETTINGS),

    STRUCTURE("Structures", Module.PARAMETRAGE),
    NATURE("Natures", Module.PARAMETRAGE),
    SPECIFIC_NATURE("Natures spécifiques", Module.PARAMETRAGE),
    DOCUMENT_TYPE("Types de document", Module.PARAMETRAGE),
    PERIODICITY("Périodicités", Module.PARAMETRAGE),
    PARTNER_GROUP("Groupes partenaire", Module.PARAMETRAGE),
    TAG("Tags", Module.PARAMETRAGE),
    PERIOD("Périodes", Module.PARAMETRAGE),
    UNIT("Unités", Module.PARAMETRAGE),
    CADRE_LOGIQUE_TYPE("Types cadre logique", Module.PARAMETRAGE),
    CADRE_LOGIQUE("Cadre logique", Module.PARAMETRAGE),
    COUNTRY("Pays", Module.PARAMETRAGE),
    SECTOR("Secteurs", Module.PARAMETRAGE),
    SUB_SECTOR("Sous secteurs", Module.PARAMETRAGE),
    SUB_CATEGORY("Sous catégorie", Module.PARAMETRAGE),
    REF_STATUS("Statuts", Module.PARAMETRAGE),
    REF_STATUS_TYPE("Types Statuts", Module.PARAMETRAGE),
    REF_INDICATOR("Indicateurs", Module.PARAMETRAGE),
    INDICATOR_TYPE("Types indicateur", Module.PARAMETRAGE),
    EXPENSE_TYPE("Types expense", Module.PARAMETRAGE),
    REQUETE_TYPE("Types requete", Module.PARAMETRAGE),
    ASSIGNMENT_TYPE("Types mission", Module.PARAMETRAGE),
    ROLE("Roles", Module.PARAMETRAGE),
    AXE_PSE("Axe PSE", Module.PARAMETRAGE),
     CATEGORY("Catégorie", Module.PARAMETRAGE),
    SOURCE("Sources", Module.PARAMETRAGE),
    LABEL("Label", Module.PARAMETRAGE),
    PROMOTEUR("Promoteur", Module.PARAMETRAGE),
    PLAN_COMPTABLE("Classe", Module.PARAMETRAGE),
    FOURNISSEUR("Fournisseur", Module.PARAMETRAGE),
    PLAN_DE_PASSATION_MARKET("Plan de passation market", Module.PROJECT_LIST),

    ALERTE("Alertes", Module.ALERTE),

    USER("Utilisateurs", Module.SECURITY),
    PROFILE("Profils", Module.SECURITY),
    LOG("Journal", Module.SECURITY),
    ALL_ACCESS("Toutes les permissions", Module.SECURITY),
    WORKFLOW("Workflow", Module.SECURITY),
    JOURNAL("Journal", Module.SECURITY);




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
        return Arrays
                .stream(values())
                .filter(securityPermissions -> securityPermissions.module.name().equals(name))
                .collect(Collectors.toSet());
    }
}
