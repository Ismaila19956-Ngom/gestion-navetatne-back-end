package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;

public enum StatutType {
    NON_CONFORME("Non conforme"),
    COMFORME("Conforme"),
    EN_COURS("En cours"),
    TRAITEMENT_ENCOUR("En cours..."),
    ACCEPTER("Accepté"),
    REFUSER("Rejeté"),
    REJETER("Refusé"),

    FERMER("Fermé");

    @Getter
    @Setter
    private String description;

    StatutType(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatutType fromValue(Object actionType) {
        if (actionType instanceof Map) {
            Map<String, Object> mapActionType = (Map<String, Object>) actionType;
            if (mapActionType.containsKey("name")) {
                return StatutType.valueOf(mapActionType.get("name").toString());
            }
        }
        if (actionType instanceof String) {
            return StatutType.valueOf(actionType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", StatutType.class, actionType, values()));
    }

    @JsonValue
    Map<String, Object> getActionType() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

}
