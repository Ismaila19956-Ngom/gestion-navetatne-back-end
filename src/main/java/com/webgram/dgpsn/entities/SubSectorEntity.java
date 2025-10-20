package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "sub_sector")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubSectorEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subsec_id")
    private Long id;
    @Column(name = "subsec_code")
    private String code;

    @Column(name = "subsec_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "subsec_linked_sector")
    private LabelEntity sector;
}
