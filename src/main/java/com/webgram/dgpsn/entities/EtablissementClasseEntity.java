package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "etablissement_classe")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtablissementClasseEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ec_id")
    private Long id;

    @Column(name = "ec_code")
    private String code;

    @Column(name = "ec_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "ec_linked_type_etablissement")
    private LabelEntity typeEtablissement;

    @Column(name = "ec_latitude")
    private String latitude;

    @Column(name = "ec_longitude")
    private String longitude;

    @Column(name = "ec_contact_person")
    private String contactPerson;

    @Column(name = "ec_contact_role")
    private String contactRole;

    @Column(name = "ec_contact_info")
    private String contactInfo;

    @Column(name = "ec_main_activity", length = 200)
    private String mainActivity;

    @ManyToOne
    @JoinColumn(name = "ec_region")
    private CadreLogiqueEntity region;

    @ManyToOne
    @JoinColumn(name = "ec_departement")
    private CadreLogiqueEntity departement;

    @ManyToOne
    @JoinColumn(name = "ec_linked_category_icpe")
    private LabelEntity categoryICPE;

}