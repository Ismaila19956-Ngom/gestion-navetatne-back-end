package com.webgram.dgpsn.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.webgram.dgpsn.entities.enums.Feature;
import com.webgram.dgpsn.entities.enums.Module;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum SecurityPermissions {
    // Dans com.webgram.dgpsn.security.SecurityPermissions

    READ_TDB_BUDGET("Consulter tableau de bord - Budget", Feature.DASHBOARD),
    READ_TDB_RESUME("Consulter tableau de bord - Rapport", Feature.DASHBOARD),
    READ_TDB_SERVICE("Consulter tableau de bord - Service Exterieur", Feature.DASHBOARD),
    READ_TDB_CONGE("Consulter tableau de bord - Congés", Feature.DASHBOARD),
    READ_TDB_AGENT("Consulter le tableau de bord des agents", Feature.DASHBOARD),
    READ_RAPPORT("Consulter le rapport", Feature.RAPPORT),
    EXPORT_RAPPORT("Exporter le rapport", Feature.RAPPORT),
    GENERATE_RAPPORT("Générer le rapport", Feature.RAPPORT),
//    AGENT_GROUPING("Consulter le tableau de bord des agents", Feature.DASHBOARD),
//    AGENT_STAT_CARD("Consulter le tableau de bord des agents", Feature.DASHBOARD),
//    AGENTS_FOR_DIRECTIONS("Consulter les agents par direction", Feature.DASHBOARD),
//    RETRAITE_PROJECTIONS("Consulter les previsions de retraite", Feature.DASHBOARD),

//    -------------

    READ_CONTRAT_AGENT("Consulter un contrat agent (RH)", Feature.AGENT),
    ADD_CONTRAT_AGENT("Ajouter un contrat agent (RH)",Feature.AGENT),
    DELETE_CONTRAT_AGENT("Supprimer un contrat agent (RH)",Feature.AGENT),
    UPDATE_CONTRAT_AGENT("Modifier un contrat agent (RH)",Feature.AGENT),

    /* début fonctionnalités sur la sécurité */
    ADD_PROFILE("Ajouter profil", Feature.PROFILE),
    EDIT_PROFILE("Modifier profil", Feature.PROFILE),
    DELETE_PROFILE("Supprimer profil", Feature.PROFILE),
    READ_PROFILE("Consulter profil", Feature.PROFILE),
    CONFIGURE_PROFILE("Configurer profil", Feature.PROFILE),
    ADD_USER("Ajouter utilisateur", Feature.USER),
    EDIT_USER("Modifier utilisateur", Feature.USER),
    DELETE_USER("Supprimer utilisateur", Feature.USER),
    READ_USER("Consulter utilisateur", Feature.USER),
    UPDATE_PASSWORD("Modifier mot de passe des utilisateurs", Feature.USER),
    UPDATE_MY_PASSWORD("Modifier mon mot de passe", Feature.USER),

    GENERATE_AUTH_CODE("Générer code d'authentification", Feature.USER),
    READ_TEMPORARY_CODE("Consulter liste des codes d'accès temporaires", Feature.USER),
    UPDATE_TEMPORARY_CODE_STATUS("Activer ou désactiver un code d'accès temporaire", Feature.USER),
    READ_LOG("Consulter journal", Feature.LOG),
    READ_ALERTE("Consulter liste alerte", Feature.ALERTE),
    ADD_ALERTE("Ajouter alerte", Feature.ALERTE),
    EDIT_ALERTE("Modifier alerte", Feature.ALERTE),
    DELETE_ALERTE("Supprimer alerte", Feature.ALERTE),
    READ_NOTIFICATION("Consulter les notifications", Feature.ALERTE),

    ADD_PLAN_DE_PASSATION("ajouter plan passation", Feature.PLAN_DE_PASSATION),
    READ_PLAN_DE_PASSATION("consulter plan passation", Feature.PLAN_DE_PASSATION),
    EDIT_PLAN_DE_PASSATION("modifier plan passation", Feature.PLAN_DE_PASSATION),
    DELETE_PLAN_DE_PASSATION("supprimer plan passation", Feature.PLAN_DE_PASSATION),

    READ_DIRECTION("Consulter les données direction", Feature.ORGANIGRAMME),
    ADD_DIRECTION("Ajouter de nouvelles  direction", Feature.ORGANIGRAMME),
    EDIT_DIRECTION("Modifier  direction", Feature.ORGANIGRAMME),
    DELETE_DIRECTION("Supprimer  direction", Feature.ORGANIGRAMME),


    READ_PROJECT("Consulter liste projet", Feature.PROJECT_LIST),
    ADD_PROJECT("Ajouter projet", Feature.PROJECT_LIST),
    EDIT_PROJECT("Modifier projet", Feature.PROJECT_LIST),
    DELETE_PROJECT("Supprimer projet", Feature.PROJECT_LIST),
    IMPORT_PROJECT("Importer projet", Feature.PROJECT_LIST),
    EXPORT_PROJECT("Exporter projet", Feature.PROJECT_LIST),
    READ_PROJECT_SETTINGS("Consulter paramétrage", Feature.PROJECT_LIST),
    READ_PROJECT_PROGRESS_TRACKING("Consulter avancement détaillé", Feature.PROJECT_LIST),
    READ_PROJECT_ACTIVITY_MONITORING("Consulter suivi des activités", Feature.PROJECT_LIST),
    READ_PROJECT_FINANCEMENT("Consulter financement", Feature.PROJECT_LIST),
    READ_PROJECT_APPEL_OFFRE("Consulter appels d'offres", Feature.PROJECT_LIST),
    READ_PROJECT_ESG("Consulter ESG", Feature.PROJECT_LIST),
    READ_PROJECT_TDB("Consulter tableau de bord projet", Feature.PROJECT_LIST),
    READ_PROJECT_EENTREPRISE("Consulter entreprises", Feature.PROJECT_LIST),
    READ_SUMMARY_SHEET("Consulter fiche de synthèse", Feature.PROJECT_LIST),
    READ_DETAIL("Consulter détails", Feature.PROJECT_LIST),
    ADD_PLAN_DE_PASSATION_MARKET("Ajouter plan de passation", Feature.PROJECT_LIST),
    READ_PLAN_DE_PASSATION_MARKET("Consulter plan de passation", Feature.PROJECT_LIST),
    DELETE_PLAN_DE_PASSATION_MARKET("Supprimer plan de passation", Feature.PROJECT_LIST),
    EDIT_PLAN_DE_PASSATION_MARKET("Modifier plan de passation", Feature.PROJECT_LIST),

    /* fin fonctionnalités sur la liste des projets */


    /* début fonctionnalités sur paramétrage des projets */

    READ_UGP("Consulter ugp", Feature.UGP),
    ADD_UGP("Ajouter ugp", Feature.UGP),
    EDIT_UGP("Modifier ugp", Feature.UGP),
    DELETE_UGP("Supprimer ugp", Feature.UGP),
    READ_INTERVENTION_AREA("Consulter zone intervention", Feature.INTERVENTION_AREA),
    ADD_INTERVENTION_AREA("Ajouter zone intervention", Feature.INTERVENTION_AREA),
    DELETE_INTERVENTION_AREA("Supprimer zone intervention", Feature.INTERVENTION_AREA),
    EXPORT_INTERVENTION_AREA("Exporter zone intervention", Feature.INTERVENTION_AREA),
    IMPORT_INTERVENTION_AREA("Importer zone intervention", Feature.INTERVENTION_AREA),

    READ_SUPERVISION_EXECUTION_STRUCTURE("Consulter structure de tutelle/exécution", Feature.SUPERVISION_EXECUTION_STRUCTURE),
    ADD_SUPERVISION_EXECUTION_STRUCTURE("Ajouter structure de tutelle/exécution", Feature.SUPERVISION_EXECUTION_STRUCTURE),
    EDIT_SUPERVISION_EXECUTION_STRUCTURE("Modifier structure de tutelle/exécution", Feature.SUPERVISION_EXECUTION_STRUCTURE),
    DELETE_SUPERVISION_EXECUTION_STRUCTURE("Supprimer structure de tutelle/exécution", Feature.SUPERVISION_EXECUTION_STRUCTURE),
    EXPORT_SUPERVISION_EXECUTION_STRUCTURE("Supprimer structure de tutelle/exécution", Feature.SUPERVISION_EXECUTION_STRUCTURE),
    IMPORT_SUPERVISION_EXECUTION_STRUCTURE("Supprimer structure de tutelle/exécution", Feature.SUPERVISION_EXECUTION_STRUCTURE),

    READ_PARTNER("Consulter partenaire", Feature.PARTNER),
    ADD_PARTNER("Ajouter partenaire", Feature.PARTNER),
    EDIT_PARTNER("Modifier partenaire", Feature.PARTNER),
    DELETE_PARTNER("Supprimer partenaire", Feature.PARTNER),
    EXPORT_PARTNER("Exporter partenaire", Feature.PARTNER),
    IMPORT_PARTNER("Importer partenaire", Feature.PARTNER),

    ADD_CONGE("Ajouter congé", Feature.DEMANDE_CONGE),
    EDIT_CONGE("Modifier congé", Feature.DEMANDE_CONGE),
    DELETE_CONGE("Supprimer congé", Feature.DEMANDE_CONGE),
    READ_CONGE("Consulter congé", Feature.DEMANDE_CONGE),
    VALIDATION_CONGE("Valider congé", Feature.DEMANDE_CONGE),
    GENERATE_FICHE_CONGE("Générer l’attestation de reprise", Feature.DEMANDE_CONGE),
    GENERATE_DEMANDE_CONGE("Générer demande  congé", Feature.DEMANDE_CONGE),
    GENERATE_DECISION_CONGE("Générer décision  congé", Feature.DEMANDE_CONGE),
   // GENERATE_BE_CONGE("Générer BE congé", Feature.DEMANDE_CONGE),
    READ_DOCUMENT_CONGE("Consulter document congé", Feature.DEMANDE_CONGE),

    READ_CESSATION_CONGE("Consulter Cessation Service", Feature.DEMANDE_CONGE),
    ADD_CESSATION("Ajouter Cessation Service", Feature.DEMANDE_CONGE),
    EDIT_CESSATION("Modifier Cessation Service", Feature.DEMANDE_CONGE),
    DELETE_CESSATION("Supprimer Cessation Service", Feature.DEMANDE_CONGE),


    READ_INDICATOR("Consulter indicateur", Feature.INDICATOR),
    ADD_INDICATOR("Ajouter indicateur", Feature.INDICATOR),
    EDIT_INDICATOR("Modifier indicateur", Feature.INDICATOR),
    DELETE_INDICATOR("Supprimer indicateur", Feature.INDICATOR),
    EXPORT_INDICATOR("Exporter indicateur", Feature.INDICATOR),
    IMPORT_INDICATOR("Importer indicateur", Feature.INDICATOR),

    READ_ACTOR("Consulter acteur", Feature.ACTOR),
    ADD_ACTOR("Ajouter acteur", Feature.ACTOR),
    EDIT_ACTOR("Modifier acteur", Feature.ACTOR),
    DELETE_ACTOR("Supprimer acteur", Feature.ACTOR),
    EXPORT_ACTOR("Exporter acteur", Feature.ACTOR),
    IMPORT_ACTOR("Importer acteur", Feature.ACTOR),


    READ_NOTATION("Consulter candidat", Feature.CANDIDAT),
    ADD_NOTATION("Ajouter candidat", Feature.CANDIDAT),
    EDIT_NOTATION("Modifier candidat", Feature.CANDIDAT),
    DELETE_NOTATION("Supprimer candidat", Feature.CANDIDAT),

    READ_CANDIDAT("Consulter candidat", Feature.CANDIDAT),
    ADD_CANDIDAT("Ajouter candidat", Feature.CANDIDAT),
    EDIT_CANDIDAT("Modifier candidat", Feature.CANDIDAT),
    DELETE_CANDIDAT("Supprimer candidat", Feature.CANDIDAT),

    READ_CARACTERISTIQUE_RECRUTEMENT("Consulter candidat", Feature.CARACTERISTIQUE_RECRUTEMENT),
    ADD_CARACTERISTIQUE_RECRUTEMENT("Ajouter candidat", Feature.CARACTERISTIQUE_RECRUTEMENT),
    EDIT_CARACTERISTIQUE_RECRUTEMENT("Modifier candidat", Feature.CARACTERISTIQUE_RECRUTEMENT),
    DELETE_CARACTERISTIQUE_RECRUTEMENT("Supprimer candidat", Feature.CARACTERISTIQUE_RECRUTEMENT),


    READ_MILESTONE("Consulter dates importantes", Feature.MILESTONE),
    ADD_MILESTONE("Ajouter dates importantes", Feature.MILESTONE),
    EDIT_MILESTONE("Modifier dates importantes", Feature.MILESTONE),
    DELETE_MILESTONE("Supprimer dates importantes", Feature.MILESTONE),
    IMPORT_MILESTONE("Supprimer dates importantes", Feature.MILESTONE),
    EXPORT_MILESTONE("Supprimer dates importantes", Feature.MILESTONE),


    READ_PROGRESS_TRACKING_INDICATOR("Consulter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    VALIDER_PROGRESS_TRACKING_INDICATOR("valider valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    ADD_PROGRESS_TRACKING_INDICATOR("Ajouter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    EDIT_PROGRESS_TRACKING_INDICATOR("Modifier valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    DELETE_PROGRESS_TRACKING_INDICATOR("Supprimer valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    IMPORT_PROGRESS_TRACKING_INDICATOR("Importer valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    EXPORT_PROGRESS_TRACKING_INDICATOR("Exporter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    READ_FILE_PROGRESS_TRACKING_INDICATOR("Exporter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),


    /// //
    READ_DATES_IMPORTANTES("Consulter les dates importantes", Feature.MILESTONE),
    ADD_DATES_IMPORTANTES("Ajouter les dates importantes", Feature.MILESTONE),
    EDIT_DATES_IMPORTANTES("Modifier les dates importantes", Feature.MILESTONE),
    DELETE_DATES_IMPORTANTES("Supprimer les dates importantes", Feature.MILESTONE),
    EXPORT_DATES_IMPORTANTES("Supprimer les dates importantes", Feature.MILESTONE),
    IMPORT_DATES_IMPORTANTES("Supprimer les dates importantes", Feature.MILESTONE),
    /// ///////////////

    READ_STATUS("Consulter statut", Feature.REF_STATUS),
    ADD_STATUS("Ajouter statut", Feature.REF_STATUS),
    EDIT_STATUS("Modifier statut", Feature.REF_STATUS),
    DELETE_STATUS("Supprimer statut", Feature.REF_STATUS),


    READ_FUNDING("Consulter financement", Feature.FUNDING),
    ADD_FUNDING("Ajouter financement", Feature.FUNDING),
    EDIT_FUNDING("Modifier financement", Feature.FUNDING),
    DELETE_FUNDING("Supprimer financement", Feature.FUNDING),
    EXPORT_FUNDING("Exporter financement", Feature.FUNDING),
    IMPORT_FUNDING("Importer financement", Feature.FUNDING),
    DETAIL_FUNDING("Detail financement", Feature.FUNDING),

    READ_PROLONGATION("Consulter prolongation", Feature.FUNDING),
    ADD_PROLONGATION("Ajouter prolongation", Feature.FUNDING),
    EDIT_PROLONGATION("Modifier prolongation", Feature.FUNDING),
    DELETE_PROLONGATION("Supprimer prolongation", Feature.FUNDING),


    READ_DOCUMENT("Consulter document", Feature.DOCUMENT),
    ADD_DOCUMENT("Ajouter document", Feature.DOCUMENT),
    EDIT_DOCUMENT("Modifier document", Feature.DOCUMENT),
    DELETE_DOCUMENT("Supprimer document", Feature.DOCUMENT),
    READ_FILE_DOCUMENT("Lire document", Feature.DOCUMENT),


    ADD_REALISATION("Suivi des lignes budgétaires / Rubriques / Réalisation / Ajouter une réalisation", Feature.BUDGET),
    EDIT_REALISATION("Suivi des lignes budgétaires / Rubriques / Réalisation / Modifier realisation", Feature.BUDGET),
    DELETE_REALISATION("Suivi des lignes budgétaires / Rubriques / Réalisation / Supprimer realisation", Feature.BUDGET),
    READ_REALISATION("Suivi des lignes budgétaires / Rubriques / Réalisation / Consulter realisation", Feature.BUDGET),

    ADD_BUDGET("Ajouter budget", Feature.BUDGET),
    EDIT_BUDGET("Modifier budget", Feature.BUDGET),
    DELETE_BUDGET("Supprimer budget", Feature.BUDGET),
    READ_BUDGET("Consulter budget", Feature.BUDGET),
    SUIVIS_LIGNE_BUDGETAIRE("Budjet / Suivis ligne budgetaire", Feature.BUDGET),

    READ_FINANCEMENT_BUDGET("Consulter financement budget", Feature.BUDGET),
    READ_PTBA("Consulter PTBA", Feature.PTBA),

    ADD_LIGNE_BUDGETAIRE("Suivi des lignes budgétaires / Rubriques /Ajouter une ligne budgetaire", Feature.BUDGET),
    EDIT_LIGNE_BUDGETAIRE("Suivi des lignes budgétaires / Rubriques /Modifier ligne budgetaire", Feature.BUDGET),
    DELETE_LIGNE_BUDGETAIRE("Suivi des lignes budgétaires / Rubriques /Supprimer ligne budgetaire", Feature.BUDGET),
    READ_LIGNE_BUDGETAIRE("Suivi des lignes budgétaires / Rubriques /Consulter ligne budgetaire", Feature.BUDGET),

    ADD_FUNDING_SOURCE("Financement / Objectifs / Ajouter Souce financement", Feature.BUDGET),
    EDIT_FUNDING_SOURCE("Financement / Objectifs / Modifier Souce financement", Feature.BUDGET),
    DELETE_FUNDING_SOURCE("Financement / Objectifs / Supprimer Souce financement", Feature.BUDGET),
    READ_FUNDING_SOURCE("Financement / Objectifs / Lire Souce financement", Feature.BUDGET),

    ADD_FUNDING_CONFIG("Ajout  besoin financement,Mobilisation et execution ", Feature.FUNDING_CONFIG),
    EDIT_FUNDING_CONFIG("Ajout  besoin financement,Mobilisation et execution ", Feature.FUNDING_CONFIG),
    DELETE_FUNDING_CONFIG("Ajout  besoin financement,Mobilisation et execution ", Feature.FUNDING_CONFIG),
    READ_FUNDING_CONFIG("Ajout  besoin financement,Mobilisation et execution ", Feature.FUNDING_CONFIG),
    ADD_FUNDING_NEED("Ajout  besoin financement ", Feature.FUNDING_NEED),
    EDIT_FUNDING_NEED("Modifier  besoin financement", Feature.FUNDING_NEED),
    DELETE_FUNDING_NEED("Supprimer  besoin financement ", Feature.FUNDING_NEED),
    READ_FUNDING_NEED("Lire besoin financement", Feature.FUNDING_NEED),
//    CONSULTER_MENU_NEED("Consulter besoin financement", Feature.FUNDING_MENU),

    ADD_FUNDING_EXECUTION("Ajout Execution ", Feature.FUNDING_EXECUTION),
    EDIT_FUNDING_EXECUTION("Modifier Execution ", Feature.FUNDING_EXECUTION),
    DELETE_FUNDING_EXECUTION("Supprimer Execution ", Feature.FUNDING_EXECUTION),
    READ_FUNDING_EXECUTION("Lire Execution  ", Feature.FUNDING_EXECUTION),

    ADD_FUNDING_MOBILISATION("Ajout Mobilisation", Feature.FUNDING_MOBILISATION),
    EDIT_FUNDING_MOBILISATION("Modifier Mobilisation", Feature.FUNDING_MOBILISATION),
    DELETE_FUNDING_MOBILISATION("Supprimer Mobilisation", Feature.FUNDING_MOBILISATION),
    READ_FUNDING_MOBILISATION("Lire Mobilisation ", Feature.FUNDING_MOBILISATION),


    ADD_BUDGET_ACTIVITY("Ajouter budget", Feature.BUDGET_ACTIVITY),
    EDIT_BUDGET_ACTIVITY("Modifier budget", Feature.BUDGET_ACTIVITY),
    DELETE_BUDGET_ACTIVITY("Supprimer budget", Feature.BUDGET_ACTIVITY),
    READ_BUDGET_ACTIVITY("Lire budget", Feature.BUDGET_ACTIVITY),
    ADD_ETAPE_MISSION_ACTIVITY("ajout etape", Feature.ETAPE_MISSION_ACTIVITY),
    EDIT_ETAPE_MISSION_ACTIVITY("Modifier etape", Feature.ETAPE_MISSION_ACTIVITY),
    DELETE_ETAPE_MISSION_ACTIVITY("Supprimer etape", Feature.ETAPE_MISSION_ACTIVITY),
    READ_ETAPE_MISSION_ACTIVITY("Lire etape", Feature.ETAPE_MISSION_ACTIVITY),

    ADD_FORMATION_EXTERIEUR("Ajouter formation externe", Feature.SERVICE_EXTERIEUR),
    READ_FORMATION_EXTERIEUR("Consulter formation externe", Feature.SERVICE_EXTERIEUR),
    EDIT_FORMATION_EXTERIEUR("Modifier formation externe", Feature.SERVICE_EXTERIEUR),
    DELETE_FORMATION_EXTERIEUR("Supprimer formation externe", Feature.SERVICE_EXTERIEUR),


    READ_ATELIER("lier atelier", Feature.SERVICE_EXTERIEUR),
    ADD_ATELIER("ajouter atelier ", Feature.SERVICE_EXTERIEUR),
    EDIT_ATELIER("modifier atelier ", Feature.SERVICE_EXTERIEUR),
    DELETE_ATELIER("supprimer atelier ", Feature.SERVICE_EXTERIEUR),

    READ_PARTICIPANT("lire participant ", Feature.PARTICIPANT),
    ADD_PARTICIPANT("ajouter participant ", Feature.PARTICIPANT),
    EDIT_PARTICIPANT("modifier participant ", Feature.PARTICIPANT),
    DELETE_PARTICIPANT("supprimer participant ", Feature.PARTICIPANT),


    ADD_EXPENSE_ACTIVITY("Ajouter expense", Feature.EXPENSE_ACTIVITY),
    EDIT_EXPENSE_ACTIVITY("Modifier expense", Feature.EXPENSE_ACTIVITY),
    DELETE_EXPENSE_ACTIVITY("Supprimer expense", Feature.EXPENSE_ACTIVITY),
    READ_EXPENSE_ACTIVITY("Lire expense", Feature.EXPENSE_ACTIVITY),


    /* fin fonctionnalités sur suivi des activités */

    /* début fonctionnalités sur le référentiel */

    READ_AGENT("Consulter agent", Feature.AGENT),
    ADD_AGENT("Ajouter agent", Feature.AGENT),
    EDIT_AGENT("Modifier agent", Feature.AGENT),
    DELETE_AGENT("Supprimer agent", Feature.AGENT),
    IMPORT_AGENT("Import agent", Feature.AGENT),
    EXPORT_AGENT("Export agent", Feature.AGENT),
    READ_FILE_AGENT("Read file agent", Feature.AGENT),

    ADD_RECRUTEMENT("Ajouter recrutement", Feature.RECRUTEMENT),
    EDIT_RECRUTEMENT("Modifier recrutement", Feature.RECRUTEMENT),
    DELETE_RECRUTEMENT("Supprimer recrutement", Feature.RECRUTEMENT),
    READ_RECRUTEMENT("Consulter recrutement", Feature.RECRUTEMENT),
    IMPORT_RECRUTEMENT("Import recrutement", Feature.RECRUTEMENT),
    UPDATE_STATUT_RECRUTEMENT("Modifier statut recrutement", Feature.RECRUTEMENT), // AJOUTER CETTE LIGNE
    EXPORT_RECRUTEMENT("Export recrutement", Feature.RECRUTEMENT),
    READ_FILE_RECRUTEMENT("read file recrutement", Feature.RECRUTEMENT),

    /*parametrage plant comptable*/
    READ_PLAN_COMPTABLE("Consulter classe comptable", Feature.PLAN_COMPTABLE),
    ADD_PLAN_COMPTABLE("Ajouter classe comptable", Feature.PLAN_COMPTABLE),
    EDIT_PLAN_COMPTABLE("Modifier classe comptable", Feature.PLAN_COMPTABLE),
    DELETE_PLAN_COMPTABLE("Supprimer classe comptable", Feature.PLAN_COMPTABLE),
    IMPORT_PLAN_COMPTABLE("Import classe comptable", Feature.PLAN_COMPTABLE),
    EXPORT_PLAN_COMPTABLE("Export class comptable", Feature.PLAN_COMPTABLE),
    READ_FILE_PLAN_COMPTABLE("Read file class comptable", Feature.PLAN_COMPTABLE),

    /*fournisseur */
    READ_FOURNISSEUR("Consulter fournisseur", Feature.FOURNISSEUR),
    ADD_FOURNISSEUR("Ajouter un fournisseur", Feature.PLAN_COMPTABLE),
    EDIT_FOURNISSEUR("Modifier un fournisseur", Feature.FOURNISSEUR),
    DELETE_FOURNISSEUR("Supprimer un fournisseur", Feature.FOURNISSEUR),

    READ_SUB_CATEGORY("Consulter sous-categorie", Feature.SUB_CATEGORY),
    ADD_SUB_CATEGORY("Ajouter sous-categorie", Feature.SUB_CATEGORY),
    EDIT_SUB_CATEGORY("Modifier sous-categorie", Feature.SUB_CATEGORY),
    DELETE_SUB_CATEGORY("Supprimer sous-categorie", Feature.SUB_CATEGORY),

    READ_PERIODICITY("Consulter periodicite", Feature.PERIODICITY),
    ADD_PERIODICITY("Ajouter periodicite", Feature.PERIODICITY),
    EDIT_PERIODICITY("Modifier periodicite", Feature.PERIODICITY),
    DELETE_PERIODICITY("Supprimer periodicite", Feature.PERIODICITY),

    READ_PARTNER_GROUP("Consulter groupe partenaire", Feature.PARTNER_GROUP),
    ADD_PARTNER_GROUP("Ajouter groupe partenaire", Feature.PARTNER_GROUP),
    EDIT_PARTNER_GROUP("Modifier groupe partenaire", Feature.PARTNER_GROUP),
    DELETE_PARTNER_GROUP("Supprimer groupe partenaire", Feature.PARTNER_GROUP),

    READ_TAG("Consulter tag", Feature.TAG),
    ADD_TAG("Ajouter tag ", Feature.TAG),
    EDIT_TAG("Modifier tag", Feature.TAG),
    DELETE_TAG("Supprimer tag", Feature.TAG),

    READ_PERIOD("Consulter periode", Feature.PERIOD),
    ADD_PERIOD("Ajouter periode ", Feature.PERIOD),
    EDIT_PERIOD("Modifier periode", Feature.PERIOD),
    DELETE_PERIOD("Supprimer periode", Feature.PERIOD),

    READ_UNIT("Consulter unite", Feature.UNIT),
    ADD_UNIT("Ajouter unite", Feature.UNIT),
    EDIT_UNIT("Modifier unite", Feature.UNIT),
    DELETE_UNIT("Supprimer unite", Feature.UNIT),

    READ_CADRE_LOGIQUE_TYPE("Consulter type cadre logique", Feature.CADRE_LOGIQUE_TYPE),
    ADD_CADRE_LOGIQUE_TYPE("Ajouter type cadre logique", Feature.CADRE_LOGIQUE_TYPE),
    EDIT_CADRE_LOGIQUE_TYPE("Modifier type cadre logiquee", Feature.CADRE_LOGIQUE_TYPE),
    DELETE_CADRE_LOGIQUE_TYPE("Supprimer type cadre logique", Feature.CADRE_LOGIQUE_TYPE),

    READ_CADRE_LOGIQUE("Consulter cadre logique", Feature.CADRE_LOGIQUE),
    ADD_CADRE_LOGIQUE("Ajouter cadre logique", Feature.CADRE_LOGIQUE),
    EDIT_CADRE_LOGIQUE("Modifier cadre logique", Feature.CADRE_LOGIQUE),
    DELETE_CADRE_LOGIQUE("Supprimer cadre logique", Feature.CADRE_LOGIQUE),

    READ_COUNTRY("Consulter pays", Feature.COUNTRY),
    ADD_COUNTRY("Ajouter pays", Feature.COUNTRY),
    EDIT_COUNTRY("Modifier pays", Feature.COUNTRY),
    DELETE_COUNTRY("Supprimer pays", Feature.COUNTRY),

    READ_SECTOR("Consulter secteur", Feature.SECTOR),
    ADD_SECTOR("Ajouter secteur", Feature.SECTOR),
    EDIT_SECTOR("Modifier secteur", Feature.SECTOR),
    DELETE_SECTOR("Supprimer secteur", Feature.SECTOR),

    READ_SUB_SECTOR("Consulter secteur", Feature.SUB_SECTOR),
    ADD_SUB_SECTOR("Ajouter secteur", Feature.SUB_SECTOR),
    EDIT_SUB_SECTOR("Modifier secteur", Feature.SUB_SECTOR),
    DELETE_SUB_SECTOR("Supprimer secteur", Feature.SUB_SECTOR),

    READ_REF_STATUS("Consulter statut", Feature.REF_STATUS),
    ADD_REF_STATUS("Ajouter  statut", Feature.REF_STATUS),
    EDIT_REF_STATUS("Modifier statut", Feature.REF_STATUS),
    DELETE_REF_STATUS("Supprimer statut", Feature.REF_STATUS),

    READ_REF_STATUS_TYPE("Consulter types statut", Feature.REF_STATUS_TYPE),
    ADD_REF_STATUS_TYPE("Ajouter types statut", Feature.REF_STATUS_TYPE),
    EDIT_REF_STATUS_TYPE("Modifier types statut", Feature.REF_STATUS_TYPE),
    DELETE_REF_STATUS_TYPE("Supprimer types statut", Feature.REF_STATUS_TYPE),
    READ_REF_INDICATOR("Consulter indicateur", Feature.REF_INDICATOR),
    ADD_REF_INDICATOR("Ajouter indicateur", Feature.REF_INDICATOR),
    EDIT_REF_INDICATOR("Modifier indicateur", Feature.REF_INDICATOR),
    DELETE_REF_INDICATOR("Supprimer indicateur", Feature.REF_INDICATOR),
    READ_REF_TYPE_EXPENSE("Supprimer Type expense", Feature.EXPENSE_TYPE),
    ADD_REF_TYPE_EXPENSE("Ajouter Type expense", Feature.EXPENSE_TYPE),
    EDIT_REF_TYPE_EXPENSE("Modifier Type expense", Feature.EXPENSE_TYPE),
    DELETE_REF_TYPE_EXPENSE("Supprimer Type expense", Feature.EXPENSE_TYPE),

    READ_REF_TYPE_REQUETE("Supprimer Type requete", Feature.REQUETE_TYPE),
    ADD_REF_TYPE_REQUETE("Ajouter Type requete", Feature.REQUETE_TYPE),
    EDIT_REF_TYPE_REQUETE("Modifier Type requete", Feature.REQUETE_TYPE),
    DELETE_REF_TYPE_REQUETE("Supprimer Type requete", Feature.REQUETE_TYPE),

    READ_INDICATOR_TYPE("Consulter type indicateur", Feature.INDICATOR_TYPE),
    ADD_INDICATOR_TYPE("Ajouter type indicateur", Feature.INDICATOR_TYPE),
    EDIT_INDICATOR_TYPE("Modifier type indicateur", Feature.INDICATOR_TYPE),
    DELETE_INDICATOR_TYPE("Supprimer type indicateur", Feature.INDICATOR_TYPE),

    READ_ASSIGNMENT_TYPE("Consulter type mission", Feature.ASSIGNMENT_TYPE),
    ADD_ASSIGNMENT_TYPE("Ajouter type mission", Feature.ASSIGNMENT_TYPE),
    EDIT_ASSIGNMENT_TYPE("Modifier type mission", Feature.ASSIGNMENT_TYPE),
    DELETE_ASSIGNMENT_TYPE("Supprimer type mission", Feature.ASSIGNMENT_TYPE),

    READ_ROLE("Consulter role", Feature.ROLE),
    ADD_ROLE("Ajouter role", Feature.ROLE),
    EDIT_ROLE("Modifier role", Feature.ROLE),
    DELETE_ROLE("Supprimer role", Feature.ROLE),

    READ_AXE_PSE("Consulter axePse", Feature.AXE_PSE),
    ADD_AXE_PSE("Ajouter axePse", Feature.AXE_PSE),
    EDIT_AXE_PSE("Modifier axePse", Feature.AXE_PSE),
    DELETE_AXE_PSE("Supprimer axePse", Feature.AXE_PSE),

    READ_CATEGORY("Consulter categorie", Feature.CATEGORY),
    ADD_CATEGORY("Ajouter categorie", Feature.CATEGORY),
    EDIT_CATEGORY("Modifier categorie", Feature.CATEGORY),
    DELETE_CATEGORY("Supprimer categorie", Feature.CATEGORY),


    READ_RESPONDENT("Consulter repondant", Feature.FUNDING),
    ADD_RESPONDENT("Ajouter repondant", Feature.FUNDING),
    EDIT_RESPONDENT("Modifier repondant", Feature.FUNDING),
    DELETE_RESPONDENT("Supprimer repondant", Feature.FUNDING),

    READ_SOURCE("Consulter canal de resolution", Feature.SOURCE),
    ADD_SOURCE("Ajouter canal de resolution", Feature.SOURCE),
    EDIT_SOURCE("Modifier canal de resolution", Feature.SOURCE),
    DELETE_SOURCE("Supprimer canal de resolution", Feature.SOURCE),

    READ_STRUCTURE("Consulter structure", Feature.STRUCTURE),
    ADD_STRUCTURE("Ajouter structure", Feature.STRUCTURE),
    EDIT_STRUCTURE("Modifier structure", Feature.STRUCTURE),
    DELETE_STRUCTURE("Supprimer structure", Feature.STRUCTURE),
    IMPORT_STRUCTURE("Import structure", Feature.STRUCTURE),
    EXPORT_STRUCTURE("Export structure", Feature.STRUCTURE),


    READ_FUNDING_TYPE("Consulter type de financement", Feature.FUNDING),
    ADD_FUNDING_TYPE("Ajouter type de financement", Feature.FUNDING),
    EDIT_FUNDING_TYPE("Modifier type de financement", Feature.FUNDING),
    DELETE_FUNDING_TYPE("Supprimer type de financement", Feature.FUNDING),


    READ_NATURE("Consulter nature", Feature.NATURE),
    ADD_NATURE("Ajouter nature", Feature.NATURE),
    EDIT_NATURE("Modifier nature", Feature.NATURE),
    DELETE_NATURE("Supprimer nature", Feature.NATURE),


    READ_SPECIFIC_NATURE("Consulter nature specifique", Feature.SPECIFIC_NATURE),
    ADD_SPECIFIC_NATURE("Ajouter nature specifique", Feature.SPECIFIC_NATURE),
    EDIT_SPECIFIC_NATURE("Modifier nature specifique", Feature.SPECIFIC_NATURE),
    DELETE_SPECIFIC_NATURE("Supprimer nature specifique", Feature.SPECIFIC_NATURE),

    READ_DOCUMENT_TYPE("Consulter document type", Feature.DOCUMENT_TYPE),
    ADD_DOCUMENT_TYPE("Ajouter document type", Feature.DOCUMENT_TYPE),
    EDIT_DOCUMENT_TYPE("Modifier document type", Feature.DOCUMENT_TYPE),
    DELETE_DOCUMENT_TYPE("Supprimer document type", Feature.DOCUMENT_TYPE),


    ALL_ACCESS("Toutes les permissions", Feature.ALL_ACCESS),
    READ_WORKFLOW_HISTORIQUE("Consulter historique de validation", Feature.WORKFLOW),
    ADD_WORKFLOW_HISTORIQUE("Ajouter historique de validation", Feature.WORKFLOW),
    EDIT_WORKFLOW_HISTORIQUE("Modifier historique de validation", Feature.WORKFLOW),
    DELETE_WORKFLOW_HISTORIQUE("Supprimer historique de validation", Feature.WORKFLOW),

    ADD_ORDRE_MISSION_AGENT("Ajouter ordre de mission", Feature.ORDRE_MISSION),
    EDIT_ORDRE_MISSION_AGENT("Modifier ordre de mission", Feature.ORDRE_MISSION),
    DELETE_ORDRE_MISSION_AGENT("Supprimer ordre de mission", Feature.ORDRE_MISSION),
    READ_ORDRE_MISSION_AGENT("Consulter ordre de mission", Feature.ORDRE_MISSION),

    READ_JOURNAL("Consulter journal", Feature.JOURNAL),
    ADD_ORGANIGRAMME("Ajouter organigramme", Feature.ORGANIGRAMME),
    EDIT_ORGANIGRAMME("Modifier organigramme", Feature.ORGANIGRAMME),
    DELETE_ORGANIGRAMME("Supprimer organigramme", Feature.ORGANIGRAMME),
    READ_ORGANIGRAMME("Consulter organigramme", Feature.ORGANIGRAMME),
    CONFIG_AGENT("Gerer agents", Feature.ORGANIGRAMME),

    READ_FOLDER("Consulter dossier", Feature.DOCUMENT),
    ADD_FOLDER("Ajouter dossier", Feature.DOCUMENT),
    EDIT_FOLDER("Modifier dossier", Feature.DOCUMENT),
    DELETE_FOLDER("Supprimer dossier", Feature.DOCUMENT),

    READ_LABEL("Consulter label", Feature.LABEL),
    ADD_LABEL("Ajouter label", Feature.LABEL),
    EDIT_LABEL("Modifier label", Feature.LABEL),
    DELETE_LABEL("Supprimer label", Feature.LABEL),

    /* Début security courriers */
    ADD_COURRIER("Ajouter courrier", Feature.COURRIER),
    READ_COURRIER("Consulter courrier", Feature.COURRIER),
    EDIT_COURRIER("Modifier courrier", Feature.COURRIER),
    DELETE_COURRIER("Supprimer courrier", Feature.COURRIER),
    ARCHIVER_COURRIER("Archiver courrier", Feature.COURRIER),
    DESARCHIVER_COURRIER("Desarchiver courrier", Feature.COURRIER),
    CHANGER_STATUT_COURRIER("Changer statut courrier", Feature.COURRIER),
    READ_STATISTIQUES("Consulter statistiques", Feature.COURRIER),
    IMPORT_COURRIER("Importer courrier", Feature.COURRIER),
    EXPORT_COURRIER("Exporter courrier", Feature.COURRIER),
    DOCUMENT_COURIER("Consulter document courrier ", Feature.COURRIER),
    /* Fin security courriers */

    ADD_PROMOTEUR("Ajouter promoteur", Feature.PROMOTEUR),

    EDIT_PROMOTEUR("Modifier promoteur", Feature.PROMOTEUR),

    DELETE_PROMOTEUR("Supprimer promoteur", Feature.PROMOTEUR),

    READ_PROMOTEUR("Consulter promoteur", Feature.PROMOTEUR),


    //
//    READ_CHAT("Consulter chat", Feature.CHAT),
//    READ_MESSAGERIE("Consulter messagerie", Feature.MESSAGERIE),

    ;

    @Getter
    private final String description;

    @Getter
    private final Feature feature;

    SecurityPermissions(String description, Feature feature) {
        this.description = description;
        this.feature = feature;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SecurityPermissions fromValue(Object permission) {
        if (permission instanceof Map) {
            Map<String, Object> mapPermission = (Map<String, Object>) permission;
            if (mapPermission.containsKey("name")) {
                return SecurityPermissions.valueOf(mapPermission.get("name").toString());
            }
        }
        if (permission instanceof String) {
            return SecurityPermissions.valueOf(permission.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", SecurityPermissions.class, permission, values()));
    }

    @JsonValue
    Map<String, Object> getSecurityPermissions() {
        return Map.of(
                "name", name(),
                "feature", feature,
                "description", description
        );
    }

    public static Set<SecurityPermissions> readSecurityPermissionsByModule(Module module) {
        if (Objects.isNull(module)) {
            return Arrays.stream(values())
                    .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ALL_ACCESS))
                    .collect(Collectors.toSet());
        }
        return Arrays.stream(values())
                .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ALL_ACCESS))
                .filter(securityPermissions -> securityPermissions.feature.getModule().equals(module))
                .collect(Collectors.toSet());
    }

    public static Set<SecurityPermissions> readSecurityPermissionsByModule(String module, String feature) {
        if (StringUtils.isNotEmpty(feature)) {
            return Arrays.stream(values())
                    .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ALL_ACCESS))
                    .filter(securityPermissions -> securityPermissions.feature.name().equals(feature))
                    .collect(Collectors.toSet());
        }
        if (StringUtils.isNotEmpty(module)) {
            return Arrays.stream(values())
                    .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ALL_ACCESS))
                    .filter(securityPermissions -> securityPermissions.feature.getModule().name().equals(module))
                    .collect(Collectors.toSet());
        }
        return Arrays.stream(values())
                .filter(securityPermissions -> !securityPermissions.equals(SecurityPermissions.ALL_ACCESS))
                .collect(Collectors.toSet());
    }

    public static Set<SecurityPermissions> getAllByModule(String name) {
        return Arrays.stream(values())
                .filter(securityPermissions -> securityPermissions.feature.getModule().name().equals(name))
                .collect(Collectors.toSet());
    }

    public static Set<SecurityPermissions> filterByModuleAndFeature(String module, String feature) {
        if (Objects.nonNull(feature)) {
            return Arrays.stream(values())
                    .filter(securityPermissions -> (securityPermissions.feature.getModule().name().equals(module) && securityPermissions.getFeature().name().equals(feature)))
                    .collect(Collectors.toSet());
        } else {
            return Arrays.stream(values())
                    .filter(securityPermissions -> (securityPermissions.feature.getModule().name().equals(module)))
                    .collect(Collectors.toSet());
        }
    }
}
