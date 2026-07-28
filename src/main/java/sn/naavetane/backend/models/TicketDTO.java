package sn.naavetane.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import sn.naavetane.backend.entities.enums.StatutTicket;
import sn.naavetane.backend.entities.enums.TypeAchat;
import sn.naavetane.backend.entities.enums.TypeAchat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private UUID id;
    private String qrCodePayload;
    private StatutTicket statut;
    private TypeAchat typeAchat;
    private LocalDateTime dateAchat;
    private LocalDateTime dateConsommation;
    private UUID matchId;
    
    // Id du point de vente pour le mapping au lieu de l'objet complet
    private UUID pointDeVenteId;
}
