package com.webgram.dgpsn.models.responses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoisCountsDTO {
    private String mois;
    private Long demandes;
//    public MoisCountsDTO(String mois, Long demandes) {
//        this.mois = mois;
//        this.demandes = demandes;
//    }

}