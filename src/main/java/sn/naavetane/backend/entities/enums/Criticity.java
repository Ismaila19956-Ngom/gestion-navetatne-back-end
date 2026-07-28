package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum Criticity {
    ELEVE(50, "Elevé"),
    MOYEN(35, "Moyen"),
    FAIBLE(15, "Faible");

    @Getter
    private final long poids;
    @Getter
    private final String description;

    Criticity(long poids, String description) {
        this.poids = poids;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Criticity fromValue(Object criticity) {
        if (criticity instanceof Map) {
            Map<String, Object> mapCriticity = (Map<String, Object>) criticity;
            if (mapCriticity.containsKey("name")) {
                return Criticity.valueOf(mapCriticity.get("name").toString());
            }
        }
        if (criticity instanceof String) {
            return Criticity.valueOf(criticity.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Criticity.class, criticity, values()));
    }
    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "poids", poids,
                "description", description
        );
    }
}
