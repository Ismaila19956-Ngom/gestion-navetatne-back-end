package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FicheRenseignementDTO implements Serializable {

    private static final long serialVersionUID = -1234567890123456789L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    // Information sur la compagnie
    private String nomCompagnie;

    // Information sur la mission
    private String structureInstruction;
    private Date dateMission;
    private String membresMission;
    private String objetMission;

    // Identification du promoteur
    private String raisonSociale;
    private Integer numeroRCCM;
    private Integer numeroNINEA;
    private String nomExploitant;
    private String telephoneExploitant;
    private String adresseExploitant;
    private String natureActivite;

    // Localisation géographique
//    private String region;
//    private String departement;
//    private String commune;
    private String quartier;
    private String limiteEst;
    private String limiteNord;
    private String limiteOuest;
    private String limiteSud;
    private String localisationGPS;

    // Superficie
    private Double surfaceEquipee;
    private Double surfaceNonEquipee;
    private Double surfaceTotale;

    // Constats généraux
    private String compositionEtablissement;
    private String listeEquipements;
    private String salubriteEtablissement;
    private String moyensSecours;
    private Boolean formationExtincteurs;
    private String equipementsProtection;
    private String affichageConsignes;
    private String sourceElectricite;
    private String sourceEau;

    // Gestion des rejets
    private String dechetsSolides;
    private String rejetsLiquides;
    private String rejetsAtmospheriques;

    // Autres informations
    private String autresConstats;
    private String prescriptionMission;
    private String conclusionMission;
    private String signatureChef;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO region;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO departement;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO arrondissement;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO commune;

    @NotNull
    private Long regionId;

    @NotNull
    private Long departementId;

    @NotNull
    private Long communeId;
}