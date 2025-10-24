package com.webgram.dgpsn.entities;
import java.io.Serializable;
import java.time.LocalDate;

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
public class MissionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_ordre")
    private Integer numeroOrdre;

    @Column(name = "type")
    private String type;

    @Column(name = "objet")
    private String objet;

    @Column(name = "destination")
    private String destination;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private  LocalDate dateFin;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "budget")
    private Double budget;

    @Column(name = "responsable")

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private AgentEntity agent;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Column(name = "rapport")
    private String rapport;

    @Column(name = "date_rapport")
    private  LocalDate dateRapport;
}
