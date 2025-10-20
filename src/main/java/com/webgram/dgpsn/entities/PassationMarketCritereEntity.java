package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "passation_market_critere")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PassationMarketCritereEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pmc_id")
    private Long id;

    @Column(name = "expected_value", unique=false)
    private String expectedValue;

    @Column(name = "ponderation", unique=false)
    private Double ponderation;

    @ManyToOne
    @JoinColumn(name = "evaluation_criteria")
    private LabelEntity evaluationCriteria;

    @ManyToOne
    @JoinColumn(name = "passation_market_critere_linked_passation_market")
    private PassationMarketEntity passationMarketEntity;







}
