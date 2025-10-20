package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "preparation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PreparationEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prp_id")
    private Long id;

    @Column(name = "prp_comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "prp_libelle")
    private String libelle;

    @Column(name = "prp_deadline")
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne()
    @JoinColumn(name = "prp_linked_phase")
    private LabelEntity phase;

    @ManyToOne()
    @JoinColumn(name = "prp_linked_agent")
    private AgentEntity agent;

    @ManyToOne()
    @JoinColumn(name = "prp_linked_projet")
    private ManagementUnitEntity projet;
}
