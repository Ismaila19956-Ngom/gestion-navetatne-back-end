package com.webgram.dgpsn.models.responses;

import com.webgram.dgpsn.entities.enums.StatutType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatutCountDTO {
    private String statut;
    private Long count;
    public StatutCountDTO(StatutType statutType, Long count) {
        this.statut = statutType != null ? statutType.name() : "INCONNU";
        this.count = count;
    }
}
