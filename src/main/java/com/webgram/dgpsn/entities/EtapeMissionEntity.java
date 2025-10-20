package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "etape_mission")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class EtapeMissionEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "etape_id")
    private Long id;

    @Column(name = "etape_libelle")
    private String libelle;

    @Column(name = "etape_plannedStartDate")
    @Temporal(TemporalType.DATE)
    private Date plannedStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "etape_planedendDate")
    private Date planedEndDate;

    @Column(name = "etape_actualStartDate")
    @Temporal(TemporalType.DATE)
    private Date actualStartDate;

    @Column(name = "etape_actualEndDate")
    @Temporal(TemporalType.DATE)
    private Date actualEndDate;


    @Column(name = "etape_comment" , columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "etape_linked_status")
    private LabelEntity status;

    @ManyToOne
    @JoinColumn(name = "etape_linked_participant")
    private ParticipantMissionEntity responsable;

    @ManyToOne
    @JoinColumn(name = "etape_linked_assignment")
    private AssignmentEntity assignment;


}
