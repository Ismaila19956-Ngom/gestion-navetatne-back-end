package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;

import jakarta.validation.constraints.NotNull;
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
public class RealisationDTO implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    private FournisseurDTO fournisseur;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;

    @JournalAttribute
    @NotNull
    private String code;

    @JournalAttribute
    @NotNull
    private Double montant;

    @JournalAttribute
    @NotNull
    private LocalDate date;

    @JournalAttribute
    @NotNull
    private Long fournisseurId;

    @JournalAttribute
    private String numeroBon;

    @JournalAttribute
    private String numeroBE;

    @JournalAttribute
    private String numeroMandat;

    @JournalAttribute
    private String facture;

    @JournalAttribute
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LigneBudgetaireDTO ligneBudgetaire;
    @JournalAttribute
    @NotNull
    private Long ligneBudgetaireId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private PlanComptableElementDTO realisations;
    @JournalAttribute
    @NotNull
    private Long realisationsId;


}