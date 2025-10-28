package com.webgram.dgpsn.services.Impl.modelExcelDTO;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionExcelDTO {

    @CsvBindByName(column = "N° Ordre")
    @CsvBindByPosition(position = 0)
    private Integer numeroOrdre;

    @CsvBindByName(column = "Type")
    @CsvBindByPosition(position = 1)
    private String type;

    @CsvBindByName(column = "Objet")
    @CsvBindByPosition(position = 2)
    private String objet;

    @CsvBindByName(column = "Destination")
    @CsvBindByPosition(position = 3)
    private String destination;

    @CsvBindByName(column = "Date Début")
    @CsvBindByPosition(position = 4)
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @CsvBindByName(column = "Date Fin")
    @CsvBindByPosition(position = 5)
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateFin;

    @CsvBindByName(column = "Durée (jours)")
    @CsvBindByPosition(position = 6)
    private Integer duree;

    @CsvBindByName(column = "Budget")
    @CsvBindByPosition(position = 7)
    private Double budget;

    @CsvBindByName(column = "Agent")
    @CsvBindByPosition(position = 8)
    private String agent;

    @CsvBindByName(column = "agent")
    @CsvBindByPosition(position = 9)
    private String statut;

    @CsvBindByName(column = "Rapport")
    @CsvBindByPosition(position = 10)
    private String rapport;

    @CsvBindByName(column = "Date Rapport")
    @CsvBindByPosition(position = 11)
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateRapport;
}