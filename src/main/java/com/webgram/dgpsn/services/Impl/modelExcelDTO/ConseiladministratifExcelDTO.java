 package com.webgram.dgpsn.services.Impl.modelExcelDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.khoutech.openexcel.annotations.CellIndex;
import java.util.Date;
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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConseiladministratifExcelDTO {

@CellIndex(index = 0)
@CsvBindByPosition(position = 0)
@CsvBindByName(column = "Code")
private String code;
@CellIndex(index = 1)
@CsvBindByPosition(position = 1)
@CsvBindByName(column = "Nom du conseil administration (ca)")
private String Nomca;
@CellIndex(index = 2)
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "Date debut")
private Date datedebut;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Date fin")
private Date dateFin;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Nombre total de membre")
private String nbreTotalMembre;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Frequence des reunions")
private String FrequenceReunions;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Description / objectifs du ca")
private String description;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Procedurenomination_Procédure de nomination ")
private String libelleProcedurenomination;

}
