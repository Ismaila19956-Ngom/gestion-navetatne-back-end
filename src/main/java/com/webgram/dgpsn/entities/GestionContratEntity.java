package com.webgram.dgpsn.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "gestion_contrat")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GestionContratEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "type_contrat_id")
    private LabelEntity typeContrat;

    @Column(name = "description", length = 1000)
    private String description;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private AgentEntity agent;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
