package com.webgram.dgpsn.entities.enums;

public enum NiveauEtude {
    BFEM("BFEM"),
    BAC("BAC"),
    BAC_PLUS_2("BAC+2"),
    LICENCE("Licence"),
    MASTER_I("Master I"),
    MASTER_II("Master II"),
    DOCTORAT("Doctorat"),
    MAITRISE("Maîtrise");

    private final String libelle;

    NiveauEtude(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
