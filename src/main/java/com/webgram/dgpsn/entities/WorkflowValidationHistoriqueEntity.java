package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.WorkflowType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Table(name = "workflow_validation_historique")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkflowValidationHistoriqueEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wvh_id")
    private Long id;

    @Column(name = "wvh_date")
    private Date date;

    @Column(name = "hwvh_validation")
    private Boolean validation;

    @Column(name = "wvh_commentaire", columnDefinition = "TEXT")
    private String commentaire;

    @Column(name = "wvh_workflowType")
    @Enumerated(EnumType.STRING)
    private WorkflowType workflowType;

    @Column(name = "wvh_entity_id")
    private Long entityId;

    @Column(name = "wvh_year") // Nouvelle colonne pour l'année
    private Integer year;

    @Column(name = "wvh_month") // Nouvelle colonne pour le mois
    private Integer month;

    @Column(name = "wvh_date_calcul")
    private LocalDate dateCalcul;

    @ManyToOne
    @JoinColumn(name = "wvh_linked_workflow_step")
    private WorkflowStepEntity etape;

    @ManyToOne
    @JoinColumn(name = "wvh_linked_user")
    private UserEntity user;
}
