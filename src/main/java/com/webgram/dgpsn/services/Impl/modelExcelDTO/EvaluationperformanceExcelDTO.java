 package com.webgram.dgpsn.services.Impl.modelExcelDTO;

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
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaluationperformanceExcelDTO {

@CellIndex(index = 0)
@CsvBindByPosition(position = 0)
@CsvBindByName(column = "Code")
private String code;
@CellIndex(index = 1)
@CsvBindByPosition(position = 1)
@CsvBindByName(column = "Libelle")
private String libelle;
@CellIndex(index = 2)
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "Date de début")
private Date Datededbut;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Date de fin")
private Date Datedefin;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Points forts")
private String Pointsforts;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Points faibles")
private String Pointsfaibles;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Appréciation de la gouvernance")
private String Apprciationgouvernance;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Respect des procédures")
private String Respectprocdures;
@CellIndex(index = 8)
@CsvBindByPosition(position = 8)
@CsvBindByName(column = "Statut")
private String Statut;
@CellIndex(index = 9)
@CsvBindByPosition(position = 9)
@CsvBindByName(column = "Notation")
private Integer Notation;
@CellIndex(index = 10)
@CsvBindByPosition(position = 10)
@CsvBindByName(column = "Recommandations")
private String Recommandations;
@CellIndex(index = 11)
@CsvBindByPosition(position = 11)
@CsvBindByName(column = "Plan d'action")
private String Plandaction;
@CellIndex(index = 12)
@CsvBindByPosition(position = 12)
@CsvBindByName(column = "Commentaire")
private String description;
@CellIndex(index = 13)
@CsvBindByPosition(position = 13)
@CsvBindByName(column = "Typedvaluation_Type d'évaluation")
private String libelleTypedvaluation;
@CellIndex(index = 14)
@CsvBindByPosition(position = 14)
@CsvBindByName(column = "Risque_Risque")
private String libelleRisque;

}
