package com.webgram.dgpsn.controllers;

import com.webgram.dgpsn.models.AgentCountByDirectionDTO;
import com.webgram.dgpsn.models.AgentDashboardDTO;
import com.webgram.dgpsn.models.AgentGroupingDTO;
import com.webgram.dgpsn.models.RetraiteProjectionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.webgram.dgpsn.entities.FundingConfigEntity;
import com.webgram.dgpsn.entities.IssueLogEntity;
import com.webgram.dgpsn.entities.enums.StatusType;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.*;
import com.webgram.dgpsn.services.*;

import java.util.*;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "dashboard-controller", description = "dashboard controller")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    private final HistoryFlagService historyFlagService;
    private final HistoryStatusService historyStatusService;
    private final BudgetService budgetService;

    @Operation(summary = "Read stat project by status", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projectsbyStatus")
    public List<StatisticalDTO> readStatProjectByStatus() {
        return dashboardService.readStatProjectByStatus();
    }

    @Operation(summary = "Read stat project by flag", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projectsbyFlag")
    public List<StatisticalDTO> readStatProjectByFlag() {
        return dashboardService.readStatProjectByFlag();
    }

    @Operation(summary = "Read stat funding", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/statFunding")
    public List<Long> readStatFunding() {
        return dashboardService.readStatFunding();
    }

    @Operation(summary = "Read stat project by Sector", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projectsbySector")
    public List<StatisticalDTO> readStatProjectBySector() {
        return dashboardService.readStatProjectBySector();
    }

    @Operation(summary = "Read stat project by ZonesExecution", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projectsbyZonesExecution")
    public List<StatisticalDTO> readStatProjectByZonesExecution() {
        return dashboardService.readStatProjectByZonesExecution();
    }

    @Operation(summary = "Read number of project by regions", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projects-by-region")
    public ResponseEntity<List<StatisticalDTO>> getProjectsByRegion() {
        List<StatisticalDTO> stats = dashboardService.getProjectsByRegion();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Read budget  by regions", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/budget-by-region")
    public ResponseEntity<List<StatisticalBudgetDTO>> getBudgetSumByRegion() {
        List<StatisticalBudgetDTO> stats = dashboardService.getBudgetDistributionByProject();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Read stat Program by project", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/nombreProgrambyProjet")
    public List<StatisticalDTO> readCountProjectsByProgramme() {
        return dashboardService.readCountProjectsByProgramme();
    }


    @Operation(summary = "Read average funding by Sector", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/averagebySector")
    public List<StatisticalFundingDTO> readAverageFundingBySector() {
        return dashboardService.readAverageFundingBySector();
    }

    @Operation(summary = "Read average need funding by Project", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalNeedFundingByProjet")
    public List<StatisticalFundingDTO> readTotalNeedByProjet() {
        return dashboardService.readTotalNeedByProjet();
    }

    @Operation(summary = "Read average need funding by Project", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalMobilisationByProjet")
    public List<StatisticalFundingDTO> readTotalMobilisationByProjet() {
        return dashboardService.readTotalMobilisationByProjet();
    }

    @Operation(summary = "Read average need funding by Project", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalExecutionByProjet")
    public List<StatisticalFundingDTO> readTotalExecutionByProjet() {
        return dashboardService.readTotalExecutiontionByProjet();
    }
    @Operation(summary = "Read total funding by Sector", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalbySector")
    public List<StatisticalFundingDTO> readTotalFundingBySector() {
        return dashboardService.readTotalFundingBySector();
    }

    @Operation(summary = "Read issueLog storage by status", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/storage")
    public List<StatisticalDTO> readStatIssueLogByStatus() {
        return dashboardService.readStatIssueLogByStatus();
    }

    @Operation(summary = "Read issueLog storage by year", description = "It takes an optional year param and returns the related list. Defaults to the last 5 years if no year is provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/storage/year")
    public List<Map<String, Object>> readStatIssueLogByYear(@RequestParam(required = false) Integer year) {
        return dashboardService.readStatIssueLogByYear(year);
    }

    @Operation(summary = "Read issueLog by nature", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count-by-nature")
    public List<StatisticalDTO> readIssuesCountByNature() {
        return dashboardService.getIssuesCountByNature();
    }

    @Operation(summary = "Read resolved issueLog", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/resolved")
    public StatResolveDTO readStatResolvedIssueLog() {
        return dashboardService.readStatResolved();
    }

    @Operation(summary = "Read number project per structure", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/countProjectPerStructure")
    public List<StatisticalDTO> readStatNumberProjectPerStructure() {
        return dashboardService.readStatNumberProjectByStructure();
    }

    @Operation(summary = "Read number project per tranche", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/countProjectPerTranche")
    public List<StatisticalDTO> readStatNumberProjectByTranche() {
        return dashboardService.readNumberProjectByTranche();
    }
    @Operation(summary = "Read number project per tranche", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/avgAgePerPartner")
    public List<StatisticalFundingDTO> readStatAverageAgeByPertner() {
        return dashboardService.readAverageAgeByPertner();
    }

    @Operation(summary = "Read number project per tranche", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/projectPerPartner")
    public List<StatisticalProjectDTO> readStatisticalData() {
        return dashboardService.getStatisticalData();
    }
    @Operation(summary = "Read number project per partner", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/totalFundingPerPartner")
    public List<StatisticalFundingDTO> readStatTotalFundinPerPartner() {
        return dashboardService.readTotalFundingtByPartner();
    }
    @Operation(summary = "Read number budget per activity", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/total-budget-by-activity")
    public ResponseEntity<List<StatisticalBudgetActivityDTO>> getTotalBudgetByActivity() {
        List<StatisticalBudgetActivityDTO> statistics = dashboardService.readTotalBudgetByActivity();
        return ResponseEntity.ok(statistics);
    }
    @Operation(summary = "Read number depense per activity", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/total-expanse-by-activity")
    public ResponseEntity<List<StatisticalBudgetActivityDTO>> getTotalExpanseByActivity() {
        List<StatisticalBudgetActivityDTO> statistics = dashboardService.readTotalExpanseByActivity();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/issue-log-count-by-supervisor")
    public ResponseEntity<List<StatisticalDTO>> getIssueLogCountBySupervisor() {
        List<StatisticalDTO> stats = dashboardService.readIssueLogCountBySupervisor();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Read number financial per projet", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/financialRisksByProject")
    public List<RisqueFinancierDto> getFinancialRisksByProject() {
        return dashboardService.getFinancialRisksByProject();
    }

    @Operation(summary = "filtre grafical budget activity per projet", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
@GetMapping("/activities/{projectId}")
public ResponseEntity<List<StatisticalBudgetActivityDTO>> getActivitiesAndBudgetsByProject(
        @PathVariable Long projectId,
        @RequestParam TypeProjet typeProjet) {
    List<StatisticalBudgetActivityDTO> result = dashboardService.getActivitiesAndBudgetsByProject(projectId, typeProjet);
    return ResponseEntity.ok(result);
}

    @Operation(summary = "filtre graphical Expanse activity per projet", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/activitiesExpense/{projectId}")
    public ResponseEntity<List<StatisticalBudgetActivityDTO>> getActivitiesAndExpanseByProject(
            @PathVariable Long projectId,
            @RequestParam TypeProjet typeProjet) {
        List<StatisticalBudgetActivityDTO> result = dashboardService.getActivitiesAndExpansesByProject(projectId, typeProjet);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/projects-by-geo")
    public List<GeographicDTO> getProjectsByGeographicalLocation(@RequestParam TypeProjet type) {
        return dashboardService.getProjectsByGeographicalLocation(type);
    }

    @Operation(summary = "Open and bloqued issuelog graphic  per annee ,trimestre and projet", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/open")
    public List<IssueLogEntity> getOpenIssues(
            @RequestParam Long projetId,
            @RequestParam Integer annee,
            @RequestParam(required = false) String trimestre) {
        return dashboardService.getOpenIssues(projetId, annee, trimestre);
    }
    @Operation(summary = "Closed issuelog graphic  per annee ,trimestre and projet", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/closed")
    public List<IssueLogEntity> getClosedIssues(
            @RequestParam Long projetId,
            @RequestParam Integer annee,
            @RequestParam(required = false) String trimestre) {
        return dashboardService.getClosedIssues(projetId, annee, trimestre);
    }

    @Operation(summary = "Read Need funding activity graphic  per annee ,trimestre and projet", description = "It take input param of the page and return this list related")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/need")
    public List<FundingConfigEntity> getFundingConfigs(
            @RequestParam Long projetId,
            @RequestParam String annee,
            @RequestParam(required = false) String trimestre) {
        return dashboardService.getFundingConfigs(projetId, annee, trimestre);
    }
    @GetMapping("/resume")
    public Map<String, Object> getResumeGlobal() {
        var stats = new HashMap<String, Object>();
        stats.put("synthese", historyFlagService.countByFlagCode());
        stats.put("programme", historyStatusService.countByStatusCode(StatusType.PROGRAM));
        stats.put("projet", historyStatusService.countByStatusCode(StatusType.PROJECT));
        stats.put("financement", budgetService.totalFunding());
        return stats;
    }

    @GetMapping("/resume/financement")
    public List<Object> getResumeFinancement() {
        return dashboardService.buildDashboardResumeFinancement();
    }

    @GetMapping("/environmental/kpis")
    @ResponseStatus(HttpStatus.OK)
    public EnvironmentalKpiResponse getEnvironmentalKpis() {
        return dashboardService.getEnvironmentalKpis();
    }

    @GetMapping("/environmental/iqa-trend")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<Date, Double>> getIqaTrend() {
        return dashboardService.getIqaTrend();
    }

    @GetMapping("/environmental/iqa-by-region")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Double>> getIqaByRegion() {
        return dashboardService.getIqaByRegion();
    }

    @GetMapping("/environmental/pollutant-distribution")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getPollutantDistribution() {
        return dashboardService.getPollutantDistribution();
    }

    @GetMapping("/evaluation/kpis")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationKpiResponse getEvaluationKpis() {
        return dashboardService.getEvaluationKpis();
    }

    @GetMapping("/evaluation/flux-dossiers")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getFluxDossiersMensuel() {
        return dashboardService.getFluxDossiersMensuel();
    }

    @GetMapping("/evaluation/projets-par-region")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getProjetsParRegion() {
        return dashboardService.getProjetsParRegion();
    }

    @GetMapping("/evaluation/statut-instructions")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getStatutInstructions() {
        return dashboardService.getStatutInstructions();
    }

    /* Pollution Dashboard START*/
    @GetMapping("/pollution/kpis")
    @ResponseStatus(HttpStatus.OK)
    public PollutionKpiResponse getPollutionKpis() {
        return dashboardService.getPollutionKpis();
    }

    @GetMapping("/pollution/volume-dechets")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Double>> getVolumeDechetsMensuel() {
        return dashboardService.getVolumeDechetsMensuel();
    }

    @GetMapping("/pollution/repartition-types")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getRepartitionTypePollution() {
        return dashboardService.getRepartitionTypePollution();
    }

    @GetMapping("/pollution/tendance-eau")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Double>> getTendancePollutionEau() {
        return dashboardService.getTendancePollutionEau();
    }

    @Operation(summary = "Get agent statistics card", description = "Returns statistics card for agents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/agent/card")
    public ResponseEntity<AgentDashboardDTO> getAgentStats() {
        return ResponseEntity.ok(dashboardService.getAgentsDashboard());
    }

    @Operation(summary = "Get agent statistics des qui sont dans les directions", description = "Returns statistics card for agents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/direction/agents")
    public ResponseEntity<List<AgentCountByDirectionDTO>> AgentCountByDirection() {
        return ResponseEntity.ok(dashboardService.AgentCountByDirection());
    }

    /* Icpe Dashboard START*/
    @GetMapping("/icpe/kpis")
    @ResponseStatus(HttpStatus.OK)
    public IcpeKpiResponse getIcpeKpis() {
        return dashboardService.getIcpeKpis();
    }

    @GetMapping("/icpe/inspections-mensuelles")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getInspectionsMensuelles() {
        return dashboardService.getInspectionsMensuelles();
    }

    @GetMapping("/icpe/repartition-categories")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getIcpeParCategorie() {
        return dashboardService.getIcpeParCategorie();
    }

    @GetMapping("/icpe/repartition-conformite")
    @ResponseStatus(HttpStatus.OK)
    public List<DataPoint<String, Long>> getRepartitionNiveauConformite() {
        return dashboardService.getRepartitionNiveauConformite();
    }
    /* Icpe Dashboard END*/


    // ================= BUDGET DASHBOARD ENDPOINTS =================

    @Operation(summary = "Read global budget KPIs (Total, Consumed, Remaining, Rate)", description = "Calcule le budget total, consommé, restant et le taux d'exécution global.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/budget/kpis")
    public Map<String, Object> getBudgetSummaryKpis() {
        return dashboardService.getBudgetSummaryKpis();
    }

    @Operation(summary = "Read budget distribution by year", description = "Répartition des montants budgétaires par année.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/budget/distribution-by-year")
    public List<StatisticalFundingDTO> getBudgetDistributionByYear() {
        return dashboardService.getBudgetDistributionByYear();
    }

    @Operation(summary = "Read top 5 budgets by total amount", description = "Top 5 des budgets classés par montant total.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/budget/top5-by-amount")
    public List<StatisticalFundingDTO> getTop5BudgetsByAmount() {
        return dashboardService.getTop5BudgetsByAmount();
    }

    @Operation(summary = "Read top 5 budgets by execution rate", description = "Top 5 des budgets classés par taux d'exécution.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/budget/top5-by-execution")
    public List<StatisticalFundingDTO> getTop5BudgetExecutionRates() {
        return dashboardService.getTop5BudgetExecutionRates();
    }

    @Operation(summary = "Read monthly evolution of budget consumption", description = "Évolution de la consommation budgétaire (réalisations) par mois.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/budget/monthly-consumption")
    public List<Map<String, Object>> getMonthlyBudgetConsumption() {
        return dashboardService.getMonthlyBudgetConsumption();
    }


    @Operation(summary = "summary of conge", description = "resumer  du conge.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/dashboard-conge")
    public ResponseEntity<CongeDashboardDTO> getDashboard() {
        return ResponseEntity.ok(dashboardService.getFullDashboard());

    }
    // ================= SERVICE EXTERIEUR DASHBOARD ENDPOINTS =================

    @Operation(summary = "Read Service Exterieur KPIs", description = "Récupère les indicateurs globaux des services extérieurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/kpis")
    public ServiceExterieurKpiResponse getServiceExterieurKpis() {
        return dashboardService.getServiceExterieurKpis();
    }

    @Operation(summary = "Read activities by type", description = "Répartition des activités par type (Formations, Missions, Ateliers)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/activities-by-type")
    public List<StatisticalDTO> getActivitiesByType() {
        return dashboardService.getActivitiesByType();
    }

    @Operation(summary = "Read budget by activity type", description = "Budget par type d'activité")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/budget-by-type")
    public List<StatisticalFundingDTO> getBudgetByActivityType() {
        return dashboardService.getBudgetByActivityType();
    }

    @Operation(summary = "Read monthly activities evolution", description = "Évolution mensuelle des activités sur 12 mois")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/monthly-evolution")
    public List<Map<String, Object>> getMonthlyActivitiesEvolution() {
        return dashboardService.getMonthlyActivitiesEvolution();
    }

    @Operation(summary = "Read activities by status", description = "Répartition des activités par statut")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/activities-by-status")
    public List<StatisticalDTO> getActivitiesByStatus() {
        return dashboardService.getActivitiesByStatus();
    }

    @Operation(summary = "Read top 5 activities by budget", description = "Top 5 des activités par budget")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/top5-by-budget")
    public List<StatisticalFundingDTO> getTop5ActivitiesByBudget() {
        return dashboardService.getTop5ActivitiesByBudget();
    }

    @Operation(summary = "Read participants by activity type", description = "Nombre de participants par type d'activité")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/service-exterieur/participants-by-type")
    public List<StatisticalDTO> getParticipantsByActivityType() {
        return dashboardService.getParticipantsByActivityType();

    }

    @Operation(summary = "Get Grouping Agent Sexe / lenght", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/agent/groups")
    public ResponseEntity<AgentGroupingDTO> getGroupingAgent() {
        return ResponseEntity.ok(dashboardService.getAgentGrouping());
    }

    @Operation(summary = "Prevision des agent retraites", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/retraites-projections")
    public ResponseEntity<List<RetraiteProjectionDTO>> getRetraiteProjections(
            @RequestParam(value = "annee", required = false) Integer annee
    ) {
        return ResponseEntity.ok(dashboardService.getRetraiteProjections(annee));
    }
}
