package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum TypeProjet {
    PROGRAMME("Programme"),
    PROJECT("Projet"),
    OBJECTIF("Objectif"),
    ACTION("Action"),
    ACTIVITY("Activité");

    @Getter
    @Setter
    private String description;

    TypeProjet(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeProjet fromValue(Object typeProjet) {
        if (typeProjet instanceof Map) {
            Map<String, Object> mapTypeProjet = (Map<String, Object>) typeProjet;
            if (mapTypeProjet.containsKey("name")) {
                return TypeProjet.valueOf(mapTypeProjet.get("name").toString());
            }
        }
        if (typeProjet instanceof String) {
            return TypeProjet.valueOf(typeProjet.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Formula.class, typeProjet, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypeProjet> getAllTypeProjets() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
