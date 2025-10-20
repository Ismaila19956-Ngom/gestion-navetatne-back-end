package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "conseiladministratif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConseiladministratifEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "nomca")
    private String nomca;

    @Column(name = "datedebut")
    @Temporal(TemporalType.DATE)
    private Date datedebut;

    @Column(name = "datefin")
    @Temporal(TemporalType.DATE)
    private Date datefin;

    @Column(name = "nbretotalmembre")
    private String nbretotalmembre;

    @Column(name = "frequencereunions")
    private String frequencereunions;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "procedurenomination")
    private ProcedurenominationEntity procedurenomination;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}