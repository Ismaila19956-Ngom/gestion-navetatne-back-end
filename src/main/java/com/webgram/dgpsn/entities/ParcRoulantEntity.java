package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "ParcRoulant")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ParcRoulantEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parc_roulant_id")
    private Long id;

    @Column(name = "parc_roulant_numero", nullable = false)
    private Integer numero;
    @ManyToOne()
    @JoinColumn(name = "parc_roulant_marque", nullable = false)
    private LabelEntity marque;

    @Column(name = "parc_roulant_immatriculation", nullable = false)
    private String immatriculation;

    @Column(name = "parc_roulant_mise_en_service", nullable = false)
    private LocalDate miseEnService;

    @ManyToOne()
    @JoinColumn(name = "parc_roulant_localisation_id", nullable = false)
    private LabelEntity localisation;

    @ManyToOne()
    @JoinColumn(name = "parc_roulant_etat")
    private LabelEntity etat;
}