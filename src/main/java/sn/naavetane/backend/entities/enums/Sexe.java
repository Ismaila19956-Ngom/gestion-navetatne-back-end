package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;

public enum Sexe {
    MASCULIN("masculin"),
    FEMININ("feminin");

    @Getter
    @Setter
    private String description;

    Sexe(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Sexe fromValue(Object statut) {
        if (statut instanceof Map) {
            Map<String, Object> mapStatut = (Map<String, Object>) statut;
            if (mapStatut.containsKey("name")) {
                return Sexe.valueOf(mapStatut.get("name").toString());
            }
        }
        if (statut instanceof String) {
            return Sexe.valueOf(statut.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Sexe.class, statut, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
            "name", name(),
            "description", description
        );
    }
}
