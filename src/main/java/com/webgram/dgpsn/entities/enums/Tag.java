package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.text.MessageFormat;
import java.util.Map;

public enum Tag {
    TYPE_DEMANDE,
    DATE_INSPECTION,
    REFERENCE_INSPECTION,
    AGENT,
    NUMERO_REFERENCE,
    SECTEUR_ACTIVITE,
    TITRE_PROJET,
    LiBELLE,
    DATE_MESURE,
    POLLUANT_PRINCIPAL,
    NOM_ENTREPRISE,
    DATE_PRELEVEMENT,
    POINT_PRELEVEMENT,
    DESTINATION,
    ORIGINE,
    DATE_TRANSPORT,
    PRODUIT,
    QUANTITE,
    DATE_MOUVEMENT,
    NUMERO_RCCM,
    DATE,
    TYPE_PROJET,
    DATE_VISITE,
    PROMATEUR,
    REFERENCE,
    DATE_RECEPTION,
    MONTANT_BUDGET,
    LIGNE_BUDGET,
    MONTANT_DEPENSE,
    NOM,
    PRENOM,
    DATE_DEBUT,
    DATE_FIN,
    ANNEE;


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Tag fromValue(Object tag) {
        if (tag instanceof Map) {
            Map<String, Object> mapTag = (Map<String, Object>) tag;
            if (mapTag.containsKey("name")) {
                return Tag.valueOf(mapTag.get("name").toString());
            }
        }
        if (tag instanceof String) {
            return Tag.valueOf(tag.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", Tag.class, tag, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name()
        );
    }
}
