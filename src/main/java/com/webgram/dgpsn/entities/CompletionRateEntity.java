package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Period;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "completion_rate")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionRateEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comr_id")
    private Long id;

    @Column(name = "comr_target_value")
    private Double targetValue;

    @Column(name = "comr_value_reched")
    private Double valueReched;

    @Column(name = "comr_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "comr_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "comr_year")
    private Integer year;

    @Column(name = "comr_period")
    @Enumerated(EnumType.STRING)
    private Period period;

    @ManyToOne
    @JoinColumn(name = "comr_linked_projet")
    private ManagementUnitEntity managementUnit;
}
