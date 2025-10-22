package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.GeographicalLocationEntity;
import com.webgram.dgpsn.entities.enums.CadreLogiqueType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.GeographicalLocationMapper;
import com.webgram.dgpsn.models.GeographicalLocationDTO;
import com.webgram.dgpsn.models.responses.*;
import com.webgram.dgpsn.repositories.GeographicalLocationRepository;
import com.webgram.dgpsn.repositories.HistoryFlagRepository;
import com.webgram.dgpsn.repositories.HistoryStatusRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.CompletionRateService;
import com.webgram.dgpsn.services.GeographicalLocationService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class GeographicalLocationServiceImpl implements GeographicalLocationService {
    private final GeographicalLocationRepository geographicalLocationRepository;
    private final GeographicalLocationMapper geographicalLocationMapper;
    private final HistoryFlagRepository historyFlagRepository;
    private final HistoryStatusRepository historyStatusRepository;
    private  final ManagementUnitRepository managementUnitRepository;
    private final CompletionRateService completionRateService;

    String GEOGRAPHICALLOCATION_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id geographicalLocation {0}";

    @Override
    @Journal(actionType = ActionType.ADD_ZONE_INVENTION)
    public void createGeographicalLocation(List<GeographicalLocationDTO> geographicalLocationDTO) {
        var geoEntities = geographicalLocationDTO.stream()
                .map(geographicalLocationMapper::asEntity)
                .collect(Collectors.toList());
        var createdGeo = geographicalLocationRepository.saveAll(geoEntities);
    //    log.info("createdGeo end ok - createdGeoId: {}", createdGeo.getId());
        log.trace("createdGeo end ok - createdGeo: {}", createdGeo);
    }


    @Override
    @Journal(actionType = ActionType.READ_ZONE_INVENTION)
    public GeographicalLocationDTO readGeographicalLocation(Long id) {
        var geo = geographicalLocationRepository.findById(id)
                .map(geographicalLocationMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(GEOGRAPHICALLOCATION_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read geo end ok - Id: {}", id);
        log.trace("read geo end ok - geographicalLocation: {}", geo);
        return geo;
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ZONE_INVENTION)
    public void deleteGeographicalLocation(Long id) {
        if(!geographicalLocationRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(GEOGRAPHICALLOCATION_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        var cadre = geographicalLocationRepository.findById(id).get();
        if(Objects.nonNull(cadre.getCadreLogique().getTypeCadreLogique()) && CadreLogiqueType.REGION.equals(cadre.getCadreLogique().getTypeCadreLogique())) {
            deleteRegion(cadre);
        }else if (Objects.nonNull(cadre.getCadreLogique().getTypeCadreLogique()) && CadreLogiqueType.DEPARTEMENT.equals(cadre.getCadreLogique().getTypeCadreLogique())) {
            deleteDepartement(cadre);
        }else if (Objects.nonNull(cadre.getCadreLogique().getTypeCadreLogique()) && CadreLogiqueType.ARRONDISSEMENT.equals(cadre.getCadreLogique().getTypeCadreLogique())) {
            deleteArrondissement(cadre);
        }else if (Objects.nonNull(cadre.getCadreLogique().getTypeCadreLogique()) && CadreLogiqueType.COMMUNE.equals(cadre.getCadreLogique().getTypeCadreLogique())) {
            geographicalLocationRepository.deleteById(id);
        }

        log.info("delete geographicalLocation ok id {}", id);
    }

    @Override
    @Journal(actionType = ActionType.READ_ZONE_INVENTION)
    public Page<GeographicalLocationDTO> readAllGeographicalLocation(Pageable pageable, String code, String libelle, CadreLogiqueType cadreLogiqueType, Long projetId) {
        var geos = geographicalLocationRepository
                .readAllByFilters(pageable, code, libelle, cadreLogiqueType, projetId)
                .map(geographicalLocationMapper::asDto);
        log.trace("list geographicalLocation ok {}", geos);
        return geos;
    }

    @Override
    public List<ReportingByRegionDTO> getReportingBySRegion(Long regionId) {
        var programmesByStructures = geographicalLocationRepository.getProgrammeByRgion(regionId);
        List<ReportingByRegionDTO> reportingList = new ArrayList<>();
        programmesByStructures.forEach(program -> {
            var report = ReportingByRegionDTO.builder()
                    .programme(program.getName())
                    .projectInformationDTOList(getProjecInformatons(program.getId(), regionId))
                    .build();
            reportingList.add(report);
        });
        return reportingList;
    }

    public void deleteRegion(GeographicalLocationEntity region) {
        var departements = geographicalLocationRepository.findByCadreLogiqueParentAndProjet(region.getCadreLogique(), region.getProjet());
        if(departements.size() > 0){
            departements.forEach(cadre -> {
                deleteDepartement(cadre);
            });
        }
        geographicalLocationRepository.delete(region);
        log.info("liste departemment deleted {}", departements.size());
    }

    public void deleteDepartement(GeographicalLocationEntity departement) {

        var arrondissements = geographicalLocationRepository.findByCadreLogiqueParentAndProjet(departement.getCadreLogique(), departement.getProjet());
        if(arrondissements.size() > 0){
            arrondissements.forEach(cadre -> {
                deleteArrondissement(cadre);
            });
        }
        geographicalLocationRepository.delete(departement);
        log.info("liste arrondissement deleted {}", arrondissements.size());
    }

    public void deleteArrondissement(GeographicalLocationEntity arrondissement) {

        var communes = geographicalLocationRepository.findByCadreLogiqueParentAndProjet(arrondissement.getCadreLogique(), arrondissement.getProjet());
        if(communes.size() > 0){
            communes.forEach(cadre -> {
                geographicalLocationRepository.delete(cadre);
            });
        }
        geographicalLocationRepository.delete(arrondissement);
        log.info("liste communes deleted {}", communes.size());
    }

    public List<ProjectInformationDTO> getProjecInformatons(Long programId, Long regionId) {
        LocalDate currentDate = LocalDate.now();
        List<ProjectInformationDTO>  projectInformationDTOList = new ArrayList<>();
        var projects = geographicalLocationRepository.getProjectByRegionAndProgramme(regionId, programId);

        projects.forEach(project -> {
            var projectInformation = ProjectInformationDTO.builder()
                    .id(project.getId())
                    .nom(project.getName())
                    .taux(getTaux(project.getId(), currentDate,false))
                    .cible(getTaux(project.getId(),currentDate,true))
                    .etat(getEtat(project.getId()))
                    .statut(getStatut(project.getId()))
                    .problemeInformationDTOList(getProblemmeInformation(project.getId()))
                    .build();
            projectInformationDTOList.add(projectInformation);
        });
        return projectInformationDTOList;
    }
    public String getEtat(Long projectId){
        var etat = historyFlagRepository.getLastEtat(projectId, new Date());
        if(Objects.nonNull(etat)) {
            return etat.getFlag().getCode();
        }
        return "-";
    }
    public String getStatut(Long projectId){
        var statut = historyStatusRepository.getLastStatut(projectId, new Date());
        if(Objects.nonNull(statut)) {
            return statut.getStatus().getLibelle();
        }
        return "-";
    }
    //    public Double getTaux(Long projectId, LocalDate currentDate){
//        var projet = managementUnitRepository
//                .findById(projectId)
//                .orElseThrow(()-> new ResourceNotFoundException("Projet", projectId));
//
//        log.info("mois {}", currentDate.getMonthValue());
//        return 0.0;
//    }
    public Double getTaux(Long projectId, LocalDate currentDate, boolean cible) {
        var projet = managementUnitRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", projectId));

        var completionRates = completionRateService.readCompletionRateByManagementUnit(projet.getId(), currentDate.getYear());
        double taux = 0.0;

        if (!cible) {
            double[] tauxTotalRealiser = calculTotalRealisations(completionRates);

            for (int i = 0; i < 4; i++) {
                if (tauxTotalRealiser[i] != 0) {
                    taux += tauxTotalRealiser[i];
                }
            }
        } else {
            double[] tauxTotalPrevision = calculTotalPrevision(completionRates);

            for (int i = 0; i < 4; i++) {
                if (tauxTotalPrevision[i] != 0) {
                    taux += tauxTotalPrevision[i];
                }
            }
        }

        if (Double.isNaN(taux)) {
            return 0.0;
        } else {
//    return Math.ceil(taux /4);
            return Math.ceil((taux/4) * 100.0) / 100.0;

        }
    }

    public Double getCible(Long projectId){
        return 0.0;
    }
    public List<ProblemeInformationDTO> getProblemmeInformation(Long projectId){
        return null;
    }

    private double[] calculTotalRealisations(CompletionRateResponse project) {
        double[] totalRealisation = new double[4];

        if ("Somme".equals(project.getManagementUnit().getFormula().getDescription())) {
            for (var child : project.getChildren()) {
                for (var completionRate : child.getCompletionRates()) {
                    switch (completionRate.getPeriod().getLibelle()) {
                        case "Trimestre 1":
                            totalRealisation[0] += completionRate.getValueReched();
                            break;
                        case "Trimestre 2":
                            totalRealisation[1] += completionRate.getValueReched();
                            break;
                        case "Trimestre 3":
                            totalRealisation[2] += completionRate.getValueReched();
                            break;
                        case "Trimestre 4":
                            totalRealisation[3] += completionRate.getValueReched();
                            break;
                    }
                }
            }
            totalRealisation[0] /= project.getChildren().size();
            totalRealisation[1] /= project.getChildren().size();
            totalRealisation[2] /= project.getChildren().size();
            totalRealisation[3] /= project.getChildren().size();
        } else {
            for (var child : project.getChildren()) {
                for (var completionRate : child.getCompletionRates()) {
                    double poids = child.getManagementUnit().getPoids();
                    switch (completionRate.getPeriod().getLibelle()) {
                        case "Trimestre 1":
                            totalRealisation[0] += completionRate.getValueReched() * poids;
                            break;
                        case "Trimestre 2":
                            totalRealisation[1] += completionRate.getValueReched() * poids;
                            break;
                        case "Trimestre 3":
                            totalRealisation[2] += completionRate.getValueReched() * poids;
                            break;
                        case "Trimestre 4":
                            totalRealisation[3] += completionRate.getValueReched() * poids;
                            break;
                    }
                }
            }
            double totalPoids = calculTotalPoids(project.getChildren());
            totalRealisation[0] /= totalPoids;
            totalRealisation[1] /= totalPoids;
            totalRealisation[2] /= totalPoids;
            totalRealisation[3] /= totalPoids;
        }

        return totalRealisation;
    }

    private double[] calculTotalPrevision(CompletionRateResponse project) {
        double[] totalPrevision = new double[4];

        if ("Somme".equals(project.getManagementUnit().getFormula().getDescription())) {
            for (var child : project.getChildren()) {
                for (var completionRate : child.getCompletionRates()) {
                    switch (completionRate.getPeriod().getLibelle()) {
                        case "Trimestre 1":
                            totalPrevision[0] += completionRate.getTargetValue();
                            break;
                        case "Trimestre 2":
                            totalPrevision[1] += completionRate.getTargetValue();
                            break;
                        case "Trimestre 3":
                            totalPrevision[2] += completionRate.getTargetValue();
                            break;
                        case "Trimestre 4":
                            totalPrevision[3] += completionRate.getTargetValue();
                            break;
                    }
                }
            }
            totalPrevision[0] /= project.getChildren().size();
            totalPrevision[1] /= project.getChildren().size();
            totalPrevision[2] /= project.getChildren().size();
            totalPrevision[3] /= project.getChildren().size();
        } else {
            for (var child : project.getChildren()) {
                for (var completionRate : child.getCompletionRates()) {
                    double poids = child.getManagementUnit().getPoids();
                    switch (completionRate.getPeriod().getLibelle()) {
                        case "Trimestre 1":
                            totalPrevision[0] += completionRate.getTargetValue() * poids;
                            break;
                        case "Trimestre 2":
                            totalPrevision[1] += completionRate.getTargetValue() * poids;
                            break;
                        case "Trimestre 3":
                            totalPrevision[2] += completionRate.getTargetValue() * poids;
                            break;
                        case "Trimestre 4":
                            totalPrevision[3] += completionRate.getTargetValue() * poids;
                            break;
                    }
                }
            }
            double totalPoids = calculTotalPoids(project.getChildren());
            totalPrevision[0] /= totalPoids;
            totalPrevision[1] /= totalPoids;
            totalPrevision[2] /= totalPoids;
            totalPrevision[3] /= totalPoids;
        }

        return totalPrevision;
    }

    private double calculTotalPoids(List<CompletionRateResponse> children) {
        return children.stream().mapToDouble(child -> child.getManagementUnit().getPoids()).sum();
    }
}
