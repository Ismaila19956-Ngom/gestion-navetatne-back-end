package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum Module {
    DASHBOARD("Tableau de bord"),
    ACTIF_PASSIF("ACTIF/PASSIF"),
    DOCUMENT("Gestion Documentaire"),
    RESULTAT_INDICATEUR("Résultat Indicateur"),
    PARAMETRE("Paramétrage"),
    ORGANIGRAMME("Organigramme"),
    UTILISATEUR("Utilisateur"),
    SECURITY("Sécurité");

    @Getter
    @Setter
    String description;

    Module(String description) {
        this.description = description;
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<Module> readModules() {
        return Arrays.stream(values())
                .collect(Collectors.toSet());
    }
}