package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FicheVisiteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    // Structure et visite
    private String structure;
    private LocalDate dateVisite;
    // Informations du projet
    private String titre;
    private String typeProjet;
    private String nomPromoteur;
    private String responsables;

    private String village;
    private Integer annee;
    private String coordX;
    private String coordY;

    // Présentation du site
    private String limiteEst;
    private String limiteOuest;
    private String limiteNord;
    private String limiteSud;
    private String statutJuridique;
    private String superficie;
    private String utilisationAnterieure;

    // Description du projet
    private String descriptionProjet;
    private String phasePreConstruction;
    private String phaseConstruction;
    private String phaseExploitation;

    // Sensibilités et recommandations
    private String sensibiliteEnvironnementale;
    private String sensibiliteSociale;
    private String particularites;
    private String pointsAccent;
    private String recommandations;
    // Chef de division
    private String chefDivision;
    // Membres
    private List<MembreMissionDTO> membres;

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
    private Long arrondissementId;

    @NotNull
    private Long regionId;

    @NotNull
    private Long departementId;

    @NotNull
    private Long communeId;
}