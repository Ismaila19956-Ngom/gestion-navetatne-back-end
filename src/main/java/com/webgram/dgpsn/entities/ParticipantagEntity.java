package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

import java.lang.Boolean;

@Entity
@Table(name = "participantag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ParticipantagEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "particpants")
    private String particpants;

    @Column(name = "role")
    private String role;

    @Column(name = "coment")
    private String coment;

    @Column(name = "presence")
   private Boolean presence = true;

    @ManyToOne
    @JoinColumn(name = "linked_assemblegeneral")
    private AssemblegeneralEntity assemblegeneral;

}