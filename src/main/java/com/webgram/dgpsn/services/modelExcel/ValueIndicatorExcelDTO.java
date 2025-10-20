package com.webgram.dgpsn.services.modelExcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.khoutech.openexcel.annotations.CellIndex;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
//exclure les propriétés ayant des valeurs nulles / vides ou par défaut.
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueIndicatorExcelDTO implements Serializable {
    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "VALEUR ATTEINTE")
    private Double targetValue;
    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "VALEUR CIBLE")
    private Double valueReched;
    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "DATE DEBUT")
    private Date startDate;
    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "DATE FIN")
    private Date endDate;
    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "INDICATEUR")
    private String indicatorCode;
    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "PERIODE")
    private String periodCode;
    private Long projectId;

}
