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

public enum Priority {
    BASSE("Basse"),
    HAUTE("Haute"),
    MOYENNE("Moyenne");

    @Getter
    @Setter
    private String description;

    Priority(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Priority fromValue(Object priority) {
        if (priority instanceof Map) {
            Map<String, Object> mapPriority = (Map<String, Object>) priority;
            if (mapPriority.containsKey("name")) {
                return Priority.valueOf(mapPriority.get("name").toString());
            }
        }
        if (priority instanceof String) {
            return Priority.valueOf(priority.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Priority.class, priority, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<Priority> getAllPriorities() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
