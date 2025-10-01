package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Map;

public enum CategoryDocument {
    DOCUMENT_GENERAL("Document general", "Paramétrage gestion documentaire"),
    DOCUMENT_NORME("Document specifique ", "Paramétrage document specifique sur les normes"),
    DOCUMENT_RATIO_BALE("Document specifique ", "Paramétrage document specifique sur les ratio"),
    DOCUMENT_NORME_IFRS("Document specifique ", "Paramétrage document specifique sur les norme ifrs");


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
