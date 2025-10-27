package com.webgram.dgpsn.entities;
import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = "plan_comptable_element")
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanComptableElementEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pce_id")
    private Long id;
    @Column(name = "pce_code", length = 100)
    private String code;
    @Column(name = "pce_libelle", length = 150)
    private String libelle;
    @Column(name = "pce_commentaire", length = 255)
    private String commentaire;
    @Enumerated(EnumType.STRING)
    @Column(name = "pce_type")
    private TypePlanComptable type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "pce_id")
    private PlanComptableElementEntity plan;
    
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PlanComptableElementEntity> children = new ArrayList<>();
}