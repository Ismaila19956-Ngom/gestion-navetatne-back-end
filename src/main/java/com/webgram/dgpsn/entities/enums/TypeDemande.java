package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum TypeDemande {
    INFOS("Demande d'information"),
    RV("Demande de rendez-vous"),
    DOC("Demande de documents"),
    AUTRES("Autre");

    @Getter
    @Setter
    private String description;

    TypeDemande(String description) {
        this.description = description;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TypeDemande fromValue(Object typedemande) {
        if (typedemande instanceof Map) {
            Map<String, Object> mapTypeDemande = (Map<String, Object>) typedemande;
            if (mapTypeDemande.containsKey("name")) {
                return TypeDemande.valueOf(mapTypeDemande.get("name").toString());
            }
        }
        if (typedemande instanceof String) {
            return TypeDemande.valueOf(typedemande.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", TypeDemande.class, typedemande, values()));
    }

    @JsonValue
    Map<String, Object> getModule() {
        return Map.of(
                "name", name(),
                "description", description
        );
    }

    public static Set<TypeDemande> getAllPriorities() {
        return stream(values())
                .collect(Collectors.toSet());
    }
}
