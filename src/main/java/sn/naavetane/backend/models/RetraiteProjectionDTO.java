package sn.naavetane.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetraiteProjectionDTO {
    private Integer annee;
    private String direction;
    private Long count;
}
