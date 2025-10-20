package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "recommendation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rec_id")
    private Long id;

    @Column(name = "rec_libelle", columnDefinition = "TEXT")
    private String libelle;

    @Column(name = "rec_responsable")
    private String responsable;

    @Column(name = "rec_deadline")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne()
    @JoinColumn(name = "rec_linked_issue_log")
    private IssueLogEntity issueLog;

    @ManyToOne()
    @JoinColumn(name = "rec_linked_risk")
    private RiskEntity risk;

    @ManyToOne()
    @JoinColumn(name = "rec_linked_assignment")
    private AssignmentEntity assignment;

    @ManyToOne()
    @JoinColumn(name = "rec_linked_status")
    private StatusEntity status;

}
