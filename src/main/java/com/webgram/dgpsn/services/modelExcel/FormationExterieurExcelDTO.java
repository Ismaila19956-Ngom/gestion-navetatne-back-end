package com.webgram.dgpsn.services.modelExcel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormationExterieurExcelDTO {

    @CsvBindByName(column = "Project ID")
    @CsvBindByPosition(position = 0)
    private Long projectId;

    @CsvBindByName(column = "Titre de la formation")
    @CsvBindByPosition(position = 1)
    private String titreFormation;

    @CsvBindByName(column = "Organisme formateur")
    @CsvBindByPosition(position = 2)
    private String organismeFormateur;

    @CsvBindByName(column = "Date de début")
    @CsvBindByPosition(position = 3)
    private String dateDebut;

    @CsvBindByName(column = "Statut de la formation")
    @CsvBindByPosition(position = 4)
    private String statut;
}
