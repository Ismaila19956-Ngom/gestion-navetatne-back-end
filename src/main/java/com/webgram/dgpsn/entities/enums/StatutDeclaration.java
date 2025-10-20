package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;

import static java.util.Arrays.stream;

public enum StatutDeclaration {
    EN_COURS("En cours"),
    TRAITEE("Traitée"),
    REJETEE("Rejetée");

    @Getter
    @Setter
    private String description;

    StatutDeclaration(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatutDeclaration fromValue(Object priority) {
        if (priority instanceof Map) {
            Map<String, Object> mapPriority = (Map<String, Object>) priority;
            if (mapPriority.containsKey("name")) {
                return StatutDeclaration.valueOf(mapPriority.get("name").toString());
            }
        }
        if (priority instanceof String) {
            return StatutDeclaration.valueOf(priority.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", StatutDeclaration.class, priority, values()));
    }

    @JsonValue
    Map<String, Object> getStatutDeclaration() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
