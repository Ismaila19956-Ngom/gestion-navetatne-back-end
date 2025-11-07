package com.webgram.dgpsn.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DureeRangeDTO.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DureeRangeDTO {
    private String range;     // "1-5 jours", "6-10 jours", ...
    private Long count;
}
