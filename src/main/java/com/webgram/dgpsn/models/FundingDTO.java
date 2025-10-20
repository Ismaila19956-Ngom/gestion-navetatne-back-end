package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingDTO implements Serializable {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;

    @NotNull
    @NotEmpty
    private String financingAgreement;

    @NotNull
    @NotEmpty
    private Double amount;

    @NotNull
    @NotEmpty
    private String cash;

    @NotNull
    @NotEmpty
    private Double rate;

    @NotNull
    @NotEmpty
    private Double equivalence;

    @NotNull
    @NotEmpty
    private Date approvalDate;

    @NotNull
    @NotEmpty
    private Date closingDate;

    @NotNull
    @NotEmpty
    private Date extentionDate;

    @NotNull
    private Date closingProrogationDate;

    @NotNull
    private Date firstDisbursementDate;

    @NotNull
    private Integer numberProrogation;

    @Schema( accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO fundingType;

    @NotNull
    @NotEmpty
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ManagementUnitDTO projet;

    @NotNull
    @NotEmpty
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StructureProjectDTO partnerProjet;

    private Date agreementSigningDate;

    private Date effectiveDate;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO cashEntity;

    @NotNull
    @NotEmpty
    private Long fundingTypeId;

    @NotNull
    @NotEmpty
    private Long projetId;

    @NotNull
    @NotEmpty
    private Long partnerProjetId;

    private Long cashId;

//    @NotNull
//    @NotEmpty
//    private Long structureId;

}
