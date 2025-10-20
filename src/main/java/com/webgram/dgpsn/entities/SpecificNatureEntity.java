package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "nature_specifique")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecificNatureEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nat_spec_id")
    private Long id;

    @Column(name = "nat_spec_code", unique=true)
    private String code;

    @Column(name = "nat_spec_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "nat_spec_linked_nature")
    private LabelEntity nature;
}
