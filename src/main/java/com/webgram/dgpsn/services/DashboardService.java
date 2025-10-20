package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.FundingConfigEntity;
import com.webgram.dgpsn.entities.IssueLogEntity;
import com.webgram.dgpsn.entities.enums.TypeProjet;
import com.webgram.dgpsn.models.responses.*;

import java.util.List;
import java.util.Map;
import java.util.Date;


public interface DashboardService {
    List<StatisticalDTO> readStatProjectByStatus();

    List<StatisticalDTO> readStatProjectByFlag();

    List<Long> readStatFunding();

    List<StatisticalDTO> readStatProjectBySector();

    List<StatisticalFundingDTO> readAverageFundingBySector();

    List<StatisticalFundingDTO> readTotalFundingBySector();

    List<StatisticalDTO> readStatProjectByPartner();

    List<StatisticalFundingDTO> readTotalFundingtByPartner();

    List<StatisticalDTO> readStatNumberProjectByStructure();

    List<StatisticalDTO> readStatIssueLogByStatus();

    List<Map<String, Object>> readStatIssueLogByYear(Integer year);

    List<StatisticalDTO> getIssuesCountByNature();

    List<StatisticalBudgetDTO> getBudgetDistributionByProject();
    List<StatisticalDTO> getProjectsByRegion();

    StatResolveDTO readStatResolved();

    List<StatisticalDTO> readNumberProjectByTranche();

    List<StatisticalDTO> readStatProjectByZonesExecution();

    List<StatisticalFundingDTO> readAverageAgeByPertner();

    List<StatisticalDTO> readCountProjectsByProgramme();

    List<StatisticalFundingDTO> readTotalNeedByProjet();

    List<StatisticalFundingDTO> readTotalMobilisationByProjet();

    List<StatisticalFundingDTO> readTotalExecutiontionByProjet();

    //    List<StatisticalDateBudgetDTO>totalBudgetActiviterByYear();
    List<StatisticalBudgetActivityDTO> readTotalBudgetByActivity();

    List<StatisticalBudgetActivityDTO> readTotalExpanseByActivity();

    List<StatisticalDTO> readIssueLogCountBySupervisor();

    List<StatisticalProjectDTO> getStatisticalData();

    List<RisqueFinancierDto> getFinancialRisksByProject();

    List<GeographicDTO> getProjectsByGeographicalLocation(TypeProjet type);


    List<StatisticalBudgetActivityDTO> getActivitiesAndBudgetsByProject(Long projectId, TypeProjet typeProjet);
    List<StatisticalBudgetActivityDTO> getActivitiesAndExpansesByProject(Long projectId, TypeProjet typeProjet);

    List<IssueLogEntity> getOpenIssues(Long projetId, Integer annee, String trimestre);
    List<IssueLogEntity> getClosedIssues(Long projetId, Integer annee, String trimestre);

    List<FundingConfigEntity> getFundingConfigs(Long projetId, String annee, String trimestre);
//    List<StatisticalProjectDTO> getProjectCountByStructure(TypeProjet typeProjet, StructureProjectType structureProjectType);
    List<Object> buildDashboardResumeFinancement();

    /* Qualité de l'air Dashboard START*/
    EnvironmentalKpiResponse getEnvironmentalKpis();
    List<DataPoint<Date, Double>> getIqaTrend();
    List<DataPoint<String, Double>> getIqaByRegion();
    List<DataPoint<String, Long>> getPollutantDistribution();
    /* Qualité de l'air Dashboard END*/

    /* Evaluation Environmental Dashboard START*/
    EvaluationKpiResponse getEvaluationKpis();
    List<DataPoint<String, Long>> getFluxDossiersMensuel();
    List<DataPoint<String, Long>> getProjetsParRegion();
    List<DataPoint<String, Long>> getStatutInstructions();
    /* Evaluation Environmental Dashboard END*/

    /* Pollution Dashboard START*/
    PollutionKpiResponse getPollutionKpis();
    List<DataPoint<String, Double>> getVolumeDechetsMensuel();
    List<DataPoint<String, Long>> getRepartitionTypePollution();
    List<DataPoint<String, Double>> getTendancePollutionEau();
    /* Pollution Dashboard END*/

    /* Icpe Dashboard START*/
    IcpeKpiResponse getIcpeKpis();
    List<DataPoint<String, Long>> getInspectionsMensuelles();
    List<DataPoint<String, Long>> getIcpeParCategorie();
    List<DataPoint<String, Long>> getRepartitionNiveauConformite();
    /* Icpe Dashboard END*/
}
