package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.*;
import com.webgram.dgpsn.mappers.ManagementUnitMapper;
import com.webgram.dgpsn.models.responses.*;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final ManagementUnitRepository managementUnitRepository;

    private final StructureProjectRepository structureProjectRepository;
    private final StatusRepository statusRepository;
    //    private final FlagRepository flagRepository;
    private final FundingRepository fundingRepository;

    private final FundingConfigRepository fundingConfigRepository;
    private final IssueLogRepository issueLogRepository;
    private final GeographicalLocationRepository geographicalLocationRepository;
    private final BudgetRepository budgetRepository;

    private final BudgetActivityRepository budgetActivityRepository;

    private final ExpenseActivityRepository expenseActivityRepository;

    private final SubSectorRepository subSectorRepository;

    private final LabelRepository labelRepository;
    private final FundingSourceRepository fundingSourceRepository;

    private final ManagementUnitMapper managementUnitMapper;

    private final StructureRepository structureRepository;
    private final QualiteAirRepository qualiteAirRepository;
    private final InstructionRepository instructionRepository;
    private final AgrementRepository agrementRepository;
    private final PollutionManagerRepository pollutionManagerRepository;
    private final MilieuxPollutionRepository milieuxPollutionRepository;
    private final RejetPollutionRepository rejetPollutionRepository;
    private final EtablissementClasseRepository etablissementClasseRepository;
    private final InspectionICPERepository inspectionICPERepository;
    private final CongeRepository congeRepository;
    private final CessationFonctionRepository cessationFonctionRepository;
    //budget
    private final BudgetDgpsnRepository budgetDgpsnRepository;
    private final LigneBudgetaireRepository ligneBudgetaireRepository;
    private final RealisationRepository realisationRepository;


    @Override
    public Map<String, Object> getBudgetSummaryKpis() {
        var budgets = budgetDgpsnRepository.findAll();

        // Budget Total: Accès direct aux méthodes de BudgetDgpsnEntity
        double totalBudget = budgets.stream()
                .mapToDouble(BudgetDgpsnEntity::getMontant)
                .sum();

        // Budget Consommé: Somme des réalisations (RealisationEntity)
        var allRealisations = realisationRepository.findAll();
        double totalConsumed = allRealisations.stream()
                .mapToDouble(RealisationEntity::getMontant)
                .sum();

        double totalRemaining = totalBudget - totalConsumed;
        double tauxExecution = totalBudget > 0 ? (totalConsumed / totalBudget) * 100 : 0.0;

        return Map.of(
                "totalBudget", totalBudget,
                "totalConsumed", totalConsumed,
                "totalRemaining", totalRemaining,
                "tauxExecution", Math.min(100.0, tauxExecution)
        );
    }

    @Override
    public List<StatisticalFundingDTO> getBudgetDistributionByYear() {
        var budgets = budgetDgpsnRepository.findAll();

        // Groupement par année (BudgetDgpsnEntity::getAnnee )
        Map<Integer, Double> budgetByYear = budgets.stream()
                .collect(Collectors.groupingBy(
                        BudgetDgpsnEntity::getAnnee,
                        Collectors.summingDouble(BudgetDgpsnEntity::getMontant)
                ));

        return budgetByYear.entrySet().stream()
                .map(e -> StatisticalFundingDTO.builder()
                        .label(e.getKey().toString())
                        .value(e.getValue())
                        .build())
                .sorted(Comparator.comparing(StatisticalFundingDTO::getLabel))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticalFundingDTO> getTop5BudgetsByAmount() {
        return budgetDgpsnRepository.findAll().stream()
                .sorted(Comparator.comparing(BudgetDgpsnEntity::getMontant, Comparator.reverseOrder()))
                .limit(5)
                .map(budget -> StatisticalFundingDTO.builder()
                        .label(budget.getCode() + " - " + budget.getLibelle())
                        .value(budget.getMontant())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticalFundingDTO> getTop5BudgetExecutionRates() {
        var budgets = budgetDgpsnRepository.findAll();
        var allLignes = ligneBudgetaireRepository.findAll();
        var allRealisations = realisationRepository.findAll();

        Map<Long, Double> totalConsumedAmountByBudget = new HashMap<>();
        Map<Long, Double> totalBudgetAmount = new HashMap<>();
        Map<Long, String> budgetCodeById = new HashMap<>();

        budgets.forEach(budget -> {
            totalBudgetAmount.put(budget.getId(), budget.getMontant());
            budgetCodeById.put(budget.getId(), budget.getCode());
            totalConsumedAmountByBudget.put(budget.getId(), 0.0);
        });

        // Mapping LigneBudgetaire (qui a le budget [5]) aux Realisations (qui ont la ligne [6])
        Map<Long, Long> budgetIdByLigneId = allLignes.stream()
                .collect(Collectors.toMap(LigneBudgetaireEntity::getId, l -> l.getBudget().getId()));

        allRealisations.forEach(realisation -> {
            Long ligneId = realisation.getLigneBudgetaire().getId();
            Long budgetId = budgetIdByLigneId.get(ligneId);
            if (budgetId != null) {
                totalConsumedAmountByBudget.merge(budgetId, realisation.getMontant(), Double::sum);
            }
        });

        List<StatisticalFundingDTO> executionRates = budgets.stream()
                .map(budget -> {
                    double total = totalBudgetAmount.getOrDefault(budget.getId(), 0.0);
                    double consumed = totalConsumedAmountByBudget.getOrDefault(budget.getId(), 0.0);
                    double taux = total > 0 ? (consumed / total) * 100 : 0.0;

                    return StatisticalFundingDTO.builder()
                            .label(budgetCodeById.get(budget.getId()))
                            .value(Math.min(100.0, taux))
                            .build();
                })
                .sorted(Comparator.comparing(StatisticalFundingDTO::getValue).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return executionRates;
    }

    @Override
    public List<Map<String, Object>> getMonthlyBudgetConsumption() {
        // allRealisations est une liste de RealisationEntity [1]
        var allRealisations = realisationRepository.findAll();

        // Groupement par Année et Mois
        Map<String, Double> monthlyConsumption = allRealisations.stream()
                .collect(Collectors.groupingBy(
                        r -> {
                            // RealisationEntity contient un champ date de type LocalDate [2]
                            LocalDate date = r.getDate();
                            // Format AAAA-MM
                            return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                        },
                        Collectors.summingDouble(RealisationEntity::getMontant) // RealisationEntity::getMontant [2]
                ));

        return monthlyConsumption.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                // CORRECTION : Utilisation explicite d'une HashMap pour garantir Map<String, Object>
                .map(e -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("monthYear", e.getKey()); // String
                    result.put("amount", e.getValue());   // Double
                    return result; // Retourne Map<String, Object>
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<StatisticalDTO> readStatProjectByStatus() {

//        var statStatusDTOS = managementUnitRepository.findStatByStatus();
//        statusRepository.findByStatusType(StatusType.PROJECT)
//                .forEach(status -> {
//                    if(statStatusDTOS.stream().noneMatch(statStatusDTO -> status.getLibelle().equals(statStatusDTO.getLabel()))){
//                        statStatusDTOS.add(StatisticalDTO.builder().label(status.getLibelle()).value(0L).build());
//                    }
//                });
//        return statStatusDTOS;
        return null;
    }

    @Override
    public List<StatisticalDTO> readStatProjectByFlag() {
//        var statFlagDTOS = managementUnitRepository.findStatByFlag();
//        flagRepository.findAll()
//                .forEach(flag -> {
//                    if(statFlagDTOS.stream().noneMatch(statStatusDTO -> flag.getLibelle().equals(statStatusDTO.getLabel()))){
//                        statFlagDTOS.add(StatisticalDTO.builder().label(flag.getLibelle()).value(0L).build());
//                    }
//                });
//        return statFlagDTOS;
        return null;
    }

    @Override
    public List<Long> readStatFunding() {

//        var totalBudget = managementUnitRepository.getTotalBudget();
//        var totalFunding = fundingRepository.getTotalFinancement();
//        var gap = totalBudget-totalFunding;
//        if(gap < 0) {
//            gap = 0;
//        }
//        var statTotalFunding = Arrays.asList(totalFunding, gap);
//
//     //   log.info("statTotalFunding {} ", statTotalFunding);
//
//        return statTotalFunding;
        return null;
    }

    @Override
    public List<StatisticalDTO> readStatProjectBySector() {
        var statSectorDTOS = managementUnitRepository.findStatBySectors();
        subSectorRepository.findAll()
                .forEach(sector -> {
                    if (statSectorDTOS.stream().noneMatch(statSectorDTO -> sector.getLibelle().equals(statSectorDTO.getLabel()))) {
                        statSectorDTOS.add(StatisticalDTO.builder().label(sector.getLibelle()).value(0L).build());
                    }
                });
        log.info("stat {}", statSectorDTOS.size());
        return statSectorDTOS;
//        return null;
    }

    @Override
    public List<StatisticalDTO> readCountProjectsByProgramme() {
        var programs = managementUnitRepository.countProjectsByProgramme();
        var result = new ArrayList<StatisticalDTO>();
        managementUnitRepository.findAll().forEach(project -> {
            if (project.getParent() != null) {
                boolean programExists = false;
                for (StatisticalDTO dto : result) {
                    if (dto.getLabel().equals(project.getParent().getName())) {
                        dto.setValue(dto.getValue() + 1);
                        programExists = true;
                        break;
                    }
                }
                if (!programExists) {
                    result.add(StatisticalDTO.builder().label(project.getParent().getName()).value(1L).build());
                }
            }
        });
        log.info("stat {}", result.size());
        return result;
    }

    @Override
    public List<StatisticalFundingDTO> readAverageFundingBySector() {
        var statAverageFundingSectorDTOS = budgetRepository.findAverageFundingBySector();
        subSectorRepository.findAll()
                .forEach(sector -> {
                    if (statAverageFundingSectorDTOS.stream().noneMatch(statSectorDTO -> sector.getLibelle().equals(statSectorDTO.getLabel()))) {
                        statAverageFundingSectorDTOS.add(StatisticalFundingDTO.builder().label(sector.getLibelle()).value(0.0).build());
                    }
                });
        return statAverageFundingSectorDTOS;

    }

    @Override
    public List<StatisticalFundingDTO> readTotalFundingBySector() {
//        var statAverageFundingSectorDTOS = fundingRepository.findTotalFundingBySector();
//        subSectorRepository.findAll()
//                .forEach(sector -> {
//                    if(statAverageFundingSectorDTOS.stream().noneMatch(statSectorDTO -> sector.getLibelle().equals(statSectorDTO.getLabel()))){
//                        statAverageFundingSectorDTOS.add(StatisticalFundingDTO.builder().label(sector.getLibelle()).value(0.0).build());
//                    }
//                });
//        return statAverageFundingSectorDTOS;
        return null;
    }

    @Override
    public List<StatisticalDTO> readStatIssueLogByStatus() {
        var statStatusDTOS = issueLogRepository.findStatIssueLogByStatus();
        statusRepository.findByStatusType(StatusType.ISSUELOG)
                .forEach(status -> {
                    if (statStatusDTOS.stream().noneMatch(statStatusDTO -> status.getLibelle().equals(statStatusDTO.getLabel()))) {
                        statStatusDTOS.add(StatisticalDTO.builder().label(status.getLibelle()).value(0L).build());
                    }
                });
        return statStatusDTOS;
//        return null;
    }

    @Override
    public List<Map<String, Object>> readStatIssueLogByYear(Integer year) {
        List<?> statStatusDTOS;

        if (year == null) {
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            int currentYear = calendar.get(Calendar.YEAR);

            // Initialize result list
            List<Map<String, Object>> result = new ArrayList<>();

            // Fetch data for each of the last 5 years
            for (int i = 0; i < 5; i++) {
                int yearToQuery = currentYear - i;
                statStatusDTOS = issueLogRepository.findStatIssueLogByYear(yearToQuery);
                Map<String, Object> yearData = new HashMap<>();
                yearData.put("year", yearToQuery);
                yearData.put("data", statStatusDTOS);
                result.add(yearData);
            }

            log.info("Statistical data for last 5 years: {}", result);
            return result;
        } else {
            statStatusDTOS = issueLogRepository.findStatIssueLogByYear(year);

            // Transforming to desired JSON structure
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> yearData = new HashMap<>();
            yearData.put("year", year);
            yearData.put("data", statStatusDTOS);
            result.add(yearData);

            log.info("Statistical data for year {}: {}", year, result);
            return result;
        }
    }

    @Override
    public List<StatisticalDTO> getIssuesCountByNature() {
        return issueLogRepository.countIssuesByNature();
    }

    @Override
    public StatResolveDTO readStatResolved() {

        var all = issueLogRepository.findAll().size();
        var resolved = issueLogRepository.countResolvedIssueLog();
        var statResolved = StatResolveDTO.builder().nbrResolved(resolved).nbrIssueLog(Long.valueOf(all)).build();
        return statResolved;
    }

    @Override
    public List<StatisticalDTO> readStatProjectByPartner() {
        var statNbrProjectByPartnerDTOS = fundingRepository.findStatByPartner();

        return statNbrProjectByPartnerDTOS;
    }

    @Override
    public List<StatisticalDTO> readStatNumberProjectByStructure() {
        StructureProjectType structureProjectType = StructureProjectType.EXECUTION;
        TypeProjet typeProjet = TypeProjet.PROJECT;
        return structureProjectRepository.countDistinctProjectsByStructure(structureProjectType, typeProjet);

    }

    @Override
    public List<StatisticalFundingDTO> readTotalFundingtByPartner() {
        var statTotalFundingByPartenerDTOS = fundingSourceRepository.findTotalFundingByPartner();
        return statTotalFundingByPartenerDTOS;
    }

    @Override
    public List<StatisticalDTO> readNumberProjectByTranche() {
        var statistic = managementUnitRepository.countProjetByTranche();

        var result = List.of(
                StatisticalDTO.builder()
                        .label("Jeune")
                        .value(statistic.getJeune())
                        .build(),
                StatisticalDTO.builder()
                        .label("Mature")
                        .value(statistic.getMature())
                        .build(),
                StatisticalDTO.builder()
                        .label("Veillissant")
                        .value(statistic.getVeillissant())
                        .build()
        );

        log.info("result: {}", result);

        return result;
    }

    @Override
    public List<StatisticalFundingDTO> readAverageAgeByPertner() {
        return fundingSourceRepository.avgAgeProjectByPartner();
    }

    public List<StatisticalProjectDTO> getStatisticalData() {
        StructureProjectType structureProjectType = StructureProjectType.PARTNER;
        TypeProjet typeProjet = TypeProjet.PROJECT;
        return structureProjectRepository.countDistinctProjectsByStructureProjectTypeAndTypeProjet(structureProjectType, typeProjet);
    }


    public List<StatisticalDTO> readStatProjectByZonesExecution() {
        var statLabelDTOS = managementUnitRepository.findStatByExecutionZoneAndReferentielType(TypeProjet.PROJECT, ReferentielType.ZONE_EXECUTION);
        labelRepository.findAll().forEach(executionZone -> {
            if (executionZone.getReferentielType() == ReferentielType.ZONE_EXECUTION &&
                    statLabelDTOS.stream().noneMatch(statZoneDTO -> executionZone.getLibelle().equals(statZoneDTO.getLabel()))) {
                statLabelDTOS.add(StatisticalDTO.builder().label(executionZone.getLibelle()).value(0L).build());
            }
        });
        log.info("stat {}", statLabelDTOS.size());
        return statLabelDTOS;
    }

    @Override
    public List<StatisticalFundingDTO> readTotalNeedByProjet() {
        var statBesoinDTOS = fundingConfigRepository.totalBesoinsByProjects();
        List<ManagementUnitEntity> allProjects = managementUnitRepository.findAll();
        List<StatisticalFundingDTO> filteredDTOS = allProjects.stream()
                .filter(project -> statBesoinDTOS.stream()
                        .anyMatch(statDTO -> project.getName().equals(statDTO.getLabel())))
                .map(project -> {
                    Optional<StatisticalFundingDTO> matchingDTO = statBesoinDTOS.stream()
                            .filter(statDTO -> project.getName().equals(statDTO.getLabel()))
                            .findFirst();
                    return matchingDTO.orElse(StatisticalFundingDTO.builder()
                            .label(project.getName())
                            .value(0.0)
                            .build());
                })
                .collect(Collectors.toList());
        log.info("stat {}", filteredDTOS.size());
        return filteredDTOS;
    }

    @Override
    public List<StatisticalFundingDTO> readTotalMobilisationByProjet() {
        var statMobilisationDTOS = fundingConfigRepository.totalMobilisationsByProjects();
        List<ManagementUnitEntity> allProjects = managementUnitRepository.findAll();
        List<StatisticalFundingDTO> filteredDTOS = allProjects.stream()
                .filter(project -> statMobilisationDTOS.stream()
                        .anyMatch(statDTO -> project.getName().equals(statDTO.getLabel())))
                .map(project -> {
                    Optional<StatisticalFundingDTO> matchingDTO = statMobilisationDTOS.stream()
                            .filter(statDTO -> project.getName().equals(statDTO.getLabel()))
                            .findFirst();
                    return matchingDTO.orElse(StatisticalFundingDTO.builder()
                            .label(project.getName())
                            .value(0.0)
                            .build());
                })
                .collect(Collectors.toList());
        log.info("stat {}", filteredDTOS.size());
        return filteredDTOS;
    }

    @Override
    public List<StatisticalFundingDTO> readTotalExecutiontionByProjet() {
        var statExecutiontionDTOS = fundingConfigRepository.totalExecutionByProjects();
        List<ManagementUnitEntity> allProjects = managementUnitRepository.findAll();
        List<StatisticalFundingDTO> filteredDTOS = allProjects.stream()
                .filter(project -> statExecutiontionDTOS.stream()
                        .anyMatch(statDTO -> project.getName().equals(statDTO.getLabel())))
                .map(project -> {
                    Optional<StatisticalFundingDTO> matchingDTO = statExecutiontionDTOS.stream()
                            .filter(statDTO -> project.getName().equals(statDTO.getLabel()))
                            .findFirst();
                    return matchingDTO.orElse(StatisticalFundingDTO.builder()
                            .label(project.getName())
                            .value(0.0)
                            .build());
                })
                .collect(Collectors.toList());
        log.info("stat {}", filteredDTOS.size());
        return filteredDTOS;
    }

    @Override
    public List<StatisticalBudgetActivityDTO> readTotalBudgetByActivity() {
        return budgetActivityRepository.findTotalBudgetByActivity(TypeProjet.ACTIVITY);
    }

    @Override
    public List<StatisticalBudgetActivityDTO> getActivitiesAndBudgetsByProject(Long projectId, TypeProjet typeProjet) {
        return budgetActivityRepository.findActivitiesAndBudgetsByProject(projectId, typeProjet);
    }

    @Override
    public List<StatisticalBudgetActivityDTO> readTotalExpanseByActivity() {
        return expenseActivityRepository.findTotalExpanseByActivity(TypeProjet.ACTIVITY);
    }

    @Override
    public List<StatisticalBudgetActivityDTO> getActivitiesAndExpansesByProject(Long projectId, TypeProjet typeProjet) {
        return expenseActivityRepository.findActivitiesAndExpensesByProject(projectId, typeProjet);
    }

    @Override
    public List<Object> buildDashboardResumeFinancement() {
        var listLast4Years = new HashMap<String, Object>();
        listLast4Years.put("years", getListLast4Years());
        var data = new ArrayList<>();
        data.add(listLast4Years);
        managementUnitRepository.findByType(TypeProjet.PROGRAMME).forEach(managementUnit -> {
            var program = new HashMap<String, Object>();
            var projects = new ArrayList<>();
            managementUnitRepository.findByParent(managementUnit).forEach(unit -> {
                var projectDetails = new HashMap<String, Object>();
                projectDetails.put("id", unit.getId());
                projectDetails.put("name", unit.getName());

                var besoinFinancier = new HashMap<String, Object>();
                getListLast4Years().forEach(year -> {
                    besoinFinancier.put(year.toString(), fundingConfigRepository.totalFundingConfigByProjectAndYear(FundingTypeConfig.FINANCING_NEED, unit.getId(), year.toString()));
                });

                var montantMobilise = new HashMap<String, Object>();
                getListLast4Years().forEach(year -> {
                    montantMobilise.put(year.toString(), fundingConfigRepository.totalFundingConfigByProjectAndYear(FundingTypeConfig.MOBILISATION, unit.getId(), year.toString()));
                });

                var montantExecute = new HashMap<String, Object>();
                getListLast4Years().forEach(year -> {
                    montantExecute.put(year.toString(), fundingConfigRepository.totalFundingConfigByProjectAndYear(FundingTypeConfig.EXECUTION, unit.getId(), year.toString()));
                });

                var tauxExecutionFinanciere = 0.00;
                var totalExecute = fundingConfigRepository.totalFundingConfigByProject(FundingTypeConfig.EXECUTION, unit.getId());
                var totalBudget = budgetRepository.getTotalBudgetByProject(unit.getId());
                if (Objects.nonNull(totalExecute) && Objects.nonNull(totalBudget)) {
                    tauxExecutionFinanciere = (totalExecute * 100) / totalBudget;
                }

                var project = new HashMap<String, Object>();
                project.put("project", projectDetails);
                project.put("montantGlobalInitial", budgetRepository.getTotalBudgetByProject(unit.getId()));
                project.put("typeFinancement", budgetRepository.findAllDistinctBySouceBudget(unit.getId()));
                project.put("sourceFinancement", fundingSourceRepository.findByManagementUnitId(unit.getId())
                        .stream().map(FundingSourceEntity::getStructure).map(StructureEntity::getNom).collect(Collectors.toList()));
                project.put("besoinFinancier", besoinFinancier);
                project.put("montantMobilise", montantMobilise);
                project.put("montantExecute", montantExecute);
                project.put("montantTotalMobilise", fundingConfigRepository.totalFundingConfigByProject(FundingTypeConfig.MOBILISATION, unit.getId()));
                project.put("tauxExecutionFinanciere", tauxExecutionFinanciere);
                project.put("montantTotalExecute", totalExecute);

                projects.add(project);
            });
            program.put("program", managementUnit.getName());
            program.put("projects", projects);
            data.add(program);
        });

        return data;
    }

    @Override
    public List<RisqueFinancierDto> getFinancialRisksByProject() {
        return issueLogRepository.getFinancialRiskByProject(TypeProjet.PROJECT);
    }

    @Override
    public List<GeographicDTO> getProjectsByGeographicalLocation(TypeProjet type) {
        List<Object[]> results = geographicalLocationRepository.findProjectsByGeographicalLocation(type);
        Map<String, List<String>> groupedResults = results.stream()
                .collect(Collectors.groupingBy(
                        result -> (String) result[0],
                        Collectors.mapping(result -> (String) result[1], Collectors.toList())
                ));
        List<GeographicDTO> filteredResults = groupedResults.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> new GeographicDTO(entry.getKey(), String.join("\n", entry.getValue())))
                .collect(Collectors.toList());
        return filteredResults;
    }

    @Override
    public List<IssueLogEntity> getOpenIssues(Long projetId, Integer annee, String trimestre) {
        Date[] dates = getStartAndEndDateForTrimestre(annee, trimestre);
        Date startDate = dates[0];
        Date endDate = dates[1];
        return issueLogRepository.findOpenIssues(projetId, annee, startDate, endDate);
    }

    @Override
    public List<IssueLogEntity> getClosedIssues(Long projetId, Integer annee, String trimestre) {
        Date[] dates = getStartAndEndDateForTrimestre(annee, trimestre);
        Date startDate = dates[0];
        Date endDate = dates[1];
        return issueLogRepository.findClosedIssues(projetId, annee, startDate, endDate);
    }

    @Override
    public List<FundingConfigEntity> getFundingConfigs(Long projetId, String annee, String trimestre) {
        Date[] dates = getStartAndEndDateForTrimestre(Integer.parseInt(annee), trimestre);
        Date startDate = dates[0];
        Date endDate = dates[1];
        return fundingConfigRepository.findFundingConfigs(projetId, annee, startDate, endDate);
    }

    private Date[] getStartAndEndDateForTrimestre(Integer annee, String trimestre) {
        Calendar cal = Calendar.getInstance();
        Date startDate = null;
        Date endDate = null;

        switch (trimestre != null ? trimestre.toUpperCase() : "") {
            case "T1":
                cal.set(annee, Calendar.JANUARY, 1);
                startDate = cal.getTime();
                cal.set(annee, Calendar.MARCH, 31);
                endDate = cal.getTime();
                break;
            case "T2":
                cal.set(annee, Calendar.APRIL, 1);
                startDate = cal.getTime();
                cal.set(annee, Calendar.JUNE, 30);
                endDate = cal.getTime();
                break;
            case "T3":
                cal.set(annee, Calendar.JULY, 1);
                startDate = cal.getTime();
                cal.set(annee, Calendar.SEPTEMBER, 30);
                endDate = cal.getTime();
                break;
            case "T4":
                cal.set(annee, Calendar.OCTOBER, 1);
                startDate = cal.getTime();
                cal.set(annee, Calendar.DECEMBER, 31);
                endDate = cal.getTime();
                break;
        }

        return new Date[]{startDate, endDate};
    }


    @Override
    public List<StatisticalDTO> readIssueLogCountBySupervisor() {
        List<Object[]> results = issueLogRepository.countIssueLogBySupervisor();
        return results.stream()
                .map(result -> new StatisticalDTO((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticalDTO> getProjectsByRegion() {
        return managementUnitRepository.countProjectsByRegion(CadreLogiqueType.REGION);
    }

    @Override
    public List<StatisticalBudgetDTO> getBudgetDistributionByProject() {
        // Retrieve all projects
        List<ManagementUnitEntity> allProjects = managementUnitRepository.findAll();

        // Map to store the total budget per region
        Map<String, Double> regionBudgetMap = new HashMap<>();

        for (ManagementUnitEntity project : allProjects) {
            // Get total budget for the current project
            Double totalBudget = budgetRepository.getTotalBudgetByProject(project.getId());

            if (totalBudget == null) {
                totalBudget = 0.0;
            }

            // Get regions associated with the current project
            List<GeographicalLocationEntity> regions = geographicalLocationRepository.findByProject(project.getId());

            for (GeographicalLocationEntity region : regions) {
                if (region.getCadreLogique().getTypeCadreLogique() == CadreLogiqueType.REGION) {
                    String regionName = region.getCadreLogique().getLibelle();
                    // Add the budget to the existing sum for the region
                    regionBudgetMap.merge(regionName, totalBudget, Double::sum);
                }
            }
        }

        return regionBudgetMap.entrySet().stream()
                .map(entry -> StatisticalBudgetDTO.builder()
                        .regionName(entry.getKey())
                        .budgetSum(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<Integer> getListLast4Years() {
        var listLast4years = new ArrayList<Integer>();
        for (int i = 3; i > 0; i--) {
            var year = LocalDate.now().getYear() - i;
            listLast4years.add(year);
        }
        listLast4years.add(LocalDate.now().getYear());
        return listLast4years;
    }

    /* Environmental Dashboard START*/
    @Override
    public EnvironmentalKpiResponse getEnvironmentalKpis() {
        Instant now = Instant.now();
        Date last30Days = Date.from(now.minus(30, ChronoUnit.DAYS));

        return EnvironmentalKpiResponse.builder()
                .averageIqa(qualiteAirRepository.findAverageIqaSince(last30Days))
                .stationCount(qualiteAirRepository.countActiveStationsSince(last30Days))
                .recentMeasurementsCount(qualiteAirRepository.countMeasurementsSince(last30Days))
                .build();
    }

    @Override
    public List<DataPoint<Date, Double>> getIqaTrend() {
        Date startDate = Date.from(Instant.now().minus(30, ChronoUnit.DAYS));
        return qualiteAirRepository.findIqaTrend(startDate);
    }

    @Override
    public List<DataPoint<String, Double>> getIqaByRegion() {
        return qualiteAirRepository.findAverageIqaByRegion();
    }

    @Override
    public List<DataPoint<String, Long>> getPollutantDistribution() {
        return qualiteAirRepository.countByMainPollutant();
    }
    /* Environmental Dashboard END*/

    /* Evaluation Environmental Dashboard START*/
    @Override
    public EvaluationKpiResponse getEvaluationKpis() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        Double delaiMoyen = instructionRepository.findDelaiMoyenTraitement(oneYearAgo);

        return EvaluationKpiResponse.builder()
                .dossiersEnCours(instructionRepository.countDossiersEnCours())
                .agrementsActifs(agrementRepository.countAgrementsActifs())
                .delaiMoyenTraitement(delaiMoyen != null ? delaiMoyen : 0.0)
                .build();
    }

    @Override
    public List<DataPoint<String, Long>> getFluxDossiersMensuel() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        return instructionRepository.countFluxDossiersMensuel(oneYearAgo);
    }

    @Override
    public List<DataPoint<String, Long>> getProjetsParRegion() {
        return instructionRepository.countProjetsParRegion();
    }

    @Override
    public List<DataPoint<String, Long>> getStatutInstructions() {
        return instructionRepository.countByNiveauInstruction();
    }
    /* Evaluation Environmental Dashboard END*/

    /* Pollution Dashboard START*/
    @Override
    public PollutionKpiResponse getPollutionKpis() {
        // 1. Calculer les dates avec l'API moderne
        int currentYear = LocalDate.now().getYear();
        Instant last30DaysInstant = Instant.now().minus(30, java.time.temporal.ChronoUnit.DAYS);

        // 2. Convertir en java.util.Date pour les appels au repository
        Date last30DaysDate = Date.from(last30DaysInstant);

        return PollutionKpiResponse.builder()
                .quantiteTotaleDechetsAnnee(pollutionManagerRepository.sumQuantiteByYear(currentYear))
                .alertesPollutionEau30j(milieuxPollutionRepository.countAlertesSince(last30DaysDate))
                .nonConformitesRejets30j(rejetPollutionRepository.countNonConformitesSince(last30DaysDate))
                .build();
    }

    @Override
    public List<DataPoint<String, Double>> getVolumeDechetsMensuel() {
        // 1. Calculer la date avec l'API moderne
        Instant oneYearAgoInstant = Instant.now().minus(365, java.time.temporal.ChronoUnit.DAYS);

        // 2. Convertir en java.util.Date pour l'appel au repository
        Date oneYearAgoDate = Date.from(oneYearAgoInstant);

        return pollutionManagerRepository.sumVolumeDechetsMensuel(oneYearAgoDate);
    }

    @Override
    public List<DataPoint<String, Long>> getRepartitionTypePollution() {
        // Pas de date nécessaire ici
        return pollutionManagerRepository.countByTypePollution();
    }

    @Override
    public List<DataPoint<String, Double>> getTendancePollutionEau() {
        // 1. Calculer la date avec l'API moderne
        Instant oneYearAgoInstant = Instant.now().minus(365, java.time.temporal.ChronoUnit.DAYS);

        // 2. Convertir en java.util.Date pour l'appel au repository
        Date oneYearAgoDate = Date.from(oneYearAgoInstant);

        return milieuxPollutionRepository.findTendanceTurbidite(oneYearAgoDate);
    }
    /* Pollution Dashboard END*/

    /* Icpe Dashboard START*/
    @Override
    public IcpeKpiResponse getIcpeKpis() {
        long totalInspections = inspectionICPERepository.count();
        long inspectionsConformes = inspectionICPERepository.countConformes();
        double tauxConformite = (totalInspections == 0) ? 0 : ((double) inspectionsConformes / totalInspections) * 100;

        return IcpeKpiResponse.builder()
                .totalIcpe(etablissementClasseRepository.count())
                .inspectionsAnneeEnCours(inspectionICPERepository.countByYear(LocalDate.now().getYear()))
                .tauxConformiteGlobal(tauxConformite)
                .risquesEleves(inspectionICPERepository.countRisquesEleves())
                .build();
    }

    @Override
    public List<DataPoint<String, Long>> getInspectionsMensuelles() {
        Instant now = Instant.now();
        Date oneYearAgo = Date.from(now.minus(365, ChronoUnit.DAYS));
        return inspectionICPERepository.countInspectionsMensuelles(oneYearAgo);
    }

    @Override
    public List<DataPoint<String, Long>> getIcpeParCategorie() {
        return etablissementClasseRepository.countByCategory();
    }

    @Override
    public List<DataPoint<String, Long>> getRepartitionNiveauConformite() {
        return inspectionICPERepository.countByComplianceLevel();
    }
    /* Icpe Dashboard END*/

    @Override
    public CongeDashboardSummaryDTO getDashboardSummary() {
        return congeRepository.getDashboardSummary();
    }

    @Override
    public CongeDashboardDTO getFullDashboard() {
        return CongeDashboardDTO.builder()
                // 1. Résumé (4 cartes)
                .summary(congeRepository.getDashboardSummary())
                // 2. Répartition types congé
                .typeCongeRepartition(congeRepository.countByTypeConge())
                // 3. Répartition statuts
                .statutRepartition(congeRepository.countByStatut())
                // 4. Évolution mensuelle (12 mois complétés)
//                .evolutionMensuelle(padEvolutionMensuelle(congeRepository.evolutionMensuelleThisYear()))
                // 5. Répartition durées
                .dureeRepartition(cessationFonctionRepository.countCessationDureeRange())
                // 6. Top 5 agents
                .topAgents(congeRepository.findTop5Agents().stream().limit(5).collect(Collectors.toList()))

                // 7. Taux approbation par type
                .tauxApprobationParType(
                        congeRepository.approvalRateByType().stream()
                                .collect(Collectors.toMap(
                                        arr -> ((TypeConge) arr[0]).name(),
                                        arr -> (Double) arr[1]
                                ))
                )
                .build();
    }
}
