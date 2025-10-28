package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtelierDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty
    private String titreAtelier;

    private String theme;

    private String objectif;

    private LocalDateTime dateAtelier;

    private Statut statut;

    private LocalDateTime heurDebut;

    private LocalDateTime heurFin;

    @NotEmpty
    private String lieu;

    @NotNull
    private Long agentId;
    private AgentDTO agent;

    private Double coutOrganisation;

    private Long nombreParticipantsMax;
}
