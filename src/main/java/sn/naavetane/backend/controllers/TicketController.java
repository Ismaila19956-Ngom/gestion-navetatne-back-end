package sn.naavetane.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sn.naavetane.backend.entities.CategorieEntity;
import sn.naavetane.backend.entities.JourneeEntity;
import sn.naavetane.backend.entities.TicketEntity;
import sn.naavetane.backend.entities.enums.StatutTicket;
import sn.naavetane.backend.repositories.CategorieRepository;
import sn.naavetane.backend.repositories.JourneeRepository;
import sn.naavetane.backend.repositories.TicketRepository;

import java.util.*;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "ticket-controller", description = "Endpoints pour la gestion des billets (QR Code)")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private JourneeRepository journeeRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private sn.naavetane.backend.repositories.FraudeLogRepository fraudeLogRepository;

    private void logFraude(String payload, String message, UUID matchId) {
        sn.naavetane.backend.entities.FraudeLogEntity fraude = sn.naavetane.backend.entities.FraudeLogEntity.builder()
                .qrCodePayload(payload)
                .message(message)
                .dateFraude(java.time.LocalDateTime.now())
                .matchId(matchId)
                .build();
        fraudeLogRepository.save(fraude);
    }

    @GetMapping("/mes-billets")
    public ResponseEntity<List<TicketEntity>> getMesBillets() {
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Endpoint Anti-Fraude : scanner un QR Code à l'entrée du stade
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/scan")
    public ResponseEntity<Map<String, Object>> scanTicket(@RequestBody Map<String, String> request) {
        String qrCodePayload = request.get("qrCodePayload");
        Map<String, Object> response = new HashMap<>();

        if (qrCodePayload == null || qrCodePayload.isEmpty()) {
            response.put("error", "Veuillez fournir un QR Code valide.");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<TicketEntity> ticketOpt = ticketRepository.findByQrCodePayload(qrCodePayload);

        if (ticketOpt.isEmpty()) {
            String errorMsg = "Billet inconnu ou faux billet.";
            logFraude(qrCodePayload, errorMsg, null);
            response.put("error", errorMsg);
            response.put("status", "INVALIDE");
            return ResponseEntity.status(404).body(response);
        }

        TicketEntity ticket = ticketOpt.get();

        // Vérification de la date de l'événement
        if (ticket.getMatchId() != null) {
            Optional<JourneeEntity> journeeOpt = journeeRepository.findById(ticket.getMatchId());
            if (journeeOpt.isPresent()) {
                JourneeEntity journee = journeeOpt.get();
                if (journee.getDate().isBefore(java.time.LocalDate.now())) {
                    String errorMsg = "Billet expiré ! L'événement est déjà passé (" + journee.getDate() + ").";
                    logFraude(qrCodePayload, errorMsg, ticket.getMatchId());
                    response.put("error", errorMsg);
                    response.put("status", "EXPIRE");
                    return ResponseEntity.status(403).body(response);
                }
            }
        }

        if (ticket.getStatut() == StatutTicket.CONSOMME || ticket.getStatut() == StatutTicket.UTILISE) {
            String errorMsg = "Ce billet a DÉJÀ ÉTÉ SCANNÉ (Fraude / Doublon).";
            logFraude(qrCodePayload, errorMsg, ticket.getMatchId());
            response.put("error", errorMsg);
            response.put("status", "FRAUDE");
            return ResponseEntity.status(403).body(response);
        }

        if (ticket.getStatut() == StatutTicket.VALIDE) {
            ticket.setStatut(StatutTicket.CONSOMME);
            ticketRepository.save(ticket);

            String categorieNom = "Billet standard";
            if (ticket.getMatchId() != null && ticket.getPrix() != null) {
                Optional<JourneeEntity> jOpt = journeeRepository.findById(ticket.getMatchId());
                if (jOpt.isPresent() && jOpt.get().getCategories() != null) {
                    for (CategorieEntity c : jOpt.get().getCategories()) {
                        if (c.getPrix() != null && c.getPrix().equals(ticket.getPrix())) {
                            categorieNom = c.getNom();
                            break;
                        }
                    }
                }
            }

            response.put("message", "Billet valide. Vous pouvez laisser passer.");
            response.put("status", "OK");
            response.put("ticketId", ticket.getQrCodePayload());
            response.put("categorie", categorieNom);
            response.put("quantite", 1);
            return ResponseEntity.ok(response);
        }

        response.put("error", "Statut du billet non reconnu.");
        return ResponseEntity.status(500).body(response);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Vente physique au guichet avec gestion du stock (décrémentation)
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/vente-physique")
    @Transactional
    public ResponseEntity<Map<String, Object>> vendreBilletsPhysiques(@RequestBody Map<String, Object> request) {
        String journeeIdStr = (String) request.get("journeeId");
        UUID matchId = UUID.fromString(journeeIdStr);

        // ── Validation du statut de la journée (Sécurité Guichet Clôturé) ──
        Optional<JourneeEntity> journeeOpt = journeeRepository.findById(matchId);
        if (journeeOpt.isPresent()) {
            JourneeEntity journee = journeeOpt.get();
            String statut = journee.getStatut();
            if (statut != null && !statut.equalsIgnoreCase("PROGRAMMEE")) {
                Map<String, Object> erreur = new HashMap<>();
                erreur.put("error", "Ventes bloquées. Cette journée est actuellement " + statut + ".");
                erreur.put("statut", statut);
                return ResponseEntity.badRequest().body(erreur);
            }
        }

        List<Map<String, Object>> generatedTickets = new ArrayList<>();
        List<TicketEntity> ticketsToSave = new ArrayList<>();

        if (request.containsKey("ticketsDetails")) {
            // ── Mode détaillé : liste de catégories avec quantité et prix ──
            List<Map<String, Object>> details = (List<Map<String, Object>>) request.get("ticketsDetails");

            for (Map<String, Object> detail : details) {
                int qte = Integer.parseInt(detail.get("quantite").toString());
                Double prix = Double.valueOf(detail.get("prix").toString());

                // Trouver la catégorie correspondante dans la journée pour décrémenter son stock
                CategorieEntity categorieADecrémenter = findCategorieForJournee(matchId, detail.get("categorieId"), prix);

                // ── Validation du stock disponible ──
                if (categorieADecrémenter != null && categorieADecrémenter.getPlacesTotal() != null) {
                    int restantes = categorieADecrémenter.getPlacesRestantes() != null
                            ? categorieADecrémenter.getPlacesRestantes() : 0;
                    if (qte > restantes) {
                        Map<String, Object> erreur = new HashMap<>();
                        erreur.put("error", "Stock insuffisant pour la catégorie \""
                                + categorieADecrémenter.getNom() + "\". Demandé: " + qte
                                + ", Disponible: " + restantes);
                        erreur.put("categorieNom", categorieADecrémenter.getNom());
                        erreur.put("demande", qte);
                        erreur.put("disponible", restantes);
                        return ResponseEntity.badRequest().body(erreur);
                    }
                }

                // ── Génération des tickets ──
                for (int i = 0; i < qte; i++) {
                    String payload = "TICKET_" + journeeIdStr + "_"
                            + System.currentTimeMillis() + "_" + i + "_"
                            + UUID.randomUUID().toString().substring(0, 5);
                    ticketsToSave.add(TicketEntity.builder()
                            .qrCodePayload(payload)
                            .statut(StatutTicket.VALIDE)
                            .typeAchat(sn.naavetane.backend.entities.enums.TypeAchat.PHYSIQUE_CASH)
                            .matchId(matchId)
                            .prix(prix)
                            .dateAchat(java.time.LocalDateTime.now())
                            .build());
                }

                // ── Décrémentation du stock ──
                if (categorieADecrémenter != null && categorieADecrémenter.getPlacesTotal() != null) {
                    String nomCat = categorieADecrémenter.getNom();
                    List<CategorieEntity> journeeCats = categorieRepository.findByJourneeId(matchId);
                    for (CategorieEntity c : journeeCats) {
                        if (c.getNom() != null && c.getNom().equalsIgnoreCase(nomCat) && c.getPlacesTotal() != null) {
                            int r = c.getPlacesRestantes() != null ? c.getPlacesRestantes() : 0;
                            c.setPlacesRestantes(Math.max(0, r - qte));
                            categorieRepository.save(c);
                        }
                    }
                }
            }

        } else {
            // ── Mode simple (rétrocompatibilité) ──
            Integer quantite = Integer.parseInt(request.get("quantite").toString());
            Double prixTotal = request.get("prixTotal") != null
                    ? Double.valueOf(request.get("prixTotal").toString()) : 0.0;
            Double prixUnitaire = quantite > 0 ? prixTotal / quantite : 0.0;

            for (int i = 0; i < quantite; i++) {
                String payload = "TICKET_" + journeeIdStr + "_"
                        + System.currentTimeMillis() + "_" + i + "_"
                        + UUID.randomUUID().toString().substring(0, 5);
                ticketsToSave.add(TicketEntity.builder()
                        .qrCodePayload(payload)
                        .statut(StatutTicket.VALIDE)
                        .typeAchat(sn.naavetane.backend.entities.enums.TypeAchat.PHYSIQUE_CASH)
                        .matchId(matchId)
                        .prix(prixUnitaire)
                        .dateAchat(java.time.LocalDateTime.now())
                        .build());
            }

            // ── Décrémentation du stock (Mode simple) ──
            CategorieEntity categorieADecrémenter = findCategorieForJournee(matchId, request.get("categorieId"), prixUnitaire);
            if (categorieADecrémenter != null && categorieADecrémenter.getPlacesTotal() != null) {
                int restantes = categorieADecrémenter.getPlacesRestantes() != null
                        ? categorieADecrémenter.getPlacesRestantes() : 0;
                if (quantite > restantes) {
                    Map<String, Object> erreur = new HashMap<>();
                    erreur.put("error", "Stock insuffisant pour la catégorie \""
                            + categorieADecrémenter.getNom() + "\". Demandé: " + quantite
                            + ", Disponible: " + restantes);
                    return ResponseEntity.badRequest().body(erreur);
                }
                String nomCat = categorieADecrémenter.getNom();
                List<CategorieEntity> journeeCats = categorieRepository.findByJourneeId(matchId);
                for (CategorieEntity c : journeeCats) {
                    if (c.getNom() != null && c.getNom().equalsIgnoreCase(nomCat) && c.getPlacesTotal() != null) {
                        int r = c.getPlacesRestantes() != null ? c.getPlacesRestantes() : 0;
                        c.setPlacesRestantes(Math.max(0, r - quantite));
                        categorieRepository.save(c);
                    }
                }
            }
        }

        // ── Sauvegarde batch ──
        ticketsToSave = ticketRepository.saveAll(ticketsToSave);

        for (TicketEntity ticket : ticketsToSave) {
            Map<String, Object> t = new HashMap<>();
            t.put("id", ticket.getId());
            t.put("qrCodePayload", ticket.getQrCodePayload());
            generatedTickets.add(t);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("tickets", generatedTickets);
        return ResponseEntity.ok(response);
    }

    private CategorieEntity findCategorieForJournee(UUID matchId, Object catIdObj, Double prix) {
        List<CategorieEntity> journeeCats = categorieRepository.findByJourneeId(matchId);
        if (catIdObj != null) {
            String str = catIdObj.toString().trim();
            try {
                UUID uuid = UUID.fromString(str);
                for (CategorieEntity c : journeeCats) {
                    if (c.getId().equals(uuid)) return c;
                }
            } catch (Exception e) {
                // Pas un UUID
            }
            for (CategorieEntity c : journeeCats) {
                if (c.getNom() != null && c.getNom().equalsIgnoreCase(str)) return c;
            }
        }
        if (prix != null) {
            for (CategorieEntity c : journeeCats) {
                if (c.getPrix() != null && c.getPrix().equals(prix)) return c;
            }
        }
        return null;
    }
}
