package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "etat_financier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EtatFinancierEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "chiffre_affaire")
    private Double chiffreAffaire;

    @Column(name = "cout_vente")
    private Double coutVente;

    @Column(name = "charge_exploitation")
    private Double chargeExploitation;

    @Column(name = "resultat_exploitation")
    private Double resultatExploitation;

    @Column(name = "resultat_financier")
    private Double resultatFinancier;

    @Column(name = "impot")
    private Double impot;

    @Column(name = "resultat_net")
    private Double resultatNet;

    @ManyToOne
    @JoinColumn(name = "linked_entreprise")
    private EntrepriseEntity entreprise;

}