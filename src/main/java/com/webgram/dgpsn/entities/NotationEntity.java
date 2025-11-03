package com.webgram.dgpsn.entities;

import com.webgram.dgpsn.entities.enums.CritereNotation;
import com.webgram.dgpsn.entities.enums.ValeurNotation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CritereNotation critere;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ValeurNotation valeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidat_id", nullable = false)
    private CandidatEntity candidat;
}
