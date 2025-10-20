package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.DetailType;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "impact_and_objective")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactsAndObjectiveEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "imp_ob_id")
    private Long id;

    @Column(name = "imp_ob_detail_type")
    @Enumerated(EnumType.STRING)
    private DetailType detailType;

    @Column(name = "imp_ob_code")
    private String code;

    @Column(name = "imp_ob_label", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private ManagementUnitEntity managementUnit;
}
