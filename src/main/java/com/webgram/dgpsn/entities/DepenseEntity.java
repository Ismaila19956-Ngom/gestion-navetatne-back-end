package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.util.Date;

@Entity
@Table(name = "depense")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DepenseEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montantdepense")
    private Double montantdepense;

    @Column(name = "datedepense")
    @Temporal(TemporalType.DATE)
    private Date datedepense;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "naturedepense")
    private NaturedepenseEntity naturedepense;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}