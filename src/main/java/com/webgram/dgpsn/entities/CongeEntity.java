package com.webgram.dgpsn.entities;

import jakarta.persistence.*;
import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypeConge;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "conge")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CongeEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cong_id")
    private Long id;

    @Column(name = "cong_numero", length = 200)
    private String numFicheConge;

    @Column(name = "cong_libelle", length = 200)
    private String libelle;

    @Column(name = "cong_dateDemande")
    @Temporal(TemporalType.DATE)
    private Date dateDemande;

    @Column(name = "cong_dateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "cong_dateDepart")
    @Temporal(TemporalType.DATE)
    private Date dateDepart;

    @Column(name = "cong_dateReprise")
    @Temporal(TemporalType.DATE)
    private Date dateReprise;

    @Column(name = "doc_src")
    private String path;

    @Column(name = "cong_duree", length = 200)
    private Integer duree;
    @Column(name = "cong_dureeCessation", length = 200)
    private Integer dureeCessation;
    /// /
    @Column(name = "cong_numeroDecision", length = 200)
    private String numeroDecision;
    /// /
    @Column(name = "cong_soldTotal", length = 200)
    private String soldTotal;

    @Column(name = "cong_type_conge")
    @Enumerated(EnumType.STRING)
    private TypeConge typeConge;

    @Column(name = "cong_type_statut")
    @Enumerated(EnumType.STRING)
    private StatutType statutType;

    @Column(name = "cong_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "final_step")
    private Boolean finalStep;

    @JoinColumn(name = "cong_linked_agent")
    @ManyToOne
    private AgentEntity agent;

    @ManyToMany
    @JoinTable(name = "cong_document",
            joinColumns = {@JoinColumn(name = "cong_id")},
            inverseJoinColumns = {@JoinColumn(name = "document_id")})
    private List<DocumentEntity> document;

    @ManyToOne
    @JoinColumn(name = "workflow_step")
    private WorkflowStepEntity workflowStep;

}
