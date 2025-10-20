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
public class ConditionnalityDTO implements Serializable {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;

    @NotNull
    @NotEmpty
    @Schema(description = "Name of conditionnality")
    @JournalAttribute
    private String libelle;
    private String comment;
    private Date deadline;
    private Long conditionnalityTypeId;
    private Long stateProgressId;
    private Long projetId;
    private Long agentId;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO stateProgress;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO conditionnalityType;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ManagementUnitDTO projet;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AgentDTO agent;
}
