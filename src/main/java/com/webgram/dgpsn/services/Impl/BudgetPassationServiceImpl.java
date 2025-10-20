package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.QBudgetPassationEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.BudgetPassationMapper;
import com.webgram.dgpsn.models.BudgetPassationDTO;
import com.webgram.dgpsn.models.EngagementDTO;
import com.webgram.dgpsn.models.OrdonnancementDTO;
import com.webgram.dgpsn.repositories.BudgetPassationRepository;
import com.webgram.dgpsn.services.BudgetPassationService;
import com.webgram.dgpsn.services.EngagementService;
import com.webgram.dgpsn.services.OrdonnancementService;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BudgetPassationServiceImpl implements BudgetPassationService {
    private final BudgetPassationRepository budgetPassationRepository;
    private final BudgetPassationMapper budgetPassationMapper;

    @Override
    @Journal(actionType = ActionType.ADD_ACTEUR)
    public BudgetPassationDTO create(BudgetPassationDTO budgetPassationDTO) {
        var savedBudgetPassation = budgetPassationRepository.save(budgetPassationMapper.asEntity(budgetPassationDTO));
        log.info("Budget passation successfully added {}", savedBudgetPassation);
        return budgetPassationMapper.asDto(savedBudgetPassation);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public BudgetPassationDTO update(BudgetPassationDTO budgetPassationDTO) {
        var budgetPassationSaved = budgetPassationMapper.asEntity(budgetPassationDTO);
        var updatedBudgetPassation = budgetPassationMapper.asDto(budgetPassationRepository.save(budgetPassationSaved));
        log.info("Budget passation successfully updated {}", updatedBudgetPassation.getId());
        return updatedBudgetPassation;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public BudgetPassationDTO read(Long budgetPassationId) {
        var budgetPassation = budgetPassationRepository
                .findById(budgetPassationId)
                .orElseThrow(() -> new ResourceNotFoundException("BudgetPassation", budgetPassationId));
        log.info("Reading budget passation id {}", budgetPassationId);
        return budgetPassationMapper.asDto(budgetPassation);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long budgetPassationId) {
        try {
            budgetPassationRepository.deleteById(budgetPassationId);
            log.info("The budget passation id {} is deleted", budgetPassationId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<BudgetPassationDTO> readAllBudgetPassations(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        return budgetPassationRepository.findAll(booleanBuilder, pageable)
                .map(budgetPassationMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QBudgetPassationEntity.budgetPassationEntity;
            if (searchParams.containsKey("program"))
                booleanBuilder.and(qEntity.program.eq(searchParams.get("program")));
            if (searchParams.containsKey("libelleSection"))
                booleanBuilder.and(qEntity.libelleSection.eq(searchParams.get("libelleSection")));
            if (searchParams.containsKey("categorie"))
                booleanBuilder.and(qEntity.categorie.eq(searchParams.get("categorie")));
            if (searchParams.containsKey("chapitre"))
                booleanBuilder.and(qEntity.chapitre.eq(searchParams.get("chapitre")));
            if (searchParams.containsKey("libelleChapitre"))
                booleanBuilder.and(qEntity.libelleChapitre.eq(searchParams.get("libelleChapitre")));
            if (searchParams.containsKey("credits"))
                booleanBuilder.and(qEntity.credits.eq(Double.valueOf(searchParams.get("credits"))));
            if (searchParams.containsKey("anneeCredits"))
                booleanBuilder.and(qEntity.anneeCredits.eq(searchParams.get("anneeCredits")));
            if (searchParams.containsKey("budgetPassationId")) {
                try {
                    Long id = Long.valueOf(searchParams.get("budgetPassationId"));
                    booleanBuilder.and(qEntity.id.eq(id));
                } catch (NumberFormatException e) {
                    // Ignorer si le format n'est pas valide
                }
            }
        }
    }

//    @Override
//    public ByteArrayResource exportAllBudgetPassationsForYearWithEngagementsAndOrdonnancements(
//            String year, EngagementService engagementService, OrdonnancementService ordonnancementService) {
//        // Récupérer tous les budgets pour l'année donnée et l'année précédente
//        int currentYear = Integer.parseInt(year);
//        int previousYear = currentYear - 1;
//        Map<String, String> searchParamsCurrent = Map.of("anneeCredits", year);
//        Map<String, String> searchParamsPrevious = Map.of("anneeCredits", String.valueOf(previousYear));
//        Pageable pageable = Pageable.unpaged();
//
//        List<BudgetPassationDTO> budgetPassationsCurrent = readAllBudgetPassations(searchParamsCurrent, pageable).getContent();
//        List<BudgetPassationDTO> budgetPassationsPrevious = readAllBudgetPassations(searchParamsPrevious, pageable).getContent();
//
//        if (budgetPassationsCurrent.isEmpty() && budgetPassationsPrevious.isEmpty()) {
//            throw new RuntimeException("Aucun budget trouvé pour l'année " + year + " ou " + previousYear);
//        }
//
//        // Combiner les budgets (éviter les doublons si un budgetPassationId existe dans les deux années)
//        Map<Long, BudgetPassationDTO> budgetMap = new HashMap<>();
//        budgetPassationsCurrent.forEach(b -> budgetMap.put(b.getId(), b));
//        budgetPassationsPrevious.forEach(b -> budgetMap.put(b.getId(), b)); // Remplace si doublon, garde la dernière année
//
//        List<BudgetPassationDTO> budgetPassations = new ArrayList<>(budgetMap.values());
//
//        // Define budget fields
//        List<String> budgetFields = Arrays.asList("program", "libelleSection", "categorie", "chapitre", "libelleChapitre");
//
//        // Month order map for sorting
//        Map<String, Integer> monthMap = new HashMap<>();
//        monthMap.put("jan", 1);
//        monthMap.put("fév", 2);
//        monthMap.put("mars", 3);
//        monthMap.put("avril", 4);
//        monthMap.put("mai", 5);
//        monthMap.put("juin", 6);
//        monthMap.put("juil", 7);
//        monthMap.put("août", 8);
//        monthMap.put("sept", 9);
//        monthMap.put("oct", 10);
//        monthMap.put("nov", 11);
//        monthMap.put("déc", 12);
//
//        // Base headers from budget fields
//        List<String> baseHeaders = budgetFields.stream().map(this::formatHeader).collect(Collectors.toList());
//
//        // Add concatenated credits headers (current year and previous year)
//        baseHeaders.add("Crédits Ouvert " + year);
//        baseHeaders.add("Crédits Ouvert " + previousYear);
//
//        // Collecter tous les engagements et ordonnancements pour tous les budgets
//        List<EngagementDTO> allEngagements = new ArrayList<>();
//        List<OrdonnancementDTO> allOrdonnancements = new ArrayList<>();
//
//        for (BudgetPassationDTO budget : budgetPassations) {
//            Map<String, String> searchParamsEng = Map.of("budgetPassationId", String.valueOf(budget.getId()));
//            List<EngagementDTO> engagements = engagementService.readAllEngagements(searchParamsEng, pageable).getContent();
//            allEngagements.addAll(engagements);
//
//            Map<String, String> searchParamsOrd = Map.of("budgetPassationId", String.valueOf(budget.getId()));
//            List<OrdonnancementDTO> ordonnancements = ordonnancementService.readAllOrdonnancements(searchParamsOrd, pageable).getContent();
//            allOrdonnancements.addAll(ordonnancements);
//        }
//
//        // For engagements - collecter tous les mois uniques
//        Set<String> uniqueMonthsEng = allEngagements.stream()
//                .map(EngagementDTO::getMois)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//
//        List<String> monthHeadersEng = uniqueMonthsEng.stream()
//                .sorted(Comparator.comparingInt(m -> monthMap.getOrDefault(m.toLowerCase(), 13)))
//                .map(month -> "Engagé " + month)
//                .collect(Collectors.toList());
//
//        // For ordonnancements - collecter tous les mois uniques
//        Set<String> uniqueMonthsOrd = allOrdonnancements.stream()
//                .map(OrdonnancementDTO::getMois)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//
//        List<String> monthHeadersOrd = uniqueMonthsOrd.stream()
//                .sorted(Comparator.comparingInt(m -> monthMap.getOrDefault(m.toLowerCase(), 13)))
//                .map(month -> "Ordonnancé " + month)
//                .collect(Collectors.toList());
//
//        // All headers for engagements
//        List<String> allHeadersEng = new ArrayList<>(baseHeaders);
//        allHeadersEng.addAll(monthHeadersEng);
//        allHeadersEng.add("Total");
//        allHeadersEng.add("Reste à programmer");
//
//        // All headers for ordonnancements
//        List<String> allHeadersOrd = new ArrayList<>(baseHeaders);
//        allHeadersOrd.addAll(monthHeadersOrd);
//        allHeadersOrd.add("Total");
//        allHeadersOrd.add("Reste à ordonnancer");
//
//        // Create workbook
//        Workbook workbook = new XSSFWorkbook();
//
//        // Engagements sheet
//        Sheet sheetEng = workbook.createSheet("Engagements");
//        Row headerRowEng = sheetEng.createRow(0);
//        for (int i = 0; i < allHeadersEng.size(); i++) {
//            headerRowEng.createCell(i).setCellValue(allHeadersEng.get(i));
//        }
//
//        // Remplir les données pour chaque budget - Engagements
//        int rowIndexEng = 1;
//        for (BudgetPassationDTO budget : budgetPassations) {
//            Row budgetRowEng = sheetEng.createRow(rowIndexEng++);
//            int colIndexEng = 0;
//
//            // Fill budget fields
//            for (String fieldName : budgetFields) {
//                try {
//                    Field field = BudgetPassationDTO.class.getDeclaredField(fieldName);
//                    field.setAccessible(true);
//                    Object value = field.get(budget);
//                    budgetRowEng.createCell(colIndexEng++).setCellValue(value != null ? value.toString() : "");
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    budgetRowEng.createCell(colIndexEng++).setCellValue("");
//                }
//            }
//
//            // Add credits values from getCredits for current and previous year
//            double creditsCurrent = budget.getAnneeCredits().equals(year) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
//            double creditsPrevious = budget.getAnneeCredits().equals(String.valueOf(previousYear)) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
//            budgetRowEng.createCell(colIndexEng++).setCellValue(creditsCurrent > 0 ? creditsCurrent : 0.0);
//            budgetRowEng.createCell(colIndexEng++).setCellValue(creditsPrevious > 0 ? creditsPrevious : 0.0);
//
//            // Calculer les totaux mensuels pour ce budget spécifique - Engagements
//            Map<String, String> searchParamsEng = Map.of("budgetPassationId", String.valueOf(budget.getId()));
//            List<EngagementDTO> budgetEngagements = engagementService.readAllEngagements(searchParamsEng, pageable).getContent();
//            Map<String, Double> monthlyTotalsEng = budgetEngagements.stream()
//                    .filter(e -> e.getMois() != null && e.getMontantEngage() != null)
//                    .collect(Collectors.groupingBy(EngagementDTO::getMois,
//                            Collectors.summingDouble(EngagementDTO::getMontantEngage)));
//
//            // Add monthly engagement totals
//            for (String monthHeader : monthHeadersEng) {
//                String month = monthHeader.replace("Engagé ", "");
//                Double total = monthlyTotalsEng.getOrDefault(month, 0.0);
//                if (total > 0) {
//                    budgetRowEng.createCell(colIndexEng++).setCellValue(total);
//                } else {
//                    budgetRowEng.createCell(colIndexEng++).setCellValue("");
//                }
//            }
//
//            // Calculate total and reste for this budget
//            double totalEng = monthlyTotalsEng.values().stream().mapToDouble(Double::doubleValue).sum();
//            double resteEng = (budget.getCredits() != null ? budget.getCredits() : 0.0) - totalEng;
//
//            if (totalEng > 0) {
//                budgetRowEng.createCell(colIndexEng++).setCellValue(totalEng);
//            } else {
//                budgetRowEng.createCell(colIndexEng++).setCellValue("");
//            }
//            if (resteEng > 0) {
//                budgetRowEng.createCell(colIndexEng++).setCellValue(resteEng);
//            } else {
//                budgetRowEng.createCell(colIndexEng++).setCellValue("");
//            }
//        }
//
//        // Auto-size columns for engagements
//        for (int i = 0; i < allHeadersEng.size(); i++) {
//            sheetEng.autoSizeColumn(i);
//        }
//
//        // Ordonnancements sheet
//        Sheet sheetOrd = workbook.createSheet("Ordonnancements");
//        Row headerRowOrd = sheetOrd.createRow(0);
//        for (int i = 0; i < allHeadersOrd.size(); i++) {
//            headerRowOrd.createCell(i).setCellValue(allHeadersOrd.get(i));
//        }
//
//        // Remplir les données pour chaque budget - Ordonnancements
//        int rowIndexOrd = 1;
//        for (BudgetPassationDTO budget : budgetPassations) {
//            Row budgetRowOrd = sheetOrd.createRow(rowIndexOrd++);
//            int colIndexOrd = 0;
//
//            // Fill budget fields
//            for (String fieldName : budgetFields) {
//                try {
//                    Field field = BudgetPassationDTO.class.getDeclaredField(fieldName);
//                    field.setAccessible(true);
//                    Object value = field.get(budget);
//                    budgetRowOrd.createCell(colIndexOrd++).setCellValue(value != null ? value.toString() : "");
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
//                }
//            }
//
//            // Add credits values from getCredits for current and previous year
//            double creditsCurrent = budget.getAnneeCredits().equals(year) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
//            double creditsPrevious = budget.getAnneeCredits().equals(String.valueOf(previousYear)) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
//            budgetRowOrd.createCell(colIndexOrd++).setCellValue(creditsCurrent > 0 ? creditsCurrent : 0.0);
//            budgetRowOrd.createCell(colIndexOrd++).setCellValue(creditsPrevious > 0 ? creditsPrevious : 0.0);
//
//            // Calculer les totaux mensuels pour ce budget spécifique - Ordonnancements
//            Map<String, String> searchParamsOrd = Map.of("budgetPassationId", String.valueOf(budget.getId()));
//            List<OrdonnancementDTO> budgetOrdonnancements = ordonnancementService.readAllOrdonnancements(searchParamsOrd, pageable).getContent();
//            Map<String, Double> monthlyTotalsOrd = budgetOrdonnancements.stream()
//                    .filter(o -> o.getMois() != null && o.getMontantOrdonne() != null)
//                    .collect(Collectors.groupingBy(OrdonnancementDTO::getMois,
//                            Collectors.summingDouble(OrdonnancementDTO::getMontantOrdonne)));
//
//            // Add monthly ordonnancement totals
//            for (String monthHeader : monthHeadersOrd) {
//                String month = monthHeader.replace("Ordonnancé ", "");
//                Double total = monthlyTotalsOrd.getOrDefault(month, 0.0);
//                if (total > 0) {
//                    budgetRowOrd.createCell(colIndexOrd++).setCellValue(total);
//                } else {
//                    budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
//                }
//            }
//
//            // Calculate total and reste for this budget
//            double totalOrd = monthlyTotalsOrd.values().stream().mapToDouble(Double::doubleValue).sum();
//            double resteOrd = (budget.getCredits() != null ? budget.getCredits() : 0.0) - totalOrd;
//
//            if (totalOrd > 0) {
//                budgetRowOrd.createCell(colIndexOrd++).setCellValue(totalOrd);
//            } else {
//                budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
//            }
//            if (resteOrd > 0) {
//                budgetRowOrd.createCell(colIndexOrd++).setCellValue(resteOrd);
//            } else {
//                budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
//            }
//        }
//
//        // Auto-size columns for ordonnancements
//        for (int i = 0; i < allHeadersOrd.size(); i++) {
//            sheetOrd.autoSizeColumn(i);
//        }
//
//        // Write to byte array
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            workbook.write(out);
//            workbook.close();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate Excel file", e);
//        }
//
//        return new ByteArrayResource(out.toByteArray());
//    }
//
@Override
public ByteArrayResource exportAllBudgetPassationsForYearWithEngagementsAndOrdonnancements(
        String year, EngagementService engagementService, OrdonnancementService ordonnancementService) {
    // Récupérer tous les budgets pour l'année donnée et l'année précédente
    int currentYear = Integer.parseInt(year);
    int previousYear = currentYear - 1;
    Map<String, String> searchParamsCurrent = Map.of("anneeCredits", year);
    Map<String, String> searchParamsPrevious = Map.of("anneeCredits", String.valueOf(previousYear));
    Pageable pageable = Pageable.unpaged();

    List<BudgetPassationDTO> budgetPassationsCurrent = readAllBudgetPassations(searchParamsCurrent, pageable).getContent();
    List<BudgetPassationDTO> budgetPassationsPrevious = readAllBudgetPassations(searchParamsPrevious, pageable).getContent();

    if (budgetPassationsCurrent.isEmpty() && budgetPassationsPrevious.isEmpty()) {
        throw new RuntimeException("Aucun budget trouvé pour l'année " + year + " ou " + previousYear);
    }

    // Combiner les budgets (éviter les doublons si un budgetPassationId existe dans les deux années)
    Map<Long, BudgetPassationDTO> budgetMap = new HashMap<>();
    budgetPassationsCurrent.forEach(b -> budgetMap.put(b.getId(), b));
    budgetPassationsPrevious.forEach(b -> budgetMap.put(b.getId(), b)); // Remplace si doublon, garde la dernière année

    List<BudgetPassationDTO> budgetPassations = new ArrayList<>(budgetMap.values());

    // Define budget fields
    List<String> budgetFields = Arrays.asList("program", "libelleSection", "categorie", "chapitre", "libelleChapitre");

    // Month order map for sorting (utiliser les noms complets avec majuscule initiale)
    Map<String, Integer> monthMap = new HashMap<>();
    monthMap.put("janvier", 1);
    monthMap.put("février", 2);
    monthMap.put("mars", 3);
    monthMap.put("avril", 4);
    monthMap.put("mai", 5);
    monthMap.put("juin", 6);
    monthMap.put("juillet", 7);
    monthMap.put("août", 8);
    monthMap.put("septembre", 9);
    monthMap.put("octobre", 10);
    monthMap.put("novembre", 11);
    monthMap.put("décembre", 12);

    // Base headers from budget fields
    List<String> baseHeaders = budgetFields.stream().map(this::formatHeader).collect(Collectors.toList());

    // Add concatenated credits headers (current year and previous year)
    baseHeaders.add("Crédits Ouvert " + year);
    baseHeaders.add("Crédits Ouvert " + previousYear);

    // Collecter tous les engagements et ordonnancements pour tous les budgets
    List<EngagementDTO> allEngagements = new ArrayList<>();
    List<OrdonnancementDTO> allOrdonnancements = new ArrayList<>();

    for (BudgetPassationDTO budget : budgetPassations) {
        Map<String, String> searchParamsEng = Map.of("budgetPassationId", String.valueOf(budget.getId()));
        List<EngagementDTO> engagements = engagementService.readAllEngagements(searchParamsEng, pageable).getContent();
        allEngagements.addAll(engagements);

        Map<String, String> searchParamsOrd = Map.of("budgetPassationId", String.valueOf(budget.getId()));
        List<OrdonnancementDTO> ordonnancements = ordonnancementService.readAllOrdonnancements(searchParamsOrd, pageable).getContent();
        allOrdonnancements.addAll(ordonnancements);
    }

    // For engagements - collecter tous les mois uniques et trier dans l'ordre chronologique
    Set<String> uniqueMonthsEng = allEngagements.stream()
            .map(e -> e.getMois() != null ? e.getMois().toLowerCase() : null) // Normalisation en minuscules
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    System.out.println("Unique months Eng (raw normalized): " + uniqueMonthsEng); // Débogage

    List<String> monthHeadersEng = uniqueMonthsEng.stream()
            .map(month -> {
                String normalized = month.toLowerCase();
                return monthMap.containsKey(normalized) ? month.substring(0, 1).toUpperCase() + month.substring(1) : month;
            })
            .sorted((m1, m2) -> {
                Integer order1 = monthMap.getOrDefault(m1.toLowerCase(), 13);
                Integer order2 = monthMap.getOrDefault(m2.toLowerCase(), 13);
                return Integer.compare(order1, order2); // Tri ascendant
            })
            .map(month -> "Engagé " + month)
            .collect(Collectors.toList());

    // For ordonnancements - collecter tous les mois uniques et trier dans l'ordre chronologique
    Set<String> uniqueMonthsOrd = allOrdonnancements.stream()
            .map(o -> o.getMois() != null ? o.getMois().toLowerCase() : null) // Normalisation en minuscules
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    System.out.println("Unique months Ord (raw normalized): " + uniqueMonthsOrd); // Débogage

    List<String> monthHeadersOrd = uniqueMonthsOrd.stream()
            .map(month -> {
                String normalized = month.toLowerCase();
                return monthMap.containsKey(normalized) ? month.substring(0, 1).toUpperCase() + month.substring(1) : month;
            })
            .sorted((m1, m2) -> {
                Integer order1 = monthMap.getOrDefault(m1.toLowerCase(), 13);
                Integer order2 = monthMap.getOrDefault(m2.toLowerCase(), 13);
                return Integer.compare(order1, order2); // Tri ascendant
            })
            .map(month -> "Ordonnancé " + month)
            .collect(Collectors.toList());

    // All headers for engagements
    List<String> allHeadersEng = new ArrayList<>(baseHeaders);
    allHeadersEng.addAll(monthHeadersEng);
    allHeadersEng.add("Total");
    allHeadersEng.add("Reste à programmer");

    // All headers for ordonnancements
    List<String> allHeadersOrd = new ArrayList<>(baseHeaders);
    allHeadersOrd.addAll(monthHeadersOrd);
    allHeadersOrd.add("Total");
    allHeadersOrd.add("Reste à ordonnancer");

    // Create workbook
    Workbook workbook = new XSSFWorkbook();

    // Engagements sheet
    Sheet sheetEng = workbook.createSheet("Engagements");
    Row headerRowEng = sheetEng.createRow(0);
    for (int i = 0; i < allHeadersEng.size(); i++) {
        headerRowEng.createCell(i).setCellValue(allHeadersEng.get(i));
    }

    // Remplir les données pour chaque budget - Engagements
    int rowIndexEng = 1;
    for (BudgetPassationDTO budget : budgetPassations) {
        Row budgetRowEng = sheetEng.createRow(rowIndexEng++);
        int colIndexEng = 0;

        // Fill budget fields
        for (String fieldName : budgetFields) {
            try {
                Field field = BudgetPassationDTO.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(budget);
                budgetRowEng.createCell(colIndexEng++).setCellValue(value != null ? value.toString() : "");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                budgetRowEng.createCell(colIndexEng++).setCellValue("");
            }
        }

        // Add credits values from getCredits for current and previous year
        double creditsCurrent = budget.getAnneeCredits().equals(year) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
        double creditsPrevious = budget.getAnneeCredits().equals(String.valueOf(previousYear)) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
        budgetRowEng.createCell(colIndexEng++).setCellValue(creditsCurrent > 0 ? creditsCurrent : 0.0);
        budgetRowEng.createCell(colIndexEng++).setCellValue(creditsPrevious > 0 ? creditsPrevious : 0.0);

        // Calculer les totaux mensuels pour ce budget spécifique - Engagements
        Map<String, String> searchParamsEng = Map.of("budgetPassationId", String.valueOf(budget.getId()));
        List<EngagementDTO> budgetEngagements = engagementService.readAllEngagements(searchParamsEng, pageable).getContent();
        Map<String, Double> monthlyTotalsEng = budgetEngagements.stream()
                .filter(e -> e.getMois() != null && e.getMontantEngage() != null)
                .collect(Collectors.groupingBy(e -> e.getMois().toLowerCase(),
                        Collectors.summingDouble(EngagementDTO::getMontantEngage)));

        // Add monthly engagement totals
        for (String monthHeader : monthHeadersEng) {
            String month = monthHeader.replace("Engagé ", "").toLowerCase();
            Double total = monthlyTotalsEng.getOrDefault(month, 0.0);
            if (total > 0) {
                budgetRowEng.createCell(colIndexEng++).setCellValue(total);
            } else {
                budgetRowEng.createCell(colIndexEng++).setCellValue("");
            }
        }

        // Calculate total and reste for this budget using only current year credits
        double totalEng = monthlyTotalsEng.values().stream().mapToDouble(Double::doubleValue).sum();
        double creditsToUse = budget.getAnneeCredits().equals(year) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
        double resteEng = creditsToUse - totalEng;

        if (totalEng > 0) {
            budgetRowEng.createCell(colIndexEng++).setCellValue(totalEng);
        } else {
            budgetRowEng.createCell(colIndexEng++).setCellValue("");
        }
        if (resteEng > 0) {
            budgetRowEng.createCell(colIndexEng++).setCellValue(resteEng);
        } else {
            budgetRowEng.createCell(colIndexEng++).setCellValue("");
        }
    }

    // Auto-size columns for engagements
    for (int i = 0; i < allHeadersEng.size(); i++) {
        sheetEng.autoSizeColumn(i);
    }
    // Ordonnancements sheet
    Sheet sheetOrd = workbook.createSheet("Ordonnancements");
    Row headerRowOrd = sheetOrd.createRow(0);
    for (int i = 0; i < allHeadersOrd.size(); i++) {
        headerRowOrd.createCell(i).setCellValue(allHeadersOrd.get(i));
    }

    // Remplir les données pour chaque budget - Ordonnancements
    int rowIndexOrd = 1;
    for (BudgetPassationDTO budget : budgetPassations) {
        Row budgetRowOrd = sheetOrd.createRow(rowIndexOrd++);
        int colIndexOrd = 0;

        // Fill budget fields
        for (String fieldName : budgetFields) {
            try {
                Field field = BudgetPassationDTO.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(budget);
                budgetRowOrd.createCell(colIndexOrd++).setCellValue(value != null ? value.toString() : "");
            } catch (NoSuchFieldException | IllegalAccessException e) {
                budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
            }
        }

        // Add credits values from getCredits for current and previous year
        double creditsCurrent = budget.getAnneeCredits().equals(year) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
        double creditsPrevious = budget.getAnneeCredits().equals(String.valueOf(previousYear)) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
        budgetRowOrd.createCell(colIndexOrd++).setCellValue(creditsCurrent > 0 ? creditsCurrent : 0.0);
        budgetRowOrd.createCell(colIndexOrd++).setCellValue(creditsPrevious > 0 ? creditsPrevious : 0.0);

        // Calculer les totaux mensuels pour ce budget spécifique - Ordonnancements
        Map<String, String> searchParamsOrd = Map.of("budgetPassationId", String.valueOf(budget.getId()));
        List<OrdonnancementDTO> budgetOrdonnancements = ordonnancementService.readAllOrdonnancements(searchParamsOrd, pageable).getContent();
        Map<String, Double> monthlyTotalsOrd = budgetOrdonnancements.stream()
                .filter(o -> o.getMois() != null && o.getMontantOrdonne() != null)
                .collect(Collectors.groupingBy(o -> o.getMois().toLowerCase(),
                        Collectors.summingDouble(OrdonnancementDTO::getMontantOrdonne)));

        // Add monthly ordonnancement totals
        for (String monthHeader : monthHeadersOrd) {
            String month = monthHeader.replace("Ordonnancé ", "").toLowerCase();
            Double total = monthlyTotalsOrd.getOrDefault(month, 0.0);
            if (total > 0) {
                budgetRowOrd.createCell(colIndexOrd++).setCellValue(total);
            } else {
                budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
            }
        }

        // Calculate total and reste for this budget using only current year credits
        double totalOrd = monthlyTotalsOrd.values().stream().mapToDouble(Double::doubleValue).sum();
        double creditsToUse = budget.getAnneeCredits().equals(year) ? (budget.getCredits() != null ? budget.getCredits() : 0.0) : 0.0;
        double resteOrd = creditsToUse - totalOrd;

        if (totalOrd > 0) {
            budgetRowOrd.createCell(colIndexOrd++).setCellValue(totalOrd);
        } else {
            budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
        }
        if (resteOrd > 0) {
            budgetRowOrd.createCell(colIndexOrd++).setCellValue(resteOrd);
        } else {
            budgetRowOrd.createCell(colIndexOrd++).setCellValue("");
        }
    }

    // Auto-size columns for ordonnancements
    for (int i = 0; i < allHeadersOrd.size(); i++) {
        sheetOrd.autoSizeColumn(i);
    }

    // Write to byte array
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        workbook.write(out);
        workbook.close();
    } catch (Exception e) {
        throw new RuntimeException("Failed to generate Excel file", e);
    }

    return new ByteArrayResource(out.toByteArray());
}


    private String formatHeader(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1).replaceAll("([A-Z])", " $1").trim();
    }
}