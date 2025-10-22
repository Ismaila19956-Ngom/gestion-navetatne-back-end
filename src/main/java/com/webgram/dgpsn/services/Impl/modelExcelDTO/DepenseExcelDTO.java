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
public class DepenseExcelDTO {

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
@CsvBindByName(column = "Montant de la dépense")
private Double MontantDepense;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Date de la dépense")
private Date Datedepense;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Commentaire/description")
private String Description;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Naturedepense_Nature de la dépense")
private String libelleNaturedepense;

}
