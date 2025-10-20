package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.MediathequeType;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "mediatheque")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediathequeEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "med_id")
    private Long id;

    @Column(name = "med_libelle")
    private String libelle;

    @Column(name = "med_src")
    private String src;

    @Column(name = "med_date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "med_mediatheque_type")
    private MediathequeType mediathequeType;

    @ManyToOne
    @JoinColumn(name = "med_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "med_linked_entreprise")
    private EntrepriseEntity entreprise;
}
