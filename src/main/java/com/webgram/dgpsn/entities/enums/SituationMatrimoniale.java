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

public enum SituationMatrimoniale {
    CELIBATAIRE("Célibataire"),
    MARIE("Marié(e)");

    @Getter
    @Setter
    private String description;

    SituationMatrimoniale(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SituationMatrimoniale fromValue(Object typeUnite) {
        if (typeUnite instanceof Map) {
            Map<String, Object> mapTypeUnite = (Map<String, Object>) typeUnite;
            if (mapTypeUnite.containsKey("name")) {
                return SituationMatrimoniale.valueOf(mapTypeUnite.get("name").toString());
            }
        }
        if (typeUnite instanceof String) {
            return SituationMatrimoniale.valueOf(typeUnite.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", SituationMatrimoniale.class, typeUnite, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<SituationMatrimoniale> getAllFamilyRelashionShip() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
