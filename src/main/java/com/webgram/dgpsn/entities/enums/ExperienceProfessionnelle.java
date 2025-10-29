package com.webgram.dgpsn.entities.enums;

public enum ExperienceProfessionnelle {
    UN_AN("1 an"),
    DEUX_ANS("2 ans"),
    TROIS_ANS("3 ans"),
    QUATRE_ANS("4 ans"),
    CINQ_ANS("5 ans"),
    PLUS_DE_CINQ_ANS("+ 5 ans");

    private final String libelle;

    ExperienceProfessionnelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}