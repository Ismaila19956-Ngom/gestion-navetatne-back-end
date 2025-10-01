package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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

    @ManyToOne()
    @JoinColumn(name = "usr_linked_agent")
    private AgentEntity agent;

    @ManyToOne()
    @JoinColumn(name = "usr_linked_profile")
    private ProfileEntity profile;

}
