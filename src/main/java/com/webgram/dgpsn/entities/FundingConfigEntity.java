package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.FundingTypeConfig;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "funding_Config")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class FundingConfigEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "conf_id")
    private Long id;

    @Column(name = "conf_libelle")
    private String libelle;

    @Column(name = "conf_annee")
    private String annee;

    @Column(name = "conf_startingDate")
    @Temporal(TemporalType.DATE)
    private Date startingDate;

    @Column(name = "conf_endingDate")
    @Temporal(TemporalType.DATE)
    private Date endingDate;

    @Column(name = "conf_estimatedAmount")
    private String estimatedAmount;

    @Column(name = "conf_actualAmount")
    private Double actualAmount;

    @Column(name = "conf_comment" , columnDefinition = "TEXT")
    private String comment;

    @Column(name = "conf_funding_type")
    @Enumerated(EnumType.STRING)
    private FundingTypeConfig fundingTypeConfig;

    @ManyToOne
    @JoinColumn(name = "management_linked_activity")
    private ManagementUnitEntity managementUnit;

}
