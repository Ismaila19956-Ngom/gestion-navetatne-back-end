package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.SexType;
import com.webgram.dgpsn.entities.enums.SituationMatrimoniale;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Table(name = "agent")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AgentEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agt_id")
    private Long id;

    @Column(name = "agt_nom")
    private String nom;

    @Column(name = "agt_prenom")
    private String prenom;

    @Column(name = "agt_sexe")
    @Enumerated(EnumType.STRING)
    private SexType sexe;

    @Column(name = "agt_situation_matrimoniale")
    @Enumerated(EnumType.STRING)
    private SituationMatrimoniale situationMatrimoniale;

    @Column(name = "agt_date_naissance")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "agt_lieu_naissance")
    private String lieuNaissance;

    @Column(name = "agt_adresse")
    private String adresse;

    @Column(name = "agt_email")
    private String email;

    @Column(name = "agt_telephone")
    private String telephone;

    @Column(name="agt_src")
    private  String src;

    @ManyToOne
    @JoinColumn(name = "agt_direction")
    private DirectionEntity direction;
}
