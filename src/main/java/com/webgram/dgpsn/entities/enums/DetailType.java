package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum DetailType {
    IMPACT( "Impact attendu"),
    OBJECTIVE( "Objectif spécifique");


    @Getter
    private final String description;

    DetailType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DetailType fromValue(Object detailType) {
        if (detailType instanceof Map) {
            Map<String, Object> mapDetailType = (Map<String, Object>) detailType;
            if (mapDetailType.containsKey("name")) {
                return DetailType.valueOf(mapDetailType.get("name").toString());
            }
        }
        if (detailType instanceof String) {
            return DetailType.valueOf(detailType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", DetailType.class, detailType, values()));
    }
    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
