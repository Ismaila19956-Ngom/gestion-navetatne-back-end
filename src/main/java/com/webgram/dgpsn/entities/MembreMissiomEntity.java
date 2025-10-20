package com.webgram.dgpsn.entities;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;


@Table(name = "membre_mission")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MembreMissiomEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membre_mission_id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "structure")
    private String structure;

    @Column(name = "telephone")
    private String telephone;
}
