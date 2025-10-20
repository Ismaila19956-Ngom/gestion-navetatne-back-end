package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "history_status")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryStatusEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hst_id")
    private Long id;

    @Column(name = "hst_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "hst_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "hst_linked_status")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "hst_linked_projet")
    private ManagementUnitEntity projet;


}
