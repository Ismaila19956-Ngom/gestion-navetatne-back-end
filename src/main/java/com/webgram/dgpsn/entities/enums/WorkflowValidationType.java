package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;

public enum WorkflowValidationType {
    INDIVIDUELLE("Individuelle"),
    COMMUNE("Commune");


    @Getter
    @Setter
    private String description;

    WorkflowValidationType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static WorkflowValidationType fromValue(Object validationType) {
        if (validationType instanceof Map) {
            Map<String, Object> mapValidationType = (Map<String, Object>) validationType;
            if (mapValidationType.containsKey("name")) {
                return WorkflowValidationType.valueOf(mapValidationType.get("name").toString());
            }
        }
        if (validationType instanceof String) {
            return WorkflowValidationType.valueOf(validationType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", WorkflowValidationType.class, validationType, values()));
    }

    @JsonValue
    Map<String, Object> getWorkflowValidationType() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

}
