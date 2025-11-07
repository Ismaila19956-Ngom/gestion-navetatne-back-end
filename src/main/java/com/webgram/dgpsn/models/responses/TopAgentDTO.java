package com.webgram.dgpsn.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TopAgentDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopAgentDTO {
    private String nomComplet;
    private Long conges;
}
