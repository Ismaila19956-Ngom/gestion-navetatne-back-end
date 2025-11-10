package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.ResponsableMission;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypeGroupe;
import com.webgram.dgpsn.entities.enums.TypeOrdreMission;
import lombok.*;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "ordre_de_mission")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrdreMissionEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ordre_mission_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeOrdreMission ordreMission;

    @Enumerated(EnumType.STRING)
    private ResponsableMission structure;

    @Enumerated(EnumType.STRING)
    private TypeGroupe groupe;

    @Enumerated(EnumType.STRING)
    private StatutType statut;

    @Column(name = "indice", length = 100)
    private String indice;

    @Column(name = "object_mission", columnDefinition = "TEXT")
    private String objectMission;

    @Column(name = "destination_mission", columnDefinition = "TEXT")
    private String destination;

    @Column(name = "itineraire_mission", columnDefinition = "TEXT")
    private String itineraire;

    @Column(name = "dateDepartOrdre")
    @Temporal(TemporalType.DATE)
    private Date dateDepartOrdre;

    @Column(name = "dateRetourOrdre")
    @Temporal(TemporalType.DATE)
    private Date dateRetourOrdre;

    @Column(name = "dateDepartMission")
    @Temporal(TemporalType.DATE)
    private Date dateDepartMission;

    @Column(name = "dateRetourMission")
    @Temporal(TemporalType.DATE)
    private Date dateRetourMission;

    @ManyToMany
    @JoinTable(name = "ordre_mission_linked_prise_en_charge",
            joinColumns = {@JoinColumn(name = "ordre_mission_id")},
            inverseJoinColumns = {@JoinColumn(name = "priseEnChargeId")}
    )
       private List<LabelEntity> priseEnCharge;


    @ManyToOne
    @JoinColumn(name = "frais")
    private LabelEntity frais;

    @ManyToOne
    @JoinColumn(name = "moyenTranport")
    private LabelEntity moyenTranport;



    @ManyToMany
    @JoinTable(name = "ordre_mission_linked_agent",
            joinColumns = {@JoinColumn(name = "ordre_mission_id")},
            inverseJoinColumns = {@JoinColumn(name = "agent_id")})
    private List<AgentEntity> agent;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ordre_mission_document",
            joinColumns = {@JoinColumn(name = "ordre_mission_id")},
            inverseJoinColumns = {@JoinColumn(name = "document_id")})
    private List<DocumentEntity> document;



}
