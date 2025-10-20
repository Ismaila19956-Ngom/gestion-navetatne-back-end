package com.webgram.dgpsn.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.webgram.dgpsn.annotations.JournalAttribute;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QualiteAirDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "L'id technique, généré au moment de persister l'objet", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JournalAttribute
    private Long id;

    @Schema(description = "Station associée", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StationDTO station;

    @NotNull
    @JournalAttribute
    @Schema(description = "ID de la station")
    private Long stationId;

    @NotNull
    @JournalAttribute
    @Schema(description = "Date de la mesure")
    private Date measurementDate;

    @NotNull
    @JournalAttribute
    @Schema(description = "Indice de qualité de l'air (ex. Bon, Moyen)")
    private String iqa;

    @Schema(description = "Polluant principal", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LabelDTO mainPollutant;

    @NotNull
    @JournalAttribute
    @Schema(description = "ID du polluant principal")
    private Long mainPollutantId;

    @JournalAttribute
    @Schema(description = "Liste des mesures de polluants")
    private List<PollutantMeasurement> pollutantMeasurements = new ArrayList<>();

    @JournalAttribute
    @Schema(description = "Sources fixes d'émissions")
    private String fixedSources;

    @JournalAttribute
    @Schema(description = "Sources mobiles d'émissions")
    private String mobileSources;

    @JournalAttribute
    @Schema(description = "Sources surfaciques d'émissions")
    private String surfacicSources;

    @JournalAttribute
    @Schema(description = "Bulletins mensuels")
    private List<String> bulletinsMonthly = new ArrayList<>();

    @JournalAttribute
    @Schema(description = "Bulletins trimestriels")
    private List<String> bulletinsQuarterly = new ArrayList<>();

    @JournalAttribute
    @Schema(description = "Bulletins annuels")
    private List<String> bulletinsAnnual = new ArrayList<>();

    @JournalAttribute
    @Schema(description = "Rapports d'analyses")
    private List<String> analysisReports = new ArrayList<>();

    @NotNull
    @JournalAttribute
    @Schema(description = "Personne ayant préparé les données")
    private String preparedBy;

    @NotNull
    @JournalAttribute
    @Schema(description = "Date de préparation des données")
    private Date preparationDate;

    @JournalAttribute
    @Schema(description = "Commentaires de validation")
    private String validationComments;

    @JournalAttribute
    @Schema(description = "Validation par le responsable")
    private Boolean responsibleValidation;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PollutantMeasurement {
        @Schema(description = "Polluant mesuré", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private LabelDTO pollutant;

        @NotNull
        @JournalAttribute
        @Schema(description = "ID du polluant")
        private Long pollutantId;

        @NotNull
        @JournalAttribute
        @Schema(description = "Concentration mesurée")
        private Double concentration;

        @Schema(description = "Unité de mesure", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private LabelDTO unit;

        @Schema(description = "Unité de mesure")
        private Long unitId;
    }
}