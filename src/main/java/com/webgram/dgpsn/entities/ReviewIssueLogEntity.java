package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "review_issuelog")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewIssueLogEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rvwisu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rvwisu_linked_review")
    private ReviewEntity review;

    @ManyToOne
    @JoinColumn(name = "rvwisu_linked_issuelog")
    private IssueLogEntity issueLog;



}
