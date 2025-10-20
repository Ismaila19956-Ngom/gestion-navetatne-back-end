package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "decisionreunion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DecisionreunionEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "sujet")
    private String sujet;

    @Column(name = "modalitevote")
    private String modalitevote;

    @Column(name = "resultat")
    private String resultat;

    @Column(name = "pourcentage")
    private Integer pourcentage;

    @ManyToOne
    @JoinColumn(name = "linked_reunion")
    private ReunionEntity reunion;

}