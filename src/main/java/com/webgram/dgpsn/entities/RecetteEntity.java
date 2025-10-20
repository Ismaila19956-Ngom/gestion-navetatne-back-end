package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "recette")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecetteEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montantdelarecette")
    private Double montantdelarecette;

    @Column(name = "datedelatransaction")
    @Temporal(TemporalType.DATE)
    private Date datedelatransaction;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "naturedelarecette")
    private NaturedelarecetteEntity naturedelarecette;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}