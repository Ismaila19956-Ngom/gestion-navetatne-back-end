package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "respondent")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RespondentEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "res_id")
    private Long id;

    @Column(name = "res_firstName")
    private String firstName;

    @Column(name = "res_lastName")
    private String lastName;

    @Column(name = "res_phone")
    private String phone;

    @Column(name = "res_e_mail")
    private String e_mail;

    @Column(name = "res_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "res_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "comment" , columnDefinition = "TEXT")
    private String comment;

    @ManyToOne()
    @JoinColumn(name = "res_linked_funding")
    private FundingEntity funding;
}
