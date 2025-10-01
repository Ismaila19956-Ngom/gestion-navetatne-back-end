package com.webgram.dgpsn.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.webgram.dgpsn.entities.enums.Feature;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum SecurityPermissions {

    READ_TDB("Consulter Tableau de bord", Feature.DASHBOARD),

    ADD_TEMPLATE("Ajouter alerte", Feature.ALERTE),
    EDIT_TEMPLATE("Modifier alerte", Feature.ALERTE),
    DELETE_TEMPLATE("Supprimer alerte", Feature.ALERTE),
    READ_TEMPLATE("Consulter alerte", Feature.ALERTE),

    ADD_FLUX_TRESORERIE_PARAMETRE("Ajouter flux de tresorerie", Feature.SOLDE_TRESSORERIE_PARAMETRE),
    EDIT_FLUX_TRESORERIE_PARAMETRE("Modifier flux de tresorerie", Feature.SOLDE_TRESSORERIE_PARAMETRE),
    DELETE_FLUX_TRESORERIE_PARAMETRE("Supprimer flux de tresorerie", Feature.SOLDE_TRESSORERIE_PARAMETRE),
    READ_FLUX_TRESORERIE_PARAMETRE("Consulter flux de tresorerie", Feature.SOLDE_TRESSORERIE_PARAMETRE),
    CHANGE_ALERTE_BLOCK_ST("Changer le statut d'alerte ou bloquant", Feature.SOLDE_TRESSORERIE_PARAMETRE),
    READ_CONFIGURATION_IMPORT_ST("Consulter configuration import du Solde journalier", Feature.SOLDE_TRESSORERIE_PARAMETRE),

    ADD_DISPLAY_CONFIG_SOLDE_JOURNALIER("Ajouter configuration affichage du Solde journalier", Feature.DISPLAY_CONFIG_SOLDE_JOURNALIER),
    EDIT_DISPLAY_CONFIG_SOLDE_JOURNALIER("Modifier configuration affichage du Solde journalier", Feature.DISPLAY_CONFIG_SOLDE_JOURNALIER),
    DELETE_DISPLAY_CONFIG_SOLDE_JOURNALIER("Supprimer configuration affichage du Solde journalier", Feature.DISPLAY_CONFIG_SOLDE_JOURNALIER),
    READ_DISPLAY_CONFIG_SOLDE_JOURNALIER("Consulter configuration affichage du Solde journalier", Feature.DISPLAY_CONFIG_SOLDE_JOURNALIER),

    ADD_BILAN_TRESORERIE_PARAMETRE("Ajouter instrument financier", Feature.BILAN_TRESORERIE_PARAMETRE),
    EDIT_BILAN_TRESORERIE_PARAMETRE("Modifier instrument financier", Feature.BILAN_TRESORERIE_PARAMETRE),
    DELETE_BILAN_TRESORERIE_PARAMETRE("Supprimer instrument financier", Feature.BILAN_TRESORERIE_PARAMETRE),
    READ_BILAN_TRESORERIE_PARAMETRE("Consulter instrument financier", Feature.BILAN_TRESORERIE_PARAMETRE),
    CHANGE_ALERTE_BLOCK_BILAN("Changer le statut d'alerte ou bloquant", Feature.BILAN_TRESORERIE_PARAMETRE),
    READ_CONFIGURATION_IMPORT_BILAN("Consulter configuration import du bilan", Feature.BILAN_TRESORERIE_PARAMETRE),


    ADD_COMPTE_RESULTAT_PARAMETRE("Ajouter configuration compte de résultat", Feature.COMPTE_RESULTAT_PARAMETRE),
    EDIT_COMPTE_RESULTAT_PARAMETRE("Modifier configuration compte de résultat", Feature.COMPTE_RESULTAT_PARAMETRE),
    DELETE_COMPTE_RESULTAT_PARAMETRE("Supprimer configuration compte de résultat", Feature.COMPTE_RESULTAT_PARAMETRE),
    READ_COMPTE_RESULTAT_PARAMETRE("Consulter configuration compte de résultat", Feature.COMPTE_RESULTAT_PARAMETRE),
    READ_CONFIGURATION_IMPORT_CR("Consulter configuration import du compte de résultat", Feature.COMPTE_RESULTAT_PARAMETRE),
    CHANGE_ALERTE_BLOCK_CR("Changer le statut d'alerte ou bloquant", Feature.COMPTE_RESULTAT_PARAMETRE),

    ADD_QUANTILE_CONFIG("Ajouter des quantiles", Feature.QUANTILE_PARAMETRE),
    EDIT_QUANTILE_CONFIG("Modifier les quantiles", Feature.QUANTILE_PARAMETRE),
    DELETE_QUANTILE_CONFIG("Supprimer les quantiles", Feature.QUANTILE_PARAMETRE),
    READ_QUANTILE_CONFIG("Consulter les quantiles", Feature.QUANTILE_PARAMETRE),

//    // [ ORGANIGRAMME ]
    ADD_ORGANIGRAMME("Ajouter organigramme", Feature.ORGANIGRAMME),
    EDIT_ORGANIGRAMME("Modifier organigramme", Feature.ORGANIGRAMME),
    DELETE_ORGANIGRAMME("Supprimer organigramme", Feature.ORGANIGRAMME),
    READ_ORGANIGRAMME("Consulter organigramme", Feature.ORGANIGRAMME),
    CONFIG_AGENT("Gerer agents", Feature.ORGANIGRAMME),

    READ_HQLA("Consulter  hqla", Feature.RATIO_PARAMETRE_LCR),
    ADD_HQLA("Ajouter hqla", Feature.RATIO_PARAMETRE_LCR),
    EDIT_HQLA("Modifier hqla", Feature.RATIO_PARAMETRE_LCR),
    DELETE_HQLA("Supprimer hqla", Feature.RATIO_PARAMETRE_LCR),
//
    READ_SEUIL("Consulter  seuil", Feature.PARAMETRE_SEUIL),
    ADD_SEUIL("Ajouter seuil", Feature.PARAMETRE_SEUIL),
    EDIT_SEUIL("Modifier seuil", Feature.PARAMETRE_SEUIL),
    DELETE_SEUIL("Supprimer seuil", Feature.PARAMETRE_SEUIL),
//
//
    READ_PERIODE("Consulter  periode", Feature.PARAMETRE_PERIODE),
    ADD_PERIODE("Ajouter periode", Feature.PARAMETRE_PERIODE),
    EDIT_PERIODE("Modifier periode", Feature.PARAMETRE_PERIODE),
    DELETE_PERIODE("Supprimer periode", Feature.PARAMETRE_PERIODE),
//
    READ_NSFR("Consulter  NSFR", Feature.PARAMETRE_NSFR),
    ADD_NSFR("Ajouter NSFR", Feature.PARAMETRE_NSFR),
    EDIT_NSFR("Modifier NSFR", Feature.PARAMETRE_NSFR),
    DELETE_NSFR("Supprimer NSFR", Feature.PARAMETRE_NSFR),

    //Rapport

    READ_RAPPORT("Consulter rapport", Feature.RAPPORT),

    READ_SOLDE_TRESORERIE("Consulter Solde Trésorerie", Feature.SOLDE_TRESORERIE),
//    ADD_SOLDE_TRESORERIE("Ajouter Solde Trésorerie", Feature.SOLDE_TRESORERIE),
//    EDIT_SOLDE_TRESORERIE("Modifier Solde Trésorerie", Feature.SOLDE_TRESORERIE),
    DELETE_SOLDE_TRESORERIE("Supprimer Solde Trésorerie", Feature.SOLDE_TRESORERIE),
    IMPORT_SOLDE_TRESORERIE("Importer Solde Trésorerie", Feature.SOLDE_TRESORERIE),
    EXPORT_SOLDE_TRESORERIE("Exporter Solde Trésorerie", Feature.SOLDE_TRESORERIE),
    VALIDATE_SOLDE_TRESORERIE("Valider Solde Trésorerie", Feature.SOLDE_TRESORERIE),
    ALERTE_SOLDE_TRESORERIE("Consulter Alerte Solde Trésorerie", Feature.SOLDE_TRESORERIE),
//    EXPORT_SOLDE_JOURNALIER("Exporter Solde Journalier", Feature.SOLDE_TRESORERIE),

    READ_BILAN("Consulter Bilan", Feature.BILAN_TRESORERIE),
//    ADD_BILAN("Ajouter Bilan", Feature.BILAN_TRESORERIE),
//    EDIT_BILAN("Modifier Bilan", Feature.BILAN_TRESORERIE),
    DELETE_BILAN("Supprimer Bilan", Feature.BILAN_TRESORERIE),
    IMPORT_BILAN("Importer Bilan", Feature.BILAN_TRESORERIE),
//    EXPORT_BILAN("Exporter Bilan", Feature.BILAN_TRESORERIE),
    VALIDATE_BILAN("Valider Bilan", Feature.BILAN_TRESORERIE),
    ALERTE_BILAN("Consulter alerte Bilan", Feature.BILAN_TRESORERIE),

    READ_CASH_FLOW("Consulter Cash Flow", Feature.CASH_FLOW),
//    ADD_CASH_FLOW("Ajouter Cash Flow", Feature.CASH_FLOW),
//    EDIT_CASH_FLOW("Modifier Cash Flow", Feature.CASH_FLOW),
    DELETE_CASH_FLOW("Supprimer Cash Flow", Feature.CASH_FLOW),
    IMPORT_CASH_FLOW("Importer Cash Flow", Feature.CASH_FLOW),
//    EXPORT_CASH_FLOW("Exporter Cash Flow", Feature.CASH_FLOW),
    VALIDATE_CASH_FLOW("Valider Cash Flow", Feature.CASH_FLOW),
    ALERTE_CASH_FLOW("Consulter alerte Cash Flow", Feature.CASH_FLOW),

    READ_COMPTE_RESULTAT("Consulter Compte de résultat", Feature.COMPTE_RESULTAT),
//    ADD_COMPTE_RESULTAT("Ajouter Compte de résultat", Feature.COMPTE_RESULTAT),
//    EDIT_COMPTE_RESULTAT("Modifier Compte de résultat", Feature.COMPTE_RESULTAT),
    DELETE_COMPTE_RESULTAT("Supprimer Compte de résultat", Feature.COMPTE_RESULTAT),
    IMPORT_COMPTE_RESULTAT("Importer Compte de résultat", Feature.COMPTE_RESULTAT),
//    EXPORT_COMPTE_RESULTAT("Exporter Compte de résultat", Feature.COMPTE_RESULTAT),
    VALIDATE_COMPTE_RESULTAT("Valider Compte de résultat", Feature.COMPTE_RESULTAT),
    ALERTE_COMPTE_RESULTAT("Consulter alerte Compte de résultat", Feature.COMPTE_RESULTAT),

    READ_WORKFLOW_HISTORIQUE("Consulter historique de validation", Feature.WORKFLOW),
    ADD_WORKFLOW_HISTORIQUE("Ajouter historique de validation", Feature.WORKFLOW),
    EDIT_WORKFLOW_HISTORIQUE("Modifier historique de validation", Feature.WORKFLOW),
    DELETE_WORKFLOW_HISTORIQUE("Supprimer historique de validation", Feature.WORKFLOW),

//    IMPORT_NSFR("Importer NSFR", Feature.NSFR),
//    EXPORT_NSFR("Exporter NSFR", Feature.NSFR),
    READ_DOCUMENT("Consulter document", Feature.DOCUMENT),
    ADD_DOCUMENT("Ajouter document", Feature.DOCUMENT),
    EDIT_DOCUMENT("Modifier document", Feature.DOCUMENT),
    DELETE_DOCUMENT("Supprimer document", Feature.DOCUMENT),

    //FOLDER
    READ_FOLDER("Consulter dossier", Feature.DOCUMENT),
    ADD_FOLDER("Ajouter dossier", Feature.DOCUMENT),
    EDIT_FOLDER("Modifier dossier", Feature.DOCUMENT),
    DELETE_FOLDER("Supprimer dossier", Feature.DOCUMENT),

    // HISTORIQUE VALIDATION WORKFLOW
    READ_HISTORIQUE_VALIDATION_WORKFLOW("Consulter l'historique de validations des workflow", Feature.WORKFLOW),
    DELETE_HISTORIQUE_VALIDATION_WORKFLOW("Supprimer l'historique de validations des workflow", Feature.WORKFLOW),
    ADD_WORKLOW_STEP("ajout etape l'historique de validations des workflow", Feature.WORKFLOW),

    READ_AGENT("Consulter liste des agents", Feature.AGENT),
    ADD_AGENT("Ajouter un agent", Feature.AGENT),
    EDIT_AGENT("Modifier un agent", Feature.AGENT),
    DELETE_AGENT("Supprimer un agent", Feature.AGENT),

    READ_USER("Consulter utilisateur", Feature.USER),
    ADD_USER("Ajouter utilisateur", Feature.USER),
    EDIT_USER("Modifier utilisateur", Feature.USER),
    DELETE_USER("Supprimer utilisateur", Feature.USER),
    UPDATE_PASSWORD("Modifier mot de passe des utilisateurs", Feature.USER),
    UPDATE_MY_PASSWORD("Modifier mon mot de passe", Feature.USER),

    READ_ALERTE("Consulter liste alerte", Feature.ALERTE),
    ADD_ALERTE("Ajouter alerte", Feature.ALERTE),
    EDIT_ALERTE("Modifier alerte", Feature.ALERTE),
    DELETE_ALERTE("Supprimer alerte", Feature.ALERTE),

    READ_CALCUL_LCR_NSFR_ALERTE("Consulter alerte sur les calcule", Feature.ALERTE),
    ADD_CALCUL_LCR_NSFR_ALERTE("Ajouter alerte sur les calcule", Feature.ALERTE),
    EDIT_CALCUL_LCR_NSFR_ALERTE("Modifier alerte sur les calcule", Feature.ALERTE),
    DELETE_CALCUL_LCR_NSFR_ALERTE("Supprimer alerte sur les calcule", Feature.ALERTE),

    READ_WORKFLOW("Consulter workflow", Feature.WORKFLOW),

    READ_PROFIL("Consulter profil", Feature.PROFIL),
    CONFIGURE_PROFIL("Configurer profil", Feature.PROFIL),
    ADD_PROFIL("Ajouter profil", Feature.PROFIL),
    EDIT_PROFIL("Modifier profil", Feature.PROFIL),
    DELETE_PROFIL("Supprimer profil", Feature.PROFIL),
    READ_JOURNAL("Consulter journal", Feature.JOURNAL),

    ALL_ACCESS("Toutes les permissions", Feature.ALL_ACCESS),
    READ_RATIO_PRUDENTIEL_LCR("Consulter ratio prutentiel lcr",Feature.RATIO_PRUDENTIEL_LCR),
    READ_RATIO_PRUDENTIEL_NSFR("Consulter ratio prutentiel Nsfr",Feature.RATIO_PRUDENTIEL_NSFR),
    READ_RATIO_PRUDENTIEL_LCR_STRESSE("Consulter ratio prutentiel lcr stresse",Feature.RISQUE_PRUDENTIEL_LCR_STRESSE),
    RISQUE_PRUDENTIEL_NSFR_STRESSE("Consulter ratio prutentiel Nsfr stresse",Feature.RISQUE_PRUDENTIEL_NSFR_STRESSE),
    RISQUE_FINANCIER_TMC("Consulter tresorerie de couverture",Feature.RISQUE_FINANCIER_TMC),
    READ_RISQUE_FINANCIER_TMC("Consulter tresorerie de couverture",Feature.RISQUE_FINANCIER_TMC),
    READ_VALIDATION_DONNEE("Consulter validation donnee pour calcul",Feature. PARAMETRE_DONNEE_VALIDE),
    EDIT_VALIDATION_DONNEE("Modifier validation donnee pour calcul",Feature. PARAMETRE_DONNEE_VALIDE);


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
