package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.SouceBudget;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "Budget")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class BudgetEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "act_id")
    private Long id;

    @Column(name = "act_libelle")
    private String libelle;

    @Column(name = "act_souceBudget")
    private SouceBudget souceBudget;

    @Column(name = "act_estimatedAmount")
    private String estimatedAmount;

    @Column(name = "act_actualAmount")
    private Double actualAmount;

    @Column(name = "act_publicShare")
    private String publicShare;

    @Column(name = "act_privateShare")
    private String  privateShare;

    @Column(name = "act_comment" , columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "management_linked_activity")
    private ManagementUnitEntity managementUnit;

}
