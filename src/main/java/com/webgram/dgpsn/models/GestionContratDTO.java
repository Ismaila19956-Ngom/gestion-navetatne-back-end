package com.webgram.dgpsn.models;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GestionContratDTO {

    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    private Long agentId;
    private LabelDTO typeContrat;
    private Long typeContratId;
    private Boolean isActive = true;
}
