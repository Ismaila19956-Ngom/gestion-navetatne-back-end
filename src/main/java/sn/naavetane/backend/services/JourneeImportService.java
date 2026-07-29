package sn.naavetane.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.naavetane.backend.entities.JourneeEntity;
import sn.naavetane.backend.entities.MatchEntity;
import sn.naavetane.backend.entities.SaisonEntity;
import sn.naavetane.backend.repositories.JourneeRepository;
import sn.naavetane.backend.repositories.SaisonRepository;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JourneeImportService {

    private final JourneeRepository journeeRepository;
    private final SaisonRepository saisonRepository;
    private final AuditService auditService;
    private final DataFormatter dataFormatter = new DataFormatter();

    @Transactional
    public List<JourneeEntity> importJourneesFromExcel(MultipartFile file, String currentUser) throws Exception {
        Map<String, JourneeEntity> journéesMap = new HashMap<>();

        String saisonActive = saisonRepository.findByIsActiveTrue()
                .map(SaisonEntity::getLibelle)
                .orElse(String.valueOf(LocalDate.now().getYear()));

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            
            // On ignore la première ligne (en-tête)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell dateCell = row.getCell(0);
                Cell stadeCell = row.getCell(1);
                Cell heureCell = row.getCell(2);
                Cell eq1Cell = row.getCell(3);
                Cell eq2Cell = row.getCell(4);

                if (dateCell == null || stadeCell == null || eq1Cell == null || eq2Cell == null) {
                    continue; // Ligne incomplète
                }

                LocalDate date = parseDate(dateCell);
                String stade = getCellValueAsString(stadeCell);
                String heure = getCellValueAsString(heureCell);
                String equipe1 = getCellValueAsString(eq1Cell);
                String equipe2 = getCellValueAsString(eq2Cell);

                if (date == null || stade.isEmpty() || equipe1.isEmpty() || equipe2.isEmpty()) {
                    continue; // Ligne invalide
                }

                String key = date.toString() + "_" + stade.trim().toLowerCase();

                JourneeEntity journee = journéesMap.get(key);
                if (journee == null) {
                    journee = JourneeEntity.builder()
                            .date(date)
                            .stade(stade)
                            .statut("PROGRAMMEE")
                            .saison(saisonActive)
                            .matchs(new ArrayList<>())
                            .build();
                    journéesMap.put(key, journee);
                }

                MatchEntity match = MatchEntity.builder()
                        .equipe1(equipe1)
                        .equipe2(equipe2)
                        .heure(heure)
                        .build();

                journee.addMatch(match);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la lecture du fichier Excel", e);
            throw new Exception("Format de fichier non valide ou erreur de lecture. " + e.getMessage());
        }

        List<JourneeEntity> savedJournees = new ArrayList<>();
        for (JourneeEntity journee : journéesMap.values()) {
            savedJournees.add(journeeRepository.save(journee));
        }

        auditService.logAction(currentUser, "IMPORT", "Journées", "Import de " + savedJournees.size() + " journées via fichier Excel", "IP_LOCALE");

        return savedJournees;
    }

    private LocalDate parseDate(Cell cell) {
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else {
                String str = dataFormatter.formatCellValue(cell);
                if (str == null || str.trim().isEmpty()) return null;
                if (str.contains("/")) {
                    return LocalDate.parse(str.trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } else if (str.contains("-")) {
                    return LocalDate.parse(str.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            }
        } catch (Exception e) {
            log.error("Impossible de parser la date: " + cell.toString(), e);
        }
        return null;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        try {
            return dataFormatter.formatCellValue(cell).trim();
        } catch (Exception e) {
            return "";
        }
    }
}
