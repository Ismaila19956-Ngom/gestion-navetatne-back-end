package sn.naavetane.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametreDTO {
    private UUID id;
    private String cle;
    private String valeur;
    private String description;
}
