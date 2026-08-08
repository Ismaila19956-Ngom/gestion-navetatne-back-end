package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.dto.RapportDTO;
import sn.naavetane.backend.entities.CategorieEntity;
import sn.naavetane.backend.entities.JourneeEntity;
import sn.naavetane.backend.entities.MatchEntity;
import sn.naavetane.backend.repositories.JourneeRepository;
import sn.naavetane.backend.repositories.TicketRepository;
import sn.naavetane.backend.entities.enums.StatutTicket;
import sn.naavetane.backend.repositories.FraudeLogRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import sn.naavetane.backend.services.PdfRendererService;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rapports")
@RequiredArgsConstructor
public class RapportController {

    private final JourneeRepository journeeRepository;
    private final TicketRepository ticketRepository;
    private final FraudeLogRepository fraudeLogRepository;
    private final PdfRendererService pdfRendererService;

    @GetMapping("/journee/{id}")
    public ResponseEntity<RapportDTO> getRapportJournee(@PathVariable UUID id) {
        RapportDTO rapport = buildRapportDTO(id);
        if (rapport == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rapport);
    }

    @GetMapping("/journee/{id}/pdf")
    public ResponseEntity<byte[]> getRapportPdf(@PathVariable UUID id) {
        RapportDTO rapport = buildRapportDTO(id);
        if (rapport == null) return ResponseEntity.notFound().build();

        Map<String, Object> vars = new HashMap<>();
        vars.put("totalVentes", rapport.getRecetteTotale());
        vars.put("totalScannes", rapport.getTotalTicketsScannes());
        vars.put("totalFrauduleux", rapport.getTotalTicketsFrauduleux());
        vars.put("categories", rapport.getDetails());

        byte[] pdf = pdfRendererService.render("rapport-template", vars);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"rapport.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    private RapportDTO buildRapportDTO(UUID id) {
        return journeeRepository.findById(id).map(journee -> {
            
            // Dédoublonner par nom comme dans CategorieController pour avoir les bonnes catégories
            List<CategorieEntity> categories = journee.getCategories() != null ? journee.getCategories().stream()
                    .filter(c -> c.getNom() != null)
                    .collect(Collectors.toMap(
                            c -> c.getNom().trim().toUpperCase(),
                            c -> c,
                            (c1, c2) -> c1
                    )).values().stream().collect(Collectors.toList()) : new ArrayList<>();

            int totalTicketsVendus = 0;
            int totalTicketsScannes = 0;
            int totalTicketsFrauduleux = 0;
            double recetteTotale = 0.0;
            List<RapportDTO.CategorieRapportDTO> details = new ArrayList<>();
            
            // Les tickets sont rattachés à la journée (journee.getId()) et non aux matchs individuels
            List<UUID> journeeIdList = Arrays.asList(journee.getId());
            
            List<StatutTicket> statutsScannes = Arrays.asList(StatutTicket.UTILISE, StatutTicket.CONSOMME);

            for (CategorieEntity cat : categories) {
                int placesTot = cat.getPlacesTotal() != null ? cat.getPlacesTotal() : 0;
                int placesRestantes = cat.getPlacesRestantes() != null ? cat.getPlacesRestantes() : 0;
                int vendus = Math.max(0, placesTot - placesRestantes);
                double prix = cat.getPrix() != null ? cat.getPrix() : 0.0;
                double recette = vendus * prix;
                
                int scannes = (int) ticketRepository.countByMatchIdInAndStatutInAndPrix(journeeIdList, statutsScannes, prix);
                int fraudes = 0;

                totalTicketsVendus += vendus;
                totalTicketsScannes += scannes;
                recetteTotale += recette;

                details.add(RapportDTO.CategorieRapportDTO.builder()
                        .nom(cat.getNom())
                        .prix(prix)
                        .placesTotal(placesTot)
                        .placesRestantes(placesRestantes)
                        .ticketsVendus(vendus)
                        .ticketsScannes(scannes)
                        .ticketsFrauduleux(fraudes)
                        .recette(recette)
                        .build());
            }

            // Les fraudes sont enregistrées dans FraudeLogEntity avec le matchId = journee.getId()
            int finalTotalFraudes = (int) fraudeLogRepository.countByMatchId(journee.getId());

            RapportDTO rapport = RapportDTO.builder()
                    .journeeId(journee.getId())
                    .date(journee.getDate() != null ? journee.getDate().toString() : "")
                    .stade(journee.getStade())
                    .totalTicketsVendus(totalTicketsVendus)
                    .totalTicketsScannes(totalTicketsScannes)
                    .totalTicketsFrauduleux(finalTotalFraudes)
                    .recetteTotale(recetteTotale)
                    .details(details)
                    .build();

            return rapport;
        }).orElse(null);
    }
}
