package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "projet_Entreprise")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ProjetEntrepriseEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "projEnt_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projEnt_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "projEnt_linked_entreprise")
    private EntrepriseEntity entreprise;

    @ManyToOne()
    @JoinColumn(name = "projEnt_roleEntreprise")
    private LabelEntity roleEntreprise;

    @ManyToOne
    @JoinColumn(name = "projEnt_linked_flag")
    private LabelEntity flag;
}
