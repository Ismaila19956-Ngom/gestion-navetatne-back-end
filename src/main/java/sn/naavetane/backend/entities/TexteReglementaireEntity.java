package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "textes_reglementaires")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TexteReglementaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private String fichierUrl; // Lien vers le document PDF
    
    @Builder.Default
    private Boolean actif = true;
}
