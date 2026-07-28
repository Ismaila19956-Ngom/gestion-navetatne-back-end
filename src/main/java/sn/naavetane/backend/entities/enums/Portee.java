package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum Portee {
    TOUT("Tout"),
    MES_DONNEES_UNIQUEMENT("Mes données uniquement"),
    MES_DONNEES_DONNEES_SUBORDONEES_DIRECT("Mes données et celles de mes subordonnées directs"),
    MES_DONNEES_DONNEES_AGENTS_MON_UNITE("Mes données et celles des agents de mon unité"),
    MES_DONNEES_DONNEES_SUBORDONEES("Mes données et celles de tous mes subordonnées");

    @Getter
    @Setter
    private String description;

    Portee(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Portee fromValue(Object portee) {
        if (portee instanceof Map) {
            Map<String, Object> mapPortee = (Map<String, Object>) portee;
            if (mapPortee.containsKey("name")) {
                return Portee.valueOf(mapPortee.get("name").toString());
            }
        }
        if (portee instanceof String) {
            return Portee.valueOf(portee.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Portee.class, portee, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<Portee> getAllPriorities() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
