package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum Module {
    DASHBOARD("Tableau de bord"),
    ACTES_GESTION("Gestion Administrative"),
    PROJECT_LIST("Gestion des projets"),
    GESTION_COURRIER("Gestion courrier"),
    FINANCEMENT_SETTINGS("Gestion Financière"),
    DOCUMENT("Gestion Documentaires"),
    ORGANIGRAMMER("Organigramme"),
    PARAMETRAGE("Parametrage"),
    ALERTE("Alerte"),
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

    //    public static Set<Module> readModules() {
//        return Arrays.stream(values())
//                    .collect(Collectors.toSet());
//    }
    public static Set<Module> readModules() {
        return Arrays.stream(values())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
