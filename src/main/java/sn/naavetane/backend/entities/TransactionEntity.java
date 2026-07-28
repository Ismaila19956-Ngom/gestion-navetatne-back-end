package sn.naavetane.backend.entities;

import lombok.*;
import sn.naavetane.backend.entities.audits.Auditable;
import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "transactions")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trx_id")
    private Long id;

    // Notre référence interne unique (ex: TX-20260719-001)
    @Column(name = "trx_reference", unique = true, nullable = false)
    private String transactionReference;

    // Le token renvoyé par PayDunya
    @Column(name = "trx_paydunya_token")
    private String paydunyaToken;

    // Le montant de la transaction
    @Column(name = "trx_amount", nullable = false)
    private Double amount;

    // Le statut (PENDING, SUCCESS, FAILED, CANCELLED)
    @Enumerated(EnumType.STRING)
    @Column(name = "trx_status", nullable = false)
    private TransactionStatus status;

    // Le moyen de paiement utilisé (ex: WAVE-SENEGAL)
    @Column(name = "trx_payment_method")
    private String paymentMethod;

    // L'utilisateur qui a effectué la transaction
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trx_user_id", nullable = false)
    private UserEntity user;

}
