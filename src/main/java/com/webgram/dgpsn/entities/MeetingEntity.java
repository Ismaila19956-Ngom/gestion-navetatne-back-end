package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "meeting")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MeetingEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meet_id")
    private Long id;

    @Column(name = "meet_libelle")
    private String libelle;

    @Column(name = "mil_predicated_date")
    @Temporal(TemporalType.DATE)
    private Date predicatedDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "mil_read_readDate")
    private Date readDate;
    @Column(name = "mil_comment" , columnDefinition = "TEXT")
    private String comment;
    @Column(name = "mil_read_heureDebutPrevue")
    @Temporal(TemporalType.TIME)
    private Date heureDebutPrevue;
    @Column(name = "mil_read_heureFinPrevue")
    @Temporal(TemporalType.TIME)
    private Date heureFinPrevue;
    @Column(name = "mil_read_heureDebutReelle")
    @Temporal(TemporalType.TIME)
    private Date heureDebutReelle;
    @Column(name = "mil_read_heureFinReelle")
    @Temporal(TemporalType.TIME)
    private Date heureFinReelle;
    @ManyToOne
    @JoinColumn(name = "milestone_linked_meetingType")
    private LabelEntity meetingType;
    @ManyToOne
    @JoinColumn(name = "milestone_linked_projet")
    private ManagementUnitEntity projet;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingAgentEntity> meetingAgents = new ArrayList<>();

    public void setMeeting(MeetingEntity meetingSaved) {
    }
}
