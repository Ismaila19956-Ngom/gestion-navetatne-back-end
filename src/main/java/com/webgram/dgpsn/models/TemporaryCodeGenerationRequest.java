package com.webgram.dgpsn.models;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Data
public class TemporaryCodeGenerationRequest {
    @NotBlank
    private String username;
    @Min(5) // Durée minimale de 5 minutes par exemple
    private int validityInMinutes = 15; // Valeur par défaut
}