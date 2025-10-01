package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Table(name = "direction")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectionEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dir_id")
    private Long id;

    @Column(name = "dir_code", unique = true)
    private String code;

    @Column(name = "dir_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "dir_parent")
    private DirectionEntity parent;
}
