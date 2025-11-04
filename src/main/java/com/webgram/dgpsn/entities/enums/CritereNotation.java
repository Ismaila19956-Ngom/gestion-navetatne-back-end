package com.webgram.dgpsn.entities.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CritereNotation {

    PREMIERE_IMPRESSION("Première impression (politesse, tenue vestimentaire, …)"),
    ECOUTE_REACTIVITE_DYNAMISME("Écoute, réactivité et dynamisme"),
    DEGRE_MOTIVATION("Degré de motivation"),
    COMMUNICATION_NON_VERBALE("Communication non verbale"),
    ADAPTABILITE("Adaptabilité");

    private final String libelle;
}
