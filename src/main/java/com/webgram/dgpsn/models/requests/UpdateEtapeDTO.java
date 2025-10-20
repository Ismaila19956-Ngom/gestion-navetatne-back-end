package com.webgram.dgpsn.models.requests;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class UpdateEtapeDTO {

    @NotBlank(message = "L'étape ne peut pas être vide")
    private String etape;

}