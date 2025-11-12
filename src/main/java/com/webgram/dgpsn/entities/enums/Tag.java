package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.text.MessageFormat;
import java.util.Map;

public enum Tag {
    AGENT,
    NUMERO_REFERENCE,
    SECTEUR_ACTIVITE,
    TITRE_PROJET,
    LIBELLE,
    DATE,
    TYPE_PROJET,
    DATE_VISITE,
    PROMATEUR,
    MONTANT_BUDGET,
    MONTANT_LIGNE_BUDGET,
    RUBRIQUE,
    TYPE_RECRUTEMENT,
    TYPE_CONTRAT,
    COMPTE,
    NUMERO_BON,
    NUMERO_BE,
    NUMERO_MANDAT,
    NUMERO_FACTURE,
    MONTANT_DEPENSE,
    NOM,
    PRENOM,
    DATE_DEBUT,
    DATE_FIN,
    ANNEE,
    /// ///courrier////

    TYPE_COURRIER,
    NUMERO_COURRIER,
    NATURE_COURRIER,
    DATE_RECEPTION,
    MODE_ENVOI,
    ARCHIVE,
    MOYEN_TRANSPORT,
    /// ////conge demande////
    DEMANDE_CONGE,
    TYPE_CONGE,
    NOMBRE_JOUR_CESSATION,
    DUREE,
    PERSONNE_EN_CONGE,
    DATE_CESSATION;


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
