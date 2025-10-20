package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Criticity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "issue_log")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IssueLogEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "islog_id")
    private Long id;

    @Column(name = "islog_libelle")
    private String libelle;

    @Column(name = "islog_description" , columnDefinition = "TEXT")
    private String description;

    @Column(name = "islog_identification_date")
    @Temporal(TemporalType.DATE)
    private Date identificationDate;

    @Column(name = "islog_deadline_date")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Column(name = "islog_resolution_date")
    @Temporal(TemporalType.DATE)
    private Date resolutionDate;

    @Column(name = "islog_risque_financier")
    private Double risqueFinancier;

    @Column(name = "islog_impact")
    private String impact;

    @Column(name = "islog_comment" , columnDefinition = "TEXT")
    private String comment;

    @Column(name = "islog_valid")
    private Boolean valid;

    @Column(name = "islog_valid_comment" , columnDefinition = "TEXT")
    private String validComment;

    @ManyToOne
    @JoinColumn(name = "islog_linked_projet")
    private ManagementUnitEntity projet;

//    @ManyToOne
    @Column(name = "islog_criticity")
    @Enumerated(EnumType.STRING)
    private Criticity criticity;
//    private LabelEntity criticity;

//    @ManyToOne
    @Column(name = "islog_delay_impact")
    @Enumerated(EnumType.STRING)
    private Criticity delayImpact;
//    private LabelEntity delayImpact;

//    @ManyToOne
    @Column(name = "islog_financial_impact")
    @Enumerated(EnumType.STRING)
    private Criticity financialImpact;
//    private LabelEntity financialImpact;

    @ManyToOne
    @JoinColumn(name = "islog_linked_status")
    private StatusEntity status;

//    @OneToMany(mappedBy = "issueLog", cascade = CascadeType.ALL)
//    private List<IssueLogRiskEntity> issueLogRiskEntities;

    @ManyToOne
    @JoinColumn(name = "islog_linked_specific_nature")
    private SpecificNatureEntity specificNature;

    @ManyToOne
    @JoinColumn(name = "islog_linked_sub_category")
    private SubCategoryEntity subCategory;

    @ManyToOne
    @JoinColumn(name = "islog_linked_supervisor")
    private StructureEntity supervisor;

    @ManyToOne
    @JoinColumn(name = "islog_linked_agnet")
    private AgentEntity dseppSupervisor;

    @ManyToOne
    @JoinColumn(name = "islog_linked_source")
    private LabelEntity source;

    @ManyToOne
    @JoinColumn(name = "islog_linked_minister")
    private StructureEntity minister;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "issuelog_resolveChannel",
            joinColumns = @JoinColumn(name="issueLog_id"),
            inverseJoinColumns = @JoinColumn(name="resolveChannel_id"))
    private Set<LabelEntity> resolveChannels = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "issuelog_risk",
            joinColumns = @JoinColumn(name="issueLog_id"),
            inverseJoinColumns = @JoinColumn(name="risk_id"))
    private Set<RiskEntity> risks = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "issuelog_structure",
            joinColumns = @JoinColumn(name="issueLog_id"),
            inverseJoinColumns = @JoinColumn(name="structure_id"))
    private Set<StructureEntity> structures = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "islog_linked_assignment")
    private AssignmentEntity assignment;




}
