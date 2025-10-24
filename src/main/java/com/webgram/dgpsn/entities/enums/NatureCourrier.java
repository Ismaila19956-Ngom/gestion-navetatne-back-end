package com.webgram.dgpsn.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

public enum NatureCourrier {
    ACADEMIQUE("Courrier académique"),
    ADMINISTRATIF("Courrier administratif"),
    FINANCIER("Courrier financier"),
    PEDAGOGIQUE("Courrier pédagogique");

    @Getter
    private final String description;

    NatureCourrier(String description) {
        this.description = description;
    }

    @JsonCreator
    public static NatureCourrier fromValue(String value) {
        return Arrays.stream(NatureCourrier.values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nature de courrier invalide: " + value));
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}