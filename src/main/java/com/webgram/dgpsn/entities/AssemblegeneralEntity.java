package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "assemblegeneral")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AssemblegeneralEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "dateassemble")
    @Temporal(TemporalType.DATE)
    private Date dateassemble;

    @Column(name = "ville")
    private String ville;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "quorum")
    private Integer quorum;

    @Column(name = "quorumen")
    private String quorumen;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "conseiladministratif")
    private ConseiladministratifEntity conseiladministratif;

    @ManyToOne
    @JoinColumn(name = "typeag")
    private TypeagEntity typeag;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}