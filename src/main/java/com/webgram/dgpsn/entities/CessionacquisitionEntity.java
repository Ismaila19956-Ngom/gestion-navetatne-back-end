package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "cessionacquisition")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CessionacquisitionEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "typeoperation")
    private String typeoperation;

    @Column(name = "montantoperation")
    private Integer montantoperation;

    @Column(name = "prixaction")
    private Integer prixaction;

    @Column(name = "nombretitre")
    private Integer nombretitre;

    @Column(name = "participationoperation")
    private Integer participationoperation;

    @Column(name = "vcoperation")
    private Integer vcoperation;

    @Column(name = "nombreactioncree")
    private Integer nombreactioncree;

    @Column(name = "valeurdecotesurcote")
    private Integer valeurdecotesurcote;

    @Column(name = "decotesurcote")
    private String decotesurcote;

    @Column(name = "coment")
    private String coment;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}