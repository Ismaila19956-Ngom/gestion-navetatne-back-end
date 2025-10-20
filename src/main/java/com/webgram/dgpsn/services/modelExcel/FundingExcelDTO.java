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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingExcelDTO implements Serializable {

    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "PROJET")
    private String nomProjet;

    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "SIGLE")
    private String sigleProjet;

    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "FINANCING_AGREEMENT")
    private String financingAgreement;

    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "AMOUNT")
    private Double amount;

    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "CASH")
    private String cash;

    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "RATE")
    private Double rate;


    @CellIndex(index = 6)
    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "EQUIVALENCE")
    private Double equivalence;

    @CellIndex(index = 7)
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "APPROVALDATE")
    private Date approvalDate;

    @CellIndex(index = 8)
    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "DATE CLOSURE")
    private Date closingDate;

    @CellIndex(index = 9)
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "EXTENTION_DATE")
    private  Date extentionDate;

    @CellIndex(index = 10)
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "CODE_FUNDING_TYPE")
    private String codeFundingType;


//    @CellIndex(index = 9)
//    @CsvBindByPosition(position = 9)
//    @CsvBindByName(column = "CODE_TYPE_CONDITIONNALITE")
//    private String codeTypeConditionnalite;

    @CellIndex(index = 11)
    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "CODE_PARTENAIRE_PROJET")
    private String partenerCode;


    @CellIndex(index = 12)
    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "CODE_STRUCTURE")
    private String codeStructure;
    private Long projectId;

}
