package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "history_flag")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryFlagEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hfl_id")
    private Long id;

    @Column(name = "hfl_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "hfl_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "hst_linked_flag")
    private LabelEntity flag;

    @ManyToOne
    @JoinColumn(name = "hst_linked_projet")
    private ManagementUnitEntity projet;


}
