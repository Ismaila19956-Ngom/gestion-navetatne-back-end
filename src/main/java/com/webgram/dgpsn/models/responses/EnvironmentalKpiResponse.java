package com.webgram.dgpsn.models.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnvironmentalKpiResponse {
    private Double averageIqa;
    private Long stationCount;
    private Long recentMeasurementsCount;
}