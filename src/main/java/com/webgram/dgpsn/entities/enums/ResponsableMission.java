package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;

public enum ResponsableMission {
    DGPSN("DGPSN"),
    PAPSA("PAPSA");

    @Getter
    @Setter
    private String description;

    ResponsableMission(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ResponsableMission fromValue(Object actionType) {
        if (actionType instanceof Map) {
            Map<String, Object> mapActionType = (Map<String, Object>) actionType;
            if (mapActionType.containsKey("name")) {
                return ResponsableMission.valueOf(mapActionType.get("name").toString());
            }
        }
        if (actionType instanceof String) {
            return ResponsableMission.valueOf(actionType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", ResponsableMission.class, actionType, values()));
    }

    @JsonValue
    Map<String, Object> getActionType() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

}
