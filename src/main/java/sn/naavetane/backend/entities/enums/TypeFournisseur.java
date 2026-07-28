package sn.naavetane.backend.entities.enums;

import lombok.Getter;
import lombok.Setter;

public enum TypeFournisseur {
    ENTREPRISE("Entreprise"),
    PARTICULIER("Particulier"),
    ADMINISTRATION("Administration"),
    ONG("ONG/Association");

    @Getter
    @Setter
    private String description;

    TypeFournisseur(String description) {
        this.description = description;
    }


}
