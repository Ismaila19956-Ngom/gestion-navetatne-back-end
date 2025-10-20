package com.webgram.dgpsn.services.modelExcel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.khoutech.openexcel.annotations.CellIndex;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
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
public class ManagementUnitExcelDTO {
    //    @CellIndex(index = 0)
//    @CsvBindByPosition(position = 0)
//    @CsvBindByName(column = "TYPE")
//    private String stringType;

    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "CODE")
    private String code;

    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "LIBELLE")
    @NotNull
    private String name;

    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "STATUT")
    private String statut;

    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "DATE DEBUT")
    private Date expectedStartDate;

    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "DATE FIN")
    private Date expectedEndDate;

    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "BUDGET")
    private Double budget;

    @CellIndex(index = 6)
    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "MONNAIE")
    private String monnaie;

    @CellIndex(index = 7)
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "TAUX")
    private Double taux;

    @CellIndex(index = 8)
    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "EQUIVALENCE")
    private Double equivalence;

    @CellIndex(index = 9)
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "SOUS SECTEUR")
    private String codeSubSector;

    @CellIndex(index = 10)
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "STRUCTURE EXECUTION")
    private String codeStructure;

    @CellIndex(index = 11)
    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "PERIODICITE")
    private String codePeriodicity;

    @CellIndex(index = 12)
    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "BENEFICIAIRE")
    private String beneficiaires;

    @CellIndex(index = 13)
    @CsvBindByPosition(position = 13)
    @CsvBindByName(column = "IMPACT ATTENDU")
    private String impactAttendu;

    @CellIndex(index = 14)
    @CsvBindByPosition(position = 14)
    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    @CellIndex(index = 15)
    @CsvBindByPosition(position = 15)
    @CsvBindByName(column = "OBJECTIF GLOBAL")
    private String objectifGlobale;

    @CellIndex(index = 16)
    @CsvBindByPosition(position = 16)
    @CsvBindByName(column = "OBJECTIFS SPECIFIQUES")
    private String objectifsSpecifiques;

    @CellIndex(index = 17)
    @CsvBindByPosition(position = 17)
    @CsvBindByName(column = "FLAG")
    private String codeFlag;

    @CellIndex(index = 18)
    @CsvBindByPosition(position = 18)
    @CsvBindByName(column = "AXE PSE")
    private String codeAxePSE;;

    @CellIndex(index = 19)
    @CsvBindByPosition(position = 19)
    @CsvBindByName(column = "COPILE")
    private boolean copile;

    @CellIndex(index = 20)
    @CsvBindByPosition(position = 20)
    @CsvBindByName(column = "FREQUENCE REUNION")
    private String codeMeetingFrequency;

    @CellIndex(index = 21)
    @CsvBindByPosition(position = 21)
    @CsvBindByName(column = "DATE DERNIERE REUNION")
    private Date lastMeetingDate;

    @CellIndex(index = 22)
    @CsvBindByPosition(position = 22)
    @CsvBindByName(column = "UGP")
    private boolean ugp;

    @CellIndex(index = 23)
    @CsvBindByPosition(position = 23)
    @CsvBindByName(column = "CP")
    private String codeCp;

//    @CellIndex(index = 20)
//    @CsvBindByPosition(position = 20)
//    @CsvBindByName(column = "MINISTERE")
//    private String codeMinister;

}
