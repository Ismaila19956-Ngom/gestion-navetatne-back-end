package com.webgram.dgpsn.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

@Data @Builder @Accessors(chain = true) @NoArgsConstructor @AllArgsConstructor @ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FournisseurDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    // Informations de base (champs requis dans le Frontend [15-17])
    private String codeFournisseur;
    private String raisonSociale;
    private String ninea;
    private String typeFournisseur; // Enum: ENTREPRISE, PARTICULIER, etc.
    private String telephone;
    private String email;
    private String adresse;
    private String statut; // Enum: ACTIF, INACTIF, SUSPENDU, BLOQUE

    private String registreCommerce; // Champ facultatif
    private String banque; // Champ facultatif
    private String rib; // Champ facultatif
    private String categorieFournisseur;
    private Integer delaiPaiement;
    private String nomContact;
    private String fonctionContact;
    private String notes;
}