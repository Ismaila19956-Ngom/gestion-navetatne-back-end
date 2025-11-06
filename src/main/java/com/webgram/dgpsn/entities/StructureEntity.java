package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.TypeStructure;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Table(name = "stucture")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StructureEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "str_id")
    private Long id;

    @Column(name = "str_code")
    private String code;

    @Column(name = "str_nom")
    private String nom;

    @Column(name = "str_address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "str_city")
    private String city;

    @Column(name = "str_telephone")
    private String telephone;

    @Column(name = "str_fax")
    private String fax;

    @Column(name = "str_email")
    private String email;

    @Column(name = "str_web_site")
    private String webSite;

    @Column(name = "str_startDateCooperation")
    @Temporal(TemporalType.DATE)
    private Date sartDateCooperation;

    @Column(name = "str_web_mechism_of_intervention")
    private String mechanismOfIntervention;

    @Column(name = "str_web_responsable")
    private String responsable;

    @Column(name = "str_is_etat")
    private boolean etat;

    @Enumerated(EnumType.STRING)
    @Column(name = "str_type_structure")
    private TypeStructure typeStructure;

    @Column(name = "str_pforR")
    private boolean pforR;

    @Column(name = "str_observation")
    private String observation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "str_linked_country")
    private LabelEntity country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "str_linked_partner_group")
    private LabelEntity partnerGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "str_linked_structure_tutelle")
    private StructureEntity tutelle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructureEntity that = (StructureEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
