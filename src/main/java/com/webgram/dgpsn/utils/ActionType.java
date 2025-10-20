package com.webgram.dgpsn.utils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass // tous les méthodes et les attributs sont static
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActionType {
    public final String UPDATE_AGENT = "Modification d'agent : GAP / Personnels / Douaniers / Bouton Modifier";
    public final String CREATE_AGENT = "Ajout d'agent : GAP / Personnels / Douaniers / Bouton Nouvel agent";
    public final String DELETE_AGENT = "Suppression d'agent : GAP / Personnels / Douaniers / Bouton Supprimer";
    public final String DELETE_PROFILPHOTO_AGENT = "Suppression photo de profil agent : GAP / Personnels / Douaniers / Bouton photo profil/ Supprimer la photo";
    public final String READ_PROFILPHOTO_AGENT = "Consultation photo de profil agent : GAP / Personnels / Douaniers / Bouton photo profil";
    public final String DETAIL_AGENT = "Détails agent : GAP / Personnels / Douaniers / Suivi";
    public final String READ_AGENT = "Consultation des agents : GAP / Personnels / Douaniers";
    public final String IMPORT_AGENT = "Importation d'agent : GAP / Personnels / Douaniers / Bouton Importer";
    public final String EXPORT_AGENT = "Exportation d'agent : GAP / Personnels / Douaniers Exporter ";
    public final String EXPORT_AGENT_RETRAITE = "Exportation d'agent en retraite: Tableau de bord / Sortie/ Prévision des retraites par corps / Telecharger ";

    public final String UPDATE_NON_AGENT = "Modification d'agent : GAP / Personnels / Non douaniers / Bouton Modifier";
    public final String CREATE_NON_AGENT = "Ajout d'agent : GAP / Personnels / Non douaniers / Bouton Nouveau personnel";
    public final String DELETE_NON_AGENT = "Suppression d'agent : GAP / Personnels / Non douaniers / Bouton Supprimer";
    public final String READ_NON_AGENT = "Consultation des agents : GAP / Personnels / Non douaniers";
    public final String IMPORT_NON_AGENT = "Importation d'agent : GAP / Personnels / Non douaniers / Bouton Importer";
    public final String EXPORT_NON_AGENT = "Exportation d'agent : GAP / Personnels / Non douaniers Exporter ";

    public final String READ_PROPOSITION_AFFECTATION_AGENT = "Consultation proposition d'affectation des agents : Actes de gestion / Affectations / suivi / Population affectable ou Proposer des affectations";

    public final String READ_DOCUMENT = "Consultation des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document";
    public final String ADD_DOCUMENT = "Ajout des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document/ Nouveau document";
    public final String UPDATE_DOCUMENT = "Modification des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document/ Modifier";
    public final String DELETE_DOCUMENT = "Suppression des document : GESTION PROJET / Liste projets / Avancement Detailles / Onglet Document/ Supprimer";

    public final String READ_PROFILE = "Consultation des profils : SECURITE / Profils ";
    public final String ADD_PROFILE = "Ajout de profil : SECURITE / Profils / Ajouter Nouveau profil";
    public final String UPDATE_PROFILE = "Modification de profil :SECURITE / Profils / Modifier";
    public final String DELETE_PROFILE = "Suppression de profil : SECURITE / Profils  / Supprimer";

    public final String READ_USER = "Consultation des utilisateurs : SECURITE / Utilisateurs ";
    public final String ADD_USER = "Ajout de utilisateur : SECURITE / Utilisateurs / Ajouter Nouvel utilisateur";
    public final String UPDATE_USER = "Modification utilisateur :SECURITE / Utilisateurs / Modifier";
    public final String DELETE_USER = "Suppression utilisateur : SECURITE / Utilisateurs  / Supprimer";
    public final String CONNEXION = "Connexion : Page connexion ";
}
