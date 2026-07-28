package sn.naavetane.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import sn.naavetane.backend.entities.audits.Auditable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fraude_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudeLogEntity extends Auditable<Long> implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "qr_code_payload")
    private String qrCodePayload;

    @Column(name = "message", length = 500)
    private String message;

    @Column(name = "date_fraude", nullable = false)
    private LocalDateTime dateFraude;

    @Column(name = "match_id")
    private UUID matchId;
}
