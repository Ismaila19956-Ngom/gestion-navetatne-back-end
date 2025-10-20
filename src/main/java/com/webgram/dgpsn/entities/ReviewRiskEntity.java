package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "review_risk")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRiskEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rvwrsk_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rvwrsk_linked_review")
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "rvwrsk_linked_risk")
    private RiskEntity risk;



}
