package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum CadreLogiqueType {

    NATIONAL("National"),
    REGION("Région"),
    DEPARTEMENT("Département"),
    ARRONDISSEMENT("Arrondissement"),
    COMMUNE("Commune");

    @Getter
    private final String description;

    CadreLogiqueType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CadreLogiqueType fromValue(Object cadreLogiqueType) {
        if (cadreLogiqueType instanceof Map) {
            Map<String, Object> mapStatusType = (Map<String, Object>) cadreLogiqueType;
            if (mapStatusType.containsKey("name")) {
                return CadreLogiqueType.valueOf(mapStatusType.get("name").toString());
            }
        }
        if (cadreLogiqueType instanceof String) {
            return CadreLogiqueType.valueOf(cadreLogiqueType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", CadreLogiqueType.class, cadreLogiqueType, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
