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

public enum     StatutType {
    TRAITEMENT_ENCOUR("En cours..."),
    ACCEPTER("Accepté"),
    REFUSER("Rejeté"),
    FERMER("Fermé");


    @Getter
    @Setter
    private String description;

    StatutType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatutType fromValue(Object typeConge) {
        if (typeConge instanceof Map) {
            Map<String, Object> mapTypeConge = (Map<String, Object>) typeConge;
            if (mapTypeConge.containsKey("name")) {
                return StatutType.valueOf(mapTypeConge.get("name").toString());
            }
        }
        if (typeConge instanceof String) {
            return StatutType.valueOf(typeConge.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", StatutType.class, typeConge, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<StatutType> getAllSexe() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
