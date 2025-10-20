package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "issueLog_action_realized")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueLogActionRealizedEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "isloac_id")
    private Long id;

    @Column(name = "isloac_action", columnDefinition = "TEXT")
    private String action;

    @ManyToOne
    @JoinColumn(name = "isloac__linked_issuelog")
    private IssueLogEntity issueLog;
}
