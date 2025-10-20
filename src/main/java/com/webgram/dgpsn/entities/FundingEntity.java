package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "funding")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FundingEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fund_id")
    private Long id;

    @Column(name = "fund_financing_agreement")
    private String financingAgreement;

    @Column(name = "fund_amount")
    private Double amount;

    @Column(name = "fund_cash")
    private String cash;

    @Column(name = "prj_path")
    private String path;

    @Column(name = "fund_rate")
    private Double rate;

    @Column(name = "fund_equivalence")
    private Double equivalence;

    @Column(name = "fund_approval_date")
    @Temporal(TemporalType.DATE)
    private Date approvalDate;

    @Column(name = "fund_closing_date")
    @Temporal(TemporalType.DATE)
    private Date closingDate;

    @Column(name = "fund_extention_date")
    @Temporal(TemporalType.DATE)
    private Date extentionDate;

    @Column(name = "fund_date_cloture_prorogation")
    @Temporal(TemporalType.DATE)
    private Date closingProrogationDate;

    @Column(name = "fund_date_premier_decaissement")
    @Temporal(TemporalType.DATE)
    private Date firstDisbursementDate;

    @Column(name = "fund_nombre_prorogation")
    private Integer numberProrogation;

    @ManyToOne
    @JoinColumn(name = "fund_linked_funding_type")
    private LabelEntity fundingType;

    @ManyToOne
    @JoinColumn(name = "fund_linked_projet")
    private ManagementUnitEntity projet;

    @Column(name = "fund_agreement_signing_date")
    @Temporal(TemporalType.DATE)
    private Date agreementSigningDate;

    @Column(name = "fund_effective_date")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @ManyToOne
    @JoinColumn(name = "fund_linked_cash")
    private LabelEntity cashEntity;

    @ManyToOne
    @JoinColumn(name = "fund_linked_partner")
    private StructureProjectEntity partnerProjet;

}
