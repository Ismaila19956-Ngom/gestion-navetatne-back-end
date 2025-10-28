package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum TypeLigneBugetaire {
    CLASSE_6( "ligne bugetaire classe 6" ),
    CLASSE_7( "ligne bugetaire classe 7"),
    CLASSE_2( "ligne bugetaire classe 2"),
    CLASSE_1( "ligne bugetaire classe 1");

    @Getter
    private final String description;

    TypeLigneBugetaire(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeLigneBugetaire fromValue(Object detailType) {
        if (detailType instanceof Map) {
            Map<String, Object> mapDetailType = (Map<String, Object>) detailType;
            if (mapDetailType.containsKey("name")) {
                return TypeLigneBugetaire.valueOf(mapDetailType.get("name").toString());
            }
        }
        if (detailType instanceof String) {
            return TypeLigneBugetaire.valueOf(detailType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeLigneBugetaire.class, detailType, values()));
    }
    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
