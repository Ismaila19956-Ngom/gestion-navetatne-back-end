package com.webgram.dgpsn.entities;
import java.io.Serializable;

import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeParticipant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @Column(name = "statut")
    private Statut statut;

    @Column(name = "note_evaluation")
    private  Double noteEvaluation;

    @Column(name = "certificat_obtenu")
    private  String certificatObtenu;

    @Column(name = "commentaires")
    private String commentaires;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_formation_exterieur")
    private TypeParticipant typeParticipant;



    @ManyToOne
    @JoinColumn(name = "agent_Id")
    private AgentEntity agent;


    @ManyToOne
    @JoinColumn(name = "formation_Id")
    private FormationExterieurEntity formation;


    @ManyToOne
    @JoinColumn(name = "atelier_Id")
    private AtelierEntity atelier;

}