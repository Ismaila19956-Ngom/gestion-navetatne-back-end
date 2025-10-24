package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.NatureCourrier;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.UrgenceCourrier;
import com.webgram.dgpsn.entities.enums.StatutCourrier;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "courrier")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CourrierEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cour_id")
    private Long id;

    @Column(name = "cour_reference", unique = true, nullable = false)
    private String reference;

    @Column(name = "cour_correspondant", nullable = false)
    private String correspondant;

    @Column(name = "cour_objet", nullable = false)
    private String objet;

    @Column(name = "cour_description", columnDefinition = "TEXT")
    private String description;

    // TYPE : ARRIVER vs DEPART
    @Enumerated(EnumType.STRING)
    @Column(name = "cour_type", nullable = false)
    private CourrierType type;

    // NATURE : ACADEMIQUE vs ADMINISTRATIF
    @Enumerated(EnumType.STRING)
    @Column(name = "cour_nature", nullable = false)
    private NatureCourrier nature;

    @Enumerated(EnumType.STRING)
    @Column(name = "cour_urgence", nullable = false)
    private UrgenceCourrier urgence;

    @Enumerated(EnumType.STRING)
    @Column(name = "cour_statut", nullable = false)
    private StatutCourrier statut;

    // Dates communes
    @Column(name = "cour_date_courrier", nullable = false)
    private LocalDateTime dateCourrier;

    @Column(name = "cour_date_reception")
    private LocalDateTime dateReception;  // Pour courrier ARRIVER

    @Column(name = "cour_date_envoi")
    private LocalDateTime dateEnvoi;      // Pour courrier DEPART

    @Column(name = "cour_date_traitement")
    private LocalDateTime dateTraitement;

    // Champs optionnels
    @Column(name = "cour_numero_enregistrement")
    private String numeroEnregistrement;

    @Column(name = "cour_numero_suivi")
    private String numeroSuivi;

    @Column(name = "cour_mode_envoi")
    private String modeEnvoi;

    @Column(name = "cour_instructions", columnDefinition = "TEXT")
    private String instructions;

    @Column(name = "cour_notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "cour_priorite")
    private String priorite;
}