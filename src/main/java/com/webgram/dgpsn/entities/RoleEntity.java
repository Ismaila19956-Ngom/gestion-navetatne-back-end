package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "role")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;
    @Column(name = "role_code", unique = true)
    private String code;
    @Column(name = "role_libelle")
    private String libelle;
    @Column(name = "role_ugp")
    private Boolean ugp;
}