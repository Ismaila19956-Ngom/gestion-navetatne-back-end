package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "inspection_icpe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspectionICPEEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "icpe_id")
    private Long id;

    @Column(name = "icpe_code", unique = true)
    private String code;

    @Column(name = "icpe_date_inspection")
    private Date dateInspection;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_type_inspection")
    private LabelEntity typeInspection;

    //Augmenter la taille de la colonne pour pouvoir stocker plus de caractères
    @Column(name = "icpe_ref", length = 150)
    private String ref;

    @ManyToOne
    @JoinColumn(name = "ec_id")
    private EtablissementClasseEntity etablissement;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_programme")
    private ManagementUnitEntity programme;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_activite")
    private ManagementUnitEntity activite;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_direction")
    private DirectionEntity direction;

//    @Column(name = "icpe_team_lead", nullable = false)
//    private AgentEntity teamLead;

    @ManyToOne
    @JoinColumn(name = "icpe_team_lead")
    private AgentEntity teamLead;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "icpe_team_members",
            joinColumns = @JoinColumn(name="icpe_id"),
            inverseJoinColumns = @JoinColumn(name="team_member_id"))
    private Set<AgentEntity> teamMembers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "icpe_services", joinColumns = @JoinColumn(name = "icpe_id"))
    @Column(name = "service", length = 100)
    private List<String> services;

    @ElementCollection
    @CollectionTable(name = "icpe_aspects", joinColumns = @JoinColumn(name = "icpe_id"))
    private List<InspectedAspect> aspects;

    @Column(name = "icpe_samples_taken")
    private Boolean samplesTaken;

    @ElementCollection
    @CollectionTable(name = "icpe_sample_types", joinColumns = @JoinColumn(name = "icpe_id"))
    @Column(name = "sample_type", length = 100)
    private List<String> sampleTypes;

    @ElementCollection
    @CollectionTable(name = "icpe_samples", joinColumns = @JoinColumn(name = "icpe_id"))
    private List<Sample> samples;

    @Column(name = "icpe_immediate_results", columnDefinition = "TEXT")
    private String immediateResults;

    @Column(name = "icpe_laboratory", length = 100)
    private String laboratory;

    @Column(name = "icpe_major_non_conformities", columnDefinition = "TEXT")
    private String majorNonConformities;

    @Column(name = "icpe_minor_non_conformities", columnDefinition = "TEXT")
    private String minorNonConformities;

    @Column(name = "icpe_corrective_actions", columnDefinition = "TEXT")
    private String correctiveActions;

    @Column(name = "icpe_compliance_deadline")
    private Date complianceDeadline;

    @Column(name = "icpe_summary", columnDefinition = "TEXT")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_compliance_level")
    private LabelEntity complianceLevel;

    @ManyToOne
    @JoinColumn(name = "icpe_linked_environmental_risk")
    private LabelEntity environmentalRisk;

    @Column(name = "icpe_followup_required")
    private Boolean followupRequired;

    @Column(name = "icpe_next_inspection_date")
    private Date nextInspectionDate;

    @ElementCollection
    @CollectionTable(name = "icpe_photos", joinColumns = @JoinColumn(name = "icpe_id"))
    @Column(name = "photo_path", length = 1000)
    private List<String> photos;

    @ElementCollection
    @CollectionTable(name = "icpe_documents", joinColumns = @JoinColumn(name = "icpe_id"))
    @Column(name = "document_path", length = 1000)
    private List<String> documents;

    @ElementCollection
    @CollectionTable(name = "icpe_analysis_reports", joinColumns = @JoinColumn(name = "icpe_id"))
    @Column(name = "report_path", length = 1000)
    private List<String> analysisReports;

    @Column(name = "icpe_prepared_by", length = 100)
    private String preparedBy;

    @Column(name = "icpe_preparation_date")
    private Date preparationDate;

    @Column(name = "icpe_validation_comments", columnDefinition = "TEXT")
    private String validationComments;

    @Column(name = "icpe_leader_validation")
    private Boolean leaderValidation;

    @Embeddable
    @Data
    public static class InspectedAspect {
        @Column(name = "aspect_name", length = 100)
        private String name;

        @Column(name = "aspect_conformity", length = 100)
        private String conformity;

        @Column(name = "aspect_observations", columnDefinition = "TEXT")
        private String observations;
    }

    @Embeddable
    @Data
    public static class Sample {
        @Column(name = "sample_reference", length = 100)
        private String reference;

        @Column(name = "sample_type", length = 100)
        private String type;

        @Column(name = "sample_location", length = 100)
        private String location;

        @Column(name = "sample_observations", columnDefinition = "TEXT")
        private String observations;
    }
}