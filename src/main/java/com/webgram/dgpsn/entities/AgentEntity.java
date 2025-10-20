package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "agent")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agt_id")
    private Long id;

    @Column(name = "agt_matricule", length = 100)
    private String matricule;

    @Column(name = "agt_nom", length = 50)
    private String nom;

    @Column(name = "agt_prenom", length = 100)
    private String prenom;

    @Column(name = "agt_adresse", length = 150)
    private String adresse;

    @Column(name = "agt_email", length = 30, unique = true)
    private String email;

    @Column(name = "agt_telephone", length = 20, unique = true)
    private String telephone;

    @Column(name="agt_photo",length = 50)
    private  String photoProfil;

    @ManyToOne()
    @JoinColumn(name = "agt_linked_structure")
    private StructureEntity structure;

    @ManyToOne()
    @JoinColumn(name = "agt_linked_fonction")
    private LabelEntity fonction;

    @Column(name = "agt_src")
    private String src;

    @ManyToOne
    @JoinColumn(name = "agt_linked_direction")
    private DirectionEntity direction;

}
