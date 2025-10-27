package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TypePlanComptable {
    CLASSE("Classe"),
    COMPTE("Compte"),
    SOUS_COMPTE("Sous Compte"),
    RUBRIQUE("Rubrique"),
    REALISATION("Realisation");

    private final String description;

    TypePlanComptable(String description) {
        this.description = description;
    }

    @JsonCreator
    public static TypePlanComptable fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        // Essayer de trouver par nom (insensible à la casse)
        for (TypePlanComptable type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        // Essayer de trouver par description (insensible à la casse)
        for (TypePlanComptable type : values()) {
            if (type.getDescription().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Valeur inconnue pour TypePlanComptable: " + value +
                ". Valeurs acceptées : " + getAcceptedValues());
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    private static String getAcceptedValues() {
        StringBuilder sb = new StringBuilder();
        for (TypePlanComptable type : values()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(type.name()).append(" (").append(type.getDescription()).append(")");
        }
        return sb.toString();
    }
}