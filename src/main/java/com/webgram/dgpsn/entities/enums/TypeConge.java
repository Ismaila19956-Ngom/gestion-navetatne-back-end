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

public enum TypeConge {
    ADMINISTRATIF("Congé Administratif"),
    MATERNITE("Congé de Maternité"),
    MALADIE("Congé Maladie"),
    CESSATION("Cessation de Service"),
    AUTRES("Congé longue durée ");


    @Getter
    @Setter
    private String description;

    TypeConge(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeConge fromValue(Object typeConge) {
        if (typeConge instanceof Map) {
            Map<String, Object> mapTypeConge = (Map<String, Object>) typeConge;
            if (mapTypeConge.containsKey("name")) {
                return TypeConge.valueOf(mapTypeConge.get("name").toString());
            }
        }
        if (typeConge instanceof String) {
            return TypeConge.valueOf(typeConge.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeConge.class, typeConge, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypeConge> getAllSexe() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
