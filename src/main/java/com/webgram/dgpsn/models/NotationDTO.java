package com.webgram.dgpsn.models;


import com.webgram.dgpsn.entities.enums.CritereNotation;
import com.webgram.dgpsn.entities.enums.ValeurNotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotationDTO {

    private Long id;
    private CritereNotation critere;
    private ValeurNotation valeur;
    private Long candidatId;
}
