package com.webgram.dgpsn.services;

import com.webgram.dgpsn.models.RealisationExportDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {

    private static final String FONT_NAME = "Arial";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Exporte les données de recettes en format Excel avec style
     */
    public byte[] exportRecettesToExcel(List<RealisationExportDTO> recettes,
                                        Integer annee,
                                        String periode,
                                        String typePeriode) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Recouvrement Recettes");

            // Créer les styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle subTitleStyle = createSubTitleStyle(workbook);
            CellStyle tableTitleStyle = createTableTitleStyle(workbook);
            CellStyle tableHeaderStyle = createTableHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle numberStyle = createNumberStyle(workbook);
            CellStyle percentStyle = createPercentStyle(workbook);
            CellStyle totalStyle = createTotalStyle(workbook);
            CellStyle footerStyle = createFooterStyle(workbook);

            int rowNum = 0;

            // En-tête principal
            Row headerRow = sheet.createRow(rowNum++);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("RÉPUBLIQUE DU SÉNÉGAL");
            headerCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

            rowNum++; // Ligne vide

            // Titre principal
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("SITUATION RECOUVREMENT DES RECETTES PAR " + typePeriode.toUpperCase());
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));

            // Période
            Row periodRow = sheet.createRow(rowNum++);
            Cell periodCell = periodRow.createCell(0);
            periodCell.setCellValue("Période : " + periode + " " + annee);
            periodCell.setCellStyle(subTitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));

            // Filtres appliqués
            Row filterRow1 = sheet.createRow(rowNum++);
            Cell filterCell1 = filterRow1.createCell(0);
            filterCell1.setCellValue("Année :");
            filterCell1.setCellStyle(createFilterLabelStyle(workbook));
            Cell filterValue1 = filterRow1.createCell(1);
            filterValue1.setCellValue(annee.toString());
            filterValue1.setCellStyle(createFilterValueStyle(workbook));

            Row filterRow2 = sheet.createRow(rowNum++);
            Cell filterCell2 = filterRow2.createCell(0);
            filterCell2.setCellValue("Type de Période :");
            filterCell2.setCellStyle(createFilterLabelStyle(workbook));
            Cell filterValue2 = filterRow2.createCell(1);
            filterValue2.setCellValue(typePeriode);
            filterValue2.setCellStyle(createFilterValueStyle(workbook));

            rowNum++; // Ligne vide
            rowNum++; // Ligne vide

            // Titre du tableau
            Row tableTitleRow = sheet.createRow(rowNum++);
            Cell tableTitleCell = tableTitleRow.createCell(0);
            tableTitleCell.setCellValue("SUIVI DU RECOUVREMENT DES RECETTES");
            tableTitleCell.setCellStyle(tableTitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 6));

            rowNum++; // Ligne vide

            // En-têtes du tableau
            Row tableHeaderRow = sheet.createRow(rowNum++);
            String[] headers = {
                    "Libellés",
                    "Services votées",
                    "Réalisations au 30/03/" + annee,
                    "Réalisation au 30/06/" + annee,
                    "Taux de réalisation",
                    "Ecart",
                    "Observations"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = tableHeaderRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(tableHeaderStyle);
            }

            // Données
            for (RealisationExportDTO recette : recettes) {
                Row row = sheet.createRow(rowNum++);
                CellStyle style = Boolean.TRUE.equals(recette.getIsTotal()) ? totalStyle : dataStyle;

                // Libellé
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(recette.getLibelle());
                cell0.setCellStyle(style);

                // Services votées
                Cell cell1 = row.createCell(1);
                if (recette.getServicesVotees() != null) {
                    cell1.setCellValue(recette.getServicesVotees());
                    cell1.setCellStyle(Boolean.TRUE.equals(recette.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Réalisation T1
                Cell cell2 = row.createCell(2);
                if (recette.getRealisationT1() != null) {
                    cell2.setCellValue(recette.getRealisationT1());
                    cell2.setCellStyle(Boolean.TRUE.equals(recette.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Réalisation T2
                Cell cell3 = row.createCell(3);
                if (recette.getRealisationT2() != null) {
                    cell3.setCellValue(recette.getRealisationT2());
                    cell3.setCellStyle(Boolean.TRUE.equals(recette.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Taux de réalisation
                Cell cell4 = row.createCell(4);
                if (recette.getTauxRealisationGlobal() != null) {
                    cell4.setCellValue(recette.getTauxRealisationGlobal() / 100);
                    CellStyle tauxStyle = createConditionalPercentStyle(workbook, recette.getTauxRealisationGlobal());
                    cell4.setCellStyle(Boolean.TRUE.equals(recette.getIsTotal()) ? totalStyle : tauxStyle);
                }

                // Écart
                Cell cell5 = row.createCell(5);
                if (recette.getEcart() != null) {
                    cell5.setCellValue(recette.getEcart());
                    cell5.setCellStyle(Boolean.TRUE.equals(recette.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Observations
                Cell cell6 = row.createCell(6);
                if (recette.getObservations() != null) {
                    cell6.setCellValue(recette.getObservations());
                    cell6.setCellStyle(style);
                }
            }

            rowNum++; // Ligne vide

            // Pied de page
            Row footerRow1 = sheet.createRow(rowNum++);
            Cell footerCell1 = footerRow1.createCell(0);
            footerCell1.setCellValue("Généré le " + LocalDate.now().format(DATE_FORMATTER) + " | Données en temps réel");
            footerCell1.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 6));

            Row footerRow2 = sheet.createRow(rowNum++);
            Cell footerCell2 = footerRow2.createCell(0);
            footerCell2.setCellValue("© Direction Générale - Contrôle Budgétaire");
            footerCell2.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 6));

            // Ajuster la largeur des colonnes
            sheet.setColumnWidth(0, 35 * 256); // Libellés
            sheet.setColumnWidth(1, 18 * 256); // Services votées
            sheet.setColumnWidth(2, 22 * 256); // Réalisation T1
            sheet.setColumnWidth(3, 22 * 256); // Réalisation T2
            sheet.setColumnWidth(4, 18 * 256); // Taux
            sheet.setColumnWidth(5, 15 * 256); // Écart
            sheet.setColumnWidth(6, 40 * 256); // Observations

            // Convertir en tableau de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * Exporte les données de dépenses en format Excel avec style
     */
    public byte[] exportDepensesToExcel(List<RealisationExportDTO> depenses,
                                        Integer annee,
                                        String periode,
                                        String typePeriode) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Dépenses Fonctionnement");

            // Créer les styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            CellStyle subTitleStyle = createSubTitleStyle(workbook);
            CellStyle tableTitleStyle = createBlackTitleStyle(workbook);
            CellStyle tableHeaderStyle = createTableHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle numberStyle = createNumberStyle(workbook);
            CellStyle percentStyle = createPercentStyle(workbook);
            CellStyle totalStyle = createTotalStyle(workbook);
            CellStyle footerStyle = createFooterStyle(workbook);

            int rowNum = 0;

            // En-tête principal
            Row headerRow = sheet.createRow(rowNum++);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("RÉPUBLIQUE DU SÉNÉGAL");
            headerCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

            rowNum++; // Ligne vide

            // Titre principal
            Row titleRow = sheet.createRow(rowNum++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("SITUATION DES DÉPENSES DE FONCTIONNEMENT");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));

            // Période
            Row periodRow = sheet.createRow(rowNum++);
            Cell periodCell = periodRow.createCell(0);
            periodCell.setCellValue("Période : " + periode + " " + annee);
            periodCell.setCellStyle(subTitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));

            // Filtres appliqués
            Row filterRow1 = sheet.createRow(rowNum++);
            Cell filterCell1 = filterRow1.createCell(0);
            filterCell1.setCellValue("Année :");
            filterCell1.setCellStyle(createFilterLabelStyle(workbook));
            Cell filterValue1 = filterRow1.createCell(1);
            filterValue1.setCellValue(annee.toString());
            filterValue1.setCellStyle(createFilterValueStyle(workbook));

            Row filterRow2 = sheet.createRow(rowNum++);
            Cell filterCell2 = filterRow2.createCell(0);
            filterCell2.setCellValue("Type de Période :");
            filterCell2.setCellStyle(createFilterLabelStyle(workbook));
            Cell filterValue2 = filterRow2.createCell(1);
            filterValue2.setCellValue(typePeriode);
            filterValue2.setCellStyle(createFilterValueStyle(workbook));

            rowNum++; // Ligne vide
            rowNum++; // Ligne vide

            // Titre du tableau
            Row tableTitleRow = sheet.createRow(rowNum++);
            Cell tableTitleCell = tableTitleRow.createCell(0);
            tableTitleCell.setCellValue("ANALYSE DES DÉPENSES DE FONCTIONNEMENT");
            tableTitleCell.setCellStyle(tableTitleStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 7));

            rowNum++; // Ligne vide

            // En-têtes du tableau
            Row tableHeaderRow = sheet.createRow(rowNum++);
            String[] headers = {
                    "DÉPENSES DE FONCTIONNEMENT",
                    "BUDGET " + annee,
                    "RÉALISATIONS AU 30-03-" + annee,
                    "TR",
                    "RÉALISATIONS AU 30-06-" + annee,
                    "TR",
                    "RÉALISATIONS AU 01-09-" + annee,
                    "TR"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = tableHeaderRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(tableHeaderStyle);
            }

            // Données
            for (RealisationExportDTO depense : depenses) {
                Row row = sheet.createRow(rowNum++);
                CellStyle style = Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : dataStyle;

                // Libellé
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(depense.getLibelle());
                cell0.setCellStyle(style);

                // Budget
                Cell cell1 = row.createCell(1);
                if (depense.getBudget() != null) {
                    cell1.setCellValue(depense.getBudget());
                    cell1.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Réalisation T1
                Cell cell2 = row.createCell(2);
                if (depense.getRealisationT1() != null) {
                    cell2.setCellValue(depense.getRealisationT1());
                    cell2.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Taux T1
                Cell cell3 = row.createCell(3);
                if (depense.getTauxRealisationT1() != null) {
                    cell3.setCellValue(depense.getTauxRealisationT1() / 100);
                    CellStyle tauxStyle = createConditionalPercentStyle(workbook, depense.getTauxRealisationT1());
                    cell3.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : tauxStyle);
                }

                // Réalisation T2
                Cell cell4 = row.createCell(4);
                if (depense.getRealisationT2() != null) {
                    cell4.setCellValue(depense.getRealisationT2());
                    cell4.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Taux T2
                Cell cell5 = row.createCell(5);
                if (depense.getTauxRealisationT2() != null) {
                    cell5.setCellValue(depense.getTauxRealisationT2() / 100);
                    CellStyle tauxStyle = createConditionalPercentStyle(workbook, depense.getTauxRealisationT2());
                    cell5.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : tauxStyle);
                }

                // Réalisation T3
                Cell cell6 = row.createCell(6);
                if (depense.getRealisationT3() != null) {
                    cell6.setCellValue(depense.getRealisationT3());
                    cell6.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : numberStyle);
                }

                // Taux T3
                Cell cell7 = row.createCell(7);
                if (depense.getTauxRealisationT3() != null) {
                    cell7.setCellValue(depense.getTauxRealisationT3() / 100);
                    CellStyle tauxStyle = createConditionalPercentStyle(workbook, depense.getTauxRealisationT3());
                    cell7.setCellStyle(Boolean.TRUE.equals(depense.getIsTotal()) ? totalStyle : tauxStyle);
                }
            }

            rowNum++; // Ligne vide

            // Pied de page
            Row footerRow1 = sheet.createRow(rowNum++);
            Cell footerCell1 = footerRow1.createCell(0);
            footerCell1.setCellValue("Généré le " + LocalDate.now().format(DATE_FORMATTER) + " | Données en temps réel");
            footerCell1.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 7));

            Row footerRow2 = sheet.createRow(rowNum++);
            Cell footerCell2 = footerRow2.createCell(0);
            footerCell2.setCellValue("© Direction Générale - Contrôle Budgétaire");
            footerCell2.setCellStyle(footerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 7));

            // Ajuster la largeur des colonnes
            sheet.setColumnWidth(0, 35 * 256); // Libellés
            sheet.setColumnWidth(1, 18 * 256); // Budget
            sheet.setColumnWidth(2, 22 * 256); // Réalisation T1
            sheet.setColumnWidth(3, 10 * 256); // TR
            sheet.setColumnWidth(4, 22 * 256); // Réalisation T2
            sheet.setColumnWidth(5, 10 * 256); // TR
            sheet.setColumnWidth(6, 22 * 256); // Réalisation T3
            sheet.setColumnWidth(7, 10 * 256); // TR

            // Convertir en tableau de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    // ==================== STYLES ====================

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createSubTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private CellStyle createTableTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        return style;
    }

    private CellStyle createBlackTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        return style;
    }

    private CellStyle createTableHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return style;
    }

    private CellStyle createNumberStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
        return style;
    }

    private CellStyle createPercentStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(FONT_NAME);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setDataFormat(workbook.createDataFormat().getFormat("0%"));
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle createConditionalPercentStyle(Workbook workbook, Double taux) {
        CellStyle style = createPercentStyle(workbook);
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(FONT_NAME);
        font.setBold(true);

        if (taux < 50) {
            font.setColor(IndexedColors.RED.getIndex());
        } else if (taux < 80) {
            font.setColor(IndexedColors.ORANGE.getIndex());
        } else {
            font.setColor(IndexedColors.GREEN.getIndex());
        }

        style.setFont(font);
        return style;
    }

    private CellStyle createTotalStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        return style;
    }

    private CellStyle createFooterStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 9);
        font.setFontName(FONT_NAME);
        font.setItalic(true);
        font.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle createFilterLabelStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName(FONT_NAME);
        font.setColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFont(font);
        return style;
    }

    private CellStyle createFilterValueStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName(FONT_NAME);
        style.setFont(font);
        return style;
    }
}
