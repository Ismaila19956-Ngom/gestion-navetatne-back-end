package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "structure_organisation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StructureOrganisationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nom;
    private String responsable;
    private String type; // Ex: ODCAV, ONCAV, CQRP
    private String zone;
    
    @Builder.Default
    private Boolean actif = true;
}
