package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "SuiviInventaire")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SuiviInventaireEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "suivi_inventaire_id")
    private Long id;

    @Column(name = "suivi_inventaire_nom_materiel", nullable = false)
    private String nomMateriel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private LabelEntity typeInventaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suivi_inventaire_localisation", nullable = false)
    private LabelEntity localisation;

    @Column(name = "suivi_inventaire_occupant_responsable", nullable = false)
    private String occupantResponsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suivi_inventaire_etat", nullable = false)
    private LabelEntity etat;

    @Column(name = "suivi_inventaire_date_acquisition", nullable = false)
    private LocalDate dateAcquisition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suivi_inventaire_bailleur", nullable = false)
    private LabelEntity bailleur;

    @Column(name = "suivi_inventaire_observation")
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventaire_id", nullable = false)
    private InventaireEntity inventaire;
}