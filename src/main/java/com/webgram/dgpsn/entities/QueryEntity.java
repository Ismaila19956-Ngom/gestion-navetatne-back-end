package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.TypeApplicant;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "query")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class QueryEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qur_id")
    private Long id;

    @Column(name = "qur_object", columnDefinition = "TEXT")
    private String object;

    @Column(name = "qur_date")
    @Temporal(TemporalType.DATE)
    private Date date;
//
//    @Column(name = "qur_amount")
//    private Double amount;

    @ManyToOne
    @JoinColumn(name = "qur_linked_categorieRequete")
    private LabelEntity categorieRequete;

    @ManyToOne
    @JoinColumn(name = "qur_linked_typeRequete")
    private TypeRequeteEntity typeRequete;

    @Column(name = "qur_typ_demandeur")
    @Enumerated(EnumType.STRING)
    private TypeApplicant typeDemandeur;

    @Column(name = "qur_typ_destinataire")
    @Enumerated(EnumType.STRING)
    private TypeApplicant typeDestinataire;

//    @ManyToOne
//    @JoinColumn(name = "actor_linked_agent")
//    private AgentEntity agent;

    @ManyToOne
    @JoinColumn(name = "qur_linked_dem_actor")
    private ActorProjetEntity demandeurActor;

    @ManyToOne
    @JoinColumn(name = "qur_linked_dem_structure")
    private StructureEntity demandeurStructure;


    @ManyToOne
    @JoinColumn(name = "qur_linked_des_actor")
    private ActorProjetEntity destinataireActor;

    @ManyToOne
    @JoinColumn(name = "qur_linked_des_structure")
    private StructureEntity destinataireStructure;

    @ManyToOne
    @JoinColumn(name = "qur_linked_projet")
    private ManagementUnitEntity projet;

//    @Column(name = "qur_path")
//    private String path;

}
