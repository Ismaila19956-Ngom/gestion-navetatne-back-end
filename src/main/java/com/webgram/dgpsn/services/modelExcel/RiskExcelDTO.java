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
public class RiskExcelDTO implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "LIBELLE")
    private String libelle;
    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "DESCRIPTION")
    private String description;
    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "RAPPORTER PAR")
    private String author;
    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "DATE IDENTIFICATION")
    private Date identificationDate;
    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "PROBABILITE")
    private Double probability;
    @CellIndex(index = 9)
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "CRITICITE")
    private String criticityCode;
    @CellIndex(index = 8)
    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "IMPACT DELAI")
    private String delayImpactCode;
    @CellIndex(index = 7)
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "IMPACT FINANCIER")
    private String financialImpactCode;
    @CellIndex(index = 6)
    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "STATUT")
    private String statusCode;
    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "NATURE")
    private String natureCode;
    private Long projectId;

}
