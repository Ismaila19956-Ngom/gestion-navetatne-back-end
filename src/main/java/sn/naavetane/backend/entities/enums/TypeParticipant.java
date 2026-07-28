package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum TypeParticipant {

    FORMATIONEXTERIEUR("Formation extérieure"),

            ATELIER("Atelier");

    @Getter
    private final String description;

    TypeParticipant(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeParticipant fromValue(Object typeParticipant) {
        if (typeParticipant instanceof Map) {
            Map<String, Object> mapTypeParticipant = (Map<String, Object>) typeParticipant;
            if (mapTypeParticipant.containsKey("name")) {
                return TypeParticipant.valueOf(mapTypeParticipant.get("name").toString());
            }
        }
        if (typeParticipant instanceof String) {
            return TypeParticipant.valueOf(typeParticipant.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format(
                "{0} not found with the value: {1} in [{2}]",
                TypeParticipant.class, typeParticipant, values()
        ));
    }

    @JsonValue
    Map<String, Object> getTypeParticipant() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}
