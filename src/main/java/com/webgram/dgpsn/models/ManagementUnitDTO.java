package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.enums.Formula;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.MissingElementDTO;

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
public class ManagementUnitDTO {

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;
    @JournalAttribute
    private String code;
    @JournalAttribute
    private String name;
    private TypeProjet type;
    private Formula formula;
    private Long poids;
    private Date expectedStartDate;
    private Date expectedEndDate;
    private Date actualStartDate;
    private Date actualEndDate;
    private Double budget;
    Double budgetInsvest;
    private String description;
    private String overallObjective;
    private boolean publish;
    private String tag;
    private boolean disorganized;
    private String statut;
    private String nomenclature;
    private List<MissingElementDTO> missingElements;
    private Integer anneeDebut;
    private Integer anneeFin;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelEntity axe;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AgentDTO responsible;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ManagementUnitDTO parent;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StructureDTO structure;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<SubSectorDTO> subSectors;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelDTO> beneficiaries;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelDTO> verificationSources;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<StructureDTO> actorsInvolved;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<LabelDTO> executionZones;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<StructureProjectDTO> ministerTutelles;

    private Long axeId;
    private Long responsibleId;
    private Long parentId;
    private List<Long> subSectorIds;
    private List<Long> beneficiarieIds;
    private List<Long> executionZoneIds;
    private List<Long> verificationSourceIds;
    private List<Long> actorInvolvedIds;
    private Long structureId;


}
