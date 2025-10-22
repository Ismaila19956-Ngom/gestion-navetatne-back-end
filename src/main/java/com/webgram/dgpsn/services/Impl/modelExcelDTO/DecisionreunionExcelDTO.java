 package com.webgram.dgpsn.services.Impl.modelExcelDTO;

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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecisionreunionExcelDTO {

@CellIndex(index = 0)
@CsvBindByPosition(position = 0)
@CsvBindByName(column = "Sujets")
private String sujet;
@CellIndex(index = 1)
@CsvBindByPosition(position = 1)
@CsvBindByName(column = "Modalité de vote")
private String modaliteVote;
@CellIndex(index = 2)
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "Résultats ")
private String resultat;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Pourcentage ")
private Integer pourcentage;

}
