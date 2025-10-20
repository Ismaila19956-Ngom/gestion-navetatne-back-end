package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sta_id")
    private Long id;

    @Column(name = "sta_code")
    private String code;

    @Column(name = "sta_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "sta_type_linked_label")
    private LabelEntity type;

    @ManyToOne
    @JoinColumn(name = "sta_region")
    private CadreLogiqueEntity region;

    @ManyToOne
    @JoinColumn(name = "sta_departement")
    private CadreLogiqueEntity departement;

    @Column(name = "sta_latitude")
    private String latitude;

    @Column(name = "sta_longitude")
    private String longitude;

}