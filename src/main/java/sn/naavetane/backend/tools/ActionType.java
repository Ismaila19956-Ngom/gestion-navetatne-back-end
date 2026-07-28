package sn.naavetane.backend.tools;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass // tous les méthodes et les attributs sont static
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionType {
    //parametrage
    public static final String CREATE_CLASSE = " Ajout de parametre: Parametrage/ parametre  nouvelle  classe";
    public static final String UPDATE_CLASSE = " Modification  de parametre:Parametrage/ parametre  list classe action";
    public static final String DELETE_CLASSE = " Suppression de  parametre: Parametrage/ parametre  list classe action";
    public  static final String READ_CLASSE = "Consultation parametre:Parametrage/ parametre  list classe action";
    public  static final String ADD_BUDGET = "Ajouter budget Financement:Financement/ Budget  list Budget";
    public  static final String UPDATE_BUDGET = "Modifier budget Financement:Financement/ Budget  list Budget";

    // fournisseur
    public static final String CREATE_FOURNISSEUR = " Ajout de parametre: Parametrage/ parametre  nouvelle  classe";
    public static final String UPDATE_FOURNISSEUR = " Modification  de parametre:Parametrage/ parametre  list classe action";
    public static final String DELETE_FOURNISSEUR = " Suppression de  parametre: Parametrage/ parametre  list classe action";
    public  static final String READ_FOURNISSEUR = " Consultation parametre:Parametrage/ parametre  list classe action";

     public static final String UPDATE_PROJECT = "Modification de projet : GESTION PROJET / Liste projets / Bouton Modifier";
     public static final String CREATE_PROJECT = "Ajout de projet : GESTION PROJET / Liste projets / Bouton Nouveau projet";
     public static final String DELETE_PROJECT = "Suppression de projet : GESTION PROJET / Liste projets / Bouton Supprimer";
     public static final String READ_PROJECT = "Consultation de projet : GESTION PROJET / Liste projets";
     public static final String IMPORT_PROJECT = "Importation de projet : GESTION PROJET / Liste projets / Bouton Importer";
     public static final String EXPORT_PROJECT_TO_EXCEL = "Exportation de projet en excel : GESTION PROJET / Liste projets / Exporter / Excel";
     public static final String EXPORT_PROJECT_TO_PDF = "Exportation de projet en pdf : GESTION PROJET / Liste projets / Exporter / PDF";

     public static final String READ_UGP = "Consultation UGP : GESTION PROJET / Liste projets / Paramétrage / Onglet UGP";
     public static final String CREATE_UGP = "Ajout UGP : GESTION PROJET / Liste projets / Paramétrage / Onglet UGP / Nouvelle UGP";
     public static final String UPDATE_UGP = "Modification UGP : GESTION PROJET / Liste projets / Paramétrage / Onglet UGP / Modifier";
     public static final String DELETE_UGP = "Suppression UGP : GESTION PROJET / Liste projets / Paramétrage / Onglet UGP / Supprimer";

     public static final String READ_COMPOSANTES = "Consultation des composantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes";
     public static final String ADD_COMPOSANTES = "Ajout de composante : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes/ Ajouter composante";
     public static final String UPDATE_COMPOSANTES = "Modification de composantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes/ Modifier";
     public static final String DELETE_COMPOSANTES = "Suppression de composantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes / Supprimer";
     public static final String READ_ACTIVITES = "Consultation des activités : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes / activités";
     public static final String ADD_ACTIVITES = "Ajout d\' activités : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes / activités / Ajouter";
     public static final String UPDATE_ACTIVITES = "Modification des activités : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes / activités / Modifier";
     public static final String DELETE_ACTIVITES = "Suppression des activités : GESTION PROJET / Liste projets / Paramétrage / Onglet Composantes / activités / Supprimer";

     public static final String READ_ZONE_INVENTION = "Consultation des zones d'intervention : GESTION PROJET / Liste projets / Paramétrage / Onglet Zones d'intervention";
     public static final String ADD_ZONE_INVENTION = "Ajout des zones d'intervention : GESTION PROJET / Liste projets / Paramétrage / Onglet Zones d'intervention / Nouvelle zone";
     public static final String DELETE_ZONE_INVENTION = "Suppression de zone d'intervention : GESTION PROJET / Liste projets / Paramétrage / Onglet Zones d'intervention / Supprimer";
     public static final String IMPORT_ZONE_INVENTION = "Importation des zones d'intervention : GESTION PROJET / Liste projets / Paramétrage / Onglet Zones d'intervention / Importer";
     public static final String EXPORT_ZONE_INVENTION_TO_EXCEL = "Exportation en excel des zones d'intervention : GESTION PROJET / Liste projets / Paramétrage / Onglet Zones d'intervention / Exporter / Excel";
     public static final String EXPORT_ZONE_INVENTION_TO_PDF = "Exportation en pdf des zones d'intervention : GESTION PROJET / Liste projets / Paramétrage / Onglet Zones d'intervention / Exporter / PDF";

     public static final String READ_STRUCTURE_TUTELLE_EXECUTION = "Consultation des structuures de tutelle/exécution : GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution";
     public static final String ADD_STRUCTURE_TUTELLE_EXECUTION = "Ajout de structuures de tutelle/exécution : GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution / Nouvelle Structure";
     public static final String DELETE_STRUCTURE_TUTELLE_EXECUTION = "Suppression de structuures de tutelle/exécution : GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution / Supprimer";
     public static final String UPDATE_STRUCTURE_TUTELLE_EXECUTION = "Modification de structuures de tutelle/exécution : GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution / Modifier";
     public static final String IMPORT_STRUCTURE_TUTELLE_EXECUTION = "Importation de structuures de tutelle/exécution : GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution / Importer";
     public static final String EXPORT_STRUCTURE_TUTELLE_EXECUTION_TO_EXCEL = "Exportation de structuures de tutelle/exécution en Excel: GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution / Exporter / Excel";
     public static final String EXPORT_STRUCTURE_TUTELLE_EXECUTION_PDF = "Exportation de structuures de tutelle/exécution en PDF: GESTION PROJET / Liste projets / Paramétrage / Onglet Structure de Tutelle/Execution / Exporter / PDF";

     public static final String READ_INDICATEUR = "Consultation des indicateurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs";
     public static final String ADD_INDICATEUR = "Ajout d'indicateur : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs / Nouveau Indicateur";
     public static final String UPDATE_INDICATEUR = "Modification d'indicateur : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs / Modifier";
     public static final String DELETE_INDICATEUR = "Suppression d'indicateur : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs / Supprimer";
     public static final String IMPORT_INDICATEUR = "Importation des indicateurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs / Importer";
     public static final String EXPORT_INDICATEUR_TO_EXCEL = "Exportation en excel des indicateurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs / Exporter / Excel";
     public static final String EXPORT_INDICATEUR_TO_PDF = "Exportation PDF des indicateurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Indicateurs / Exporter / PDF";

     public static final String READ_ACTEUR = "Consultation des acteurs : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet etape / List Partiticipant";
     public static final String ADD_PARTICIPANT = "Ajout Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet participant / Nouveau participant";
     public static final String READ_PARTICIPANT = "Lire Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet participant / Liste";
     public static final String UPDATE_PARTICIPANT = "Modifier Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet participant / Lire";
     public static final String DELETE_PARTICIPANT = "Delete Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet participant /Delete";

     public static final String READ_ETAPE = "Consultation des acteurs :GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet etape / List etape";
     public static final String ADD_ETAPE = "Ajout Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet etape / Nouvelle etape";
     public static final String UPDATE_ETAPE = "Modifier Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet etape / Lire";
     public static final String DELETE_ETAPE = "Delete Participant : GESTION ACTIVITER / Liste activites / Configuration activite / Onglet Mission/Mission-config/Onglet etape /Delete";

     public static final String ADD_ACTEUR = "Modification d'acteur : GESTION PROJET / Liste projets / Paramétrage / Onglet Acteurs / Nouvel Acteur";

     public static final String UPDATE_ACTEUR = "Modification d'acteur : GESTION PROJET / Liste projets / Paramétrage / Onglet Acteurs / Modifier";
     public static final String DELETE_ACTEUR = "Suppression d'acteur : GESTION PROJET / Liste projets / Paramétrage / Onglet Acteurs / Supprimer";
     public static final String IMPORT_ACTEUR = "Importation des acteurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Acteurs / Importer";
     public static final String EXPORT_ACTEUR_TO_EXCEL = "Exportation en excel des acteurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Acteurs / Exporter / Excel";
     public static final String EXPORT_ACTEUR_TO_PDF = "Exportation PDF des acteurs : GESTION PROJET / Liste projets / Paramétrage / Onglet Acteurs / Exporter / PDF";

     public static final String READ_DATES_IMPORTANTE = "Consultation des dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes";
     public static final String ADD_DATES_IMPORTANTE = "Ajout dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes / Nouvelle date";
     public static final String UPDATE_DATES_IMPORTANTE = "Modification dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes / Modifier";
     public static final String DELETE_DATES_IMPORTANTE = "Suppression dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes / Supprimer";
     public static final String IMPORT_DATES_IMPORTANTE = "Importation des dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes / Importer";
     public static final String EXPORT_DATES_IMPORTANTE_TO_EXCEL = "Exportation en excel des dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes / Exporter / Excel";
     public static final String EXPORT_DATES_IMPORTANTE_TO_PDF = "Exportation PDF des dates importantes : GESTION PROJET / Liste projets / Paramétrage / Onglet Dates importantes / Exporter / PDF";


    public static final String READ_WORKFLOW_HISTORIQUE = "Consultation de l'historique de validation : SÉCURITÉ / Workflow";
    public static final String ADD_WORKFLOW_HISTORIQUE = "Ajout à l'historique de validation : SÉCURITÉ / Workflow / Ajouter";
    public static final String EDIT_WORKFLOW_HISTORIQUE = "Modification de l'historique de validation : SÉCURITÉ / Workflow / Modifier";
    public static final String DELETE_WORKFLOW_HISTORIQUE = "Suppression de l'historique de validation : SÉCURITÉ / Workflow / Supprimer";


     public static final String READ_COMPOSANTE_TO_AVANCEMENT = "Consultation des composantes : GESTION PROJET / Liste projets / avancement detaille / Onglet Composantes";
     public static final String READ_INDICATEURS_TO_AVANCEMENT = "Consultation des indicateurs : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs";
     public static final String ADD_INDICATEURS_TO_AVANCEMENT = "Ajout d'indicateur : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs / Nouvelle valeur indicateur";
     public static final String UPDATE_INDICATEURS_TO_AVANCEMENT = "Modification d'indicateur : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs / Modifier";
     public static final String DELETE_INDICATEURS_TO_AVANCEMENT = "Suppression d'indicateur : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs / Supprimer";
     public static final String EXPORT_INDICATEURS_TO_AVANCEMENT = "Exportation en excel des indicateurs : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs / Exporter / Excel ";
     public static final String EXPORT_INDICATEURS_TO_AVANCEMENT_PDF = "Exportation en pdf des indicateurs : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs / Exporter / Pdf ";
     public static final String IMPORT_INDICATEURS_TO_AVANCEMENT = "Importation en excel des indicateurs : GESTION PROJET / Liste projets / avancement detaille / Onglet indicateurs / Importer /Excel ";


     public static final String READ_RISQUES_TO_AVANCEMENT = "Consultation des risques : GESTION PROJET / Liste projets / avancement detaille / Onglet risque";
     public static final String ADD_RISQUES_TO_AVANCEMENT = "Ajout risque : GESTION PROJET / Liste projets / avancement detaille /Onglet risque/ Nouveau risque";
     public static final String UPDATE_RISQUES_TO_AVANCEMENT = "Modification risque : GESTION PROJET / Liste projets / avancement detaille /Onglet risque/ Modifier";
     public static final String DELETE_RISQUES_TO_AVANCEMENT = "Suppression risque : GESTION PROJET / Liste projets / avancement detaille / Onglet risque / Supprimer";
     public static final String EXPORT_RISQUES_TO_AVANCEMENT = "Exportation en excel des risques : GESTION PROJET / Liste projets / avancement detaille / Onglet risque / Exporter / Excel ";
     public static final String EXPORT_RISQUES_TO_AVANCEMENT_PDF = "Exportation en pdf des risques : GESTION PROJET / Liste projets / avancement detaille / Onglet risque / Exporter / Pdf ";
     public static final String IMPORT_RISQUES_TO_AVANCEMENT = "Importation en excel des risques : GESTION PROJET / Liste projets / avancement detaille / Onglet risque / Importer /Excel ";
     public static final String CONSULTER_RISQUES_TO_AVANCEMENT = "Consulter les risques : GESTION PROJET / Liste projets / avancement detaille / Onglet risque / consulter ";
     public static final String PROBLEMES_RISQUES_TO_AVANCEMENT = "Probleme lies aux risques : GESTION PROJET / Liste projets / avancement detaille / Onglet risque / Problemes ";
     public static final String ADD_RECOMMENDATIONS_RISQUES_TO_AVANCEMENT = "Ajout Recommendations a un risque : GESTION PROJET / Liste projets / avancement detaille /Onglet risque / Recommandations /Nouvelle recommandation";


     public static final String READ_PROBLEMES_TO_AVANCEMENT = "Consultation des problemes : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes";
     public static final String ADD_PROBLEMES_TO_AVANCEMENT = "Ajout probleme : GESTION PROJET / Liste projets / avancement detaille /Onglet problemes / Nouveau probleme";
     public static final String UPDATE_PROBLEMES_TO_AVANCEMENT = "Modification probleme : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Modifier";
     public static final String DELETE_PROBLEMES_TO_AVANCEMENT = "Suppression probleme : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Supprimer";
     public static final String IMPORT_PROBLEMES_TO_AVANCEMENT = "Importation en excel des problemes : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Import";
     public static final String EXPORT_PROBLEMES_TO_AVANCEMENT = "Exportation en excel des problemes : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Export";
     public static final String ACTIONS_REALISES_PROBLEMES_TO_AVANCEMENT = "Actions realises aux problemes : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Actions realisees";
     public static final String ADD_ACTIONS_REALISES_PROBLEMES_TO_AVANCEMENT = " Ajouter des Actions realises aux problemes : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Actions realisees /Nouvelle action";
     public static final String RECOMMANDATIONS_REALISES_PROBLEMES_TO_AVANCEMENT = "Recommandations aux problemes : GESTION PROJET / Liste projets / avancement detaille / Onglet problemes / Recommandations ";

     public static final String READ_POINT_DE_VIGILANCE_TO_AVANCEMENT = "Consultation des points de vigilance : GESTION PROJET / Liste projets / avancement detaille / Onglet point de vigilance";
     public static final String ADD_POINT_DE_VIGILANCE_TO_AVANCEMENT = "Ajout d'un point de vigilance : GESTION PROJET / Liste projets / avancement detaille /Onglet problemes / Nouveau point de vigilance";
     public static final String UPDATE_POINT_DE_VIGILANCE_TO_AVANCEMENT = "Modification d'un point de vigilance : GESTION PROJET / Liste projets / avancement detaille / Onglet point de vigilance / Modifier";
     public static final String DELETE_POINT_DE_VIGILANCE_TO_AVANCEMENT = "Suppression d'un point de vigilance : GESTION PROJET / Liste projets / avancement detaille / Onglet point de vigilance / Supprimer";


     public static final String READ_DOCUMENT = "Consultation des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document";
     public static final String ADD_DOCUMENT = "Ajout des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document/ Nouveau document";
     public static final String UPDATE_DOCUMENT = "Modification des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document/ Modifier";
     public static final String DELETE_DOCUMENT = "Suppression des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document/ Supprimer";

     public static final String READ_PREPARATION = "Consultation des préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations";
     public static final String ADD_PREPARATION = "Ajout préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations / Nouvelle préparation";
     public static final String UPDATE_PREPARATION = "Modification préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations / Modifier";
     public static final String DELETE_PREPARATION = "Suppression préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations / Supprimer";
     public static final String IMPORT_PREPARATION = "Importation des préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations / Importer";
     public static final String EXPORT_PREPARATION_TO_EXCEL = "Exportation en excel des préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations / Exporter / Excel";
     public static final String EXPORT_PREPARATION_TO_PDF = "Exportation PDF des préparations : GESTION PROJET / Liste projets / Suivi activités / Préparations / Exporter / PDF";

     public static final String READ_CONDITIONALITY = "Consultation des conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités";
     public static final String ADD_CONDITIONALITY = "Ajout conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités / Nouvelle conditionnalité";
     public static final String UPDATE_CONDITIONALITY = "Modification conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités / Modifier";
     public static final String DELETE_CONDITIONALITY = "Suppression conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités / Supprimer";
     public static final String IMPORT_CONDITIONALITY = "Importation des conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités / Importer";
     public static final String EXPORT_CONDITIONALITY_TO_EXCEL = "Exportation en excel des conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités / Exporter / Excel";
     public static final String EXPORT_CONDITIONALITY_TO_PDF = "Exportation PDF des conditionnalités : GESTION PROJET / Liste projets / Suivi activités / Conditionnalités / Exporter / PDF";


     public static final String READ_ASSIGNMENT = "Consultation des missions : GESTION PROJET / Liste projets / Suivi activités / Missions";
     public static final String ADD_ASSIGNMENT = "Ajout missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Nouvelle mission";
     public static final String UPDATE_ASSIGNMENT = "Modification missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Modifier";
     public static final String DELETE_ASSIGNMENT = "Suppression missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Supprimer";
     public static final String IMPORT_ASSIGNMENT = "Importation des missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Importer";
     public static final String EXPORT_ASSIGNMENT_TO_EXCEL = "Exportation en excel des missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Exporter / Excel";
     public static final String EXPORT_ASSIGNMENT_TO_PDF = "Exportation PDF des missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Exporter / PDF";

     public static final String READ_ASSIGNMENT_ISSUE_LOG = "Consultation des problèmes dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Problème";
     public static final String ADD_ASSIGNMENT_ISSUE_LOG = "Ajout problème dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Problème / Nouveau problème";
     public static final String UPDATE_ASSIGNMENT_ISSUE_LOG = "Modification problème dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Problème  / Modifier";
     public static final String DELETE_ASSIGNMENT_ISSUE_LOG = "Suppression problème dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Problème / Supprimer";

     public static final String READ_ASSIGNMENT_RECOMMANDATION = "Consultation des recommandation dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Recommandation";
     public static final String ADD_ASSIGNMENT_RECOMMANDATION = "Ajout recommandation dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Recommandation / Nouvelle recommandation";
     public static final String UPDATE_ASSIGNMENT_RECOMMANDATION = "Modification recommandation dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Recommandation  / Modifier";
     public static final String DELETE_ASSIGNMENT_RECOMMANDATION = "Suppression recommandation dans missions : GESTION PROJET / Liste projets / Suivi activités / Missions / Recommandation / Supprimer";

     public static final String READ_REVIEW = "Consultation des revues : GESTION PROJET / Liste projets / Suivi activités / Revues";
     public static final String ADD_REVIEW = "Ajout revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Nouvelle revue";
     public static final String UPDATE_REVIEW = "Modification revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Modifier";
     public static final String DELETE_REVIEW = "Suppression revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Supprimer";
     public static final String IMPORT_REVIEW = "Importation des revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Importer";
     public static final String EXPORT_REVIEW_TO_EXCEL = "Exportation en excel des revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Exporter / Excel";
     public static final String EXPORT_REVIEW_TO_PDF = "Exportation PDF des revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Exporter / PDF";

     public static final String READ_REVIEW_RISK = "Consultation des risques dans une revue : GESTION PROJET / Liste projets / Suivi activités / Revues / Risque";
     public static final String ADD_REVIEW_RISK = "Ajout risque dans revues : GESTION PROJET / Liste projets / Suivi activités / Revues / Risque / Nouvelle risque";
     public static final String UPDATE_REVIEW_RISK = "Modification risque dans une revue : GESTION PROJET / Liste projets / Suivi activités / Revues / Risque / Modifier";
     public static final String DELETE_REVIEW_RISK = "Suppression risque dans une revue : GESTION PROJET / Liste projets / Suivi activités / Revues / Risque / Supprimer";

     public static final String READ_REVIEW_ISSUE_LOG = "Consultation problème dans revue  : GESTION PROJET / Liste projets / Suivi activités / Revues / Problème";
     public static final String ADD_REVIEW_ISSUE_LOG = "Ajout problème dans revue : GESTION PROJET / Liste projets / Suivi activités / Revues / Problème / Nouvelle problème";
     public static final String UPDATE_REVIEW_ISSUE_LOG = "Modification problème dans revue : GESTION PROJET / Liste projets / Suivi activités / Revues / Problème / Modifier";
     public static final String DELETE_REVIEW_ISSUE_LOG = "Suppression problème dans revue : GESTION PROJET / Liste projets / Suivi activités / Revues / Problème / Supprimer";

     public static final String READ_QUERY = "Consultation des requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes";
     public static final String ADD_QUERY = "Ajout requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Nouvelle requête";
     public static final String UPDATE_QUERY = "Modification requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Modifier";
     public static final String DELETE_QUERY = "Suppression requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Supprimer";
     public static final String IMPORT_QUERY = "Importation des requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Importer";
     public static final String EXPORT_QUERY_TO_EXCEL = "Exportation en excel des requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Exporter / Excel";
     public static final String EXPORT_QUERY_TO_PDF = "Exportation PDF des requêtes : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Exporter / PDF";

     public static final String READ_STATUS_QUERY = "Consultation des situation dans une requête : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Situation";
     public static final String ADD_STATUS_QUERY = "Ajout des situation dans une requête : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Situations / Nouvelle situation";
     public static final String UPDATE_STATUS_QUERY = "Modification des situation dans une requête : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Situations / Modifier";
     public static final String DELETE_STATUS_QUERY = "Suppression des situation dans une requête : GESTION PROJET / Liste projets / Suivi activités / Requêtes / Situations / Supprimer";

     public static final String READ_MEETING = "Consultation des réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions";
     public static final String ADD_MEETING = "Ajout réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions / Nouvelle réunion";
     public static final String UPDATE_MEETING = "Modification réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions / Modifier";
     public static final String DELETE_MEETING = "Suppression réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions / Supprimer";
     public static final String IMPORT_MEETING = "Importation des réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions / Importer";
     public static final String EXPORT_MEETING_TO_EXCEL = "Exportation en excel des réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions / Exporter / Excel";
     public static final String EXPORT_MEETING_TO_PDF = "Exportation PDF des réunions : GESTION PROJET / Liste projets / Suivi activités / Réunions / Exporter / PDF";

     public static final String READ_MEDIATHEQUE = "Consultation des images et videos : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Mediatheque";
     public static final String ADD_MEDIATHEQUE = "Ajout des images et videos : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Mediatheque / Image / Nouvelle Image ou Video";
     public static final String UPDATE_MEDIATHEQUE = "Modification des images et videos  : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Mediatheque / Modifier";
     public static final String DELETE_MEDIATHEQUE = "Suppression des images et videos : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Mediatheque  / Supprimer";

     public static final String READ_FUNDING = "Consultation des financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Financements";
     public static final String ADD_FUNDING = "Ajout financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Indicateurs / Nouveau Financements";
     public static final String UPDATE_FUNDING = "Modification financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Financements / Modifier";
     public static final String DELETE_FUNDING = "Suppression financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Financements / Supprimer";
     public static final String IMPORT_FUNDING = "Importation des financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Financements / Importer";
     public static final String EXPORT_FUNDING_TO_EXCEL = "Exportation en excel des financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Financements/ Exporter / Excel";
     public static final String EXPORT_FUNDING_TO_PDF = "Exportation PDF des financement : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Financements / Exporter / PDF";

     public static final String READ_FLAGS = "Consultation des flags : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Flags";
     public static final String ADD_FLAGS = "Ajout des flags : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Flags / Nouveau Flag ";
     public static final String UPDATE_FLAGS = "Modification des flags : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Flags / Modifier";
     public static final String DELETE_FLAGS = "Suppression des flags : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Flags / Supprimer";

     public static final String READ_STATUTS = "Consultation des statuts : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Statuts";
     public static final String ADD_STATUTS = "Ajout des statuts : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Statuts / Nouveau Statut ";
     public static final String UPDATE_STATUTS = "Modification des statuts : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Statuts / Modifier";
     public static final String DELETE_STATUTS = "Suppression des statuts : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Statuts / Supprimer";

     public static final String READ_ENV_IMPACT = "Consultation des impacts environnementaux : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL/ Onglet Impacts";
     public static final String ADD_ENV_IMPACT = "Ajout d'un impact environnemental : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Impacts / Nouvel Impact environnemental";
     public static final String UPDATE_ENV_IMPACT = "Modification d'un impact environnemental : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Impacts / Modifier";
     public static final String DELETE_ENV_IMPACT = "Suppression d'un impact environnemental : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Impacts / Supprimer";

     public static final String READ_ENV_INDICATEUR = "Consultation des indicateur environnementaux : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL/ Onglet Indicateurs";
     public static final String ADD_ENV_INDICATEUR = "Ajout d'un indicateur environnemental : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Indicateurs / Nouvel Indicateur environnemental";
     public static final String UPDATE_ENV_INDICATEUR = "Modification d'un indicateur environnemental : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Indicateurs / Modifier";
     public static final String DELETE_ENV_INDICATEUR = "Suppression d'un indicateur environnemental : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Indicateurs / Supprimer";

     public static final String READ_ENV_CONFORMITE = "Consultation des conformités réglementaires environnementaux : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL/ Onglet Conformités";
     public static final String ADD_ENV_CONFORMITE = "Ajout d'une conformité réglementaire environnementale : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Conformités / Nouvelles Conformités reglementaire";
     public static final String UPDATE_ENV_CONFORMITE = "Modification d'une conformité réglementaire environnementale : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Conformités / Modifier";
     public static final String DELETE_ENV_CONFORMITE = "Suppression d'une conformité réglementaire environnementale : GESTION PROJET / Liste projets / ESG / ENVIRONNEMENTAL / Onglet Conformités / Supprimer";

     public static final String READ_SOCIAL_IMPACT = "Consultation des impacts sociaux : GESTION PROJET / Liste projets / ESG / SOCIAL/ Onglet Impacts";
     public static final String ADD_SOCIAL_IMPACT = "Ajout d'un impact social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Impacts / Nouvel Impact social";
     public static final String UPDATE_SOCIAL_IMPACT = "Modification d'un impact social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Impacts / Modifier";
     public static final String DELETE_SOCIAL_IMPACT = "Suppression d'un impact social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Impacts / Supprimer";

     public static final String READ_SOCIAL_INDICATEUR = "Consultation des indicateur sociaux : GESTION PROJET / Liste projets / ESG / SOCIAL/ Onglet Indicateurs";
     public static final String ADD_SOCIAL_INDICATEUR = "Ajout d'un indicateur social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Indicateurs / Nouvel Indicateur social";
     public static final String UPDATE_SOCIAL_INDICATEUR = "Modification d'un indicateur social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Indicateurs / Modifier";
     public static final String DELETE_SOCIAL_INDICATEUR = "Suppression d'un indicateur social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Indicateurs / Supprimer";

     public static final String READ_SOCIAL_CONFORMITE = "Consultation des conformités réglementaires sociaux : GESTION PROJET / Liste projets / ESG / SOCIAL/ Onglet Conformités";
     public static final String ADD_SOCIAL_CONFORMITE = "Ajout d'une conformité réglementaire social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Conformités / Nouvelles Conformités reglementaire";
     public static final String UPDATE_SOCIAL_CONFORMITE = "Modification d'une conformité réglementaire social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Conformités / Modifier";
     public static final String DELETE_SOCIAL_CONFORMITE = "Suppression d'une conformité réglementaire social : GESTION PROJET / Liste projets / ESG / SOCIAL / Onglet Conformités / Supprimer";

     public static final String READ_GOUV_IMPACT = "Consultation des impacts de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE/ Onglet Impacts";
     public static final String ADD_GOUV_IMPACT = "Ajout d'un impact gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Impacts / Nouvel Impact social";
     public static final String UPDATE_GOUV_IMPACT = "Modification d'un impact gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Impacts / Modifier";
     public static final String DELETE_GOUV_IMPACT = "Suppression d'un impact gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Impacts / Supprimer";

     public static final String READ_GOUV_INDICATEUR = "Consultation des indicateur de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE/ Onglet Indicateurs";
     public static final String ADD_GOUV_INDICATEUR = "Ajout d'un indicateur de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Indicateurs / Nouvel Indicateur de gouvernance";
     public static final String UPDATE_GOUV_INDICATEUR = "Modification d'un indicateur de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Indicateurs / Modifier";
     public static final String DELETE_GOUV_INDICATEUR = "Suppression d'un indicateur de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Indicateurs / Supprimer";

     public static final String READ_GOUV_CONFORMITE = "Consultation des conformités réglementaires sociaux : GESTION PROJET / Liste projets / ESG / GOUVERNANCE/ Onglet Conformités";
     public static final String ADD_GOUV_CONFORMITE = "Ajout d'une conformité réglementaire de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Conformités / Nouvelles Conformités reglementaire";
     public static final String UPDATE_GOUV_CONFORMITE = "Modification d'une conformité réglementaire de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Conformités / Modifier";
     public static final String DELETE_GOUV_CONFORMITE = "Suppression d'une conformité réglementaire de gouvernance : GESTION PROJET / Liste projets / ESG / GOUVERNANCE / Onglet Conformités / Supprimer";

     public static final String ADD_ETABLISSEMENT_CLASSE = "Ajout d’établissement classé : GESTION ÉTABLISSEMENTS / Liste établissements / Bouton Nouvel établissement";
     public static final String UPDATE_ETABLISSEMENT_CLASSE = "Modification d’établissement classé : GESTION ÉTABLISSEMENTS / Liste établissements / Bouton Modifier";
     public static final String READ_ETABLISSEMENT_CLASSE = "Consultation d’établissement classé : GESTION ÉTABLISSEMENTS / Liste établissements";
     public static final String DELETE_ETABLISSEMENT_CLASSE = "Suppression d’établissement classé : GESTION ÉTABLISSEMENTS / Liste établissements / Bouton Supprimer";

     public static final String ADD_INSPECTION_ICPE = "Ajout d’inspection ICPE : GESTION INSPECTIONS / Liste inspections / Bouton Nouvelle inspection";
     public static final String UPDATE_INSPECTION_ICPE = "Modification d’inspection ICPE : GESTION INSPECTIONS / Liste inspections / Bouton Modifier";
     public static final String READ_INSPECTION_ICPE = "Consultation d’inspection ICPE : GESTION INSPECTIONS / Liste inspections";
     public static final String DELETE_INSPECTION_ICPE = "Suppression d’inspection ICPE : GESTION INSPECTIONS / Liste inspections / Bouton Supprimer";

     public static final String ADD_STATION = "Ajout de station : GESTION STATIONS / Liste stations / Bouton Nouvelle station";
     public static final String UPDATE_STATION = "Modification de station : GESTION STATIONS / Liste stations / Bouton Modifier";
     public static final String READ_STATION = "Consultation de station : GESTION STATIONS / Liste stations";
     public static final String DELETE_STATION = "Suppression de station : GESTION STATIONS / Liste stations / Bouton Supprimer";

     public static final String ADD_QUALITE_AIR = "Ajout d’évaluation de qualité de l'air : GESTION QUALITE AIR / Liste évaluations / Bouton Nouvelle évaluation";
     public static final String UPDATE_QUALITE_AIR = "Modification d’évaluation de qualité de l'air : GESTION QUALITE AIR / Liste évaluations / Bouton Modifier";
     public static final String READ_QUALITE_AIR = "Consultation d’évaluation de qualité de l'air : GESTION QUALITE AIR / Liste évaluations";
     public static final String DELETE_QUALITE_AIR = "Suppression d’évaluation de qualité de l'air : GESTION QUALITE AIR / Liste évaluations / Bouton Supprimer";

    //EVALUATION_ENV

     public static final String ADD_EVALUATION_ENV = "Ajout d’évaluation environnementale : GESTION EVALUATION ENVIRONNEMENTALE / Liste évaluations / Bouton Nouvelle évaluation";
     public static final String UPDATE_EVALUATION_ENV = "Modification d’évaluation environnementale : GESTION EVALUATION ENVIRONNEMENTALE / Liste évaluations / Bouton Modifier";
     public static final String READ_EVALUATION_ENV = "Consultation d’évaluation environnementale : GESTION EVALUATION ENVIRONNEMENTALE / Liste évaluations";
     public static final String DELETE_EVALUATION_ENV = "Suppression d’évaluation environnementale : GESTION EVALUATION ENVIRONNEMENTALE / Liste évaluations / Bouton Supprimer";

    //VALIDATE_TEMPORARY_CODE
     public static final String VALIDATE_TEMPORARY_CODE = "Validation d’un code temporaire : Authentification / Bouton Valider";
    //SIGN_TEMPORARY_CODE
     public static final String SIGN_TEMPORARY_CODE = "Connexion par code temporaire : Authentification / Connexion par code temporaire";
    //GENERATE_TEMPORARY_CODE
     public static final String GENERATE_TEMPORARY_CODE = "Génération de code temporaire : Paramétres / Utilisateur / Bouton Générer un code d'accès temporaire";
    //READ_TEMPORARY_CODE
     public static final String READ_TEMPORARY_CODE = "Consultation des codes temporaires : Paramétres / Utilisateur / Bouton Voir l'historique des codes d'accès";
}
