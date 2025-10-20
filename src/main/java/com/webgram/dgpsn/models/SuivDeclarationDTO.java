package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuivDeclarationDTO {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String ref;

    private LocalDate date;

    private String plaignant;

    private String contactPlaignant;

    private String evenement;

    private String localisation;

    private String miseEnCause;

    private String contactMiseEnCause;

    private String constat;

    private LocalDate dateConstat;

    private String mesuresPrises;

    private String observations;

    private String pv;

    private String autresPj;

    @NotNull
    private Long declarationId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DeclarationDTO declaration;
}