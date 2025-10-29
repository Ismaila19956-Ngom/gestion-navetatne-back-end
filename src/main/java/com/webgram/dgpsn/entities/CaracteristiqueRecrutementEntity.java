package com.webgram.dgpsn.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;

@Table(name = "caracteristique_recrutement")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CaracteristiqueRecrutementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "caracteristique_recrutement_id")
    private Long id;

    @Column(name = "code_caracteristique", nullable = false)
    private String code;
    @Column(name = "libelle_caracteristique", nullable = false)
    private String libelle;
    @ManyToOne
    @JoinColumn(name = "caracteristique_type")
    private LabelEntity caracteristiqueType;

}
