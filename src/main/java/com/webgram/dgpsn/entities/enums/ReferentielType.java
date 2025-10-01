package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum ReferentielType {
    BILAN_CONFIG("Bilan", "Paramétrage bilan"),
    SOLDE_JOURNALIER_CONFIG("Solde journalier", "Paramétrage Solde journalier"),
    HQLAS_CONFIG("Hqlas", "Paramétrage Hqlas"),
    BILAN_CATEGORY("Categorie bilan", "Paramétrage catégorie bilan"),
    COMPTE_RESULTAT_CONFIG("Compte de résultat", "Paramétrage compte de résultat");

    @Getter
    private final String label;
    @Getter
    private final String description;

    ReferentielType(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ReferentielType fromValue(Object referentielType) {
        if (referentielType instanceof Map) {
            Map<String, Object> mapReferentielType = (Map<String, Object>) referentielType;
            if (mapReferentielType.containsKey("name")) {
                return ReferentielType.valueOf(mapReferentielType.get("name").toString());
            }
        }
        if (referentielType instanceof String) {
            return ReferentielType.valueOf(referentielType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", ReferentielType.class, referentielType, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "label", label,
                "description", description
        );
    }
}
