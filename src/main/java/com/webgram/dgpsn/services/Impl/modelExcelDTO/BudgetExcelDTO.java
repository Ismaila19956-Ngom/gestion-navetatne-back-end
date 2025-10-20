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
public class BudgetExcelDTO {

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
@CsvBindByName(column = "Année fiscale ")
private String anneeFiscale;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Montant alloué (m francs cfa)")
private Double Montantalloue;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Projet associé (si applicable) :")
private String Projetassocisier;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Date limite d'utilisation")
private Date Datelimitedutilisation;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Statut du budget")
private String Statutdubudget;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Commentaire")
private String description;
@CellIndex(index = 8)
@CsvBindByPosition(position = 8)
@CsvBindByName(column = "Categoriebudgetaire_Categorie budgetaire")
private String libelleCategoriebudgetaire;
@CellIndex(index = 9)
@CsvBindByPosition(position = 9)
@CsvBindByName(column = "Periode_Periode")
private String libellePeriode;
@CellIndex(index = 10)
@CsvBindByPosition(position = 10)
@CsvBindByName(column = "Periodicite_Périodicité")
private String libellePeriodicite;

}
