package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.Criticity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "risk")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rsk_id")
    private Long id;

    @Column(name = "rsk_libelle")
    private String libelle;

    @Column(name = "rsk_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "rsk_author")
    private String author;

    @Column(name = "rsk_identification_date")
    @Temporal(TemporalType.DATE)
    private Date identificationDate;

    @Column(name = "rsk_probability")
    private Double probability;

    @Column(name = "rsk_resolution_date")
    @Temporal(TemporalType.DATE)
    private Date resolutionDate;


    @ManyToOne
    @JoinColumn(name = "rsk_linked_projet")
    private ManagementUnitEntity projet;

    @Column(name = "rsk_delay_impact")
    @Enumerated(EnumType.STRING)
    private Criticity delayImpact;

    @Column(name = "rsk_financial_impact")
    @Enumerated(EnumType.STRING)
    private Criticity financialImpact;

    @Column(name = "rsk_criticity")
    @Enumerated(EnumType.STRING)
    private Criticity criticity;

    @ManyToOne
    @JoinColumn(name = "rsk_linked_status")
    private StatusEntity status;

    @ManyToOne
    @JoinColumn(name = "rsk_linked_nature")
    private LabelEntity nature;


}
