package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webgram.dgpsn.entities.enums.ResponsableMission;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypeGroupe;
import com.webgram.dgpsn.entities.enums.TypeOrdreMission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdreMissionDTO implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private TypeOrdreMission ordreMission;
    private TypeGroupe groupe;
    private StatutType statut;
    private String objectMission;
    private String destination;
    private String itineraire;
    private Date dateDepartOrdre;
    private Date dateRetourOrdre;
    private Date dateDepartMission;
    private Date dateRetourMission;
    private String responsableMission;
    private List<ResponsableMission> structures;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelDTO> priseEnCharge;

    // CHANGEMENT : frais devient une liste
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelDTO> frais;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<AgentDTO> agent;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<DocumentDto> document;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO moyenTranport;

    // CHANGEMENT : fraisId devient une liste
    private List<Long> fraisIds;

    private List<Long> priseEnChargeId;
    private List<Long> agentIds;
    private List<Long> documentIds;
    private Long moyenTranportId;



}
