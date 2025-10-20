package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum Period {
    ANNEE("Année", Periodicity.ANNUELLE),
    S1("Semestre 1", Periodicity.SEMESTRIELLE),
    S2("Semestre 2", Periodicity.SEMESTRIELLE),
    T1("Trimestre 1", Periodicity.TRIMESTRIELLE),
    T2("Trimestre 2", Periodicity.TRIMESTRIELLE),
    T3("Trimestre 3", Periodicity.TRIMESTRIELLE),
    T4("Trimestre 4", Periodicity.TRIMESTRIELLE),
    JANVIER("Janvier", Periodicity.MOISUELLE),
    FEVRIER("Février", Periodicity.MOISUELLE),
    MARS("Mars", Periodicity.MOISUELLE),
    AVRIL("Avril", Periodicity.MOISUELLE),
    MAI("Mai", Periodicity.MOISUELLE),
    JUIN("Juin", Periodicity.MOISUELLE),
    JUILLET("Juillet", Periodicity.MOISUELLE),
    AOUT("Août", Periodicity.MOISUELLE),
    SEPTEMBRE("Septembre", Periodicity.MOISUELLE),
    OCTOBRE("Octobre", Periodicity.MOISUELLE),
    NOVEMBRE("Novembre", Periodicity.MOISUELLE),
    DECEMBRE("Decembre", Periodicity.MOISUELLE);

    @Getter
    private final String libelle;
    @Getter
    private final Periodicity periodicity;

    Period(String libelle, Periodicity periodicity) {
        this.libelle = libelle;
        this.periodicity = periodicity;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Period fromValue(Object periodicity) {
        if (periodicity instanceof Map) {
            Map<String, Object> mapPeriod = (Map<String, Object>) periodicity;
            if (mapPeriod.containsKey("name")) {
                return Period.valueOf(mapPeriod.get("name").toString());
            }
        }
        if (periodicity instanceof String) {
            return Period.valueOf(periodicity.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Period.class, periodicity, values()));
    }

    @JsonValue
    Map<String, Object> getPeriod() {
        return Map.of(
                "name", name(),
                "libelle", libelle,
                "periodicity", this.periodicity
        );
    }

}
