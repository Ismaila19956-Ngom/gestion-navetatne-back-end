package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

public enum StatutCourrier {
    // Pour courriers arrivés
    NON_TRAITE("Non traité"),
    EN_COURS("En cours de traitement"),
    TRAITE("Traité"),

    // Pour courriers départ
    BROUILLON("Brouillon"),
    EN_PREPARATION("En préparation"),
    ENVOYE("Envoyé"),
    ANNULE("Annulé"),

    // Commun
    ARCHIVE("Archivé");

    @Getter
    private final String description;

    StatutCourrier(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatutCourrier fromValue(Object statutType) {
        if (statutType instanceof Map) {
            Map<String, Object> mapStatutType = (Map<String, Object>) statutType;
            if (mapStatutType.containsKey("name")) {
                String name = mapStatutType.get("name").toString();
                return Arrays.stream(StatutCourrier.values())
                        .filter(statut -> statut.name().equals(name))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Statut non trouvé: {0}", name)));
            }
        }
        if (statutType instanceof String) {
            String name = statutType.toString();
            return Arrays.stream(StatutCourrier.values())
                    .filter(statut -> statut.name().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Statut non trouvé: {0}", name)));
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", StatutCourrier.class, statutType, Arrays.toString(values())));
    }

    @JsonValue
    public Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }
}