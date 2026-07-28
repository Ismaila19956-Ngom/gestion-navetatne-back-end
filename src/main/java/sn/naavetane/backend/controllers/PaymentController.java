package sn.naavetane.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

import sn.naavetane.backend.entities.TicketEntity;
import sn.naavetane.backend.entities.enums.StatutTicket;
import sn.naavetane.backend.entities.enums.TypeAchat;
import sn.naavetane.backend.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "payment-controller", description = "Endpoints pour les paiements")
public class PaymentController {

    @Autowired
    private TicketRepository ticketRepository;

    @Value("${paydunya.master-key}")
    private String masterKey;

    @Value("${paydunya.private-key}")
    private String privateKey;

    @Value("${paydunya.token}")
    private String paydunyaToken;

    @Value("${paydunya.mode}")
    private String mode;

    @PostMapping("/init")
    public ResponseEntity<Map<String, Object>> initPayment(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        String reference = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        try {
            Double amount = Double.valueOf(request.get("amount").toString());
            String description = request.getOrDefault("description", "Paiement Billet").toString();

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            headers.set("PAYDUNYA-MASTER-KEY", masterKey);
            headers.set("PAYDUNYA-PRIVATE-KEY", privateKey);
            headers.set("PAYDUNYA-TOKEN", paydunyaToken);
            headers.set("PAYDUNYA-MODE", mode); // <--- AJOUT DU MODE (test ou live)

            Map<String, Object> invoice = new HashMap<>();
            invoice.put("total_amount", amount);
            invoice.put("description", description);

            Map<String, Object> store = new HashMap<>();
            store.put("name", "Naavetane App");

            Map<String, Object> customData = new HashMap<>();
            customData.put("reference", reference);

            Map<String, Object> body = new HashMap<>();
            body.put("invoice", invoice);
            body.put("store", store);
            body.put("custom_data", customData);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            String url = mode.equalsIgnoreCase("test") 
                ? "https://app.paydunya.com/sandbox-api/v1/checkout-invoice/create"
                : "https://app.paydunya.com/api/v1/checkout-invoice/create";            
            ResponseEntity<Map> paydunyaRes = restTemplate.postForEntity(url, entity, Map.class);
            Map<String, Object> resBody = paydunyaRes.getBody();

            if (resBody != null && "00".equals(resBody.get("response_code"))) {
                String invoiceToken = resBody.get("token").toString();
                String paymentUrl = resBody.get("response_text").toString();

                response.put("reference", reference);
                response.put("token", invoiceToken);
                response.put("paymentUrl", paymentUrl);
                response.put("status", "PENDING");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Erreur PayDunya: " + resBody);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("error", "Erreur interne: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/status/{reference}")
    public ResponseEntity<Map<String, Object>> checkStatus(
            @PathVariable String reference, 
            @RequestParam String token) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("PAYDUNYA-MASTER-KEY", masterKey);
            headers.set("PAYDUNYA-PRIVATE-KEY", privateKey);
            headers.set("PAYDUNYA-TOKEN", paydunyaToken);
            headers.set("PAYDUNYA-MODE", mode);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // L'URL de confirmation est la même pour Sandbox et Live (sauf le domaine/path)
            String url = mode.equalsIgnoreCase("test") 
                ? "https://app.paydunya.com/sandbox-api/v1/checkout-invoice/confirm/" + token
                : "https://app.paydunya.com/api/v1/checkout-invoice/confirm/" + token;
                
            ResponseEntity<Map> paydunyaRes = restTemplate.exchange(
                url, 
                org.springframework.http.HttpMethod.GET, 
                entity, 
                Map.class
            );
            
            Map<String, Object> resBody = paydunyaRes.getBody();
            
            if (resBody != null && "completed".equalsIgnoreCase(String.valueOf(resBody.get("status")))) {
                
                // === IDEMPOTENCE : anti-doublon ===
                // On vérifie si un ticket a déjà été généré pour ce paiement
                Optional<TicketEntity> existingTicket = ticketRepository.findByPaymentReference(reference);
                if (existingTicket.isPresent()) {
                    // Le ticket existe déjà (polling répété) - on retourne le même
                    response.put("status", "SUCCESS");
                    response.put("reference", reference);
                    response.put("ticketQrCode", existingTicket.get().getQrCodePayload());
                    return ResponseEntity.ok(response);
                }
                
                // PAIEMENT REUSSI ET NOUVEAU ! On génère le billet.
                String ticketQrCode = UUID.randomUUID().toString();
                
                TicketEntity ticket = new TicketEntity();
                ticket.setQrCodePayload(ticketQrCode);
                ticket.setPaymentReference(reference); // Sauvegarde la référence pour l'idempotence
                ticket.setStatut(StatutTicket.VALIDE);
                ticket.setTypeAchat(TypeAchat.DIGITAL_WAVE);
                ticket.setDateAchat(LocalDateTime.now());
                ticket.setMatchId(UUID.randomUUID()); // TODO: récupérer le vrai matchId depuis la requête
                
                ticketRepository.save(ticket);
                
                response.put("status", "SUCCESS");
                response.put("reference", reference);
                response.put("ticketQrCode", ticketQrCode);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "PENDING");
                response.put("reference", reference);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            response.put("error", "Erreur vérification: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/simulate-success")
    public ResponseEntity<Map<String, Object>> simulatePaymentSuccess(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        
        String reference = "SIM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Anti-doublon : on vérifie si un ticket existe déjà pour cette simulation
        Optional<TicketEntity> existing = ticketRepository.findByPaymentReference(reference);
        if (existing.isPresent()) {
            response.put("status", "SUCCESS");
            response.put("ticketQrCode", existing.get().getQrCodePayload());
            return ResponseEntity.ok(response);
        }
        
        // Génération du billet simulé
        String ticketQrCode = UUID.randomUUID().toString();
        
        TicketEntity ticket = new TicketEntity();
        ticket.setQrCodePayload(ticketQrCode);
        ticket.setPaymentReference(reference);
        ticket.setStatut(StatutTicket.VALIDE);
        ticket.setTypeAchat(TypeAchat.DIGITAL_WAVE);
        ticket.setDateAchat(LocalDateTime.now());
        ticket.setMatchId(UUID.randomUUID());
        
        ticketRepository.save(ticket);
        
        response.put("status", "SUCCESS");
        response.put("reference", reference);
        response.put("ticketQrCode", ticketQrCode);
        response.put("simulated", true);
        return ResponseEntity.ok(response);
    }
}
