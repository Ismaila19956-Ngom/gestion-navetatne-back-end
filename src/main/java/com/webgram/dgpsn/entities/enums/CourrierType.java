package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum CourrierType {
    ARRIVER("courrier arriver"),
    DEPART("courrier depart");

    @Getter
    private final String description;

    CourrierType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CourrierType fromValue(Object value) {
        if (value instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) value;
            Object name = map.get("name");
            if (name != null) {
                return CourrierType.valueOf(name.toString());
            }
        }
        if (value instanceof String) {
            String stringValue = value.toString();
            try {
                return CourrierType.valueOf(stringValue);
            } catch (IllegalArgumentException e) {
                // Si la valeur string ne correspond pas directement, chercher par description
                for (CourrierType type : values()) {
                    if (type.description.equalsIgnoreCase(stringValue) || type.name().equalsIgnoreCase(stringValue)) {
                        return type;
                    }
                }
            }
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1}", CourrierType.class.getSimpleName(), value));
    }

    @JsonValue
    public Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}