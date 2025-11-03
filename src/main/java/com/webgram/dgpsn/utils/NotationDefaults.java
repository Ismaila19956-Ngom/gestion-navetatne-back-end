package com.webgram.dgpsn.utils;

import com.webgram.dgpsn.entities.NotationEntity;
import com.webgram.dgpsn.entities.enums.CritereNotation;
import com.webgram.dgpsn.entities.enums.ValeurNotation;


import java.util.List;

public class NotationDefaults {

    public static List<NotationEntity> defaultNotations() {
        return List.of(
                NotationEntity.builder()
                        .critere(CritereNotation.PREMIERE_IMPRESSION)
                        .valeur(ValeurNotation.BIEN)
                        .build(),
                NotationEntity.builder()
                        .critere(CritereNotation.ECOUTE_REACTIVITE_DYNAMISME)
                        .valeur(ValeurNotation.TRES_BIEN)
                        .build(),
                NotationEntity.builder()
                        .critere(CritereNotation.DEGRE_MOTIVATION)
                        .valeur(ValeurNotation.BIEN)
                        .build(),
                NotationEntity.builder()
                        .critere(CritereNotation.COMMUNICATION_NON_VERBALE)
                        .valeur(ValeurNotation.INSUFFISANT)
                        .build(),
                NotationEntity.builder()
                        .critere(CritereNotation.ADAPTABILITE)
                        .valeur(ValeurNotation.INSUFFISANT)
                        .build()
        );
    }
}
