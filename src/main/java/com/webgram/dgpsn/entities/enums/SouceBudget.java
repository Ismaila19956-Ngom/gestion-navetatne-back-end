package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

import static java.util.Arrays.stream;

public enum SouceBudget {

    PRIVEE("Privée"),
    PUBLIC("Public"),
    PPP("PPP");

    @Getter
    private final String description;

    SouceBudget(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SouceBudget fromValue(Object SouceType) {
        if (  SouceType instanceof Map) {
            Map<String, Object> mapSouceType = (Map<String, Object>)   SouceType;
            if (mapSouceType.containsKey("name")) {
                return SouceBudget.valueOf(mapSouceType.get("name").toString());
            }
        }
        if (  SouceType instanceof String) {
            return SouceBudget.valueOf(  SouceType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", SouceBudget.class,   SouceType, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

}
