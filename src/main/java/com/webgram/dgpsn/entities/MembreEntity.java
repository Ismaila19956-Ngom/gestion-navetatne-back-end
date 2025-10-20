package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.audits.Auditable;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "membre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MembreEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "agent")
    private String agent;

    @Column(name = "role")
    private String role;

    @Column(name = "mandat")
    private Integer mandat;

    @ManyToOne
    @JoinColumn(name = "linked_conseiladministratif")
    private ConseiladministratifEntity conseiladministratif;

}