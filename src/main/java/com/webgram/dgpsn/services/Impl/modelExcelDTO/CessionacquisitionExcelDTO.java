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
public class CessionacquisitionExcelDTO {

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
@CsvBindByName(column = "Date")
private Date Date;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Type d'opération ")
private String typeOperation;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Montant de l'operation (fcfa)")
private Integer MontantOperation;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Prix par action")
private Integer prixAction;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Nombre de titre cédé/acquis")
private Integer nombreTitre;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Participation après opération ")
private Integer participationOperation;
@CellIndex(index = 8)
@CsvBindByPosition(position = 8)
@CsvBindByName(column = "Valeur total du capital après operation (fcfa)")
private Integer vcOperation;
@CellIndex(index = 9)
@CsvBindByPosition(position = 9)
@CsvBindByName(column = "Nombre action crée ")
private Integer NombreActionCree;
@CellIndex(index = 10)
@CsvBindByPosition(position = 10)
@CsvBindByName(column = "Décote /surcote (%)")
private Integer valeurDecoteSurcote;
@CellIndex(index = 11)
@CsvBindByPosition(position = 11)
@CsvBindByName(column = "Décote/surcote")
private String DecoteSurcote;
@CellIndex(index = 12)
@CsvBindByPosition(position = 12)
@CsvBindByName(column = "Commentaires ")
private String coment;

}
