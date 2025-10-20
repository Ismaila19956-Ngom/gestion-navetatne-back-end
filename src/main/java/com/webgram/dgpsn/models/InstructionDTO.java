package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstructionDTO implements Serializable {

    @Schema(description = "ID technique", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String intitule;
    private String niveauInstruction;
    private String observations;
    private String consultant;
    private LocalDate dateDepot;
    private LocalDate dateVisite;
    private LocalDate dateReponse;
    private LocalDate dateDepotTDR;
    private LocalDate dateVisiteTDR;
    private LocalDate dateValidationTDR;
    private LocalDate dateDepotEIE;
    private LocalDate dateValidationRapport;
    private LocalDate dateAudiencePublique;
    private LocalDate dateDepotRapportFinal;
    private LocalDate dateDelivranceAttestation;
    private LocalDate dateDelivranceArrete;

    // Champs pour les relations
    private Long promoteurId;
    private Long regionId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PromoteurDTO promoteur;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CadreLogiqueDTO region;
}