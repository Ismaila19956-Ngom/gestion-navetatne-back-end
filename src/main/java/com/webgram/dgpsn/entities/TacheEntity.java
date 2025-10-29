package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tache")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TacheEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "trimestre")
    private Integer trimestre;

    @Column(name = "mois")
    private Integer mois;

    @Column(name = "semaines")
    private String semaines; // Format: "1,2,3,4" ou stockage JSON

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Column(name = "commentaire", length = 1000)
    private String commentaire;

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutTache statut;

    @ManyToOne
    @JoinColumn(name = "activite_id")
    private ManagementUnitEntity activite;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private ValueIndicatorEntity indicator;

    public enum StatutTache {
        PLANIFIE,
        EN_COURS,
        TERMINE
    }
}
