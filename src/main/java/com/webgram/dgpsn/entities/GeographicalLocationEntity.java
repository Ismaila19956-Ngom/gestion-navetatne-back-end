package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "geographical_location")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeographicalLocationEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "geo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "linked_cadre_logique")
    private CadreLogiqueEntity cadreLogique;

    @ManyToOne
    @JoinColumn(name = "geographical_linked_projet")
    private ManagementUnitEntity projet;
}
