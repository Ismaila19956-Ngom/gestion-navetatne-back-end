package com.webgram.dgpsn.entities;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.webgram.dgpsn.entities.enums.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormationExterieurEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre_formation")
    private String titreFormation;
    @Column(name = "Cout_Total")
    private Double coutTotal;

    @Column(name = "organisme_formateur")
    private String organismeFormateur;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "objectifs")
    private String objectifs;

    @Column(name = "date_debut")
    private LocalDateTime dateDebut;

    @Column(name = "dureeJours")
    private Long dureeJours;

    @Column(name = "date_Fin")
    private LocalDateTime dateFin;

    @ManyToOne
    @JoinColumn(name = "agent_Id")
    private AgentEntity agent;


    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;
}
