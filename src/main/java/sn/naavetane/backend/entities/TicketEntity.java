package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import sn.naavetane.backend.entities.enums.TypeAchat;
import sn.naavetane.backend.entities.enums.StatutTicket;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets", indexes = {
    @Index(name = "idx_ticket_match", columnList = "match_id"),
    @Index(name = "idx_ticket_statut", columnList = "statut"),
    @Index(name = "idx_ticket_type_achat", columnList = "type_achat")
})
@EntityListeners(AuditingEntityListener.class)
public class TicketEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "qr_code_payload", nullable = false, unique = true)
    private String qrCodePayload;

    // Référence unique du paiement (clé idempotence pour éviter les doublons de tickets)
    @Column(name = "payment_reference", unique = true)
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutTicket statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_achat", nullable = false)
    private TypeAchat typeAchat;

    @CreatedDate
    @Column(name = "date_achat", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateAchat;

    @Column(name = "date_consommation")
    private LocalDateTime dateConsommation;

    @Column(name = "match_id", nullable = false)
    private UUID matchId;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "user_id")
    private Long userId;

    // Exclure la relation LAZY du JSON pour éviter les erreurs de sérialisation
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_de_vente_id")
    private PointDeVenteEntity pointDeVente;
}
