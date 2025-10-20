package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;
import com.webgram.dgpsn.entities.enums.StructureProjectType;

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
public class AssignmentDTO implements Serializable {
    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;
    @JournalAttribute
    private String libelle;
    private String path;
    private Date startDate;
    private Date endDate;
    private StructureProjectType structureProjectType;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO assignmentType;

//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StructureDTO structure;
//
//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private StructureDTO structureExecution;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ManagementUnitDTO projet;
//    private List<IssueLogDTO> issueLogs;
//
    private Long assignmentTypeId;
//    private Long partenerId;
    private Long structureId;
    private Long projetId;
}
