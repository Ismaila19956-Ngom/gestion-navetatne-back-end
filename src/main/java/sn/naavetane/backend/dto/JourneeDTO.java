package sn.naavetane.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JourneeDTO {
    private UUID id;
    private LocalDate date;
    private String stade;
    private List<MatchDTO> matchs;
    private List<CategorieDTO> categories;
    private String statut;
    private String saison;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchDTO {
        private UUID id;
        private String equipe1;
        private String equipe2;
        private String heure;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorieDTO {
        private UUID id;
        private String nom;
        private Double prix;
        private Integer placesTotal;
        private Integer placesRestantes;
    }
}
