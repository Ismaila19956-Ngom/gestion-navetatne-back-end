package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;


import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspectionICPEDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;

    @NotNull
    @JournalAttribute
    private String code;

    @NotNull
    @JournalAttribute
    private Date dateInspection;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO typeInspection;

    @NotNull
    @JournalAttribute
    private Long typeInspectionId;

    @NotNull
    @JournalAttribute
    private String ref;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EtablissementClasseDTO etablissement;

    @NotNull
    @JournalAttribute
    private Long etablissementId;

    @NotNull
    @JournalAttribute
    private AgentDTO teamLead;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long teamLeadId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<AgentDTO> teamMembers;

    @JournalAttribute
    private Set<Long> teamMemberIds;

    private List<String> services;

    private List<InspectedAspect> aspects;

    private Boolean samplesTaken;

    private List<String> sampleTypes;

    private List<Sample> samples;

    private String immediateResults;

    private String laboratory;

    private String majorNonConformities;

    private String minorNonConformities;

    private String correctiveActions;

    private Date complianceDeadline;

    @NotNull
    @JournalAttribute
    private String summary;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO complianceLevel;

    @NotNull
    @JournalAttribute
    private Long complianceLevelId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO environmentalRisk;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ManagementUnitDTO programme;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ManagementUnitDTO projet;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public ManagementUnitDTO activite;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public DirectionDTO direction;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long directionId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long programmeId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    protected Long projetId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    protected Long activiteId;



    @NotNull
    @JournalAttribute
    private Long environmentalRiskId;

    private Boolean followupRequired;

    private Date nextInspectionDate;

    private List<String> photos;

    private List<String> documents;

    private List<String> analysisReports;

    @NotNull
    @JournalAttribute
    private String preparedBy;

    @NotNull
    @JournalAttribute
    private Date preparationDate;

    private String validationComments;

    private Boolean leaderValidation;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InspectedAspect {
        @NotNull
        private String name;
        @NotNull
        private String conformity;
        private String observations;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sample {
        private String reference;
        private String type;
        private String location;
        private String observations;
    }
}