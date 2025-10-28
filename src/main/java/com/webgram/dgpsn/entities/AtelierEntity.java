package com.webgram.dgpsn.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.webgram.dgpsn.entities.enums.Statut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.loadtime.Agent;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "atalier")

public class AtelierEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre_atelier")
    private String titreAtelier;

    @Column(name = "theme")
    private String theme;

    @Column(name = "objectif")
    private String objectif;

    @Column(name = "date_atelier")
    private LocalDateTime dateAtelier;

    @Column(name = "heur_debut")
    private LocalDateTime heurDebut;

    @Column(name = "heur_fin")
    private LocalDateTime heurFin;

    @Column(name = "lieu")
    private String lieu;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private AgentEntity agent;

    @Column(name = "cout_Organisation")
    private  Double coutOrganisation;

    @Column(name = "nombre_participants_max")
    private Long nombreParticipantsMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;
}