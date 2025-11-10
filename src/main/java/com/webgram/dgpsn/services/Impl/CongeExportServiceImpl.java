package com.webgram.dgpsn.services.Impl;
import com.opencsv.CSVWriter;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.CessationFonctionEntity;
import com.webgram.dgpsn.entities.CongeEntity;
import com.webgram.dgpsn.entities.enums.TypeConge;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.CessationFonctionRepository;
import com.webgram.dgpsn.repositories.CongeRepository;
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
    private final CongeRepository congeRepository;

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

            // Styles - TOUS LES STYLES NECESSAIRES
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle subHeaderStyle = createSubHeaderStyle(workbook);
            CellStyle dateStyle = createDateStyle(workbook);
            CellStyle numberStyle = createNumberStyle(workbook);
            CellStyle wrapStyle = createWrapTextStyle(workbook);
            CellStyle greenStyle = createGreenHighlightStyle(workbook);
            CellStyle yellowStyle = createYellowHighlightStyle(workbook);
            CellStyle centerStyle = createCenterStyle(workbook);
            CellStyle directionStyle = createDirectionStyle(workbook);
            CellStyle nomPrenomStyle = createNomPrenomStyle(workbook);
            CellStyle emploiStyle = createEmploiStyle(workbook);
            CellStyle dateEntreeStyle = createDateEntreeStyle(workbook);
            CellStyle periodeStyle = createPeriodeStyle(workbook);

            // En-têtes
            createDetailedHeader(sheet, headerStyle, subHeaderStyle, annee);

            // Récupérer tous les agents SAUF ceux avec prénom "Administrateur"
            List<AgentEntity> agents = agentRepository.findAll().stream()
                    .filter(agent -> agent.getPrenom() == null ||
                            !agent.getPrenom().equalsIgnoreCase("Administrateur"))
                    .collect(Collectors.toList());

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
                                annee, dateStyle, numberStyle, wrapStyle, centerStyle, directionStyle,
                                yellowStyle, nomPrenomStyle, emploiStyle, dateEntreeStyle, periodeStyle);
                    } else {
                        // Agent avec cessations
                        createAgentRowWithCessations(sheet, rowNum++, agentCounter++, agent, cessations, annee,
                                isFirstAgentInDirection ? directionName : "",
                                sdf, dateStyle, numberStyle, wrapStyle, greenStyle, yellowStyle, centerStyle,
                                directionStyle, nomPrenomStyle, emploiStyle, dateEntreeStyle, periodeStyle);
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

            // Récupérer tous les agents SAUF ceux avec prénom "Administrateur"
            List<AgentEntity> agents = agentRepository.findAll().stream()
                    .filter(agent -> agent.getPrenom() == null ||
                            !agent.getPrenom().equalsIgnoreCase("Administrateur"))
                    .collect(Collectors.toList());

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
                "janv", "févr", "mars", "avril", "mai", "juin",
                "juil", "août", "sept", "oct", "nov", "déc",
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
                                                Integer annee,
                                                CellStyle dateStyle, CellStyle numberStyle,
                                                CellStyle wrapStyle, CellStyle centerStyle,
                                                CellStyle directionStyle, CellStyle yellowStyle,
                                                CellStyle nomPrenomStyle, CellStyle emploiStyle,
                                                CellStyle dateEntreeStyle, CellStyle periodeStyle) {
        Row row = sheet.createRow(rowNum);
        int col = 0;

        createCell(row, col++, agentNumber, centerStyle); // N - GRIS
        createCell(row, col++, directionName, directionStyle); // Direction - GRIS
        createCell(row, col++, agent.getPrenom() != null ? agent.getPrenom() : "", nomPrenomStyle); // Prénom - GRIS
        createCell(row, col++, agent.getNom() != null ? agent.getNom() : "", nomPrenomStyle); // Nom - GRIS
        createCell(row, col++, agent.getFonction() != null ? agent.getFonction().getLibelle() : "", emploiStyle); // Emploi - BLEU CLAIR
        createCell(row, col++, "", dateEntreeStyle); // Date entrée - BLEU CLAIR
        createCell(row, col++, "", periodeStyle); // Période - VERT CLAIR
        createCell(row, col++, "", numberStyle); // Droit brut - GRIS

        // Calculer les absences AUTRES même si pas de cessations
        int[] absencesParMois = calculerAbsencesAutresParMois(agent, annee);
        int totalAbsences = 0;

        for (int i = 0; i < 12; i++) {
            if (absencesParMois[i] > 0) {
                createCell(row, col++, absencesParMois[i], yellowStyle); // JAUNE si valeur
                totalAbsences += absencesParMois[i];
            } else {
                createCell(row, col++, "", numberStyle); // GRIS si vide
            }
        }

        createCell(row, col++, totalAbsences > 0 ? totalAbsences : "", numberStyle); // Total - GRIS
        createCell(row, col++, "", numberStyle); // Congés pris - GRIS
        createCell(row, col++, "", numberStyle); // À supprimer - GRIS
        createCell(row, col++, "", wrapStyle);   // Période - GRIS
        createCell(row, col++, "", numberStyle); // Autorisés N-1 - GRIS
        createCell(row, col++, "", numberStyle); // Solde - GRIS
    }

    private void createAgentRowWithCessations(Sheet sheet, int rowNum, int agentNumber,
                                              AgentEntity agent,
                                              List<CessationFonctionEntity> cessations,
                                              Integer annee, String directionName,
                                              SimpleDateFormat sdf,
                                              CellStyle dateStyle, CellStyle numberStyle,
                                              CellStyle wrapStyle, CellStyle greenStyle,
                                              CellStyle yellowStyle, CellStyle centerStyle,
                                              CellStyle directionStyle, CellStyle nomPrenomStyle,
                                              CellStyle emploiStyle, CellStyle dateEntreeStyle,
                                              CellStyle periodeStyle) {
        Row row = sheet.createRow(rowNum);
        int col = 0;

        // Colonnes de base avec les bonnes couleurs
        createCell(row, col++, agentNumber, centerStyle); // N - GRIS
        createCell(row, col++, directionName, directionStyle); // Direction - GRIS
        createCell(row, col++, agent.getPrenom() != null ? agent.getPrenom() : "", nomPrenomStyle); // Prénom - GRIS
        createCell(row, col++, agent.getNom() != null ? agent.getNom() : "", nomPrenomStyle); // Nom - GRIS
        createCell(row, col++, agent.getFonction() != null ? agent.getFonction().getLibelle() : "", emploiStyle); // Emploi - BLEU CLAIR

        // Date d'entrée - première date de congé - BLEU CLAIR
        String dateEntree = "";
        if (!cessations.isEmpty() && cessations.get(0).getConge().getDateDepart() != null) {
            dateEntree = sdf.format(cessations.get(0).getConge().getDateDepart());
        }
        createCell(row, col++, dateEntree, dateEntreeStyle);

        // Période de référence - du premier au dernier congé - VERT CLAIR
        String periodeReference = buildPeriodeReference(cessations, sdf);
        createCell(row, col++, periodeReference, periodeStyle);

        // Droit brut - dernier solde annuel - GRIS
        Integer droitBrut = getDroitBrut(cessations);
        createCell(row, col++, droitBrut != null ? droitBrut : "", numberStyle);

        // Absences par mois UNIQUEMENT pour type AUTRES
        int[] absencesParMois = calculerAbsencesAutresParMois(agent, annee);
        int totalAbsences = 0;

        for (int i = 0; i < 12; i++) {
            if (absencesParMois[i] > 0) {
                // JAUNE pour les congés AUTRES avec valeur
                createCell(row, col++, absencesParMois[i], yellowStyle);
                totalAbsences += absencesParMois[i];
            } else {
                // GRIS pour les cases vides
                createCell(row, col++, "", numberStyle);
            }
        }

        // Total absences - GRIS
        createCell(row, col++, totalAbsences > 0 ? totalAbsences : "", numberStyle);

        // Congés pris = somme des nombreDeJoursdemande - GRIS
        Integer congesPris = calculerCongesPris(cessations);
        createCell(row, col++, congesPris > 0 ? congesPris : "", numberStyle);

        // Congé à supprimer - GRIS
        createCell(row, col++, "", numberStyle);

        // Période des congés avec décisions - GRIS
        String periodeConges = buildPeriodeCongesAvecDecisions(cessations, sdf);
        createCell(row, col++, periodeConges, wrapStyle);

        // Jours autorisés année précédente - GRIS
        Integer joursAutoN1 = getJoursAutorisesAnneePrecedente(agent.getId(), annee);
        createCell(row, col++, joursAutoN1 != null ? joursAutoN1 : "", numberStyle);

        // Solde à reporter - GRIS
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
     * Calcule les absences UNIQUEMENT pour les congés de type AUTRES
     */
    private int[] calculerAbsencesAutresParMois(AgentEntity agent, Integer annee) {
        int[] absences = new int[12];

        // Récupérer DIRECTEMENT les congés de type AUTRES pour cet agent
        List<CongeEntity> congesAutres = congeRepository.findByAgentIdAndTypeConge(
                agent.getId(),
                TypeConge.AUTRES
        );

        log.info("Agent {} {} - {} congés AUTRES trouvés",
                agent.getPrenom(), agent.getNom(), congesAutres.size());

        for (CongeEntity conge : congesAutres) {
            Date dateDemande = conge.getDateDemande();
            Integer duree = conge.getDuree();

            log.info("  → Congé: dateDemande={}, duree={}", dateDemande, duree);

            if (dateDemande != null && duree != null && duree > 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateDemande);

                int anneeConge = cal.get(Calendar.YEAR);
                if (anneeConge == annee) {
                    int mois = cal.get(Calendar.MONTH);
                    absences[mois] += duree;

                    log.info("    ✓ Ajouté: {} jours au mois {} ({})", duree, mois + 1, MOIS[mois]);
                } else {
                    log.info("    ✗ Année différente: {} vs {}", anneeConge, annee);
                }
            } else {
                log.warn("    ✗ Données manquantes: dateDemande={}, duree={}", dateDemande, duree);
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

//            if (cessation.getNombreDeJoursdemande() != null) {
//                sb.append(" (").append(cessation.getNombreDeJoursdemande()).append(" jrs)");
//            }

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

        row.add(String.valueOf(agentNumber));
        row.add(directionName);
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

        int[] absences = calculerAbsencesAutresParMois(agent, annee);
        int total = 0;
        for (int a : absences) {
            row.add(a > 0 ? String.valueOf(a) : "");
            total += a;
        }

        row.add(total > 0 ? String.valueOf(total) : "");

        Integer congesPris = calculerCongesPris(cessations);
        row.add(congesPris > 0 ? congesPris.toString() : "");

        row.add("");

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
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        // GRIS pour l'en-tête principal
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
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
        font.setFontHeightInPoints((short) 9);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        // GRIS pour les en-têtes de colonnes
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
        // GRIS pour Direction
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        applyBorders(style);
        return style;
    }

    private CellStyle createNomPrenomStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(false);
        style.setFont(font);
        // GRIS pour Nom et Prénom
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        applyBorders(style);
        return style;
    }

    private CellStyle createEmploiStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(false);
        style.setFont(font);
        // BLEU CLAIR pour Emploi dans l'établissement
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        applyBorders(style);
        return style;
    }

    private CellStyle createDateEntreeStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // BLEU CLAIR pour Date d'entrée
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("dd/mm/yyyy"));
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createPeriodeStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // VERT CLAIR pour Période de référence
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
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
        // GRIS pour les colonnes normales
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createCenterStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // GRIS pour N° (première colonne)
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        applyBorders(style);
        return style;
    }

    private CellStyle createWrapTextStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(VerticalAlignment.TOP);
        // GRIS pour période des congés
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        applyBorders(style);
        return style;
    }

    private CellStyle createGreenHighlightStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // VERT CLAIR pour certaines valeurs
        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(false);
        style.setFont(font);
        applyBorders(style);
        return style;
    }

    private CellStyle createYellowHighlightStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        // JAUNE pour les absences AUTRES
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);
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