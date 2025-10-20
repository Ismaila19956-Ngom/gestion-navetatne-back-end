package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "Passation")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PassationEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -1234567890123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "passation_id")
    private Long id;

    @Column(name = "passation_reference", nullable = false)
    private String reference;

    @Column(name = "passation_realisation", nullable = false)
    private String realisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_financement_id", nullable = false)
    private LabelEntity sourceFinancement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_marche_id", nullable = false)
    private LabelEntity typeMarche;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_passation_id", nullable = false)
    private LabelEntity modePassation;

    @Column(name = "passation_montant", nullable = false)
    private Double montant;

    @Column(name = "passation_date_lancement", nullable = false)
    private LocalDate dateLancement;

    @Column(name = "passation_date_attribution", nullable = false)
    private LocalDate dateAttribution;

    @Column(name = "passation_date_demarrage", nullable = false)
    private LocalDate dateDemarrage;

    @Column(name = "passation_date_achevement", nullable = false)
    private LocalDate dateAchevement;
}