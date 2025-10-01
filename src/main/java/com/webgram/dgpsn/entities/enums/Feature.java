package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public enum Feature {
    DASHBOARD("Tableau de bord", Module.DASHBOARD),
    RAPPORT("Rapport", Module.DASHBOARD),

    SOLDE_TRESORERIE("Solde Trésorerie", Module.ACTIF_PASSIF),
    BILAN_TRESORERIE("Bilan", Module.ACTIF_PASSIF),
    COMPTE_RESULTAT("Compte de résultat", Module.ACTIF_PASSIF),
    CASH_FLOW("Cash Flow", Module.ACTIF_PASSIF),

    // Parametre
//    QUANTILE_CONFIQ("Quantile",Module.PARAMETRE),
//    PARAMETRE_HQLA("HQLA",Module.PARAMETRE),
    PARAMETRE_PERIODE("Période",Module.PARAMETRE),
    PARAMETRE_SEUIL("Seuil",Module.PARAMETRE),
    PARAMETRE_DONNEE_VALIDE("Validaton donnee",Module.PARAMETRE),
    SOLDE_TRESSORERIE_PARAMETRE("Solde Trésorerie", Module.PARAMETRE),
    DISPLAY_CONFIG_SOLDE_JOURNALIER("Configuration affichage Solde journalier", Module.PARAMETRE),
    BILAN_TRESORERIE_PARAMETRE("Bilan", Module.PARAMETRE),
    COMPTE_RESULTAT_PARAMETRE("Compte de résultat", Module.PARAMETRE),
    RATIO_PARAMETRE_LCR("Ratio --> LCR", Module.PARAMETRE),
    PARAMETRE_NSFR("Ratio --> NSFR", Module.PARAMETRE),
//    RATIO_PARAMETRE_TMC("Ratio --> Tresorie en mois de couverture", Module.PARAMETRE),
    QUANTILE_PARAMETRE("Quantile", Module.PARAMETRE),

    DOCUMENT("Document", Module.DOCUMENT),

    ORGANIGRAMME("Organigramme", Module.ORGANIGRAMME),

    AGENT("Agent", Module.UTILISATEUR),
    USER("Utilisateur", Module.UTILISATEUR),

    ALERTE("Alerte", Module.SECURITY),
    WORKFLOW("Workflow", Module.SECURITY),
    PROFIL("Profils", Module.SECURITY),
    JOURNAL("Journal", Module.SECURITY),

//    RESULTAT_INDICATEUR("Résultat Indicateur", Module.RESULTAT_INDICATEUR),
    RATIO_PRUDENTIEL_LCR("Ratios prudentiels --> LCR", Module.RESULTAT_INDICATEUR),
    RATIO_PRUDENTIEL_NSFR("Ratios prudentiels --> NSFR", Module.RESULTAT_INDICATEUR),
    RISQUE_FINANCIER_TMC("Risque financier ALM --> Tresorie en mois de couverture", Module.RESULTAT_INDICATEUR),
    RISQUE_PRUDENTIEL_LCR_STRESSE("Ratios prudentiels --> LCR_STRESSE", Module.RESULTAT_INDICATEUR),
    RISQUE_PRUDENTIEL_NSFR_STRESSE("Ratios prudentiels --> NSFR_STRESSE", Module.RESULTAT_INDICATEUR),
    ALL_ACCESS("Toutes les permissions", Module.SECURITY);


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
                    .collect(Collectors.toSet());
        }
        return Arrays.stream(values())
                .filter(securityPermissions -> !securityPermissions.equals(Feature.ALL_ACCESS))
                .filter(securityPermissions -> securityPermissions.module.name().equals(module))
                .collect(Collectors.toSet());
    }

    public static Set<Feature> getAllByModule(String name) {
        return Arrays.stream(values())
                .filter(securityPermissions -> securityPermissions.module.name().equals(name))
                .collect(Collectors.toSet());
    }
}