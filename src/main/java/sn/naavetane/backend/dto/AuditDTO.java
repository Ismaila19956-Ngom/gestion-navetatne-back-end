package sn.naavetane.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditDTO {
    private UUID id;
    private String acteur;
    private String action;
    private String ressource;
    private String details;
    private String adresseIp;
    private LocalDateTime createdDate;
}
