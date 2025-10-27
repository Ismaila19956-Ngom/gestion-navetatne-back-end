package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.enums.CourrierType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourrierDTO {
    private Long id;
    private String reference;
    private String correspondant;
    private String objet;
    private String description;
    private CourrierType type;
    private LocalDateTime dateCourrier;
    private LocalDateTime dateReception;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateTraitement;
    private String numeroEnregistrement;
    private String numeroSuivi;
    private String instructions;
    private String notes;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO urgence;
    private Long urgenceId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO nature;
    private Long natureId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO statut;
    private Long statutId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO modeEnvoi;
    private Long modeEnvoiId;
}