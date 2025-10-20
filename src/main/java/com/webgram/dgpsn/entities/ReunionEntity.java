package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import com.webgram.dgpsn.entities.enums.Statut;
import java.util.Date;

@Entity
@Table(name = "reunion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ReunionEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "typereunion")
    private TypereunionEntity typereunion;

    @Column(name = "participant")
    private String participant;

    @Column(name = "dateprevue")
    @Temporal(TemporalType.DATE)
    private Date dateprevue;

    @Column(name = "datereelle")
    @Temporal(TemporalType.DATE)
    private Date datereelle;

    @Column(name = "heuredebutprevue")
    private String heuredebutprevue;

    @Column(name = "heurefinprevue")
    private String heurefinprevue;

    @Column(name = "heuredebutreelle")
    private String heuredebutreelle;

    @Column(name = "heurefinreelle")
    private String heurefinreelle;

    @Column(name = "coment")
    private String coment;

    @ManyToOne
    @JoinColumn(name = "linked_conseiladministratif")
    private ConseiladministratifEntity conseiladministratif;

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private Statut statut;

}