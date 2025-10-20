package com.webgram.dgpsn.services.modelExcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.khoutech.openexcel.annotations.CellIndex;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndicatorProjectExcelDTO {

    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "TARGET_VALUE")
    private Double targetValue;

    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "CODE_INDICATOR")
    private String codeIndicator;

    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "CODE_PERIODICITY")
    private String codePeriodicity;
}
