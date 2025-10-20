package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "indicator")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicatorEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ind_id")
    private Long id;

    @Column(name = "ind_code", unique=true)
    private String code;

    @Column(name = "ind_libelle")
    private String libelle;

    @ManyToOne()
    @JoinColumn(name = "ind_linked_unit")
    private LabelEntity unit;

    @ManyToOne()
    @JoinColumn(name = "ind_linked_indicatorType")
    private LabelEntity indicatorType;
}
