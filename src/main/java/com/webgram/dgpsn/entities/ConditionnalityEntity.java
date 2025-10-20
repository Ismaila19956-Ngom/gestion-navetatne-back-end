package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "conditionnality")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ConditionnalityEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cnd_id")
    private Long id;

    @Column(name = "cnd_comment" , columnDefinition = "TEXT")
    private String comment;

    @Column(name = "cnd_libelle")
    private String libelle;

    @Column(name = "cnd_deadline")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne()
    @JoinColumn(name = "cnd_linked_state_progress")
    private LabelEntity stateProgress;

    @ManyToOne()
    @JoinColumn(name = "cnd_linked_conditionnality_type")
    private LabelEntity conditionnalityType;

    @ManyToOne()
    @JoinColumn(name = "cnd_linked_agent")
    private AgentEntity agent;

    @ManyToOne()
    @JoinColumn(name = "cnd_linked_projet")
    private ManagementUnitEntity projet;
}
