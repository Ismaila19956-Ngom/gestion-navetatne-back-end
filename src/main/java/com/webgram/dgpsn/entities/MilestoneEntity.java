package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "milestone")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MilestoneEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mil_id")
    private Long id;

    @Column(name = "mil_libelle")
    private String libelle;

    @Column(name = "mil_predicated_date")
    @Temporal(TemporalType.DATE)
    private Date predicatedDate;

    @Column(name = "mil_read_date")
    @Temporal(TemporalType.DATE)
    private Date readDate;

    @Column(name = "mil_comment" , columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "milestone_linked_projet")
    private ManagementUnitEntity projet;
}
