package sn.naavetane.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDeVenteDTO {
    private UUID id;
    private String nom;
    private Long guichetierId;
    private Double montantEncaisse;
    private LocalDateTime createdAt;
}
