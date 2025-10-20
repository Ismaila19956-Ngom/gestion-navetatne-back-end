package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "type_requete")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeRequeteEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "req_id")
    private Long id;

    @Column(name = "req_code", unique=true)
    private String code;

    @Column(name = "req_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "ind_linked_categorieRequete")
    private LabelEntity categorieRequete;


}
