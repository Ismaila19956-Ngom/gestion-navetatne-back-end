package sn.naavetane.backend.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import java.text.MessageFormat;

public enum StatutFournisseur {
    ACTIF("Actif"),
    INACTIF("Inactif"),
    SUSPENDU("Suspendu"),
    BLOQUE("Bloqué");

    @Getter @Setter private String description;

    StatutFournisseur(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatutFournisseur fromValue(Object statutType) {
        if (statutType instanceof String) {
            return StatutFournisseur.valueOf(statutType.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1}", StatutFournisseur.class, statutType));
    }
}
