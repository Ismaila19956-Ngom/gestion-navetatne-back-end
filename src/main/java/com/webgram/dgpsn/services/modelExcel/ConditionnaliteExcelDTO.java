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
public class ConditionnaliteExcelDTO {

    @CellIndex(index = 0)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "LIBELLE")
    private String libelle;

    @CellIndex(index = 1)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "COMMENT")
    private String comment;

    @CellIndex(index = 2)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "DEADLINE")
    private Date deadline;

    @CellIndex(index = 3)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "CODE_ETAT_AVANCEMENT")
    private String codeEtatAvancement;

    @CellIndex(index = 4)
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "CODE_TYPE_CONDITIONNALITE")
    private String codeTypeConditionnalite;

    @CellIndex(index = 5)
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "MATRICULE_AGENT")
    private String matriculeAgent;
}
