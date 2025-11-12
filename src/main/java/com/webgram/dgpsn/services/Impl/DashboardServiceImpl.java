package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.*;
import com.webgram.dgpsn.entities.enums.*;
import com.webgram.dgpsn.mappers.ManagementUnitMapper;
import com.webgram.dgpsn.models.AgentCountByDirectionDTO;
import com.webgram.dgpsn.models.AgentDashboardDTO;
import com.webgram.dgpsn.models.AgentGroupingDTO;
import com.webgram.dgpsn.models.RetraiteProjectionDTO;
import com.webgram.dgpsn.models.responses.*;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.AgentService;
import com.webgram.dgpsn.services.CongeService;
import com.webgram.dgpsn.services.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Calendar;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final ManagementUnitRepository managementUnitRepository;

    private final StructureProjectRepository structureProjectRepository;
    private final StatusRepository statusRepository;
    private final FundingRepository fundingRepository;

    private final FundingConfigRepository fundingConfigRepository;
    private final IssueLogRepository issueLogRepository;
    private final GeographicalLocationRepository geographicalLocationRepository;
    private final BudgetRepository budgetRepository;
    private final AgentRepository agentRepository;

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
    private final AgentService agentService;
    private final CongeRepository congeRepository;
    private final CessationFonctionRepository cessationFonctionRepository;
    //budget
    private final BudgetDgpsnRepository budgetDgpsnRepository;
    private final LigneBudgetaireRepository ligneBudgetaireRepository;
    private final RealisationRepository realisationRepository;
    private final CongeService congeService;


    //service exterieur
    private final FormationExterieurRepository formationExterieurRepository;
    private final OrdreMissionRepository ordreMissionRepository;
    private final AtelierRepository atelierRepository;


    @Override
    public ServiceExterieurKpiResponse getServiceExterieurKpis() {
        // Compter les formations
        Long totalFormations = formationExterieurRepository.count();

        // Compter les missions
        Long totalMissions = ordreMissionRepository.count();

        // Compter les ateliers
        Long totalAteliers = atelierRepository.count();

        // Calculer le budget total
        Double budgetFormations = formationExterieurRepository.findAll().stream()
                .mapToDouble(f -> f.getCoutTotal() != null ? f.getCoutTotal() : 0.0)
                .sum();

        Double budgetAteliers = atelierRepository.findAll().stream()
                .mapToDouble(a -> a.getCoutOrganisation() != null ? a.getCoutOrganisation() : 0.0)
                .sum();

        Double totalBudget = budgetFormations + budgetAteliers;

        // Calculer le nombre total de participants (estimé pour les missions à 0)
        Long participantsAteliers = atelierRepository.findAll().stream()
                .mapToLong(a -> a.getNombreParticipantsMax() != null ? a.getNombreParticipantsMax() : 0L)
                .sum();

        Long totalParticipants = participantsAteliers;

        return ServiceExterieurKpiResponse.builder()
                .totalFormations(totalFormations)
                .totalMissions(totalMissions)
                .totalAteliers(totalAteliers)
                .totalBudget(totalBudget)
                .totalParticipants(totalParticipants)
                .build();
    }

    @Override
    public List<StatisticalDTO> getActivitiesByType() {
        Long countFormations = formationExterieurRepository.count();
        Long countMissions = ordreMissionRepository.count();
        Long countAteliers = atelierRepository.count();

        return List.of(
                StatisticalDTO.builder().label("Formations").value(countFormations).build(),
                StatisticalDTO.builder().label("Missions").value(countMissions).build(),
                StatisticalDTO.builder().label("Ateliers").value(countAteliers).build()
        );
    }

    @Override
    public List<StatisticalFundingDTO> getBudgetByActivityType() {
        Double budgetFormations = formationExterieurRepository.findAll().stream()
                .mapToDouble(f -> f.getCoutTotal() != null ? f.getCoutTotal() : 0.0)
                .sum();

        Double budgetAteliers = atelierRepository.findAll().stream()
                .mapToDouble(a -> a.getCoutOrganisation() != null ? a.getCoutOrganisation() : 0.0)
                .sum();

        return List.of(
                StatisticalFundingDTO.builder().label("Formations").value(budgetFormations).build(),
                StatisticalFundingDTO.builder().label("Missions").value(0.0).build(),
                StatisticalFundingDTO.builder().label("Ateliers").value(budgetAteliers).build()
        );
    }

    @Override
    public List<Map<String, Object>> getMonthlyActivitiesEvolution() {
        LocalDate now = LocalDate.now();
        Map<String, Long> formationsByMonth = new HashMap<>();
        Map<String, Long> missionsByMonth = new HashMap<>();
        Map<String, Long> ateliersByMonth = new HashMap<>();

        // Initialiser les 12 derniers mois
        for (int i = 11; i >= 0; i--) {
            LocalDate monthDate = now.minusMonths(i);
            String monthKey = monthDate.getYear() + "-" + String.format("%02d", monthDate.getMonthValue());
            formationsByMonth.put(monthKey, 0L);
            missionsByMonth.put(monthKey, 0L);
            ateliersByMonth.put(monthKey, 0L);
        }

        // Compter les formations par mois
        formationExterieurRepository.findAll().forEach(formation -> {
            if (formation.getDateDebut() != null) {
                LocalDate date = formation.getDateDebut().toLocalDate();
                if (date.isAfter(now.minusMonths(12))) {
                    String monthKey = date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                    formationsByMonth.merge(monthKey, 1L, Long::sum);
                }
            }
        });

        // Compter les missions par mois - CORRECTION POUR java.sql.Date
        ordreMissionRepository.findAll().forEach(mission -> {
            if (mission.getDateDepartOrdre() != null) {
                try {
                    LocalDate date = convertDateToLocalDate(mission.getDateDepartOrdre());
                    if (date != null && date.isAfter(now.minusMonths(12))) {
                        String monthKey = date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                        missionsByMonth.merge(monthKey, 1L, Long::sum);
                    }
                } catch (Exception e) {
                    log.warn("Impossible de convertir la date pour la mission ID {}: {}",
                            mission.getId(), e.getMessage());
                }
            }
        });

        // Compter les ateliers par mois
        atelierRepository.findAll().forEach(atelier -> {
            if (atelier.getDateAtelier() != null) {
                LocalDate date = atelier.getDateAtelier().toLocalDate();
                if (date.isAfter(now.minusMonths(12))) {
                    String monthKey = date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                    ateliersByMonth.merge(monthKey, 1L, Long::sum);
                }
            }
        });

        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 11; i >= 0; i--) {
            LocalDate monthDate = now.minusMonths(i);
            String monthKey = monthDate.getYear() + "-" + String.format("%02d", monthDate.getMonthValue());

            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", monthKey);
            monthData.put("formations", formationsByMonth.getOrDefault(monthKey, 0L));
            monthData.put("missions", missionsByMonth.getOrDefault(monthKey, 0L));
            monthData.put("ateliers", ateliersByMonth.getOrDefault(monthKey, 0L));
            result.add(monthData);
        }

        return result;
    }

    /**
     * Méthode utilitaire pour convertir java.util.Date ou java.sql.Date en LocalDate
     * Cette méthode gère les deux types de dates utilisés dans l'application
     *
     * @param date la date à convertir (peut être java.util.Date ou java.sql.Date)
     * @return LocalDate ou null si la conversion échoue
     */
    private LocalDate convertDateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }

        try {
            // Si c'est java.sql.Date, utiliser toLocalDate() directement
            if (date instanceof java.sql.Date) {
                return ((java.sql.Date) date).toLocalDate();
            }

            // Pour java.util.Date, utiliser toInstant()
            return date.toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
        } catch (UnsupportedOperationException e) {
            // Fallback pour les cas edge
            log.warn("Conversion via toInstant() non supportée, utilisation de Calendar");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(date);
            return LocalDate.of(
                    cal.get(java.util.Calendar.YEAR),
                    cal.get(java.util.Calendar.MONTH) + 1,
                    cal.get(java.util.Calendar.DAY_OF_MONTH)
            );
        } catch (Exception e) {
            log.error("Erreur lors de la conversion de la date: {}", e.getMessage());
            return null;
        }
    }
    @Override
    public List<StatisticalDTO> getActivitiesByStatus() {
        Map<String, Long> statusCount = new HashMap<>();


        formationExterieurRepository.findAll().forEach(f -> {
            String statut = f.getStatut() != null ? f.getStatut().name() : "NON_DEFINI";
            statusCount.merge(statut, 1L, Long::sum);
        });

        // Compter par statut pour missions
        ordreMissionRepository.findAll().forEach(m -> {
            String statut = m.getStatut() != null ? m.getStatut().name() : "NON_DEFINI";
            statusCount.merge(statut, 1L, Long::sum);
        });

        // Compter par statut pour ateliers
        atelierRepository.findAll().forEach(a -> {
            String statut = a.getStatut() != null ? a.getStatut().name() : "NON_DEFINI";
            statusCount.merge(statut, 1L, Long::sum);
        });

        return statusCount.entrySet().stream()
                .map(entry -> StatisticalDTO.builder()
                        .label(entry.getKey())
                        .value(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(StatisticalDTO::getValue).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticalFundingDTO> getTop5ActivitiesByBudget() {
        List<StatisticalFundingDTO> allActivities = new ArrayList<>();

        // Ajouter les formations
        formationExterieurRepository.findAll().forEach(f -> {
            if (f.getCoutTotal() != null && f.getCoutTotal() > 0) {
                allActivities.add(StatisticalFundingDTO.builder()
                        .label(f.getTitreFormation())
                        .value(f.getCoutTotal())
                        .build());
            }
        });

        // Ajouter les ateliers
        atelierRepository.findAll().forEach(a -> {
            if (a.getCoutOrganisation() != null && a.getCoutOrganisation() > 0) {
                allActivities.add(StatisticalFundingDTO.builder()
                        .label(a.getTitreAtelier())
                        .value(a.getCoutOrganisation())
                        .build());
            }
        });

        return allActivities.stream()
                .sorted(Comparator.comparing(StatisticalFundingDTO::getValue).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticalDTO> getParticipantsByActivityType() {
        // Pour les formations, on compte le nombre de formations (1 agent par formation)
        Long participantsFormations = formationExterieurRepository.count();

        // Pour les missions, on compte le nombre d'agents dans toutes les missions
        Long participantsMissions = ordreMissionRepository.findAll().stream()
                .mapToLong(m -> m.getAgent() != null ? m.getAgent().size() : 0L)
                .sum();

        // Pour les ateliers, on utilise le nombre max de participants
        Long participantsAteliers = atelierRepository.findAll().stream()
                .mapToLong(a -> a.getNombreParticipantsMax() != null ? a.getNombreParticipantsMax() : 0L)
                .sum();

        return List.of(
                StatisticalDTO.builder().label("Formations").value(participantsFormations).build(),
                StatisticalDTO.builder().label("Missions").value(participantsMissions).build(),
                StatisticalDTO.builder().label("Ateliers").value(participantsAteliers).build()
        );
    }


    @Override
    public AgentGroupingDTO getAgentGrouping() {
        List<AgentEntity> agents = agentRepository.findAll();

        Map<String, Map<String, Long>> grouped = new LinkedHashMap<>();
        List<String> tranches = List.of("18-25", "26-35", "36-45", "46-55", "56-60", "60+");

        // Initialiser les tranches avec 0
        for (String tranche : tranches) {
            grouped.put(tranche, new HashMap<>(Map.of("masculin", 0L, "feminin", 0L)));
        }

        long totalHommes = 0;
        long totalFemmes = 0;

        for (AgentEntity agent : agents) {
            if (agent.getDateNaissance() == null || agent.getSexe() == null) continue;

            LocalDate naissance = (agent.getDateNaissance() instanceof java.sql.Date sqlDate)
                    ? sqlDate.toLocalDate()
                    : agent.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            int age = LocalDate.now().getYear() - naissance.getYear();

            String tranche;
            if (age >= 18 && age <= 25) {
                tranche = "18-25";
            } else if (age >= 26 && age <= 35) {
                tranche = "26-35";
            } else if (age >= 36 && age <= 45) {
                tranche = "36-45";
            } else if (age >= 46 && age <= 55) {
                tranche = "46-55";
            } else if (age >= 56 && age <= 60) {
                tranche = "56-60";
            } else {
                tranche = "60+";
            }

            String sexeKey = (agent.getSexe() == Sexe.MASCULIN) ? "masculin" : "feminin";

            Map<String, Long> counts = grouped.get(tranche);
            counts.put(sexeKey, counts.get(sexeKey) + 1);
            grouped.put(tranche, counts);

            if (agent.getSexe() == Sexe.MASCULIN) totalHommes++;
            else totalFemmes++;
        }

        return AgentGroupingDTO.builder()
                .ageGroups(grouped)
                .totalHommes(totalHommes)
                .totalFemmes(totalFemmes)
                .build();
    }


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
                // FILTRE AJOUTÉ ICI pour ignorer les réalisations sans date
                .filter(r -> r.getDate() != null)
                .collect(Collectors.groupingBy(
                        r -> {
                            LocalDate date = r.getDate();
                            return date.getYear() + "-" + String.format("%02d", date.getMonthValue());
                        },
                        Collectors.summingDouble(RealisationEntity::getMontant)
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
    public List<AgentCountByDirectionDTO> AgentCountByDirection() {
        return agentRepository.countAgentsByDirection();
    }

    @Override
    public AgentDashboardDTO getAgentsDashboard() {
        Long totalAgents = agentRepository.count();
        Long totalAgentsEnConges = (long) congeService.readAll().size();
        Long totalAgentsParDirection = agentRepository.countAgentsByDirection()
                .stream()
                .mapToLong(AgentCountByDirectionDTO::getTotalAgents)
                .sum();
        return new AgentDashboardDTO(totalAgents, totalAgentsEnConges, totalAgentsParDirection);
    }

    @Override
    public List<RetraiteProjectionDTO> getRetraiteProjections(Integer annee) {
        return agentRepository.countFutureRetraitesByDirection(annee);
    }

    @Override
    public List<DataPoint<String, Long>> getRepartitionNiveauConformite() {
        return inspectionICPERepository.countByComplianceLevel();
    }
    /* Icpe Dashboard END*/


    @Override
    public List<AgentCountByDirectionDTO> AgentCountByDirections() {
        return agentRepository.countAgentsByDirection();
    }

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

    /**
     * Complète les 12 mois (même ceux à 0 demandes)
     */
    private List<MoisCountsDTO> padEvolutionMensuelle(List<MoisCountsDTO> src) {
        String[] mois = {"Jan", "Fév", "Mar", "Avr", "Mai", "Jun",
                "Jul", "Aoû", "Sep", "Oct", "Nov", "Déc"};
        Map<String, Long> mapDemandes = src.stream()
                .collect(Collectors.toMap(MoisCountsDTO::getMois, MoisCountsDTO::getDemandes));

        return Arrays.stream(mois)
                .map(m -> MoisCountsDTO.builder()
                        .mois(m)
                        .demandes(mapDemandes.getOrDefault(m, 0L))
                        .build())
                .collect(Collectors.toList());
    }
}
