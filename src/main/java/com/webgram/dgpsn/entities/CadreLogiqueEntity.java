package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "cadre_logique")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CadreLogiqueEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cad_id")
    private Long id;

    @Column(name = "cad_code" , unique=true)
    private String code;

    @Column(name = "cad_libelle")
    private String libelle;

    @Column(name = "cad_latitude")
    private Double latitude;

    @Column(name = "cad_longitude")
    private Double longitude;

    @Column(name = "cadre_logique_type")
    @Enumerated(EnumType.STRING)
    private CadreLogiqueType typeCadreLogique;


    @ManyToOne
    @JoinColumn(name = "linked_parent")
    private CadreLogiqueEntity parent;
}