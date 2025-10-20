package com.webgram.dgpsn.entities;

import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "participant_mission_activity")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ParticipantMissionEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "part_id")
    private Long id;

    @Column(name = "part_comment" , columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "part_linked_assignment")
    private AssignmentEntity assignment;

    @ManyToOne
    @JoinColumn(name = "part_linked_actor")
    private ActorProjetEntity actor;

    @ManyToOne
    @JoinColumn(name = "part_linked_role")
    private RoleEntity role;
}
