package com.webgram.dgpsn.security.rules;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import com.webgram.dgpsn.security.SecurityPermissions;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardRule {
    static final String DASHBOARD_API_PREFIX = "/dashboard";
    static final String PROJECTS_BY_STATUS_PREFIX = "/{projectsbyStatus}";
    static final String PROJECTS_BY_FLAG_PREFIX = "/projectsbyFlag";
    static final String STAT_FUNDING_PREFIX = "/statFunding";
    static final String STAT_BUDGET_BY_ACTIVITY = "/total-budget-by-activity";
    static final String STAT_EXPANSE_BY_ACTIVITY = "/total-expanse-by-activity";
    static final String STAT_ISSUE_LOG_BY_SUPERVISOR = "/issue-log-count-by-supervisor";
    static final String STAT_PROJECT_BY_PARTNER = "/projectPerPartner";
    static final String STAT_PROJECT_BY_FINANCIAL = "/financialRisksByProject";
    static final String STAT_ISSUELOG_PER_YEAR = "/storage/year";
    static final String STAT_ISSUELOG_BY_NATURE = "/count-by-nature";
    static final String STAT_PROJ_BY_REGION = "/projects-by-region";
    static final String STAT_BUDGET_BY_REGION = "/budget-by-region";

    static final String FILTER_ACTIVITY_BY_PROJECT = "/activities";
    static final String FILTER_ACTIVITY_DEPENSE_BY_PROJECT = "/activitiesExpense";
    static final String PROJECT_ID = "/{projectId}";
    static final String GEO_PROJECT_ID = "/projects-by-geo";

    static final String ISSUE_LOG_OPEN = "/open";
    static final String ISSUE_LOG_CLOSE = "/closed";
    static final String NEED_FUNDING_ACTIVITY = "/need";
    static final String ENVIRONMENTAL_KPIS = "/environmental/kpis";
    static final String ENVIRONMENTAL_IQA_TREND = "/environmental/iqa-trend";
    static final String ENVIRONMENTAL_IQA_BY_REGION = "/environmental/iqa-by-region";
    static final String ENVIRONMENTAL_POLLUTANT_DISTRIBUTION = "/environmental/pollutant-distribution";
    static final String EVALUATION_KPIS = "/evaluation/kpis";
    static final String EVALUATION_FLUX_DOSSIERS = "/evaluation/flux-dossiers";
    static final String EVALUATION_STATUT_INSTRUCTIONS = "/evaluation/statut-instructions";
    static final String EVALUATION_PROJECTS_PAR_REGION = "/evaluation/projets-par-region";
    static final String POLLUTION_KPIS = "/pollution/kpis";
    static final String POLLUTION_VOLUME_DECHETS = "/pollution/volume-dechets";
    static final String POLLUTION_REPARTITION_TYPES = "/pollution/repartition-types";
    static final String POLLUTION_TENDANCE_EAU = "/pollution/tendance-eau";
    static final String ICPE_KPIS = "/icpe/kpis";
    static final String ICPE_INSPECTIONS_MENSUELLES = "/icpe/inspections-mensuelles";
    static final String ICPE_REPARTITION_CATEGORIE = "/icpe/repartition-categories";
    static final String ICPE_REPARTITION_CONFORMITE = "/icpe/repartition-conformite";
    // agents DGPSN
    static final String AGENT_STAT_CARD = "/agent/card";
    static final String AGENTS_FOR_DIRECTIONS = "/direction/agents";
    static final String AGENT_GROUPING = "/agent/groups";

    //budget
    static final String BUDGET_KPIS = "/budget/kpis";
    static final String BUDGET_DISTRIBUTION_BY_YEAR = "/budget/distribution-by-year";
    static final String BUDGET_TOP_5_BY_AMOUNT = "/budget/top5-by-amount";
    static final String BUDGET_TOP_5_EXECUTION= "/budget/top5-by-execution";
    static final String MONTHLY_BUDGET_CONSUMPTION = "/budget/monthly-consumption";
    //Conge DGPSN
    static final String CONGE_SUMMARY ="/dashboard-conge";
    // service exterieur
    static final String SERVICE_EXTERIEUR_KPIS = "/service-exterieur/kpis";
    static final String SERVICE_EXTERIEUR_ACTIVITIES_BY_TYPE = "/service-exterieur/activities-by-type";
    static final String SERVICE_EXTERIEUR_BUDGET_BY_TYPE = "/service-exterieur/budget-by-type";
    static final String SERVICE_EXTERIEUR_MONTHLY_EVOLUTION= "/service-exterieur/monthly-evolution";
    static final String MONTHLY_SERVICE_EXTERIEUR_ACTIVITIES_BY_STATUS = "/service-exterieur/activities-by-status";
    static final String SERVICE_EXTERIEUR_TOP_5_BY_BUDGET = "/service-exterieur/top5-by-budget";
    static final String SERVICE_EXTERIEUR_PARTICIPANTS_BY_TYPE = "/service-exterieur/participants-by-type";
    static final String RETRAITE_PROJECTIONS = "/retraites-projections";


    @Bean
    public SecurityRule getRetraiteProjections() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + RETRAITE_PROJECTIONS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.RETRAITE_PROJECTIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getAgentStats() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + AGENT_STAT_CARD)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.AGENT_STAT_CARD)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }
    @Bean
    public SecurityRule AgentCountByDirection() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + AGENTS_FOR_DIRECTIONS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.AGENTS_FOR_DIRECTIONS)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getGroupingAgent() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + AGENT_GROUPING)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.AGENT_GROUPING)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurKpis() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + SERVICE_EXTERIEUR_KPIS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurActivitiesByType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + SERVICE_EXTERIEUR_ACTIVITIES_BY_TYPE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurBudgetByType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + SERVICE_EXTERIEUR_BUDGET_BY_TYPE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurMonthlyEvolution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + SERVICE_EXTERIEUR_MONTHLY_EVOLUTION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurActivitiesByStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + MONTHLY_SERVICE_EXTERIEUR_ACTIVITIES_BY_STATUS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurTop5ByBudget() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + SERVICE_EXTERIEUR_TOP_5_BY_BUDGET)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getServiceExterieurParticipantsByType() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + SERVICE_EXTERIEUR_PARTICIPANTS_BY_TYPE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions. READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


    @Bean
    public SecurityRule getBudgetKpis() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + BUDGET_KPIS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getBudgetDistributionByYear() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + BUDGET_DISTRIBUTION_BY_YEAR)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getBudgetTop5ByAmount() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + BUDGET_TOP_5_BY_AMOUNT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getBudgetTop5ExecutionRates() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + BUDGET_TOP_5_EXECUTION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getMonthlyBudgetConsumption() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + MONTHLY_BUDGET_CONSUMPTION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_BUDGET)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getIcpeKpis() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ICPE_KPIS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getInspectionsMensuelles() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ICPE_INSPECTIONS_MENSUELLES)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getIcpeParCategorie() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ICPE_REPARTITION_CATEGORIE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getRepartitionNiveauConformite() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ICPE_REPARTITION_CONFORMITE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getPollutionKpis() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + POLLUTION_KPIS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getPollutionVolumeDechets() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + POLLUTION_VOLUME_DECHETS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEvaluationKpis() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + EVALUATION_KPIS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEvaluationFluxDossiers() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + EVALUATION_FLUX_DOSSIERS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEvaluationStatutInstructions() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + EVALUATION_STATUT_INSTRUCTIONS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEvaluationProjectsParRegion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + EVALUATION_PROJECTS_PAR_REGION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEnvironmentalKpis() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ENVIRONMENTAL_KPIS)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEnvironmentalIqaTrend() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ENVIRONMENTAL_IQA_TREND)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEnvironmentalIqaByRegion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ENVIRONMENTAL_IQA_BY_REGION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getEnvironmentalPollutantDistribution() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ENVIRONMENTAL_POLLUTANT_DISTRIBUTION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getOpenIssues() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ISSUE_LOG_OPEN)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getClosedIssues() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + ISSUE_LOG_CLOSE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getFundingConfigs() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + NEED_FUNDING_ACTIVITY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    static final String STAT_RESUME = "/resume";
    static final String FINANCEMENT = "/financement";

    @Bean
    public SecurityRule readStatProjectByStatus() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + PROJECTS_BY_STATUS_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getProjectsByGeographicalLocation() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + GEO_PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getActivitiesAndBudgetsByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + FILTER_ACTIVITY_BY_PROJECT + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getActivitiesAndExpanseByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + FILTER_ACTIVITY_DEPENSE_BY_PROJECT + PROJECT_ID)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStatisticalData() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_PROJECT_BY_PARTNER)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStatProjectByFlag() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + PROJECTS_BY_FLAG_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getTotalBudgetByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_BUDGET_BY_ACTIVITY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getFinancialRisksByProject() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_PROJECT_BY_FINANCIAL)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getTotalExpanseByActivity() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_EXPANSE_BY_ACTIVITY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule readStatFunding() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_FUNDING_PREFIX)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getIssueLogCountBySupervisor() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_ISSUE_LOG_BY_SUPERVISOR)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getStatIssueLogByYear() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_ISSUELOG_PER_YEAR)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getIssuesCountByNature() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_ISSUELOG_BY_NATURE)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getProjectsByRegion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_PROJ_BY_REGION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getBudgetsByRegion() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_BUDGET_BY_REGION)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getStatRAPPORT() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_RESUME)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getResumeFinancment() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + STAT_RESUME + FINANCEMENT)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_RESUME)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    @Bean
    public SecurityRule getDashboardSummary() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + CONGE_SUMMARY)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_CONGE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }

    // === Endpoints manquants ajoutés avec READ_TDB_SERVICE ===
    @Bean
    public SecurityRule getPollutionRepartitionTypes() {
        return SecurityRule.builder()
                .httpMethod(HttpMethod.GET)
                .apiPattern(DASHBOARD_API_PREFIX + POLLUTION_REPARTITION_TYPES)
                .build()
                .condition()
                .hasPermission(SecurityPermissions.READ_TDB_SERVICE)
                .hasPermission(SecurityPermissions.ALL_ACCESS)
                .end();
    }


}