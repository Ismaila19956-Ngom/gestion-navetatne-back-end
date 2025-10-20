package com.webgram.dgpsn.models;

import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaluationfinanciereDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String code;

    private String libelle;

    private Double chiffreaffaire;

    private Date datedebut;

    private Date datefin;

    private Double resultatnetreel;

    private Double resultatnetprevu;

    private Double margebruteexpreel;

    private Double margebrutedexpprevue;

    private Double capacitdautofinancereel;

    private Double cafprevue;

    private Double fondsderoulementreel;

    private Double frngprevue;

    private Double ratiolgreel;

    private Double gearingreel;

    private Double gearingprevue;

    private Double roerel;

    private Double roeprvue;

    private Double cashflowreel;

    private Double cashflowprevu;

    private String commentaire;

    private Integer notation;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EntrepriseDto entreprise;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private Long entrepriseId;

}