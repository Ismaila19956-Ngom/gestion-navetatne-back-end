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
public class EntrepriseExcelDTO {

@CellIndex(index = 0)
@CsvBindByPosition(position = 0)
@CsvBindByName(column = "Code")
private String code;
@CellIndex(index = 1)
@CsvBindByPosition(position = 1)
@CsvBindByName(column = "Dénomination sociale")
private String denomination;
@CellIndex(index = 2)
@CsvBindByPosition(position = 2)
@CsvBindByName(column = "Capital  social")
private String capitalSocial;
@CellIndex(index = 3)
@CsvBindByPosition(position = 3)
@CsvBindByName(column = "Participation de l'etat")
private String participationetat;
@CellIndex(index = 4)
@CsvBindByPosition(position = 4)
@CsvBindByName(column = "Droits de vote")
private String droitVote;
@CellIndex(index = 5)
@CsvBindByPosition(position = 5)
@CsvBindByName(column = "Valeur nominal de la participation ")
private String vnp;
@CellIndex(index = 6)
@CsvBindByPosition(position = 6)
@CsvBindByName(column = "Valeur comptable de la participation ")
private String vcp;
@CellIndex(index = 7)
@CsvBindByPosition(position = 7)
@CsvBindByName(column = "Valeur de marché de la participation")
private String vmp;
@CellIndex(index = 8)
@CsvBindByPosition(position = 8)
@CsvBindByName(column = "Longitude")
private String longitude;
@CellIndex(index = 9)
@CsvBindByPosition(position = 9)
@CsvBindByName(column = "Latitude")
private String latitude;
@CellIndex(index = 10)
@CsvBindByPosition(position = 10)
@CsvBindByName(column = "Responsable")
private String responsable;
@CellIndex(index = 11)
@CsvBindByPosition(position = 11)
@CsvBindByName(column = "Numéro de téléphone du responsable")
private String telephoneresponsable;
@CellIndex(index = 12)
@CsvBindByPosition(position = 12)
@CsvBindByName(column = "Email du responsable")
private String emailResponsable;
@CellIndex(index = 13)
@CsvBindByPosition(position = 13)
@CsvBindByName(column = "Adresse ")
private String adresse;
@CellIndex(index = 14)
@CsvBindByPosition(position = 14)
@CsvBindByName(column = "Website")
private String website;
@CellIndex(index = 15)
@CsvBindByPosition(position = 15)
@CsvBindByName(column = "Téléphone ")
private String telephone;
@CellIndex(index = 16)
@CsvBindByPosition(position = 16)
@CsvBindByName(column = "Email")
private String Email;
@CellIndex(index = 17)
@CsvBindByPosition(position = 17)
@CsvBindByName(column = "Secteuractivite_Secteur d'activité")
private String libelleSecteuractivite;
@CellIndex(index = 18)
@CsvBindByPosition(position = 18)
@CsvBindByName(column = "Formejuridique_Forme juridique ")
private String libelleFormejuridique;
@CellIndex(index = 19)
@CsvBindByPosition(position = 19)
@CsvBindByName(column = "Region_Région ")
private String libelleRegion;
@CellIndex(index = 20)
@CsvBindByPosition(position = 20)
@CsvBindByName(column = "Departement_Département ")
private String libelleDepartement;

}
