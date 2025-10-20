package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum CategoryDocument {
//    PROGRAMME("Programmes", "Paramétrage des types de document pour les programmes"),
//    PROJECT("Projets", "Paramétrage des types de document pour les projets"),
//     ACTIVITY("Activités", "Paramétrage des types de document pour les activités"),
    RH("Ressources Humaines", "Paramétrage des types de document pour les Ressources Humaines"),
//    ENTREPRISE("Entreprises", "Paramétrage des types de document pour les entreprises"),
    AVIS_PROJET("Avis de Projets", "Paramétrage des types de document pour les avis de projets"),
    SUIVI_SURVEILLANCE("Suivi Surveillance", "Paramétrage des types de document pour les suivi surveillance"),
    AGREMENT("Agréments", "Paramétrage des types de document pour les agréments"),
    TDR("Termes de Référence (TDR)", "Paramétrage des types de document pour les TDR");



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
