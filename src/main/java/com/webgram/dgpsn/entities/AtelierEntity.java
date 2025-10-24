package com.webgram.dgpsn.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
public class AtelierEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "theme")
    private String theme;

    @Column(name = "objectif")
    private String objectif;

    @Column(name = "date")
    private LocalDate  date;

    @Column(name = "heur_debut")
    private LocalDate heurDebut;

    @Column(name = "heur_fin")
    private LocalDate heurFin;

    @Column(name = "lieu")
    private String lieu;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agent agent;


    @Column(name = "cout")
    private  Double cout;
}