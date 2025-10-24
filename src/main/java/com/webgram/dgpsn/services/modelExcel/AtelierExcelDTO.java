package com.webgram.dgpsn.services.modelExcel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtelierExcelDTO {
    @com.opencsv.bean.CsvBindByName(column = "Titre")
    @com.opencsv.bean.CsvBindByPosition(position = 0)
    private String titre;

    @com.opencsv.bean.CsvBindByName(column = "Thème")
    @com.opencsv.bean.CsvBindByPosition(position = 1)
    private String theme;

    @com.opencsv.bean.CsvBindByName(column = "Objectif")
    @com.opencsv.bean.CsvBindByPosition(position = 2)
    private String objectif;

    @com.opencsv.bean.CsvBindByName(column = "Date")
    @com.opencsv.bean.CsvBindByPosition(position = 3)
    private String date;

    @com.opencsv.bean.CsvBindByName(column = "Heure Début")
    @com.opencsv.bean.CsvBindByPosition(position = 4)
    private String heurDebut;

    @com.opencsv.bean.CsvBindByName(column = "Heure Fin")
    @com.opencsv.bean.CsvBindByPosition(position = 5)
    private String heurFin;

    @com.opencsv.bean.CsvBindByName(column = "Lieu")
    @com.opencsv.bean.CsvBindByPosition(position = 6)
    private String lieu;

    @com.opencsv.bean.CsvBindByName(column = "Agent")
    @com.opencsv.bean.CsvBindByPosition(position = 7)
    private String agent;

    @com.opencsv.bean.CsvBindByName(column = "Coût")
    @com.opencsv.bean.CsvBindByPosition(position = 8)
    private String cout;
}
