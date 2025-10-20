package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "actor_projet")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ActorProjetEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "acp_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actor_linked_projet")
    private ManagementUnitEntity projet;

    @ManyToOne
    @JoinColumn(name = "actor_linked_agent")
    private AgentEntity agent;

    @ManyToOne
    @JoinColumn(name = "actor_linked_role")
    private RoleEntity role;
}
