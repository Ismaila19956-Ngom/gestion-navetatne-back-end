package com.webgram.dgpsn.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import com.webgram.dgpsn.entities.enums.Module;
import com.webgram.dgpsn.models.Feature;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum SecurityPermissions {

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

    /* fin fonctionnalités sur la sécurité */

    /* début fonctionnalités sur les alertes */

    READ_ALERTE("Consulter liste alerte", Feature.ALERTE),
    ADD_ALERTE("Ajouter alerte", Feature.ALERTE),
    EDIT_ALERTE("Modifier alerte", Feature.ALERTE),
    DELETE_ALERTE("Supprimer alerte", Feature.ALERTE),
    /// direc
    READ_CONTACT_REQUEST("Consulter  formulaire général", Feature.FORMULAIRE_GENERALE),
    ADD_CONTACT_REQUEST("Ajouter un formulaire général", Feature.FORMULAIRE_GENERALE),
    EDIT_CONTACT_REQUEST("Modifier un formulaire général", Feature.FORMULAIRE_GENERALE),
    DELETE_CONTACT_REQUEST("Supprimer un formulaire général", Feature.FORMULAIRE_GENERALE),
    READ_FILE_CONTACT_REQUEST("consulter document",Feature.FORMULAIRE_GENERALE),

    READ_INSPECTION_ICPE("Consulter les inspections des installations classées", Feature.INSPECTION_ICPE),
    ADD_INSPECTION_ICPE("Ajouter une nouvelle inspection d'installation classée", Feature.INSPECTION_ICPE),
    EDIT_INSPECTION_ICPE("Modifier une inspection d'installation classée existante", Feature.INSPECTION_ICPE),
    DELETE_INSPECTION_ICPE("Supprimer une inspection d'installation classée", Feature.INSPECTION_ICPE),
    CONSULTER_INSPECTION_ICPE("Consulter inspection d'installation classée", Feature.INSPECTION_ICPE),

    READ_EVALUATION_ENVIRO("Consulter les évaluations environnementales", Feature.EVALUATION_ENVIRONNEMENTAL),
    ADD_EVALUATION_ENVIRO("Ajouter une nouvelle évaluation environnementale", Feature.EVALUATION_ENVIRONNEMENTAL),
    EDIT_EVALUATION_ENVIRO("Modifier une évaluation environnementale existante", Feature.EVALUATION_ENVIRONNEMENTAL),
    DELETE_EVALUATION_ENVIRO("Supprimer une évaluation environnementale", Feature.EVALUATION_ENVIRONNEMENTAL),
    CONSULTE_EVALUATION_ENVIRO("Consulter une évaluation environnementale", Feature.EVALUATION_ENVIRONNEMENTAL),

    READ_QUALITE_AIR("Consulter les rapports sur la qualité de l'air", Feature.QUALITE_AIR),
    ADD_QUALITE_AIR("Ajouter un nouveau rapport sur la qualité de l'air", Feature.QUALITE_AIR),
    EDIT_QUALITE_AIR("Modifier un rapport sur la qualité de l'air existant", Feature.QUALITE_AIR),
    DELETE_QUALITE_AIR("Supprimer un rapport sur la qualité de l'air", Feature.QUALITE_AIR),
    CONSULTE_QUALITE_AIR("consulter un rapport sur la qualité de l'air", Feature.QUALITE_AIR),
    TELECHARGER_QUALITE_AIR("Telecharger un rapport sur la qualité de l'air", Feature.QUALITE_AIR),
    READ_POLLUTION("Consulter pollution", Feature.GESTION_POLLUTION),
    ADD_POLLUTION("Ajouter pollution", Feature.GESTION_POLLUTION),
    EDIT_POLLUTION("Modifier pollution", Feature.GESTION_POLLUTION),
    DELETE_POLLUTION("Supprimer pollution", Feature.GESTION_POLLUTION),
    EXPORT_POLLUTION("Exporter pollution", Feature.GESTION_POLLUTION),
    VALIDATION_POLLUTION("Valider  pollution", Feature.GESTION_POLLUTION),

    READ_FICHE_RENSEIGNEMENT("Consulter Renseignement", Feature.DIRECTION_REGIONALE),
    ADD_FICHE_RENSEIGNEMENT("Ajouter  Renseignement", Feature.DIRECTION_REGIONALE),
    EDIT_FICHE_RENSEIGNEMENT("Modifier  Renseignement", Feature.DIRECTION_REGIONALE),
    DELETE_FICHE_RENSEIGNEMENT("Supprimer  Renseignement", Feature.DIRECTION_REGIONALE),

    READ_FICHE_VISITE("Consulter fiche visite", Feature.DIRECTION_REGIONALE),
    ADD_FICHE_VISITE("Ajouter visite", Feature.DIRECTION_REGIONALE),
    EDIT_FICHE_VISITE("Modifier visite", Feature.DIRECTION_REGIONALE),
    DELETE_FICHE_VISITE("Supprimer visite", Feature.DIRECTION_REGIONALE),

    READ_URGENCE_ENVIRO("Consulter les plans d'urgence environnementale", Feature.URGENCE_ENVIRO),
    ADD_URGENCE_ENVIRO("Ajouter un nouveau plan d'urgence environnementale", Feature.URGENCE_ENVIRO),
    EDIT_URGENCE_ENVIRO("Modifier un plan d'urgence environnementale existant", Feature.URGENCE_ENVIRO),
    DELETE_URGENCE_ENVIRO("Supprimer un plan d'urgence environnementale", Feature.URGENCE_ENVIRO),
    EXPORT_URGENCE_ENVIRO("Exporter un plan d'urgence environnementale existant", Feature.URGENCE_ENVIRO),
    IMPORT_URGENCE_ENVIRO("Importer un plan d'urgence environnementale", Feature.URGENCE_ENVIRO),


    READ_EVALUATION_STARTUP("Consulter les évaluations des startups", Feature.EVALUATION_STARTUP),
    ADD_EVALUATION_STARTUP("Ajouter une nouvelle évaluation de startup", Feature.EVALUATION_STARTUP),
    EDIT_EVALUATION_STARTUP("Modifier une évaluation de startup existante", Feature.EVALUATION_STARTUP),
    DELETE_EVALUATION_STARTUP("Supprimer une évaluation de startup", Feature.EVALUATION_STARTUP),

    ADD_PASSATION("ajouter plan passation", Feature.BUREAU_AFFAIRES),
    READ_PASSATION("consulter plan passation", Feature.BUREAU_AFFAIRES),
    EDIT_PASSATION("modifier plan passation", Feature.BUREAU_AFFAIRES),
    DELETE_PASSATION("supprimer plan passation", Feature.BUREAU_AFFAIRES),

    ADD_ENGAGEMENT("ajouter engagement", Feature.BUREAU_AFFAIRES),
    READ_ENGAGEMENT("consulter engagement", Feature.BUREAU_AFFAIRES),
    EDIT_ENGAGEMENT("modifier engagement", Feature.BUREAU_AFFAIRES),
    DELETE_ENGAGEMENT("supprimer engagement", Feature.BUREAU_AFFAIRES),

    ADD_BUDGET_PASSATION("ajouter budget passation", Feature.BUREAU_AFFAIRES),
    READ_BUDGET_PASSATION("consulter budget passation", Feature.BUREAU_AFFAIRES),
    EDIT_BUDGET_PASSATION("modifier budget passation", Feature.BUREAU_AFFAIRES),
    DELETE_BUDGET_PASSATION("supprimer budget passation", Feature.BUREAU_AFFAIRES),

    ADD_ORDONNANCEMENT("ajouter ordenancement", Feature.BUREAU_AFFAIRES),
    READ_ORDONNANCEMENT("consulter ordenancement", Feature.BUREAU_AFFAIRES),
    EDIT_ORDONNANCEMENT("modifier ordenancement", Feature.BUREAU_AFFAIRES),
    DELETE_ORDONNANCEMENT("supprimer ordenancement", Feature.BUREAU_AFFAIRES),

    ADD_DECAISSEMENT("ajouter decaissement", Feature.BUREAU_AFFAIRES),
    READ_DECAISSEMENT("consulter decaissement", Feature.BUREAU_AFFAIRES),
    EDIT_DECAISSEMENT("modifier decaissement", Feature.BUREAU_AFFAIRES),
    DELETE_DECAISSEMENT("supprimer decaissement", Feature.BUREAU_AFFAIRES),

    ADD_PARC_ROULANT("ajouter parc roulant", Feature.BUREAU_AFFAIRES),
    READ_PARC_ROULANT("consulter  parc roulant", Feature.BUREAU_AFFAIRES),
    EDIT_PARC_ROULANT("modifier  parc roulant", Feature.BUREAU_AFFAIRES),
    DELETE_PARC_ROULANT("supprimer  parc roulant", Feature.BUREAU_AFFAIRES),

    ADD_INVENTAIRE("ajouter inventaire", Feature.BUREAU_AFFAIRES),
    READ_INVENTAIRE("consulter  inventaire", Feature.BUREAU_AFFAIRES),
    EDIT_INVENTAIRE("modifier inventaire", Feature.BUREAU_AFFAIRES),
    DELETE_INVENTAIRE("supprimer  inventaire", Feature.BUREAU_AFFAIRES),


    ADD_SUIVI_INVENTAIRE("ajouter suivi inventaire", Feature.BUREAU_AFFAIRES),
    READ_SUIVI_INVENTAIRE("consulter suivi  inventaire", Feature.BUREAU_AFFAIRES),
    EDIT_SUIVI_INVENTAIRE("modifier suivi inventaire", Feature.BUREAU_AFFAIRES),
    DELETE_SUIVI_INVENTAIRE("supprimer suivi  inventaire", Feature.BUREAU_AFFAIRES),

    ADD_AGENT_BAF("ajouter agent baf", Feature.BUREAU_AFFAIRES),
    READ_AGENT_BAF("consulter agent baf", Feature.BUREAU_AFFAIRES),
    EDIT_AGENT_BAF("modifier agent baf", Feature.BUREAU_AFFAIRES),
    DELETE_AGENT_BAF("supprimer agent baf", Feature.BUREAU_AFFAIRES),

    READ_ETABLISSEMENTS_CLASSES("Consulter les données des établissements classés", Feature.ETABLISSEMENTS_CLASSES),
    ADD_ETABLISSEMENTS_CLASSES("Ajouter de nouvelles données pour les établissements classés", Feature.ETABLISSEMENTS_CLASSES),
    EDIT_ETABLISSEMENTS_CLASSES("Modifier les données des établissements classés", Feature.ETABLISSEMENTS_CLASSES),
    DELETE_ETABLISSEMENTS_CLASSES("Supprimer les données des établissements classés", Feature.ETABLISSEMENTS_CLASSES),

    READ_STATIONS("Consulter les données des stations", Feature.STATIONS),
    ADD_STATIONS("Ajouter de nouvelles données pour les stations", Feature.STATIONS),
    EDIT_STATIONS("Modifier les données des stations", Feature.STATIONS),
    DELETE_STATIONS("Supprimer les données des stations", Feature.STATIONS),

    READ_STARTUP("Consulter les données des startups", Feature.STARTUP),
    ADD_STARTUP("Ajouter de nouvelles données pour les startups", Feature.STARTUP),
    EDIT_STARTUP("Modifier les données des startups", Feature.STARTUP),
    DELETE_STARTUP("Supprimer les données des startups", Feature.STARTUP),

    READ_DIRECTION("Consulter les données direction", Feature.ORGANIGRAMME),
    ADD_DIRECTION("Ajouter de nouvelles  direction", Feature.ORGANIGRAMME),
    EDIT_DIRECTION("Modifier  direction", Feature.ORGANIGRAMME),
    DELETE_DIRECTION("Supprimer  direction", Feature.ORGANIGRAMME),

//    READ_CRITERE_MARKET("Consulter le(s) criteres d'evaluation d'un marche", Feature.PASSATION_PLAN),
//    ADD_CRITERE_MARKET("Ajouter le(s) criteres d'evaluation d'un marche", Feature.PASSATION_PLAN),
//    EDIT_CRITERE_MARKET("Modifier le(s) criteres d'evaluation d'un marche", Feature.PASSATION_PLAN),
//    DELETE_CRITERE_MARKET("Supprimer le(s) criteres d'evaluation d'un marche", Feature.PASSATION_PLAN),


//    READ_MARKET_FILE("Consulter le(s) dossier d'un marche", Feature.PASSATION_PLAN),
//    ADD_MARKET_FILE("Ajouter le(s) dossier d'un marche", Feature.PASSATION_PLAN),
//    EDIT_MARKET_FILE("Modifier le(s) dossier d'un marche", Feature.PASSATION_PLAN),
//    DELETE_MARKET_FILE("Supprimer le(s) dossier d'un marche", Feature.PASSATION_PLAN),


//    READ_NOTE_FILE("Consulter les notes du dossier d'un marche", Feature.PASSATION_PLAN),
//    ADD_NOTE_FILE("Ajouter le(s) une note pour un dossier d'un marche", Feature.PASSATION_PLAN),
//    EDIT_NOTE_FILE("Modifier la note pour un dossier ", Feature.PASSATION_PLAN),
//    DELETE_NOTE_FILE("Supprimer la note pour un dossier d'un marche", Feature.PASSATION_PLAN),


    //    READ_PRESELECTIONNED_FILE("Consulter les dossiers preselectionnes d'un marche", Feature.PASSATION_PLAN),
//    ADD_PRESELECTIONNED_FILE("Ajouter le(s) les dossiers preselectionnes d'un marche", Feature.PASSATION_PLAN),
//    EDIT_PRESELECTIONNED_FILE("Modifier les dossiers preselectionnes d'un marche ", Feature.PASSATION_PLAN),
//    DELETE_PRESELECTIONNED_FILE("Supprimer le dossier preselectionne d'un marche", Feature.PASSATION_PLAN),
    READ_TEMPLATE("Consulter liste alerte", Feature.TEMPLATE),
    ADD_TEMPLATE("Ajouter alerte", Feature.TEMPLATE),
    EDIT_TEMPLATE("Modifier alerte", Feature.TEMPLATE),
    DELETE_TEMPLATE("Supprimer alerte", Feature.TEMPLATE),


    /* fin fonctionnalités sur les alertes */


    /* début fonctionnalités sur la liste des projets */


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

    /* fin fonctionnalités sur la liste des projets */


    /* début fonctionnalités sur paramétrage des projets */

    READ_UGP("Consulter ugp", Feature.UGP),
    ADD_UGP("Ajouter ugp", Feature.UGP),
    EDIT_UGP("Modifier ugp", Feature.UGP),
    DELETE_UGP("Supprimer ugp", Feature.UGP),

//    READ_COMPONENT("Consulter composante", Feature.COMPONENT),
//    ADD_COMPONENT("Ajouter composante", Feature.COMPONENT),
//    EDIT_COMPONENT("Modifier composante", Feature.COMPONENT),
//    DELETE_COMPONENT("Supprimer composante", Feature.COMPONENT),

//    READ_ACTIVITY_COMPONENT("Consulter activite", Feature.COMPONENT),
//    ADD_ACTIVITY_COMPONENT("Ajouter activite", Feature.COMPONENT),
//    EDIT_ACTIVITY_COMPONENT("Modifier activite", Feature.COMPONENT),
//    DELETE_ACTIVITY_COMPONENT("Supprimer activite", Feature.COMPONENT),
//
//    READ_COMPONENT_REALIZATION("Consulter Component Realisation", Feature.COMPONENT),
//    ADD_COMPONENT_REALIZATION("Ajouter Component Realisation", Feature.COMPONENT),
//    EDIT_COMPONENT_REALIZATION("Modifier Component Realisation", Feature.COMPONENT),
//    DELETE_COMPONENT_REALIZATION("Supprimer Component Realisation", Feature.COMPONENT),

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


//    READ_PROJET_ENTREPRISE("Consulter projet entrprise", Feature.PROJET_ENTREPRISE),
//    ADD_PROJET_ENTREPRISE("Ajouter projet entrprise", Feature.PROJET_ENTREPRISE),
//    EDIT_PROJET_ENTREPRISE("Modifier projet entrprise", Feature.PROJET_ENTREPRISE),
//    DELETE_PROJET_ENTREPRISE("Supprimer projet entrprise", Feature.PROJET_ENTREPRISE),

    READ_MILESTONE("Consulter dates importantes", Feature.MILESTONE),
    ADD_MILESTONE("Ajouter dates importantes", Feature.MILESTONE),
    EDIT_MILESTONE("Modifier dates importantes", Feature.MILESTONE),
    DELETE_MILESTONE("Supprimer dates importantes", Feature.MILESTONE),
    IMPORT_MILESTONE("Supprimer dates importantes", Feature.MILESTONE),
    EXPORT_MILESTONE("Supprimer dates importantes", Feature.MILESTONE),

    /* fin fonctionnalités sur paramétrage des projets */

    /* début fonctionnalités sur avancement détaillé des projets */

    READ_ISSUE_LOG_COMPONENT("Consulter probleme pour composantes", Feature.ISSUE_LOG),
    ADD_ISSUE_LOG_COMPONENT("Ajouter probleme pour composantes", Feature.ISSUE_LOG),
    EDIT_ISSUE_LOG_COMPONENT("Modifier probleme pour composantes", Feature.ISSUE_LOG),
    DELETE_ISSUE_LOG_COMPONENT("Supprimer probleme pour composantes", Feature.ISSUE_LOG),

    READ_DISBURSEMENT_RATE("Consulter les taux de decaissement physique des composantes", Feature.DISBURSEMENT_RATE),
    ADD_DISBURSEMENT_RATE("Ajouter les taux de decaissement physique des composantes", Feature.DISBURSEMENT_RATE),
    EDIT_DISBURSEMENT_RATE("Modifier les taux de decaissement physique des composantes", Feature.DISBURSEMENT_RATE),
    DELETE_DISBURSEMENT_RATE("Supprimer les taux de decaissement physique des composantes", Feature.DISBURSEMENT_RATE),
    READ_PROGRESS_TRACKING_INDICATOR("Consulter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    VALIDER_PROGRESS_TRACKING_INDICATOR("valider valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    ADD_PROGRESS_TRACKING_INDICATOR("Ajouter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    EDIT_PROGRESS_TRACKING_INDICATOR("Modifier valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    DELETE_PROGRESS_TRACKING_INDICATOR("Supprimer valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    IMPORT_PROGRESS_TRACKING_INDICATOR("Importer valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    EXPORT_PROGRESS_TRACKING_INDICATOR("Exporter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),
    READ_FILE_PROGRESS_TRACKING_INDICATOR("Exporter valeur indicateur", Feature.PROGRESS_TRACKING_INDICATOR),

    READ_RISK("Consulter risque", Feature.RISK),
    ADD_RISK("Ajouter risque", Feature.RISK),
    EDIT_RISK("Modifier risque", Feature.RISK),
    DELETE_RISK("Supprimer risque", Feature.RISK),
    IMPORT_RISK("Importer risque", Feature.RISK),
    EXPORT_RISK("Exporter risque", Feature.RISK),
    PROBLEME_RISK ("Probleme risque", Feature.RISK),
    RECOMMENDATION_RISK ("Recommendation risque", Feature.RISK),

    READ_ISSUE_LOG("Consulter problème", Feature.ISSUE_LOG),
    ADD_ISSUE_LOG("Ajouter problème", Feature.ISSUE_LOG),
    EDIT_ISSUE_LOG("Modifier problème", Feature.ISSUE_LOG),
    DELETE_ISSUE_LOG("Supprimer problème", Feature.ISSUE_LOG),
    VALID_ISSUE_LOG("valider problème", Feature.ISSUE_LOG),
    ACTION_ISSUE_LOG("Action problème", Feature.ISSUE_LOG),
    RECOMMENDATION_ISSUE_LOG("Recommendation problème", Feature.ISSUE_LOG),

    READ_COMPLETED_ACTIVITY("Consulter Action realiser des problème", Feature.ISSUE_LOG),
    ADD_COMPLETED_ACTIVITY("Ajouter Action realiser des problème", Feature.ISSUE_LOG),
    EDIT_COMPLETED_ACTIVITY("Modifier Action realiser des problème", Feature.ISSUE_LOG),
    DELETE_COMPLETED_ACTIVITY("Supprimer Action realiser des problème", Feature.ISSUE_LOG),


    /////
    READ_DATES_IMPORTANTES("Consulter les dates importantes", Feature.MILESTONE),
    ADD_DATES_IMPORTANTES("Ajouter les dates importantes", Feature.MILESTONE),
    EDIT_DATES_IMPORTANTES("Modifier les dates importantes", Feature.MILESTONE),
    DELETE_DATES_IMPORTANTES("Supprimer les dates importantes", Feature.MILESTONE),
    EXPORT_DATES_IMPORTANTES("Supprimer les dates importantes", Feature.MILESTONE),
    IMPORT_DATES_IMPORTANTES("Supprimer les dates importantes", Feature.MILESTONE),
    //////////////////

    READ_RECOMMENDATION("Consulter recommandation", Feature.ISSUE_LOG),
    ADD_RECOMMENDATION("Ajouter recommandation", Feature.ISSUE_LOG),
    EDIT_RECOMMENDATION("Modifier recommandation", Feature.ISSUE_LOG),
    DELETE_RECOMMENDATION("Supprimer recommandation", Feature.ISSUE_LOG),
    EXPORT_ISSUE_LOG("Exporter financement", Feature.ISSUE_LOG),
    IMPORT_ISSUE_LOG("Exporter financement", Feature.ISSUE_LOG),

    READ_POINT_OF_VIGILANCE("Consulter point de vigilance", Feature.POINT_OF_VIGILANCE),
    ADD_POINT_OF_VIGILANCE("Ajouter point de vigilance", Feature.POINT_OF_VIGILANCE),
    EDIT_POINT_OF_VIGILANCE("Modifier point de vigilance", Feature.POINT_OF_VIGILANCE),
    DELETE_POINT_OF_VIGILANCE("Supprimer point de vigilance", Feature.POINT_OF_VIGILANCE),

    READ_STATUS("Consulter statut", Feature.STATUS),
    ADD_STATUS("Ajouter statut", Feature.STATUS),
    EDIT_STATUS("Modifier statut", Feature.STATUS),
    DELETE_STATUS("Supprimer statut", Feature.STATUS),



    READ_FLAG("Consulter flag", Feature.FLAG),
    ADD_FLAG("Ajouter flag", Feature.FLAG),
    EDIT_FLAG("Modifier flag", Feature.FLAG),
    DELETE_FLAG("Supprimer flag", Feature.FLAG),

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

    READ_IMAGE("Consulter image", Feature.MEDIATHEQUE),
    ADD_IMAGE("Ajouter image", Feature.MEDIATHEQUE),
    EDIT_IMAGE("Modifier image", Feature.MEDIATHEQUE),
    DELETE_IMAGE("Supprimer vidéo", Feature.MEDIATHEQUE),
    READ_VIDEO("Consulter vidéo", Feature.MEDIATHEQUE),
    ADD_VIDEO("Ajouter vidéo", Feature.MEDIATHEQUE),
    EDIT_VIDEO("Modifier vidéo", Feature.MEDIATHEQUE),
    DELETE_VIDEO("Supprimer vidéo", Feature.MEDIATHEQUE),

    READ_DOCUMENT("Consulter document", Feature.DOCUMENT),
    ADD_DOCUMENT("Ajouter document", Feature.DOCUMENT),
    EDIT_DOCUMENT("Modifier document", Feature.DOCUMENT),
    DELETE_DOCUMENT("Supprimer document", Feature.DOCUMENT),
    READ_FILE_DOCUMENT("Lire document", Feature.DOCUMENT),

    /* fin fonctionnalités sur avancement détaillé des projets */

    /* début fonctionnalités sur suivi des activités */

    ADD_BUDGET("Ajouter budget", Feature.BUDGET),
    EDIT_BUDGET("Modifier budget", Feature.BUDGET),
    DELETE_BUDGET("Supprimer budget", Feature.BUDGET),
    READ_BUDGET("Lire budget", Feature.BUDGET),

    ADD_FUNDING_SOURCE("Ajouter Souce financement", Feature.BUDGET),
    EDIT_FUNDING_SOURCE("Modifier Souce financement", Feature.BUDGET),
    DELETE_FUNDING_SOURCE("Supprimer Souce financement", Feature.BUDGET),
    READ_FUNDING_SOURCE("Lire Souce financement", Feature.BUDGET),

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

    ADD_EXPENSE_ACTIVITY("Ajouter expense", Feature.EXPENSE_ACTIVITY),
    EDIT_EXPENSE_ACTIVITY("Modifier expense", Feature.EXPENSE_ACTIVITY),
    DELETE_EXPENSE_ACTIVITY("Supprimer expense", Feature.EXPENSE_ACTIVITY),
    READ_EXPENSE_ACTIVITY("Lire expense", Feature.EXPENSE_ACTIVITY),

    READ_PREPARATION("Consulter préparation", Feature.PREPARATION),
    ADD_PREPARATION("Ajouter préparation", Feature.PREPARATION),
    EDIT_PREPARATION("Modifier préparation", Feature.PREPARATION),
    DELETE_PREPARATION("Supprimer préparation", Feature.PREPARATION),
    EXPORT_PREPARATION("Exporter préparation", Feature.PREPARATION),
    IMPORT_PREPARATION("Importer préparation", Feature.PREPARATION),

    READ_CONDITIONALITY("Consulter conditionnalité", Feature.CONDITIONALITY),
    ADD_CONDITIONALITY("Ajouter conditionnalité", Feature.CONDITIONALITY),
    EDIT_CONDITIONALITY("Modifier conditionnalité", Feature.CONDITIONALITY),
    DELETE_CONDITIONALITY("Supprimer conditionnalité", Feature.CONDITIONALITY),
    EXPORT_CONDITIONALITY("Supprimer conditionnalité", Feature.CONDITIONALITY),
    IMPORT_CONDITIONALITY("Supprimer conditionnalité", Feature.CONDITIONALITY),

    READ_ASSIGNMENT("Consulter mission", Feature.ASSIGNMENT),
    ADD_ASSIGNMENT("Ajouter mission", Feature.ASSIGNMENT),
    EDIT_ASSIGNMENT("Modifier mission", Feature.ASSIGNMENT),
    DELETE_ASSIGNMENT("Supprimer mission", Feature.ASSIGNMENT),
    IMPORT_ASSIGNMENT("importer mission", Feature.ASSIGNMENT),
    EXPORT_ASSIGNMENT("exporter mission", Feature.ASSIGNMENT),
    READ_ASSIGNMENT_ISSUE_LOG("Consulter problème des missions", Feature.ASSIGNMENT),
    ADD_ASSIGNMENT_ISSUE_LOG("Ajouter problème des missions", Feature.ASSIGNMENT),
    EDIT_ASSIGNMENT_ISSUE_LOG("Modifier problème des missions", Feature.ASSIGNMENT),
    DELETE_ASSIGNMENT_ISSUE_LOG("Supprimer problème des missions", Feature.ASSIGNMENT),
    READ_ASSIGNMENT_RECOMMENDATION("Consulter recommandation", Feature.ASSIGNMENT),

    READ_REVIEW("Consulter revue", Feature.REVIEW),
    ADD_REVIEW("Ajouter revue", Feature.REVIEW),
    EDIT_REVIEW("Modifier revue", Feature.REVIEW),
    DELETE_REVIEW("Supprimer revue", Feature.REVIEW),
    EXPORT_REVIEW("Export revue", Feature.REVIEW),
    IMPORT_REVIEW("Import revue", Feature.REVIEW),


    READ_REVIEW_ISSUE_LOG("Consulter problème", Feature.REVIEW),
    ADD_REVIEW_ISSUE_LOG("Ajouter problème", Feature.REVIEW),
    EDIT_REVIEW_ISSUE_LOG("Modifier problème", Feature.REVIEW),
    DELETE_REVIEW_ISSUE_LOG("Supprimer problème dans revue", Feature.REVIEW),
    READ_REVIEW_RISK("Consulter risque dans revue", Feature.REVIEW),
    ADD_REVIEW_RISK("Ajouter risque dans revue", Feature.REVIEW),
    EDIT_REVIEW_RISK("Modifier risque dans revue", Feature.REVIEW),
    DELETE_REVIEW_RISK("Supprimer risque dans revue", Feature.REVIEW),

    READ_QUERY("Consulter requête", Feature.QUERY),
    ADD_QUERY("Ajouter requête", Feature.QUERY),
    EDIT_QUERY("Modifier requête", Feature.QUERY),
    DELETE_QUERY("Supprimer requête", Feature.QUERY),
    EXPORT_QUERY("Supprimer requête", Feature.QUERY),
    IMPORT_QUERY("Supprimer requête", Feature.QUERY),
    READ_QUERY_PROGESS("Consulter suivi requête", Feature.QUERY),
    ADD_QUERY_PROGESS("Ajouter statut requête", Feature.QUERY),
    EDIT_QUERY_PROGESS("Modifier statut requête", Feature.QUERY),
    DELETE_QUERY_PROGESS("Supprimer statut requête", Feature.QUERY),

    READ_MEETING("Consulter réunion", Feature.MEETING),
    ADD_MEETING("Ajouter réunion", Feature.MEETING),
    EDIT_MEETING("Modifier réunion", Feature.MEETING),
    DELETE_MEETING("Supprimer réunion", Feature.MEETING),

    /* fin fonctionnalités sur suivi des activités */

    /* début fonctionnalités sur le référentiel */

    READ_AGENT("Consulter agent", Feature.AGENT),
    ADD_AGENT("Ajouter agent", Feature.AGENT),
    EDIT_AGENT("Modifier agent", Feature.AGENT),
    DELETE_AGENT("Supprimer agent", Feature.AGENT),
    IMPORT_AGENT("Import agent", Feature.AGENT),
    EXPORT_AGENT("Export agent", Feature.AGENT),
    READ_FILE_AGENT("Read file agent", Feature.AGENT),

    READ_MEETINGTYPE("Consulter Type reunion", Feature.MEETINGTYPE),
    ADD_MEETINGTYPE("Ajouter Type reunion", Feature.MEETINGTYPE),
    EDIT_MEETINGTYPE("Modifier Type reunion", Feature.MEETINGTYPE),
    DELETE_MEETINGTYPE("Supprimer Type reunion", Feature.MEETINGTYPE),

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

    READ_PARTICIPANT_MISSION("Consulter periode", Feature.PARTICIPANT_MISSION),
    ADD_PARTICIPANT_MISSION("Ajouter periode ", Feature.PARTICIPANT_MISSION),
    EDIT_PARTICIPANT_MISSION("Modifier periode", Feature.PARTICIPANT_MISSION),
    DELETE_PARTICIPANT_MISSION("Supprimer periode", Feature.PARTICIPANT_MISSION),

    READ_STATE_PROGRESS("Consulter etat d'avancement", Feature.STATE_PROGRESS),
    ADD_STATE_PROGRESS("Ajouter etat d'avancement", Feature.STATE_PROGRESS),
    EDIT_STATE_PROGRESS("Modifier etat d'avancement", Feature.STATE_PROGRESS),
    DELETE_STATE_PROGRESS("Supprimer etat d'avancement", Feature.STATE_PROGRESS),

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

    READ_DELAY_IMPACT("Consulter impact-delais", Feature.DELAY_IMPACT),
    ADD_DELAY_IMPACT("Ajouter impact-delais", Feature.DELAY_IMPACT),
    EDIT_DELAY_IMPACT("Modifier impact-delais", Feature.DELAY_IMPACT),
    DELETE_DELAY_IMPACT("Supprimer impact-delais", Feature.DELAY_IMPACT),

    READ_FINANCIAL_IMPACT("Consulter impact-financier", Feature.FINANCIAL_IMPACT),
    ADD_FINANCIAL_IMPACT("Ajouter impact-financier", Feature.FINANCIAL_IMPACT),
    EDIT_FINANCIAL_IMPACT("Modifier impact-financier", Feature.FINANCIAL_IMPACT),
    DELETE_FINANCIAL_IMPACT("Supprimer impact-financier", Feature.FINANCIAL_IMPACT),

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
    EDIT_REF_TYPE_EXPENSE("Modifier Type expense", Feature. EXPENSE_TYPE),
    DELETE_REF_TYPE_EXPENSE("Supprimer Type expense", Feature.EXPENSE_TYPE),

    READ_REF_TYPE_REQUETE("Supprimer Type requete", Feature.REQUETE_TYPE),
    ADD_REF_TYPE_REQUETE("Ajouter Type requete", Feature.REQUETE_TYPE),
    EDIT_REF_TYPE_REQUETE("Modifier Type requete", Feature. REQUETE_TYPE),
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

    READ_RESOLVE_CHANNEL("Consulter canal de resolution", Feature.RESOLVE_CHANNEL),
    ADD_RESOLVE_CHANNEL("Ajouter canal de resolution", Feature.RESOLVE_CHANNEL),
    EDIT_RESOLVE_CHANNEL("Modifier canal de resolution", Feature.RESOLVE_CHANNEL),
    DELETE_RESOLVE_CHANNEL("Supprimer canal de resolution", Feature.RESOLVE_CHANNEL),


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

    READ_PHASE("Consulter phase", Feature.PHASE),
    ADD_PHASE("Ajouter phase", Feature.PHASE),
    EDIT_PHASE("Modifier phase", Feature.PHASE),
    DELETE_PHASE("Supprimer phase", Feature.PHASE),
    EXPORT_PHASE("Exporter phase", Feature.PHASE),

    READ_MEDIATHEQUE("Consulter mediatheque", Feature.MEDIATHEQUE),
    ADD_MEDIATHEQUE("Ajouter mediatheque", Feature.MEDIATHEQUE),
    EDIT_MEDIATHEQUE("Modifier mediatheque", Feature.MEDIATHEQUE),
    DELETE_MEDIATHEQUE("Supprimer mediatheque", Feature.MEDIATHEQUE),

    READ_FUNCTION("Consulter function", Feature.FUNCTION),
    ADD_FUNCTION("Ajouter function", Feature.FUNCTION),
    EDIT_FUNCTION("Modifier function", Feature.FUNCTION),
    DELETE_FUNCTION("Supprimer function", Feature.FUNCTION),

    READ_REF_FLAG("Consulter flag", Feature.REF_FLAG),
    ADD_REF_FLAG("Ajouter flag", Feature.REF_FLAG),
    EDIT_REF_FLAG("Modifier flag", Feature.REF_FLAG),
    DELETE_REF_FLAG("Supprimer flag", Feature.REF_FLAG),

    READ_FUNDING_TYPE("Consulter type de financement", Feature.FUNDING_TYPE),
    ADD_FUNDING_TYPE("Ajouter type de financement", Feature.FUNDING_TYPE),
    EDIT_FUNDING_TYPE("Modifier type de financement", Feature.FUNDING_TYPE),
    DELETE_FUNDING_TYPE("Supprimer type de financement", Feature.FUNDING_TYPE),

    READ_CONDITIONALITY_TYPE("Consulter type de conditionnalite", Feature.CONDITIONALITY_TYPE),
    ADD_CONDITIONALITY_TYPE("Ajouter type de conditionnalite", Feature.CONDITIONALITY_TYPE),
    EDIT_CONDITIONALITY_TYPE("Modifier type de conditionnalite", Feature.CONDITIONALITY_TYPE),
    DELETE_CONDITIONALITY_TYPE("Supprimer type de conditionnalite", Feature.CONDITIONALITY_TYPE),

    READ_NATURE("Consulter nature", Feature.NATURE),
    ADD_NATURE("Ajouter nature", Feature.NATURE),
    EDIT_NATURE("Modifier nature", Feature.NATURE),
    DELETE_NATURE("Supprimer nature", Feature.NATURE),

    READ_NATURE_RECETTE("Consulter nature", Feature.NATURE_RECETTE),
    ADD_NATURE_RECETTE("Ajouter nature", Feature.NATURE_RECETTE),
    EDIT_NATURE_RECETTE("Modifier nature", Feature.NATURE_RECETTE),
    DELETE_NATURE_RECETTE("Supprimer nature", Feature.NATURE_RECETTE),

    READ_SPECIFIC_NATURE("Consulter nature specifique", Feature.SPECIFIC_NATURE),
    ADD_SPECIFIC_NATURE("Ajouter nature specifique", Feature.SPECIFIC_NATURE),
    EDIT_SPECIFIC_NATURE("Modifier nature specifique", Feature.SPECIFIC_NATURE),
    DELETE_SPECIFIC_NATURE("Supprimer nature specifique", Feature.SPECIFIC_NATURE),

    READ_DOCUMENT_TYPE("Consulter document type", Feature.DOCUMENT_TYPE),
    ADD_DOCUMENT_TYPE("Ajouter document type", Feature.DOCUMENT_TYPE),
    EDIT_DOCUMENT_TYPE("Modifier document type", Feature.DOCUMENT_TYPE),
    DELETE_DOCUMENT_TYPE("Supprimer document type", Feature.DOCUMENT_TYPE),

    READ_CRITICITY("Consulter criticite", Feature.CRITICITY),
    ADD_CRITICITY("Ajouter criticite", Feature.CRITICITY),
    EDIT_CRITICITY("Modifier criticite", Feature.CRITICITY),
    DELETE_CRITICITY("Supprimer criticite", Feature.CRITICITY),


    /* début fonctionnalités sur le Dashboard */
    READ_TDB_RESUME("Consulter resumé tableau de bord", Feature.TDB_RESUME),
    READ_TDB_PROJETS("Consulter tableau de bord des projets", Feature.TDB_PROJETS),
    READ_TDB_FINANCMENTS("Consulter tableau de bord des financements", Feature.TDB_Financements),
    READ_TDB_PROBLEMES("Consulter tableau de bord des problèmes", Feature.TDB_PROBLEMES),
    READ_TDB_ENTREPRISE("Consulter tableau de bord des entreprises", Feature.TDB_Enttrepsie),
    READ_TDB_QUALITE_AIR("Consulter tableau de bord des qualité de l'air", Feature.TDB_QUALITE_AIR),
    READ_TDB_EVALUATION("Consulter tableau de bord de l'évaluation environnementale", Feature.TDB_EVALUATION),
    READ_TDB_POLLUTION("Consulter tableau de bord de la pollution", Feature.TDB_POLLUTION),
    READ_TDB_ICPE("Consulter tableau de bord ICPE", Feature.TDB_ICPE),

    /* fin fonctionnalités sur le Dashboard */



    /* fin fonctionnalités sur le référentiel */

    ADD_CASH("Ajouter monnaie", Feature.CASH),
    EDIT_CASH("Modifier monnaie", Feature.CASH),
    DELETE_CASH("Supprimer monnaie", Feature.CASH),
    READ_CASH("Consulter monnaie", Feature.CASH),

    ADD_ECHANGE("Ajouter taux echange", Feature.ECHANGE),
    EDIT_ECHANGE("Modifier taux echange", Feature.ECHANGE),
    DELETE_ECHANGE("Supprimer taux echange", Feature.ECHANGE),
    READ_ECHANGE("Consulter taux echange", Feature.ECHANGE),


    ALL_ACCESS("Toutes les permissions", Feature.ALL_ACCESS),

    READ_WORKFLOW_HISTORIQUE("Consulter historique de validation", Feature.WORKFLOW),
    ADD_WORKFLOW_HISTORIQUE("Ajouter historique de validation", Feature.WORKFLOW),
    EDIT_WORKFLOW_HISTORIQUE("Modifier historique de validation", Feature.WORKFLOW),
    DELETE_WORKFLOW_HISTORIQUE("Supprimer historique de validation", Feature.WORKFLOW),


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


    /* début instruction */
    READ_INSTRUCTION("Consulter instruction", Feature.EVALUATION_ENVIRONNEMENTAL),
    ADD_INSTRUCTION("Ajouter instruction", Feature.EVALUATION_ENVIRONNEMENTAL),
    EDIT_INSTRUCTION("Modifier instruction", Feature.EVALUATION_ENVIRONNEMENTAL),
    DELETE_INSTRUCTION("Supprimer instruction", Feature.EVALUATION_ENVIRONNEMENTAL),
    /* fin instruction */

    /* début tdr */
    READ_TDR("Consulter tdr", Feature.EVALUATION_ENVIRONNEMENTAL),
    ADD_TDR("Ajouter tdr", Feature.EVALUATION_ENVIRONNEMENTAL),
    EDIT_TDR("Modifier tdr", Feature.EVALUATION_ENVIRONNEMENTAL),
    DELETE_TDR("Supprimer tdr", Feature.EVALUATION_ENVIRONNEMENTAL),
    /* fin tdr */

    /* suivi surveillance */
    READ_SUIVI_SURVEILLANCE("Consulter suivi surveillance", Feature.EVALUATION_ENVIRONNEMENTAL),
    ADD_SUIVI_SURVEILLANCE("Ajouter suivi surveillance", Feature.EVALUATION_ENVIRONNEMENTAL),
    EDIT_SUIVI_SURVEILLANCE("Modifier suivi surveillance", Feature.EVALUATION_ENVIRONNEMENTAL),
    DELETE_SUIVI_SURVEILLANCE("Supprimer suivi surveillance", Feature.EVALUATION_ENVIRONNEMENTAL),
    /* fin suivi surveillance */

    /* début agrement */
    READ_AGREMENT("Consulter agrement", Feature.EVALUATION_ENVIRONNEMENTAL),
    ADD_AGREMENT("Ajouter agrement", Feature.EVALUATION_ENVIRONNEMENTAL),
    EDIT_AGREMENT("Modifier agrement", Feature.EVALUATION_ENVIRONNEMENTAL),
    DELETE_AGREMENT("Supprimer agrement", Feature.EVALUATION_ENVIRONNEMENTAL),
    /* fin agrement */

    /* début label */
    READ_LABEL("Consulter label", Feature.LABEL),
    ADD_LABEL("Ajouter label", Feature.LABEL),
    EDIT_LABEL("Modifier label", Feature.LABEL),
    DELETE_LABEL("Supprimer label", Feature.LABEL),

    /* fin label */

    READ_EVALUATION("Consulter Evaluation", Feature.EVALUATION),
    ADD_EVALUATION("Ajouter Evaluation", Feature.EVALUATION),
    EDIT_EVALUATION("Modifier Evaluation", Feature.EVALUATION),
    DELETE_EVALUATION("Supprimer Evaluation", Feature.EVALUATION),

    READ_COMPANY_EVALUATION("Consulter Evaluation", Feature.EVALUATION),
    ADD_COMPANY_EVALUATION("Ajouter Evaluation", Feature.EVALUATION),
    EDIT_COMPANY_EVALUATION("Modifier Evaluation", Feature.EVALUATION),
    DELETE_COMPANY_EVALUATION("Supprimer Evaluation", Feature.EVALUATION),


    /* début label */
    READ_IMPACTSANDOBJECTIVE("Consulter impats attendus et objectifs spécifiques", Feature.IMPACTSANDOBJECTIVE),
    ADD_IMPACTSANDOBJECTIVE("Ajouter impats attendus et objectifs spécifiques", Feature.IMPACTSANDOBJECTIVE),
    EDIT_IMPACTSANDOBJECTIVE("Modifier impats attendus et objectifs spécifiques", Feature.IMPACTSANDOBJECTIVE),
    DELETE_IMPACTSANDOBJECTIVE("Supprimer impats attendus et objectifs spécifiques", Feature.IMPACTSANDOBJECTIVE),
    /* fin label */

    /* début Impact environnemental */
//    READ_ENV_IMPACTS("Consulter impacts environnementale", Feature.ENVIRONNEMENTAL),
//    ADD_ENV_IMPACTS("Ajouter impact environnementale", Feature.ENVIRONNEMENTAL),
//    EDIT_ENV_IMPACTS("Modifier impact environnementale", Feature.ENVIRONNEMENTAL),
//    DELETE_ENV_IMPACTS("Supprimer impact environnementale", Feature.ENVIRONNEMENTAL),
//    /* fin Impact environnemental */


    /* début Indicateur environnemental */
//    READ_ENV_INDICATEURS("Consulter indicateurs environnementale", Feature.ENVIRONNEMENTAL),
//    ADD_ENV_INDICATEURS("Ajouter indicateur environnementale", Feature.ENVIRONNEMENTAL),
//    EDIT_ENV_INDICATEURS("Modifier indicateur environnementale", Feature.ENVIRONNEMENTAL),
//    DELETE_ENV_INDICATEURS("Supprimer indicateur environnementale", Feature.ENVIRONNEMENTAL),
    /* fin Indicateur environnemental */


    /* début Conformites environnemental */
//    READ_ENV_CONFORMITES("Consulter conformites reglementaire environnementale", Feature.ENVIRONNEMENTAL),
//    ADD_ENV_CONFORMITES("Ajouter conformites reglementaire environnementale", Feature.ENVIRONNEMENTAL),
//    EDIT_ENV_CONFORMITES("Modifier conformites reglementaire environnementale", Feature.ENVIRONNEMENTAL),
//    DELETE_ENV_CONFORMITES("Supprimer conformites reglementaire environnementale", Feature.ENVIRONNEMENTAL),
    /* fin Conformites environnemental */

    /* début Impact social */
//    READ_SOCIAL_IMPACTS("Consulter impacts social", Feature.SOCIAL),
//    ADD_SOCIAL_IMPACTS("Ajouter impact social", Feature.SOCIAL),
//    EDIT_SOCIAL_IMPACTS("Modifier impact social", Feature.SOCIAL),
//    DELETE_SOCIAL_IMPACTS("Supprimer impact social", Feature.SOCIAL),
//    /* fin Impact social */


    /* début Indicateur social */
//    READ_SOCIAL_INDICATEURS("Consulter indicateurs social", Feature.SOCIAL),
//    ADD_SOCIAL_INDICATEURS("Ajouter indicateur social", Feature.SOCIAL),
//    EDIT_SOCIAL_INDICATEURS("Modifier indicateur social", Feature.SOCIAL),
//    DELETE_SOCIAL_INDICATEURS("Supprimer indicateur social", Feature.SOCIAL),
//    /* fin Indicateur social */


    /* début Conformites social */
//    READ_SOCIAL_CONFORMITES("Consulter conformites reglementaire social", Feature.SOCIAL),
//    ADD_SOCIAL_CONFORMITES("Ajouter conformites reglementaire social", Feature.SOCIAL),
//    EDIT_SOCIAL_CONFORMITES("Modifier conformites reglementaire social", Feature.SOCIAL),
//    DELETE_SOCIAL_CONFORMITES("Supprimer conformites reglementaire social", Feature.SOCIAL),
//    /* fin Conformites social */

    /* début Impact gouvernance */
//    READ_GOUV_IMPACTS("Consulter impacts gouvernance", Feature.GOUVERNANCE),
//    ADD_GOUV_IMPACTS("Ajouter impact gouvernance", Feature.GOUVERNANCE),
//    EDIT_GOUV_IMPACTS("Modifier impact gouvernance", Feature.GOUVERNANCE),
//    DELETE_GOUV_IMPACTS("Supprimer impact gouvernance", Feature.GOUVERNANCE),
//    /* fin Impact gouvernance */


    /* début Indicateur gouvernance */
//    READ_GOUV_INDICATEURS("Consulter indicateurs gouvernance", Feature.GOUVERNANCE),
//    ADD_GOUV_INDICATEURS("Ajouter indicateur gouvernance", Feature.GOUVERNANCE),
//    EDIT_GOUV_INDICATEURS("Modifier indicateur gouvernance", Feature.GOUVERNANCE),
//    DELETE_GOUV_INDICATEURS("Supprimer indicateur gouvernance", Feature.GOUVERNANCE),
//    /* fin Indicateur gouvernance */


    /* début Conformites gouvernance */
//    READ_GOUV_CONFORMITES("Consulter conformites reglementaire gouvernance", Feature.GOUVERNANCE),
//    ADD_GOUV_CONFORMITES("Ajouter conformites reglementaire gouvernance", Feature.GOUVERNANCE),
//    EDIT_GOUV_CONFORMITES("Modifier conformites reglementaire gouvernance", Feature.GOUVERNANCE),
//    DELETE_GOUV_CONFORMITES("Supprimer conformites reglementaire gouvernance", Feature.GOUVERNANCE),
//    /* fin Conformites gouvernance */

    READ_COMPLETIONRATE("Consulter taux avancement", Feature.COMPLETION_RATE),
    ADD_COMPLETIONRATE("Ajouter taux avancement", Feature.COMPLETION_RATE),
    EDIT_COMPLETIONRATE("Modifier taux avancement", Feature.COMPLETION_RATE),
    DELETE_COMPLETIONRATE("Supprimer taux avancement", Feature.COMPLETION_RATE),

    /* début Cessions acquisition */
//    READ_CESSIONACQUISITIONS("Consulter Cessions acquisition", Feature.ENTREPRISE_LIST),
//    ADD_CESSIONACQUISITIONS("Ajouter Cessions acquisition", Feature.ENTREPRISE_LIST),
//    EDIT_CESSIONACQUISITIONS("Modifier Cessions acquisition", Feature.ENTREPRISE_LIST),
//    DELETE_CESSIONACQUISITIONS("Supprimer Cessions acquisition", Feature.ENTREPRISE_LIST),
//    EXPORT_CESSIONACQUISITIONS("Exporter Cessions acquisition", Feature.ENTREPRISE_LIST),
//    /* fin Cessions acquisition */
//
//    /* début Conseil administratif */
//    READ_CA("Consulter Conseil administratif", Feature.ENTREPRISE_LIST),
//    ADD_CA("Ajouter Conseil administratif", Feature.ENTREPRISE_LIST),
//    EDIT_CA("Modifier Conseil administratif", Feature.ENTREPRISE_LIST),
//    DELETE_CA("Supprimer Conseil administratif", Feature.ENTREPRISE_LIST),
//    EXPORT_CA("Exporter Conseil administratif", Feature.ENTREPRISE_LIST),
//    /* fin Conseil administratif*/
//
//    /* début Assemble generale */
//    READ_AG("Consulter Assemble generale", Feature.ENTREPRISE_LIST),
//    ADD_AG("Ajouter Assemble generale", Feature.ENTREPRISE_LIST),
//    EDIT_AG("Modifier Assemble generale", Feature.ENTREPRISE_LIST),
//    DELETE_AG("Supprimer Assemble generale", Feature.ENTREPRISE_LIST),
//    EXPORT_AG("Exporter Assemble generale", Feature.ENTREPRISE_LIST),
    /* fin Assemble generale*/

    /* début Budget */
//    READ_LIST_ENTREPRISE("Consulter entreprise", Feature.ENTREPRISE_LIST),
//    READ_BUDGET_ENTREPRISE("Consulter Budget", Feature.ENTREPRISE_LIST),
//    ADD_BUDGET_ENTREPRISE("Ajouter Budget", Feature.ENTREPRISE_LIST),
//    EDIT_BUDGET_ENTREPRISE("Modifier Budget", Feature.ENTREPRISE_LIST),
//    DELETE_BUDGET_ENTREPRISE("Supprimer Budget", Feature.ENTREPRISE_LIST),
//    EXPORT_BUDGET_ENTREPRISE("Exporter Budget", Feature.ENTREPRISE_LIST),
//    /* fin Budget*/
//
//    /* début Recette */
//    READ_RECETTE("Consulter Recette", Feature.ENTREPRISE_LIST),
//    ADD_RECETTE("Ajouter Recette", Feature.ENTREPRISE_LIST),
//    EDIT_RECETTE("Modifier Recette", Feature.ENTREPRISE_LIST),
//    DELETE_RECETTE("Supprimer Recette", Feature.ENTREPRISE_LIST),
//    EXPORT_RECETTE("Exporter Recette", Feature.ENTREPRISE_LIST),
//    /* fin Recette*/
//
//    /* début Depense */
//    READ_DEPENSE("Consulter Depense", Feature.ENTREPRISE_LIST),
//    ADD_DEPENSE("Ajouter Depense", Feature.ENTREPRISE_LIST),
//    EDIT_DEPENSE("Modifier Depense", Feature.ENTREPRISE_LIST),
//    DELETE_DEPENSE("Supprimer Depense", Feature.ENTREPRISE_LIST),
//    EXPORT_DEPENSE("Exporter Depense", Feature.ENTREPRISE_LIST),



//    READ_DECISION_AG("Decision Decision", Feature.ENTREPRISE_LIST),
//    ADD_DECISION_AG("Ajouter Decision", Feature.ENTREPRISE_LIST),
//    EDIT_DECISION_AG("Modifier Decision", Feature.ENTREPRISE_LIST),
//    DELETE_DECISION_AG("Supprimer Decision", Feature.ENTREPRISE_LIST),
//    EXPORT_DECISION_AG("Exporter Decision", Feature.ENTREPRISE_LIST),
//    /* fin Depense*/
//
//    READ_PARTICIPATION_AG("paricipation paricipation", Feature.ENTREPRISE_LIST),
//    ADD_PARTICIPATION_AG("Ajouter paricipation", Feature.ENTREPRISE_LIST),
//    EDIT_PARTICIPATION_AG("Modifier paricipation", Feature.ENTREPRISE_LIST),
//    DELETE_PARTICIPATION_AG("Supprimer paricipation", Feature.ENTREPRISE_LIST),
//    EXPORT_PARTICIPATION_AG("Exporter paricipation", Feature.ENTREPRISE_LIST),

    READ_PROGRAMME("Consulter programme", Feature.LIST_PROGRAMME),
    ADD_PROGRAMME("Ajouter programme", Feature.LIST_PROGRAMME),
    EDIT_PROGRAMME("Modifier programme", Feature.LIST_PROGRAMME),
    DELETE_PROGRAMME("Supprimer programme", Feature.LIST_PROGRAMME),
    CONFIG_PROGRAMME("Configurer programme", Feature.LIST_PROGRAMME),

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
