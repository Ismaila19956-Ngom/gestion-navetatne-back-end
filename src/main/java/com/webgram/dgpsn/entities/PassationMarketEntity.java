package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "passation_market")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PassationMarketEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pm_id")
    private Long id;

    @Column(name = "libelle", unique=false)
    private String libelle;

    @Column(name = "reference", unique=false)
    private String reference;

    @Column(name = "estimated_amount", unique=false)
    private Double estimatedAmount;

    @Column(name = "deposit_location", unique=false)
    private String depositLocation;

    @Column(name = "Consultation_Link", unique=false)
    private String consultationLink;

    @Column(name = "email_recept", unique=false)
    private String emailRecept;

    @Column(name = "object_market", columnDefinition = "TEXT")
    private String objectMarket;

    @Column(name = "planned_start_launch")
    @Temporal(TemporalType.DATE)
    private Date plannedStartLaunch;

    @Column(name = "planned_real_launch")
    @Temporal(TemporalType.DATE)
    private Date plannedRealLaunch;

    @Column(name = "start_receipt_bids")
    @Temporal(TemporalType.DATE)
    private Date startReceiptBids;

    @Column(name = "end_receipt_bids")
    @Temporal(TemporalType.DATE)
    private Date endReceiptBids;

    @Column(name = "start_receipt_bids_real")
    @Temporal(TemporalType.DATE)
    private Date startReceiptBidsReal;

    @Column(name = "end_receipt_bids_real")
    @Temporal(TemporalType.DATE)
    private Date endReceiptBidsReal;

    @ManyToOne
    @JoinColumn(name = "type_market")
    private LabelEntity typeMarket;


    @ManyToOne
    @JoinColumn(name = "type_passation")
    private LabelEntity typePassation;

    @ManyToOne
    @JoinColumn(name = "cible")
    private LabelEntity cible;

    @ManyToOne
    @JoinColumn(name = "passation_market_linked_passation_plan")
    private PassationPlanEntity passationPlanEntity;

    @Column(name = "pass_market_description", columnDefinition = "TEXT")
    private String description;

//    @OneToMany(mappedBy = "passationMarketEntity",fetch = FetchType.EAGER)
//    private List<PassationMarketCritereEntity> passationMarketCritereEntityList;



}
