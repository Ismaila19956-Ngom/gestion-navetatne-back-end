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
public class MembreExcelDTO {

@CellIndex(index = 0)
@CsvBindByPosition(position = 0)
@CsvBindByName(column = "Membres")
private String agent;
@CellIndex(index = 1)
@CsvBindByPosition(position = 1)
@CsvBindByName(column = "Rôle ")
private String role;
@CellIndex(index = 2)
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "Mandat (ans)")
private Integer mandat;

}
