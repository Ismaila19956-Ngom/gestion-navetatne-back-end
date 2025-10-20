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
public class ReunionExcelDTO {

@CellIndex(index = 0)
@CsvBindByPosition(position = 0)
@CsvBindByName(column = "Libelle")
private String libelle;
@CellIndex(index = 1)
@CsvBindByPosition(position = 1)
@CsvBindByName(column = "Typereunion_Type réunion ")
private String libelleTypereunion;
@CellIndex(index = 2)
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "Participants ")
private String participant;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Date prévue ")
private Date dateprevue;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Date réelle ")
private Date datereelle;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Heure début prévue ")
private String heuredebutprevue;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Heure fin prévue ")
private String heurefinprevue;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Heure début  réelle ")
private String heuredebutreelle;
@CellIndex(index = 8)
@CsvBindByPosition(position = 8)
@CsvBindByName(column = "Heure fin réelle ")
private String heurefinreelle;
@CellIndex(index = 9)
@CsvBindByPosition(position = 9)
@CsvBindByName(column = "Commentaire")
private String coment;

}
