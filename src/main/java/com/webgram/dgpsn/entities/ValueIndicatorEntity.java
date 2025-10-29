package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Period;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "value_indicator")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValueIndicatorEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vind_id")
    private Long id;

    @Column(name = "vind_target_value")
    private Double targetValue;

    @Column(name = "vind_value_reched")
    private Double valueReched;

    @Column(name = "vind_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "vind_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "vind_year")
    private Integer year;

    @Column(name = "vind_period")
    @Enumerated(EnumType.STRING)
    private Period period;

    @ManyToOne
    @JoinColumn(name = "vind_linked_indicator_projet")
    private IndicatorProjetEntity indicatorProjet;

    @ManyToOne
    @JoinColumn(name = "vind_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "vind_linked_activity")
    private ManagementUnitEntity activity;

    @Column(name = "vind_validation")
    private Boolean valid;


}
