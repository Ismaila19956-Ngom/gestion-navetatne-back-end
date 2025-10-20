package com.webgram.dgpsn.services.modelExcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.khoutech.openexcel.annotations.CellIndex;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import lombok.experimental.Accessors;

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
public class    StructureExcelDTO {

    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "CODE")
    private String code;

    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "NOM")
    private String nom;

    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "ADRESSE")
    private String address;

    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "CITY")
    private String city;

    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "TELEPHONE")
    private String telephone;

    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "FAX")
    private String fax;

    @CellIndex(index = 6)
    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "EMAIL")
    private String email;

    @CellIndex(index = 7)
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "WEBSITE")
    private String webSite;

    @CellIndex(index = 8)
    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "START_DATE_COOPERATION")
    private Date sartDateCooperation;

    @CellIndex(index = 9)
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "MECHANISM_OF_INTERVENTION")
    private String mechanismOfIntervention;

    @CellIndex(index = 10)
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "RESPONSABLE")
    private String responsable;

    @CellIndex(index = 11)
    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "ETAT")
    private String etat;

    @CellIndex(index = 12)
    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "TYPE_STRUCTURE")
    private String typeStructure;
}
