package com.webgram.dgpsn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetraiteProjectionDTO {
    private int annee;
    private String direction;
    private Long totalAgents;
}