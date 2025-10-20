package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum Periodicity {
    TRIMESTRIELLE("Trimestrielle"),
    SEMESTRIELLE("Semestrielle"),
    ANNUELLE("Annuelle"),
    MOISUELLE("Mensuelle"),
    JOURNALIER("Journalier");

    @Getter
    private final String libelle;

    Periodicity(String libelle) {
        this.libelle = libelle;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Periodicity fromValue(Object periodicity) {
        if (periodicity instanceof Map) {
            Map<String, Object> mapPeriodicity = (Map<String, Object>) periodicity;
            if (mapPeriodicity.containsKey("name")) {
                return Periodicity.valueOf(mapPeriodicity.get("name").toString());
            }
        }
        if (periodicity instanceof String) {
            return Periodicity.valueOf(periodicity.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Periodicity.class, periodicity, values()));
    }

    @JsonValue
    Map<String, Object> getPeriodicity() {
        return Map.of(
                "name", name(),
                "libelle", libelle
        );
    }

}
