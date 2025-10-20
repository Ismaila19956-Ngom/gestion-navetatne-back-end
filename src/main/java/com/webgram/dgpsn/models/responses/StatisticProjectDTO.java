package com.webgram.dgpsn.models.responses;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class StatisticProjectDTO {
    Double totalProject;
    Integer averageAge;
    Double totalFunding;
    Double averageFundingPerProject;
    Double tauxDecaissement; //taux de decaissement
}
