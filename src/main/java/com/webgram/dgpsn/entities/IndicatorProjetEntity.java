package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Periodicity;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "indicator_projet")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class IndicatorProjetEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "indprj_id")
    private Long id;

    @Column(name = "indprj_target_value")
    private Double targetValue;

    @Column(name = "indprj_periodicity")
    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    @ManyToOne
    @JoinColumn(name = "indprj_linked_indicator")
    private IndicatorEntity indicator;

    @ManyToOne
    @JoinColumn(name = "indprj_linked_projet")
    private ManagementUnitEntity projet;
}
