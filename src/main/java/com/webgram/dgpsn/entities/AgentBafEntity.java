package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "AgentBaf")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AgentBafEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -7788999012345678901L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agent_baf_id")
    private Long id;

    @Column(name = "agent_baf_numero", nullable = false)
    private Integer numero;

    @Column(name = "agent_baf_prenom", nullable = false)
    private String prenom;

    @Column(name = "agent_baf_nom", nullable = false)
    private String nom;

    @Column(name = "agent_baf_date_naissance_matricule", nullable = false)
    private LocalDate dateNaissanceMatricule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_baf_type_contrat", nullable = false)
    private LabelEntity typeContrat;

    @Column(name = "agent_baf_date_entree", nullable = false)
    private LocalDate dateEntree;

    @Column(name = "agent_baf_date_sortie")
    private LocalDate dateSortie;

    @Column(name = "agent_baf_motif_sortie")
    private String motifSortie;

    @Column(name = "agent_baf_lieu_service", nullable = false)
    private String lieuService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poste_id", nullable = false)
    private LabelEntity poste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fonction_id", nullable = false)
    private LabelEntity fonction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_baf_profil", nullable = false)
    private LabelEntity profil;

    @Column(name = "agent_baf_genre", nullable = false)
    private String genre;

    @ManyToOne()
    @JoinColumn(name = "agent_baf_diplome", nullable = false)
    private LabelEntity diplome;
}