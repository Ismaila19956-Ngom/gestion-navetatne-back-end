package com.webgram.dgpsn.entities;

import lombok.*;
import com.webgram.dgpsn.entities.audits.Auditable;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "qualite_air")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QualiteAirEntity extends Auditable<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @Column(name = "measurement_date")
    private Date measurementDate;

    @Column(name = "iqa")
    private String iqa;

    @ManyToOne
    @JoinColumn(name = "sta_main_pollutant_linked_label")
    private LabelEntity mainPollutant;

    @ElementCollection
    @CollectionTable(name = "pollutant_measurements", joinColumns = @JoinColumn(name = "qualite_air_id"))
    private List<PollutantMeasurement> pollutantMeasurements = new ArrayList<>();

    @Column(name = "fixed_sources", columnDefinition = "TEXT")
    private String fixedSources;

    @Column(name = "mobile_sources", columnDefinition = "TEXT")
    private String mobileSources;

    @Column(name = "surfacic_sources", columnDefinition = "TEXT")
    private String surfacicSources;

    @ElementCollection
    @CollectionTable(name = "bulletins_monthly", joinColumns = @JoinColumn(name = "qualite_air_id"))
    @Column(name = "bulletin_path", length = 1000)
    private List<String> bulletinsMonthly = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "bulletins_quarterly", joinColumns = @JoinColumn(name = "qualite_air_id"))
    @Column(name = "bulletin_path", length = 1000)
    private List<String> bulletinsQuarterly = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "bulletins_annual", joinColumns = @JoinColumn(name = "qualite_air_id"))
    @Column(name = "bulletin_path", length = 1000)
    private List<String> bulletinsAnnual = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "analysis_reports", joinColumns = @JoinColumn(name = "qualite_air_id"))
    @Column(name = "report_path", length = 1000)
    private List<String> analysisReports = new ArrayList<>();

    @Column(name = "prepared_by")
    private String preparedBy;

    @Column(name = "preparation_date")
    private Date preparationDate;

    @Column(name = "validation_comments", columnDefinition = "TEXT")
    private String validationComments;

    @Column(name = "responsible_validation")
    private Boolean responsibleValidation;

    @Embeddable
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PollutantMeasurement {
        @ManyToOne
        @JoinColumn(name = "pollutant_linked_label")
        private LabelEntity pollutant;

        @Column(name = "concentration")
        private Double concentration;

        @ManyToOne
        @JoinColumn(name = "unit_linked_label")
        private LabelEntity unit;
    }
}