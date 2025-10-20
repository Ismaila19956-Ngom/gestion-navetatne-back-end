package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "prolongation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ProlongationEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prl_id")
    private Long id;

    @Column(name = "prl_justification" , columnDefinition = "TEXT")
    private String justification;

    @Column(name = "prl_path")
    private String path;

    @Column(name = "prl_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "prl_duration")
    private Integer duration;

    @ManyToOne()
    @JoinColumn(name = "cnd_linked_funding")
    private FundingEntity funding;
}
