package com.webgram.dgpsn.entities;
import java.io.Serializable;
import java.time.LocalDate;

import com.webgram.dgpsn.entities.enums.Statut;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormationExterieurEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "projet_id")
    private Long projectId;

    @Column(name = "titre_formation")
    private String titreFormation;

    @Column(name = "organisme_formateur")
    private String organismeFormateur;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;
}
