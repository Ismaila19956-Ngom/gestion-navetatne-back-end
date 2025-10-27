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
public class EvaluationfinanciereExcelDTO {

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
@CsvBindByName(column = "Chiffre d'affaire")
private Double chiffreaffaire;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Date debut")
private Date datedebut;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Date fin")
private Date datefin;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Resultat net reel")
private Double resultatnetreel;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Resultat net prevue")
private Double resultatnetprevu;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Marge brute d'exploitation reel")
private Double Margebruteexpreel;
@CellIndex(index = 8)
@CsvBindByPosition(position = 8)
@CsvBindByName(column = "Marge brute d'exploitation prevue")
private Double Margebrutedexpprevue;
@CellIndex(index = 9)
@CsvBindByPosition(position = 9)
@CsvBindByName(column = "Capacité d'autofinancement (caf) réel ")
private Double Capacitdautofinancereel;
@CellIndex(index = 10)
@CsvBindByPosition(position = 10)
@CsvBindByName(column = "(caf) prévue ")
private Double CAFprevue;
@CellIndex(index = 11)
@CsvBindByPosition(position = 11)
@CsvBindByName(column = "Fonds de roulement net global réel ")
private Double Fondsderoulementreel;
@CellIndex(index = 12)
@CsvBindByPosition(position = 12)
@CsvBindByName(column = "(frng) prévue ")
private Double FRNGprevue;
@CellIndex(index = 13)
@CsvBindByPosition(position = 13)
@CsvBindByName(column = "Ratio de liquidité générale en (%) réel ")
private Double Ratiolgreel;
@CellIndex(index = 14)
@CsvBindByPosition(position = 14)
@CsvBindByName(column = "Gearing (en %) réel ")
private Double Gearingreel;
@CellIndex(index = 15)
@CsvBindByPosition(position = 15)
@CsvBindByName(column = "Gearing (en %) prévue ")
private Double Gearingprevue;
@CellIndex(index = 16)
@CsvBindByPosition(position = 16)
@CsvBindByName(column = "Return on equity (roe) réel ")
private Double ROErel;
@CellIndex(index = 17)
@CsvBindByPosition(position = 17)
@CsvBindByName(column = "Return on equity (roe) prévue ")
private Double ROEprvue;
@CellIndex(index = 18)
@CsvBindByPosition(position = 18)
@CsvBindByName(column = "Cash-flow opérationnel réel ")
private Double Cashflowreel;
@CellIndex(index = 19)
@CsvBindByPosition(position = 19)
@CsvBindByName(column = "Cash-flow opérationnel prévue ")
private Double Cashflowprevu;
@CellIndex(index = 20)
@CsvBindByPosition(position = 20)
@CsvBindByName(column = "Commentaire ")
private String Commentaire;
@CellIndex(index = 21)
@CsvBindByPosition(position = 21)
@CsvBindByName(column = "Notation ")
private Integer Notation;

}
