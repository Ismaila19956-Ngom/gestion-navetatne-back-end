package sn.naavetane.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentCountByDirectionDTO {
    private String libelle;
    private Long count;
}
