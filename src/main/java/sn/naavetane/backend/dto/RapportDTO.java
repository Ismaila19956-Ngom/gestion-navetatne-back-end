package sn.naavetane.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportDTO {
    private UUID journeeId;
    private String date;
    private String stade;
    private int totalTicketsVendus;
    private int totalTicketsScannes;
    private int totalTicketsFrauduleux;
    private double recetteTotale;
    private List<CategorieRapportDTO> details;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorieRapportDTO {
        private String nom;
        private Double prix;
        private int placesTotal;
        private int placesRestantes;
        private int ticketsVendus;
        private int ticketsScannes;
        private int ticketsFrauduleux;
        private double recette;
    }
}
