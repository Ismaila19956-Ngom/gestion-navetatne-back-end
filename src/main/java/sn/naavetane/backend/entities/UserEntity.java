package sn.naavetane.backend.entities;

import lombok.*;
import sn.naavetane.backend.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_login", unique = true)
    private String login;

    @Column(name = "usr_password")
    private String password;

    // First connection
    @Column(name = "usr_first_attempt")
    private boolean firstAttempt;

    // Actived or Deactived
    @Column(name = "usr_status")
    private Boolean status;

    @Column(name = "usr_online")
    private Boolean online;

    @Column(name = "usr_lastConnexion")
    private LocalDateTime lastConnexion;

    @ManyToOne()
    @JoinColumn(name = "usr_linked_agent")
    private AgentEntity agent;

    @ManyToOne()
    @JoinColumn(name = "usr_linked_structure")
    private StructureEntity structure;

    @ManyToOne()
    @JoinColumn(name = "usr_linked_profile")
    private ProfileEntity profile;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favorite_teams",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "equipe_id")
    )
    private java.util.Set<EquipeEntity> favoriteEquipes;

}
