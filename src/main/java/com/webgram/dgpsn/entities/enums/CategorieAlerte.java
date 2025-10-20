package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum CategorieAlerte {
//    PROJET("Activite"),
    EVALUATION_ENVIRONNEMENTAL("Evaluation Environmental"),
    GESTION_POLLUTION("Gestions pollution");
//    QUALITE_AIR("Gestions qualite air"),
//    INSPECTION_ICPE("Gestions inspection icpe"),
//    URGENCE_ENVIRONNEMENTAL("Urgence Environmental"),
//    FORMULAIRE_GENERAL("Formulaire General"),
//    DIRECTION_REGIONAL("Direction Regional"),
//    EVALUATION_STARTUP("Evaluation Startup");

    @Getter
    @Setter
    String description;


    CategorieAlerte(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CategorieAlerte fromValue(Object categorieAlerte) {
        if (categorieAlerte instanceof Map) {
            Map<String, Object> mapTag = (Map<String, Object>) categorieAlerte;
            if (mapTag.containsKey("name")) {
                return CategorieAlerte.valueOf(mapTag.get("name").toString());
            }
        }
        if (categorieAlerte instanceof String) {
            return CategorieAlerte.valueOf(categorieAlerte.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", CategorieAlerte.class, categorieAlerte, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description

        );
    }

    public static Set<CategorieAlerte> getAllCategorieAlerte() {
        return Arrays.stream(values()).collect(Collectors.toSet());
    }
}
