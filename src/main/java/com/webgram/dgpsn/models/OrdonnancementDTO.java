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
public class OrdonnancementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String reference;
    private LocalDate dateOrdonnancement;
    private Double montantOrdonne;
    private String mois;
    private String objet;
    private String statut;
    private String modePaiement;

//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private AgentDTO beneficiaire;
//    private Long beneficiaireId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BudgetPassationDTO budgetPassation;
    private Long budgetPassationId;


//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private EngagementDTO engagement;
//    private Long engagementId;

    private String justificatif;
}