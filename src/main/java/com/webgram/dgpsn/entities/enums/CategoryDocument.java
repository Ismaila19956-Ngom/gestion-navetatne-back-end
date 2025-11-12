package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum CategoryDocument {

//    ENTREPRISE("Entreprises", "Paramétrage des types de document pour les entreprises"),
    PROJECT("Document Projet", "Paramétrage des types de document pour les Projet"),
    ORDRE_DE_MISSION_DOCUMENT("Document Ordre de mission", "Paramétrage des types de documents pour les ordres de mission"),
//    TYPE_DOCUMENT("Type Document", "Paramétrage des types de documents"),
    ADMINISTRATIF("Document Conge Annuel", "Paramétrage des types de documents pour les congés Annuel"),
    MATERNITE("Document Conge maternite", "Paramétrage des types de documents pour les congés de maternite"),
    MALADIE("Document Conge Absence", "Paramétrage des types de documents pour les congés Absence"),
    COURRIER("Document Courrier", "Paramétrage des types de documents pour les courriers"),

    AUTRES("Document Absence", "Paramétrage des types de documents pour lesAbsence");



    @Getter
    private final String label;
    @Getter
    private final String description;


    CategoryDocument(String label, String description) {
        this.label = label;
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CategoryDocument fromValue(Object categoryDocument) {
        if (categoryDocument instanceof Map) {
            Map<String, Object> mapCategoryDocument = (Map<String, Object>) categoryDocument;
            if (mapCategoryDocument.containsKey("name")) {
                return CategoryDocument.valueOf(mapCategoryDocument.get("name").toString());
            }
        }
        if (categoryDocument instanceof String) {
            return CategoryDocument.valueOf(categoryDocument.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", CategoryDocument.class, categoryDocument, values()));
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
