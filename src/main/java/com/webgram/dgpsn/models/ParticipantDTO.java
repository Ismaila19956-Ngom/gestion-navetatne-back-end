package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeParticipant;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private LocalDate dateInscription;

    private Statut statut;

    private Double noteEvaluation;

    private Boolean certificatObtenu;

    private String commentaires;

    private Long agentId;

    private AgentDTO agent;

    private FormationExterieurDTO formation;
    private Long formationId;

    private AtelierDTO atelier;
    private Long atelierId;

    private TypeParticipant typeParticipant;

}
