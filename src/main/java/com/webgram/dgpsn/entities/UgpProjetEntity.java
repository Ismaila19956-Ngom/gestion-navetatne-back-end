package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "ugp_projet")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UgpProjetEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ugp_id")
    private Long id;

    @Column(name = "ugp_existed")
    private Boolean existed;

    @Column(name = "ugp_occupied")
    private Boolean occupied;

    @Column(name = "ugp_statut")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ugp_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "ugp_linked_role")
    private RoleEntity ugpRole;
}
