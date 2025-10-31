//package com.webgram.dgpsn.services.Impl;
//
//import com.opencsv.CSVWriter;
//import com.webgram.dgpsn.entities.AgentEntity;
//import com.webgram.dgpsn.entities.CessationFonctionEntity;
//import com.webgram.dgpsn.entities.CongeEntity;
//import com.webgram.dgpsn.entities.enums.StatutType;
//import com.webgram.dgpsn.entities.enums.TypeConge;
//import com.webgram.dgpsn.repositories.AgentRepository;
//import com.webgram.dgpsn.repositories.CessationFonctionRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//@Slf4j
//public class CongeExportServiceImpl {
//
//    private final AgentRepository agentRepository;
//    private final CessationFonctionRepository cessationRepository;
//
//    private static final String[] MOIS = {"janv", "fevr", "mars", "avril", "mai", "juin",
//            "juil", "aout", "sept", "oct", "nov", "déc"};
//
//    /**
//     * Export Excel fidèle à la capture d'écran
//     */
//    public byte[] exportCongesAgentsExcel(Integer annee) throws IOException {
//        log.info("Export Excel - Année {} avec calcul des absences par mois", annee);
//
//        try (Workbook workbook = new XSSFWorkbook();
//             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//
//            Sheet sheet = workbook.createSheet("ABSENCES " + annee);
//
//            // Styles
//            CellStyle headerStyle = createHeaderStyle(workbook);
//            CellStyle subHeaderStyle = createSubHeaderStyle(workbook);
//            CellStyle dateStyle = createDateStyle(workbook);
//            CellStyle numberStyle = createNumberStyle(workbook);
//            CellStyle wrapStyle = createWrapTextStyle(workbook);
//            CellStyle greenStyle = createGreenHighlightStyle(workbook);
//            CellStyle yellowStyle = createYellowHighlightStyle(workbook);
//            CellStyle centerStyle = createCenterStyle(workbook);
//
//            // En-têtes
//            createDetailedHeader(sheet, headerStyle, subHeaderStyle, annee);
//
//            // Données
//            List<AgentEntity> agents = agentRepository.findAll();
//            int rowNum = 2;
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//            for (AgentEntity agent : agents) {
//                List<CessationFonctionEntity> cessations =
//                        cessationRepository.findByAgentIdAndAnnee(agent.getId(), annee);
//
//                if (cessations.isEmpty()) {
//                    // Agent sans cessation - ligne vide
//                    createAgentRowWithoutCessation(sheet, rowNum++, agent,
//                            dateStyle, numberStyle, wrapStyle, centerStyle);
//                } else {
//                    // Une ligne par agent avec toutes ses cessations
//                    createAgentRowWithCessations(sheet, rowNum++, agent, cessations, annee,
//                            sdf, dateStyle, numberStyle, wrapStyle, greenStyle, yellowStyle, centerStyle);
//                }
//            }
//
//            // Ajuster les colonnes
//            for (int i = 0; i < 30; i++) {
//                sheet.autoSizeColumn(i);
//            }
//            // Période des congés plus large
//            sheet.setColumnWidth(22, 256 * 60);
//
//            workbook.write(out);
//            log.info("Export Excel terminé - {} agents traités", agents.size());
//            return out.toByteArray();
//        }
//    }
//
//    /**
//     * Export CSV
//     */
//    public void exportCongesAgentsCSV(PrintWriter writer, Integer annee) {
//        log.info("Export CSV - Année {}", annee);
//
//        try (CSVWriter csv = new CSVWriter(writer, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER,
//                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
//
//            // En-têtes
//            String[] headers = buildCSVHeaders(annee);
//            csv.writeNext(headers);
//
//            // Données
//            List<AgentEntity> agents = agentRepository.findAll();
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//            for (AgentEntity agent : agents) {
//                List<CessationFonctionEntity> cessations =
//                        cessationRepository.findByAgentIdAndAnnee(agent.getId(), annee);
//                csv.writeNext(buildAgentCSVRow(agent, cessations, annee, sdf));
//            }
//
//            log.info("Export CSV terminé");
//        } catch (IOException e) {
//            throw new RuntimeException("Erreur lors de l'export CSV", e);
//        }
//    }
//
//    // ==================== EN-TÊTES ====================
//
//    private void createDetailedHeader(Sheet sheet, CellStyle headerStyle,
//                                      CellStyle subHeaderStyle, Integer annee) {
//        // Ligne 0: Titre principal
//        Row titleRow = sheet.createRow(0);
//        Cell titleCell = titleRow.createCell(0);
//        titleCell.setCellValue("ABSENCES " + annee);
//        titleCell.setCellStyle(headerStyle);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 29));
//
//        // Ligne 1: En-têtes de colonnes
//        Row headerRow = sheet.createRow(1);
//        String[] headers = {
//                "N", "DIRECTION", "PRENOM", "NOM",
//                "EMPLOI DANS L'ETABLISSEMENT", "DATE D'ENTREE", "PERIODE DE REFERENCE",
//                "DROIT BRUT " + annee,
//                "janv", "fevr", "mars", "avril", "mai", "juin",
//                "juil", "aout", "sept", "oct", "nov", "déc",
//                "Total Absences",
//                "CONGES PRIS " + annee,
//                "CONGE À SUPP",
//                "PERIODE DES CONGES",
//                "Jrs autorisées " + (annee - 1),
//                "SOLDE " + annee + " À REPORTER"
//        };
//
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//            cell.setCellStyle(subHeaderStyle);
//        }
//    }
//
//    // ==================== CRÉATION DES LIGNES ====================
//
//    private void createAgentRowWithoutCessation(Sheet sheet, int rowNum, AgentEntity agent,
//                                                CellStyle dateStyle, CellStyle numberStyle,
//                                                CellStyle wrapStyle, CellStyle centerStyle) {
//        Row row = sheet.createRow(rowNum);
//        int col = 0;
//
//        createCell(row, col++, rowNum - 1, centerStyle); // N
//        createCell(row, col++, agent.getDirection() != null ? agent.getDirection().getLibelle() : "", null);
//        createCell(row, col++, agent.getPrenom() != null ? agent.getPrenom() : "", null);
//        createCell(row, col++, agent.getNom() != null ? agent.getNom() : "", null);
//        createCell(row, col++, agent.getFonction() != null ? agent.getFonction().getLibelle() : "", null);
//        createCell(row, col++, "", dateStyle);
//        createCell(row, col++, "", null);
//        createCell(row, col++, "", numberStyle);
//
//        // 12 mois vides
//        for (int i = 0; i < 12; i++) {
//            createCell(row, col++, "", numberStyle);
//        }
//
//        createCell(row, col++, "", numberStyle); // Total
//        createCell(row, col++, "", numberStyle); // Congés pris
//        createCell(row, col++, "", numberStyle); // À supprimer
//        createCell(row, col++, "", wrapStyle);   // Période
//        createCell(row, col++, "", numberStyle); // Autorisés N-1
//        createCell(row, col++, "", numberStyle); // Solde
//    }
//
//    private void createAgentRowWithCessations(Sheet sheet, int rowNum, AgentEntity agent,
//                                              List<CessationFonctionEntity> cessations,
//                                              Integer annee, SimpleDateFormat sdf,
//                                              CellStyle dateStyle, CellStyle numberStyle,
//                                              CellStyle wrapStyle, CellStyle greenStyle,
//                                              CellStyle yellowStyle, CellStyle centerStyle) {
//        Row row = sheet.createRow(rowNum);
//        int col = 0;
//
//        // Colonnes de base
//        createCell(row, col++, rowNum - 1, centerStyle); // N
//        createCell(row, col++, agent.getDirection() != null ? agent.getDirection().getLibelle() : "", null);
//        createCell(row, col++, agent.getPrenom() != null ? agent.getPrenom() : "", null);
//        createCell(row, col++, agent.getNom() != null ? agent.getNom() : "", null);
//        createCell(row, col++, agent.getFonction() != null ? agent.getFonction().getLibelle() : "", null);
//
//        // Date d'entrée - première date de congé
//        String dateEntree = "";
//        if (!cessations.isEmpty() && cessations.get(0).getConge().getDateDepart() != null) {
//            dateEntree = sdf.format(cessations.get(0).getConge().getDateDepart());
//        }
//        createCell(row, col++, dateEntree, dateStyle);
//
//        // Période de référence - du premier au dernier congé
//        String periodeReference = buildPeriodeReference(cessations, sdf);
//        createCell(row, col++, periodeReference, null);
//
//        // Droit brut - dernier solde annuel
//        Integer droitBrut = getDroitBrut(cessations);
//        createCell(row, col++, droitBrut != null ? droitBrut : "", numberStyle);
//
//        // Absences par mois avec coloration
//        int[] absencesParMois = calculerAbsencesParMois(cessations, annee);
//        int totalAbsences = 0;
//
//        for (int i = 0; i < 12; i++) {
//            if (absencesParMois[i] > 0) {
//                // Vérifier si c'est un congé admin ou autre pour la couleur
//                boolean isCongeAdmin = hasCongeAdminInMonth(cessations, annee, i);
//                CellStyle monthStyle = isCongeAdmin ? greenStyle : yellowStyle;
//                createCell(row, col++, absencesParMois[i], monthStyle);
//                totalAbsences += absencesParMois[i];
//            } else {
//                createCell(row, col++, "", numberStyle);
//            }
//        }
//
//        // Total absences
//        createCell(row, col++, totalAbsences > 0 ? totalAbsences : "", numberStyle);
//
//        // Congés pris = somme des nombreDeJoursdemande
//        Integer congesPris = calculerCongesPris(cessations);
//        createCell(row, col++, congesPris > 0 ? congesPris : "", numberStyle);
//
//        // Congé à supprimer (vide pour l'instant)
//        createCell(row, col++, "", numberStyle);
//
//        // Période des congés avec décisions
//        String periodeConges = buildPeriodeCongesAvecDecisions(cessations, sdf);
//        createCell(row, col++, periodeConges, wrapStyle);
//
//        // Jours autorisés année précédente
//        Integer joursAutoN1 = getJoursAutorisesAnneePrecedente(agent.getId(), annee);
//        createCell(row, col++, joursAutoN1 != null ? joursAutoN1 : "", numberStyle);
//
//        // Solde à reporter
//        Integer solde = calculerSolde(droitBrut, congesPris);
//        createCell(row, col++, solde != null && solde != 0 ? solde : "", numberStyle);
//    }
//
//    // ==================== CALCULS ====================
//
//    private String buildPeriodeReference(List<CessationFonctionEntity> cessations, SimpleDateFormat sdf) {
//        if (cessations.isEmpty()) return "";
//
//        Date premiereDateDepart = null;
//        Date derniereDateReprise = null;
//
//        for (CessationFonctionEntity cessation : cessations) {
//            CongeEntity conge = cessation.getConge();
//            if (conge.getDateDepart() != null) {
//                if (premiereDateDepart == null || conge.getDateDepart().before(premiereDateDepart)) {
//                    premiereDateDepart = conge.getDateDepart();
//                }
//            }
//            if (conge.getDateReprise() != null) {
//                if (derniereDateReprise == null || conge.getDateReprise().after(derniereDateReprise)) {
//                    derniereDateReprise = conge.getDateReprise();
//                }
//            }
//        }
//
//        if (premiereDateDepart != null && derniereDateReprise != null) {
//            return sdf.format(premiereDateDepart) + " au " + sdf.format(derniereDateReprise);
//        }
//        return "";
//    }
//
//    private Integer getDroitBrut(List<CessationFonctionEntity> cessations) {
//        return cessations.stream()
//                .map(CessationFonctionEntity::getSoldeAnnuel)
//                .filter(Objects::nonNull)
//                .max(Integer::compareTo)
//                .orElse(null);
//    }
//
//    private int[] calculerAbsencesParMois(List<CessationFonctionEntity> cessations, Integer annee) {
//        int[] absences = new int[12];
//
//        for (CessationFonctionEntity cessation : cessations) {
//            CongeEntity conge = cessation.getConge();
//
//            // Utiliser la dateCessation et nombreDeJoursdemande
//            if (cessation.getDateCessation() != null && cessation.getNombreDeJoursdemande() != null) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(cessation.getDateCessation());
//
//                if (cal.get(Calendar.YEAR) == annee) {
//                    int mois = cal.get(Calendar.MONTH);
//                    absences[mois] += cessation.getNombreDeJoursdemande();
//                }
//            }
//            // Ou utiliser dateDepart/dateReprise si dateCessation n'est pas disponible
//            else if (conge.getDateDepart() != null && conge.getDateReprise() != null) {
//                Calendar start = Calendar.getInstance();
//                start.setTime(conge.getDateDepart());
//
//                Calendar end = Calendar.getInstance();
//                end.setTime(conge.getDateReprise());
//
//                while (!start.after(end)) {
//                    if (start.get(Calendar.YEAR) == annee) {
//                        int mois = start.get(Calendar.MONTH);
//                        absences[mois]++;
//                    }
//                    start.add(Calendar.DAY_OF_MONTH, 1);
//                }
//            }
//        }
//
//        return absences;
//    }
//
//    private boolean hasCongeAdminInMonth(List<CessationFonctionEntity> cessations,
//                                         Integer annee, int mois) {
//        for (CessationFonctionEntity cessation : cessations) {
//            CongeEntity conge = cessation.getConge();
//
//            if (conge.getTypeConge() == TypeConge.ADMINISTRATIF) {
//                Date dateToCheck = cessation.getDateCessation() != null ?
//                        cessation.getDateCessation() : conge.getDateDepart();
//
//                if (dateToCheck != null) {
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(dateToCheck);
//                    if (cal.get(Calendar.YEAR) == annee && cal.get(Calendar.MONTH) == mois) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private Integer calculerCongesPris(List<CessationFonctionEntity> cessations) {
//        return cessations.stream()
//                .map(CessationFonctionEntity::getNombreDeJoursdemande)
//                .filter(Objects::nonNull)
//                .mapToInt(Integer::intValue)
//                .sum();
//    }
//
//    private String buildPeriodeCongesAvecDecisions(List<CessationFonctionEntity> cessations,
//                                                   SimpleDateFormat sdf) {
//        List<String> periodes = new ArrayList<>();
//
//        for (CessationFonctionEntity cessation : cessations) {
//            CongeEntity conge = cessation.getConge();
//            StringBuilder sb = new StringBuilder();
//
//            if (conge.getNumeroDecision() != null) {
//                sb.append("Décision N°").append(conge.getNumeroDecision());
//            }
//
//            if (conge.getDateDepart() != null) {
//                if (sb.length() > 0) sb.append(" du ");
//                sb.append(sdf.format(conge.getDateDepart()));
//            }
//
//            if (conge.getDateReprise() != null) {
//                if (conge.getDateDepart() != null) sb.append(" au ");
//                sb.append(sdf.format(conge.getDateReprise()));
//            }
//
//            if (cessation.getNombreDeJoursdemande() != null) {
//                sb.append(" (").append(cessation.getNombreDeJoursdemande()).append(" jrs)");
//            }
//
//            if (sb.length() > 0) {
//                periodes.add(sb.toString());
//            }
//        }
//
//        return String.join("\n", periodes);
//    }
//
//    private Integer getJoursAutorisesAnneePrecedente(Long agentId, Integer annee) {
//        List<Integer> soldes = cessationRepository.findSoldeAnnuelByAgentAndAnnee(
//                agentId, annee - 1);
//        return soldes.isEmpty() ? null : soldes.get(0);
//    }
//
//    private Integer calculerSolde(Integer droitBrut, Integer congesPris) {
//        if (droitBrut == null || congesPris == null) return null;
//        return droitBrut - congesPris;
//    }
//
//    // ==================== CSV ====================
//
//    private String[] buildCSVHeaders(Integer annee) {
//        return new String[]{
//                "N", "DIRECTION", "PRENOM", "NOM",
//                "EMPLOI DANS L'ETABLISSEMENT", "DATE D'ENTREE", "PERIODE DE REFERENCE",
//                "DROIT BRUT " + annee,
//                "JANV", "FEVR", "MARS", "AVRIL", "MAI", "JUIN",
//                "JUIL", "AOUT", "SEPT", "OCT", "NOV", "DEC",
//                "Total Absences", "CONGES PRIS " + annee, "CONGE À SUPP",
//                "PERIODE DES CONGES", "Jrs autorisées " + (annee - 1),
//                "SOLDES " + annee + " À REPORTER"
//        };
//    }
//
//    private String[] buildAgentCSVRow(AgentEntity agent,
//                                      List<CessationFonctionEntity> cessations,
//                                      Integer annee, SimpleDateFormat sdf) {
//        List<String> row = new ArrayList<>();
//
//        row.add(""); // N (sera rempli par Excel)
//        row.add(agent.getDirection() != null ? agent.getDirection().getLibelle() : "");
//        row.add(agent.getPrenom() != null ? agent.getPrenom() : "");
//        row.add(agent.getNom() != null ? agent.getNom() : "");
//        row.add(agent.getFonction() != null ? agent.getFonction().getLibelle() : "");
//
//        if (!cessations.isEmpty() && cessations.get(0).getConge().getDateDepart() != null) {
//            row.add(sdf.format(cessations.get(0).getConge().getDateDepart()));
//        } else {
//            row.add("");
//        }
//
//        row.add(buildPeriodeReference(cessations, sdf));
//
//        Integer droitBrut = getDroitBrut(cessations);
//        row.add(droitBrut != null ? droitBrut.toString() : "");
//
//        int[] absences = calculerAbsencesParMois(cessations, annee);
//        int total = 0;
//        for (int a : absences) {
//            row.add(a > 0 ? String.valueOf(a) : "");
//            total += a;
//        }
//
//        row.add(total > 0 ? String.valueOf(total) : "");
//
//        Integer congesPris = calculerCongesPris(cessations);
//        row.add(congesPris > 0 ? congesPris.toString() : "");
//
//        row.add(""); // Congé à supp
//
//        row.add(buildPeriodeCongesAvecDecisions(cessations, sdf));
//
//        Integer joursN1 = getJoursAutorisesAnneePrecedente(agent.getId(), annee);
//        row.add(joursN1 != null ? joursN1.toString() : "");
//
//        Integer solde = calculerSolde(droitBrut, congesPris);
//        row.add(solde != null && solde != 0 ? solde.toString() : "");
//
//        return row.toArray(new String[0]);
//    }
//
//    // ==================== STYLES ====================
//
//    private void createCell(Row row, int col, Object value, CellStyle style) {
//        Cell cell = row.createCell(col);
//        if (value instanceof String) {
//            cell.setCellValue((String) value);
//        } else if (value instanceof Integer) {
//            cell.setCellValue((Integer) value);
//        } else if (value instanceof Double) {
//            cell.setCellValue((Double) value);
//        }
//        if (style != null) {
//            cell.setCellStyle(style);
//        }
//    }
//
//    private CellStyle createHeaderStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        Font font = wb.createFont();
//        font.setBold(true);
//        font.setFontHeightInPoints((short) 14);
//        font.setColor(IndexedColors.WHITE.getIndex());
//        style.setFont(font);
//        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createSubHeaderStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        Font font = wb.createFont();
//        font.setBold(true);
//        font.setFontHeightInPoints((short) 10);
//        style.setFont(font);
//        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setWrapText(true);
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createDateStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setDataFormat(wb.createDataFormat().getFormat("dd/mm/yyyy"));
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createNumberStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createCenterStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createWrapTextStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setWrapText(true);
//        style.setVerticalAlignment(VerticalAlignment.TOP);
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createGreenHighlightStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        Font font = wb.createFont();
//        font.setBold(true);
//        style.setFont(font);
//        applyBorders(style);
//        return style;
//    }
//
//    private CellStyle createYellowHighlightStyle(Workbook wb) {
//        CellStyle style = wb.createCellStyle();
//        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
//        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//        style.setAlignment(HorizontalAlignment.CENTER);
//        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        applyBorders(style);
//        return style;
//    }
//
//    private void applyBorders(CellStyle style) {
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBorderTop(BorderStyle.THIN);
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setBorderRight(BorderStyle.THIN);
//    }
//}


package com.webgram.dgpsn.services.Impl;

import com.opencsv.CSVWriter;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.CessationFonctionEntity;
import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypeConge;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.CessationFonctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CongeExportServiceImpl {

    private final AgentRepository agentRepository;
    private final CessationFonctionRepository cessationRepository;

    private static final String[] MOIS = {"janv", "fevr", "mars", "avril", "mai", "juin",
            "juil", "aout", "sept", "oct", "nov", "déc"};

    /**
     * Export Excel avec groupement par direction
     */
    public byte[] exportCongesAgentsExcel(Integer annee) throws IOException {
        log.info("Export Excel - Année {} avec calcul des absences AUTRES par mois et groupement par direction", annee);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("ABSENCES " + annee);

            // Styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle subHeaderStyle = createSubHeaderStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle numberStyle = createNumberStyle(workbook);
            CellStyle wrapStyle = createWrapTextStyle(workbook);
            CellStyle greenStyle = createGreenHighlightStyle(workbook);
            CellStyle yellowStyle = createYellowHighlightStyle(workbook);
            CellStyle centerStyle = createCenterStyle(workbook);
            CellStyle directionStyle = createDirectionStyle(workbook);

            // En-têtes
            createDetailedHeader(sheet, headerStyle, subHeaderStyle, annee);

            // Récupérer tous les agents et grouper par direction
            List<AgentEntity> agents = agentRepository.findAll();
            Map<String, List<AgentEntity>> agentsByDirection = agents.stream()
                    .collect(Collectors.groupingBy(
                            agent -> agent.getDirection() != null ? agent.getDirection().getLibelle() : "Sans direction",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            int rowNum = 2;
            int agentCounter = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Parcourir chaque direction
            for (Map.Entry<String, List<AgentEntity>> entry : agentsByDirection.entrySet()) {
                String directionName = entry.getKey();
                List<AgentEntity> directionAgents = entry.getValue();

                log.info("Traitement de la direction: {} - {} agents", directionName, directionAgents.size());

                // Première ligne de la direction : afficher le nom de la direction
                boolean isFirstAgentInDirection = true;

                for (AgentEntity agent : directionAgents) {
                    List<CessationFonctionEntity> cessations =
                            cessationRepository.findByAgentIdAndAnnee(agent.getId(), annee);

                    if (cessations.isEmpty()) {
                        // Agent sans cessation
                        createAgentRowWithoutCessation(sheet, rowNum++, agentCounter++, agent,
                                isFirstAgentInDirection ? directionName : "",
                                dateStyle, numberStyle, wrapStyle, centerStyle, directionStyle);
                    } else {
                        // Agent avec cessations
                        createAgentRowWithCessations(sheet, rowNum++, agentCounter++, agent, cessations, annee,
                                isFirstAgentInDirection ? directionName : "",
                                sdf, dateStyle, numberStyle, wrapStyle, greenStyle, yellowStyle, centerStyle, directionStyle);
                    }

                    isFirstAgentInDirection = false;
                }
            }

            // Ajuster les colonnes
            for (int i = 0; i < 30; i++) {
                sheet.autoSizeColumn(i);
            }
            // Période des congés plus large
            sheet.setColumnWidth(22, 256 * 60);

            workbook.write(out);
            log.info("Export Excel terminé - {} agents traités dans {} directions",
                    agentCounter - 1, agentsByDirection.size());
            return out.toByteArray();
        }
    }

    /**
     * Export CSV avec groupement par direction
     */
    public void exportCongesAgentsCSV(PrintWriter writer, Integer annee) {
        log.info("Export CSV - Année {} avec groupement par direction", annee);

        try (CSVWriter csv = new CSVWriter(writer, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // En-têtes
            String[] headers = buildCSVHeaders(annee);
            csv.writeNext(headers);

            // Récupérer tous les agents et grouper par direction
            List<AgentEntity> agents = agentRepository.findAll();
            Map<String, List<AgentEntity>> agentsByDirection = agents.stream()
                    .collect(Collectors.groupingBy(
                            agent -> agent.getDirection() != null ? agent.getDirection().getLibelle() : "Sans direction",
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            int agentCounter = 1;

            // Parcourir chaque direction
            for (Map.Entry<String, List<AgentEntity>> entry : agentsByDirection.entrySet()) {
                String directionName = entry.getKey();
                List<AgentEntity> directionAgents = entry.getValue();
                boolean isFirstAgentInDirection = true;

                for (AgentEntity agent : directionAgents) {
                    List<CessationFonctionEntity> cessations =
                            cessationRepository.findByAgentIdAndAnnee(agent.getId(), annee);

                    csv.writeNext(buildAgentCSVRow(
                            agentCounter++,
                            agent,
                            cessations,
                            annee,
                            sdf,
                            isFirstAgentInDirection ? directionName : ""
                    ));

                    isFirstAgentInDirection = false;
                }
            }

            log.info("Export CSV terminé");
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'export CSV", e);
        }
    }

    // ==================== EN-TÊTES ====================

    private void createDetailedHeader(Sheet sheet, CellStyle headerStyle,
                                      CellStyle subHeaderStyle, Integer annee) {
        // Ligne 0: Titre principal
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("ABSENCES " + annee);
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 29));

        // Ligne 1: En-têtes de colonnes
        Row headerRow = sheet.createRow(1);
        String[] headers = {
                "N", "DIRECTION", "PRENOM", "NOM",
                "EMPLOI DANS L'ETABLISSEMENT", "DATE D'ENTREE", "PERIODE DE REFERENCE",
                "DROIT BRUT " + annee,
                "janv", "fevr", "mars", "avril", "mai", "juin",
                "juil", "aout", "sept", "oct", "nov", "déc",
                "Total Absences",
                "CONGES PRIS " + annee,
                "CONGE À SUPP",
                "PERIODE DES CONGES",
                "Jrs autorisées " + (annee - 1),
                "SOLDE " + annee + " À REPORTER"
        };

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(subHeaderStyle);
        }
    }

    // ==================== CRÉATION DES LIGNES ====================

    private void createAgentRowWithoutCessation(Sheet sheet, int rowNum, int agentNumber,
                                                AgentEntity agent, String directionName,
                                                CellStyle dateStyle, CellStyle numberStyle,
                                                CellStyle wrapStyle, CellStyle centerStyle,
                                                CellStyle directionStyle) {
        Row row = sheet.createRow(rowNum);
        int col = 0;

        createCell(row, col++, agentNumber, centerStyle); // N
        createCell(row, col++, directionName, directionStyle); // Direction (vide si pas première ligne)
        createCell(row, col++, agent.getPrenom() != null ? agent.getPrenom() : "", null);
        createCell(row, col++, agent.getNom() != null ? agent.getNom() : "", null);
        createCell(row, col++, agent.getFonction() != null ? agent.getFonction().getLibelle() : "", null);
        createCell(row, col++, "", dateStyle);
        createCell(row, col++, "", null);
        createCell(row, col++, "", numberStyle);

        // 12 mois vides
        for (int i = 0; i < 12; i++) {
            createCell(row, col++, "", numberStyle);
        }

        createCell(row, col++, "", numberStyle); // Total
        createCell(row, col++, "", numberStyle); // Congés pris
        createCell(row, col++, "", numberStyle); // À supprimer
        createCell(row, col++, "", wrapStyle);   // Période
        createCell(row, col++, "", numberStyle); // Autorisés N-1
        createCell(row, col++, "", numberStyle); // Solde
    }

    private void createAgentRowWithCessations(Sheet sheet, int rowNum, int agentNumber,
                                              AgentEntity agent,
                                              List<CessationFonctionEntity> cessations,
                                              Integer annee, String directionName,
                                              SimpleDateFormat sdf,
                                              CellStyle dateStyle, CellStyle numberStyle,
                                              CellStyle wrapStyle, CellStyle greenStyle,
                                              CellStyle yellowStyle, CellStyle centerStyle,
                                              CellStyle directionStyle) {
        Row row = sheet.createRow(rowNum);
        int col = 0;

        // Colonnes de base
        createCell(row, col++, agentNumber, centerStyle); // N
        createCell(row, col++, directionName, directionStyle); // Direction (vide si pas première ligne)
        createCell(row, col++, agent.getPrenom() != null ? agent.getPrenom() : "", null);
        createCell(row, col++, agent.getNom() != null ? agent.getNom() : "", null);
        createCell(row, col++, agent.getFonction() != null ? agent.getFonction().getLibelle() : "", null);

        // Date d'entrée - première date de congé
        String dateEntree = "";
        if (!cessations.isEmpty() && cessations.get(0).getConge().getDateDepart() != null) {
            dateEntree = sdf.format(cessations.get(0).getConge().getDateDepart());
        }
        createCell(row, col++, dateEntree, dateStyle);

        // Période de référence - du premier au dernier congé
        String periodeReference = buildPeriodeReference(cessations, sdf);
        createCell(row, col++, periodeReference, null);

        // Droit brut - dernier solde annuel
        Integer droitBrut = getDroitBrut(cessations);
        createCell(row, col++, droitBrut != null ? droitBrut : "", numberStyle);

        // Absences par mois UNIQUEMENT pour type AUTRES avec coloration
        int[] absencesParMois = calculerAbsencesAutresParMois(cessations, annee);
        int totalAbsences = 0;

        for (int i = 0; i < 12; i++) {
            if (absencesParMois[i] > 0) {
                // Colorer en jaune pour les congés AUTRES
                createCell(row, col++, absencesParMois[i], yellowStyle);
                totalAbsences += absencesParMois[i];
            } else {
                createCell(row, col++, "", numberStyle);
            }
        }

        // Total absences
        createCell(row, col++, totalAbsences > 0 ? totalAbsences : "", numberStyle);

        // Congés pris = somme des nombreDeJoursdemande
        Integer congesPris = calculerCongesPris(cessations);
        createCell(row, col++, congesPris > 0 ? congesPris : "", numberStyle);

        // Congé à supprimer (vide pour l'instant)
        createCell(row, col++, "", numberStyle);

        // Période des congés avec décisions
        String periodeConges = buildPeriodeCongesAvecDecisions(cessations, sdf);
        createCell(row, col++, periodeConges, wrapStyle);

        // Jours autorisés année précédente
        Integer joursAutoN1 = getJoursAutorisesAnneePrecedente(agent.getId(), annee);
        createCell(row, col++, joursAutoN1 != null ? joursAutoN1 : "", numberStyle);

        // Solde à reporter
        Integer solde = calculerSolde(droitBrut, congesPris);
        createCell(row, col++, solde != null && solde != 0 ? solde : "", numberStyle);
    }

    // ==================== CALCULS ====================

    private String buildPeriodeReference(List<CessationFonctionEntity> cessations, SimpleDateFormat sdf) {
        if (cessations.isEmpty()) return "";

        Date premiereDateDepart = null;
        Date derniereDateReprise = null;

        for (CessationFonctionEntity cessation : cessations) {
            CongeEntity conge = cessation.getConge();
            if (conge.getDateDepart() != null) {
                if (premiereDateDepart == null || conge.getDateDepart().before(premiereDateDepart)) {
                    premiereDateDepart = conge.getDateDepart();
                }
            }
            if (conge.getDateReprise() != null) {
                if (derniereDateReprise == null || conge.getDateReprise().after(derniereDateReprise)) {
                    derniereDateReprise = conge.getDateReprise();
                }
            }
        }

        if (premiereDateDepart != null && derniereDateReprise != null) {
            return sdf.format(premiereDateDepart) + " au " + sdf.format(derniereDateReprise);
        }
        return "";
    }

    private Integer getDroitBrut(List<CessationFonctionEntity> cessations) {
        return cessations.stream()
                .map(CessationFonctionEntity::getSoldeAnnuel)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(null);
    }

    /**
     * NOUVEAU: Calcule les absences UNIQUEMENT pour les congés de type AUTRES
     * en utilisant dateDemande pour déterminer le mois
     */
//    private int[] calculerAbsencesAutresParMois(List<CessationFonctionEntity> cessations, Integer annee) {
//        int[] absences = new int[12];
//
//        for (CessationFonctionEntity cessation : cessations) {
//            CongeEntity conge = cessation.getConge();
//
//            // Vérifier si c'est un congé de type AUTRES
//            if (conge.getTypeConge() != TypeConge.AUTRES) {
//                continue; // Ignorer les autres types de congés
//            }
//
//            // Utiliser dateDemande pour déterminer le mois
//            Date dateDemande = conge.getDateDemande();
//            Integer duree = conge.getDuree();
//            log.info("duree recuper not :{}",duree);
//            log.info("dateDemande recuper not :{}",dateDemande);// ou cessation.getNombreDeJoursdemande()
//
//            if (dateDemande != null && duree != null && duree > 0) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(dateDemande);
//
//                if (cal.get(Calendar.YEAR) == annee) {
//                    int mois = cal.get(Calendar.MONTH);
//                    absences[mois] += duree;
//                }
//            }
//        }
//
//        return absences;
//    }

    private int[] calculerAbsencesAutresParMois(List<CessationFonctionEntity> cessations, Integer annee) {
        int[] absences = new int[12];

        for (CessationFonctionEntity cessation : cessations) {
            CongeEntity conge = cessation.getConge();

            // Vérifier si c'est un congé de type AUTRES
            if (conge.getTypeConge() != TypeConge.AUTRES) {
                continue; // Ignorer les autres types de congés
            }

            // Utiliser dateDemande pour déterminer le mois
            Date dateDemande = conge.getDateDemande();
            Integer duree = conge.getDuree();
            log.info("duree recuper not :{}",duree);
            log.info("dateDemande recuper not :{}",dateDemande);// ou cessation.getNombreDeJoursdemande()

            if (dateDemande != null && duree != null && duree > 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateDemande);

                if (cal.get(Calendar.YEAR) == annee) {
                    int mois = cal.get(Calendar.MONTH);
                    absences[mois] += duree;
                }
            }
        }

        return absences;
    }

    private Integer calculerCongesPris(List<CessationFonctionEntity> cessations) {
        return cessations.stream()
                .map(CessationFonctionEntity::getNombreDeJoursdemande)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private String buildPeriodeCongesAvecDecisions(List<CessationFonctionEntity> cessations,
                                                   SimpleDateFormat sdf) {
        List<String> periodes = new ArrayList<>();

        for (CessationFonctionEntity cessation : cessations) {
            CongeEntity conge = cessation.getConge();
            StringBuilder sb = new StringBuilder();

            if (conge.getNumeroDecision() != null) {
                sb.append("Décision N°").append(conge.getNumeroDecision());
            }

            if (conge.getDateDepart() != null) {
                if (sb.length() > 0) sb.append(" du ");
                sb.append(sdf.format(conge.getDateDepart()));
            }

            if (conge.getDateReprise() != null) {
                if (conge.getDateDepart() != null) sb.append(" au ");
                sb.append(sdf.format(conge.getDateReprise()));
            }

            if (cessation.getNombreDeJoursdemande() != null) {
                sb.append(" (").append(cessation.getNombreDeJoursdemande()).append(" jrs)");
            }

            if (sb.length() > 0) {
                periodes.add(sb.toString());
            }
        }

        return String.join("\n", periodes);
    }

    private Integer getJoursAutorisesAnneePrecedente(Long agentId, Integer annee) {
        List<Integer> soldes = cessationRepository.findSoldeAnnuelByAgentAndAnnee(
                agentId, annee - 1);
        return soldes.isEmpty() ? null : soldes.get(0);
    }

    private Integer calculerSolde(Integer droitBrut, Integer congesPris) {
        if (droitBrut == null || congesPris == null) return null;
        return droitBrut - congesPris;
    }

    // ==================== CSV ====================

    private String[] buildCSVHeaders(Integer annee) {
        return new String[]{
                "N", "DIRECTION", "PRENOM", "NOM",
                "EMPLOI DANS L'ETABLISSEMENT", "DATE D'ENTREE", "PERIODE DE REFERENCE",
                "DROIT BRUT " + annee,
                "JANV", "FEVR", "MARS", "AVRIL", "MAI", "JUIN",
                "JUIL", "AOUT", "SEPT", "OCT", "NOV", "DEC",
                "Total Absences", "CONGES PRIS " + annee, "CONGE À SUPP",
                "PERIODE DES CONGES", "Jrs autorisées " + (annee - 1),
                "SOLDES " + annee + " À REPORTER"
        };
    }

    private String[] buildAgentCSVRow(int agentNumber,
                                      AgentEntity agent,
                                      List<CessationFonctionEntity> cessations,
                                      Integer annee, SimpleDateFormat sdf,
                                      String directionName) {
        List<String> row = new ArrayList<>();

        row.add(String.valueOf(agentNumber)); // N
        row.add(directionName); // Direction (vide si pas première ligne)
        row.add(agent.getPrenom() != null ? agent.getPrenom() : "");
        row.add(agent.getNom() != null ? agent.getNom() : "");
        row.add(agent.getFonction() != null ? agent.getFonction().getLibelle() : "");

        if (!cessations.isEmpty() && cessations.get(0).getConge().getDateDepart() != null) {
            row.add(sdf.format(cessations.get(0).getConge().getDateDepart()));
        } else {
            row.add("");
        }

        row.add(buildPeriodeReference(cessations, sdf));

        Integer droitBrut = getDroitBrut(cessations);
        row.add(droitBrut != null ? droitBrut.toString() : "");

        int[] absences = calculerAbsencesAutresParMois(cessations, annee);
        int total = 0;
        for (int a : absences) {
            row.add(a > 0 ? String.valueOf(a) : "");
            total += a;
        }

        row.add(total > 0 ? String.valueOf(total) : "");

        Integer congesPris = calculerCongesPris(cessations);
        row.add(congesPris > 0 ? congesPris.toString() : "");

        row.add(""); // Congé à supp

        row.add(buildPeriodeCongesAvecDecisions(cessations, sdf));

        Integer joursN1 = getJoursAutorisesAnneePrecedente(agent.getId(), annee);
        row.add(joursN1 != null ? joursN1.toString() : "");

        Integer solde = calculerSolde(droitBrut, congesPris);
        row.add(solde != null && solde != 0 ? solde.toString() : "");

        return row.toArray(new String[0]);
    }

    // ==================== STYLES ====================

    private void createCell(Row row, int col, Object value, CellStyle style) {
        Cell cell = row.createCell(col);
        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    private CellStyle createHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createSubHeaderStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        applyBorders(style);
        return style;
    }

    private CellStyle createDirectionStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createDateStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(wb.createDataFormat().getFormat("dd/mm/yyyy"));
        applyBorders(style);
        return style;
    }

    private CellStyle createNumberStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createCenterStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createWrapTextStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        applyBorders(style);
        return style;
    }

    private CellStyle createGreenHighlightStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
        applyBorders(style);
        return style;
    }

    private CellStyle createYellowHighlightStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private void applyBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}