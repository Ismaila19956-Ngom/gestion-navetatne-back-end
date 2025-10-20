package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "completed_Action")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedActivityEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comp_id")
    private Long id;

    @Column(name = "comp_code")
    private String code;

    @Column(name = "comp_libelle")
    private String libelle;

    @Column(name = "comp_date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "comp_date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "comp_linked_isseulog")
    private IssueLogEntity issueLog;
}
