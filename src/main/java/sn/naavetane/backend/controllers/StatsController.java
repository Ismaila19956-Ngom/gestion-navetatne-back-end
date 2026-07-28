package sn.naavetane.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.naavetane.backend.entities.JourneeEntity;
import sn.naavetane.backend.entities.TransactionEntity;
import sn.naavetane.backend.entities.TransactionStatus;
import sn.naavetane.backend.entities.TicketEntity;
import sn.naavetane.backend.entities.enums.StatutTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final sn.naavetane.backend.repositories.TicketRepository ticketRepository;
    private final sn.naavetane.backend.repositories.TransactionRepository transactionRepository;
    private final sn.naavetane.backend.repositories.FraudeLogRepository fraudeLogRepository;
    private final sn.naavetane.backend.repositories.JourneeRepository journeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(StatsController.class);

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats(@RequestParam(required = false) String journeeId) {
        Map<String, Object> stats = new HashMap<>();

        try {
            java.util.UUID filterMatchId = null;
            if (journeeId != null && !journeeId.isEmpty() && !journeeId.equals("ALL")) {
                filterMatchId = java.util.UUID.fromString(journeeId);
            }
            final java.util.UUID finalFilterMatchId = filterMatchId;

            java.time.LocalDate filterDate = null;
            if (finalFilterMatchId != null) {
                Optional<sn.naavetane.backend.entities.JourneeEntity> jOpt = journeeRepository.findById(finalFilterMatchId);
                if (jOpt.isPresent()) {
                    filterDate = jOpt.get().getDate();
                }
            }
            final java.time.LocalDate finalFilterDate = filterDate;

            // 1. Nombre total de billets vendus
            long totalTickets = 0;
            if (finalFilterMatchId != null) {
                totalTickets = ticketRepository.countByMatchId(finalFilterMatchId);
            } else {
                totalTickets = ticketRepository.count();
            }
            stats.put("totalTicketsSold", totalTickets);
            
            // 1b. Billets vendus par prix (pour déduire les catégories côté front)
            Map<Integer, Long> ticketsByPrice = new HashMap<>();
            List<Object[]> priceStats = finalFilterMatchId != null ? 
                    ticketRepository.countTicketsByPriceAndMatchId(finalFilterMatchId) : 
                    ticketRepository.countTicketsByPrice();
            
            for (Object[] row : priceStats) {
                if (row[0] != null && row[1] != null) {
                    Double price = (Double) row[0];
                    Long count = (Long) row[1];
                    ticketsByPrice.put(price.intValue(), count);
                }
            }
            stats.put("ticketsByPrice", ticketsByPrice);

            // 2. Nombre de billets scannés (CONSOMME)
            long scannedTickets = 0;
            if (finalFilterMatchId != null) {
                scannedTickets = ticketRepository.countByMatchIdAndStatut(finalFilterMatchId, StatutTicket.CONSOMME);
            } else {
                scannedTickets = ticketRepository.countByStatut(StatutTicket.CONSOMME);
            }
            stats.put("totalTicketsScanned", scannedTickets);

            // 3. Nombre de fraudes (tentatives bloquées)
            long totalFraudes = 0;
            if (finalFilterMatchId != null) {
                // On compte les fraudes liées spécifiquement à ce match
                totalFraudes = fraudeLogRepository.countByMatchId(finalFilterMatchId);
                
                // + Les faux billets tentés le jour de l'événement (sans matchId)
                if (finalFilterDate != null) {
                    java.time.LocalDateTime startOfDay = finalFilterDate.atStartOfDay();
                    java.time.LocalDateTime endOfDay = finalFilterDate.plusDays(1).atStartOfDay();
                    totalFraudes += fraudeLogRepository.countByDateFraudeBetweenAndMatchIdIsNull(startOfDay, endOfDay);
                }
            } else {
                totalFraudes = fraudeLogRepository.count();
            }
            stats.put("totalFraudes", totalFraudes);

            // 4. Chiffre d'affaires total
            double transactionRevenue = 0.0;
            if (finalFilterMatchId == null) {
                transactionRevenue = transactionRepository.sumAmountByStatus(TransactionStatus.SUCCESS);
            } else {
                long digitalTicketsCount = ticketRepository.countByMatchIdAndTypeAchat(finalFilterMatchId, sn.naavetane.backend.entities.enums.TypeAchat.DIGITAL_WAVE);
                transactionRevenue = digitalTicketsCount * 1000.0; // Simplification métier demandée
            }
                    
            // Ajouter les revenus des ventes physiques (qui n'ont pas de transaction)
            double physicalRevenue = 0.0;
            if (finalFilterMatchId == null) {
                physicalRevenue = ticketRepository.sumPrixByTypeAchat(sn.naavetane.backend.entities.enums.TypeAchat.PHYSIQUE_CASH);
            } else {
                physicalRevenue = ticketRepository.sumPrixByMatchIdAndTypeAchat(finalFilterMatchId, sn.naavetane.backend.entities.enums.TypeAchat.PHYSIQUE_CASH);
            }
            
            double totalRevenue = transactionRevenue + physicalRevenue;
            stats.put("totalRevenue", totalRevenue);
            stats.put("onlineRevenue", transactionRevenue);
            stats.put("physicalRevenue", physicalRevenue);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des statistiques du dashboard", e);
            
            // Retourner des valeurs par défaut pour ne pas bloquer l'application mobile
            stats.put("totalTicketsSold", 0);
            stats.put("totalTicketsScanned", 0);
            stats.put("totalFraudes", 0);
            stats.put("totalRevenue", 0.0);
            stats.put("onlineRevenue", 0.0);
            stats.put("physicalRevenue", 0.0);
            
            return ResponseEntity.ok(stats);
        }
    }

    @GetMapping("/report-html")
    public org.springframework.web.servlet.ModelAndView getReportHtml(
            @RequestParam(required = false) String journeeId,
            @RequestParam(required = false) String vendeur) {
        org.springframework.web.servlet.ModelAndView mav = new org.springframework.web.servlet.ModelAndView("rapport-stats");
        
        try {
            ResponseEntity<Map<String, Object>> statsResponse = getDashboardStats(journeeId);
            Map<String, Object> stats = statsResponse.getBody();
            
            String filtreNom = "Toutes les journées";
            if (journeeId != null && !journeeId.isEmpty() && !journeeId.equals("ALL")) {
                java.util.UUID matchId = java.util.UUID.fromString(journeeId);
                Optional<sn.naavetane.backend.entities.JourneeEntity> journeeOpt = journeeRepository.findById(matchId);
                if (journeeOpt.isPresent()) {
                    filtreNom = "Stade " + journeeOpt.get().getStade() + " - " + journeeOpt.get().getDate();
                }
            }
            
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            mav.addObject("dateExport", java.time.LocalDateTime.now().format(formatter));
            mav.addObject("filtreNom", filtreNom);
            if (vendeur != null && !vendeur.trim().isEmpty()) {
                mav.addObject("vendeur", vendeur);
            }
            
            mav.addObject("totalBilletsVendus", stats.get("totalTicketsSold"));
            mav.addObject("totalBilletsScannes", stats.get("totalTicketsScanned"));
            mav.addObject("totalFraudes", stats.get("totalFraudes"));
            mav.addObject("totalRevenus", stats.get("totalRevenue"));
            mav.addObject("onlineRevenus", stats.get("onlineRevenue"));
            mav.addObject("physicalRevenus", stats.get("physicalRevenue"));
            
        } catch (Exception e) {
            logger.error("Erreur génération rapport HTML", e);
            mav.addObject("error", "Erreur lors de la génération du rapport");
        }
        
        return mav;
    }
}
