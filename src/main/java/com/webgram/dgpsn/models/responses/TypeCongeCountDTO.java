package com.webgram.dgpsn.models.responses;

import com.webgram.dgpsn.entities.enums.TypeConge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeCongeCountDTO {
    private String type;
    private Long   count;
    public TypeCongeCountDTO(TypeConge typeConge, Long count) {
        this.type = typeConge != null ? typeConge.name() : "INCONNU";
        this.count = count;
    }
}


