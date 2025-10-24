package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

public enum Module {
    DASHBOARD("Tableau de bord"),
    PROJECT_LIST("Liste projets"),
    PROJECT_SETTINGS("Paramétrage"),
    ORDRE_MISSION("Ordre de mission"),
    FORMULAIRE("Gestion des procédures"),
    FINANCEMENT_SETTINGS("Financement"),
    ACTES_GESTION("Gestion Administrative"),
    PROJECT_DETAILED_PROGRESS_TRACKING("Avancement détaillé"),
    PROJECT_ACTIVITY_MONITORING("Suivi des activités"),
    PROGRAMME("Programme"),
    ACTIVITY_SETTINGS("Suivi des activités"),
    SITE_STATION("Sites et stations"),
    SETTINGS_ACTIVITE_MISSION("Suivi des mission"),
    REFERENTIEL("Référentiel"),
    ALERTE("Alerte"),
    DOCUMENT("Document"),
    ORGANIGRAMMER("Organigramme"),
    SECURITY("Sécurité"),
    COURRIER("Courrier");


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

    //    public static Set<Module> readModules() {
//        return Arrays.stream(values())
//                    .collect(Collectors.toSet());
//    }
    public static Set<Module> readModules() {
        return Arrays.stream(values())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
