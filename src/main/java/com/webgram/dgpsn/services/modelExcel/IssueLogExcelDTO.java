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
public class IssueLogExcelDTO {

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
    @CsvBindByName(column = "SOURCE")
    private String codeSource;
    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "DATE IDENTIFICATION")
    private Date identificationDate;
    @CellIndex(index = 10)
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "DATE RESOLUTION")
    private Date resolutionDate;

    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "DATE ECHEANCE")
    private Date deadline;

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
    @CsvBindByName(column = "NATURE SPECIFIQUE")
    private String specificNatureCode;

    @CellIndex(index = 11)
    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "SOUS CATEGORIE")
    private String subCategoryCode;

    @CellIndex(index = 12)
    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "RESPONSABLE")
    private String supervisorCode;

    @CellIndex(index = 13)
    @CsvBindByPosition(position = 13)
    @CsvBindByName(column = "RESPONSABLE DSEPP")
    private String dseppSupervisorCode;

    @CellIndex(index = 14)
    @CsvBindByPosition(position = 14)
    @CsvBindByName(column = "SOURCE")
    private String sourceCode;

    @CellIndex(index = 15)
    @CsvBindByPosition(position = 15)
    @CsvBindByName(column = "MINISTERE")
    private String ministerCode;

    private Long projectId;
}



