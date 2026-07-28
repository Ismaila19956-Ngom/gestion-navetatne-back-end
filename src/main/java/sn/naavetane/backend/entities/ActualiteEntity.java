package sn.naavetane.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "actualites")
@Data
public class ActualiteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String contenu;
    
    private String imageCouverture;
    private String fichierJoint; // ex: PDF communiqué
    
    private LocalDateTime datePublication = LocalDateTime.now();
    private Boolean actif = true;
    
    private String type = "ACTUALITE"; // ACTUALITE ou COMMUNIQUE
}
