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
public class AssignmentExcelDTO {

    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "LIBELLE")
    private String libelle;

    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "START_DATE")
    private Date startDate;

    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "END_DATE")
    private Date endDate;

    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "CODE_ASSIGNMENT_TYPE")
    private String codeAssignmentType;

    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "CODE_PARTENAIRE")
    private String codePartenaire;

    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "CODE_STRUCTURE_EXECUTION")
    private String codeStructureExecution;
}
