package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.enums.Statut;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
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
public class MissionDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Le numéro d'ordre est obligatoire")
    private Integer numeroOrdre;

    private String type;

    private String objet;

    private String destination;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private Integer duree;

    private Double budget;

    @JsonProperty("agentId")
    private Long agentId;

    @NotNull(message = "Le statut est obligatoire")
    private Statut statut;

    private String rapport;

    private LocalDate dateRapport;
}