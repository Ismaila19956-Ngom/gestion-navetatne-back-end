package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StructureProjectType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "assignment")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class AssignmentEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "asm_id")
    private Long id;

    @Column(name = "asm_libelle")
    private String libelle;
    @Column(name = "asm_path")
    private String path;

    @Column(name = "asm_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "asm_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne()
    @JoinColumn(name = "asm_linked_assignment_type")
    private LabelEntity assignmentType;

    @Column(name = "qur_type_structure")
    @Enumerated(EnumType.STRING)
    private StructureProjectType structureProjectType;

    @ManyToOne()
    @JoinColumn(name = "asm_linked_structure")
    private StructureEntity structure;

//    @ManyToOne()
//    @JoinColumn(name = "asm_linked_structure_execution")
//    private StructureProjectEntity structure;

    @ManyToOne()
    @JoinColumn(name = "asm_linked_projet")
    private ManagementUnitEntity projet;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "assig_issue",
//            joinColumns = @JoinColumn(name = "assigment_id", referencedColumnName = "asm_id"),
//            inverseJoinColumns = @JoinColumn(name = "issuelog_id", referencedColumnName = "islog_id"))
//    private List<IssueLogEntity> issueLogs;
//
//    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
//    private List<RecommendationEntity> recommendations;
}
