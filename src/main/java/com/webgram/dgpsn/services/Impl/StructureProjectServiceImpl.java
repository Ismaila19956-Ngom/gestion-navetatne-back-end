package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.beans.ExcelBean;
import com.khoutech.openexcel.beans.ExcelBeanBuilder;
import com.khoutech.openexcel.models.ExcelContentType;
import com.khoutech.openexcel.services.WorkbookService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.StructureProjectEntity;
import com.webgram.dgpsn.entities.enums.StructureProjectType;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PartnerProjectMapper;
import com.webgram.dgpsn.models.StructureProjectDTO;
import com.webgram.dgpsn.models.responses.CompletionRateResponse;
import com.webgram.dgpsn.models.responses.ProblemeInformationDTO;
import com.webgram.dgpsn.models.responses.ProjectInformationDTO;
import com.webgram.dgpsn.models.responses.ReportingByStructureDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.CompletionRateService;
import com.webgram.dgpsn.services.StructureProjectService;
import com.webgram.dgpsn.services.modelExcel.PartnerProjectExcelDTO;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StructureProjectServiceImpl implements StructureProjectService {
    private final StructureProjectRepository partnerProjetRepository;
    private final PartnerProjectMapper partnerProjectMapper;

    private final WorkbookService workbookService;
    private final CompletionRateService completionRateService;

    private final ManagementUnitRepository managementUnitRepository;
    private final HistoryFlagRepository historyFlagRepository;
    private final HistoryStatusRepository historyStatusRepository;
    private final IssueLogRepository issueLogRepository;
    private final RecommendationRepository recommendationRepository;
    private final CompletionRateRepository completionRateRepository;

    @Override
    @Journal(actionType = ActionType.ADD_STRUCTURE_TUTELLE_EXECUTION)
    public StructureProjectDTO create(StructureProjectDTO partnerProjetDTO) {
         StructureProjectEntity savedPartnerProjet = partnerProjetRepository.save(partnerProjectMapper.asEntity(partnerProjetDTO));

        log.info("PartnerProjet successfully added {}", savedPartnerProjet);

        return partnerProjectMapper.asDto(savedPartnerProjet);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_STRUCTURE_TUTELLE_EXECUTION)
    public StructureProjectDTO update(StructureProjectDTO partnerProjetDTO) {
        try{
            if(partnerProjetRepository.existsById(partnerProjetDTO.getId())) {
                var partnerProjet = partnerProjectMapper.asEntity(partnerProjetDTO);

                var updatedPartnerProjet = partnerProjectMapper.asDto(partnerProjetRepository.save(partnerProjet));

                log.info("PartnerProjet successfully updated {} ", partnerProjet.getId());

                return updatedPartnerProjet;
            } else {
                throw new ResourceNotFoundException("PartnerProjet", partnerProjetDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("PartnerProjet", partnerProjetDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_STRUCTURE_TUTELLE_EXECUTION)
    public StructureProjectDTO read(Long partnerProjetId) {
        var partnerProjet = partnerProjetRepository
                .findById(partnerProjetId)
                .orElseThrow(()-> new ResourceNotFoundException("PartnerProjet", partnerProjetId));

        log.info("reading partnerProjet id {}", partnerProjetId);

        return partnerProjectMapper.asDto(partnerProjet);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_STRUCTURE_TUTELLE_EXECUTION)
    public void delete(Long natureId) {
        try {
            partnerProjetRepository.deleteById(natureId);
            log.info("The partnerProjet id {} is deleted", natureId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("PartnerProjet", natureId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_STRUCTURE_TUTELLE_EXECUTION)
    public Page<StructureProjectDTO> readAll(
            Pageable pageable,
            Double montant,
            Long projetId,
            Long structureId,
            TypeStructure typeStructure,
            StructureProjectType structureProjectType,
            List<StructureProjectType> structureProjectTypeList,
            String sortBy,
            Boolean ascending,
            String startDate,
            String endDate
    ) throws ParseException {
        return partnerProjetRepository
                .readAllByFiltering(pageable, montant, projetId, structureId, startDate,endDate,sortBy,ascending ,typeStructure, structureProjectType, structureProjectTypeList)
                .map(partnerProjectMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_STRUCTURE_TUTELLE_EXECUTION)
    public void importPartener(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<PartnerProjectExcelDTO> partnerProjectExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, PartnerProjectExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<PartnerProjectExcelDTO> partnerProjectExcelDTOS = partnerProjectExcelDTOExcelBean.parse();

            var projet = managementUnitRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));

            List<StructureProjectEntity> partnerProjets = partnerProjectExcelDTOS.stream()
                    .map(partnerProjectMapper::asEntity)
                    .map(partnerProjet -> partnerProjet.setProjet(projet))
                    .collect(Collectors.toList());

            partnerProjetRepository.saveAll(partnerProjets);

            log.info("importPartener end ok");
            log.trace("importPartener end ok - projects: {}", partnerProjets);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_STRUCTURE_TUTELLE_EXECUTION_TO_EXCEL)
    public void exportPartener(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(PartnerProjectExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<PartnerProjectExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<PartnerProjectExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var partenerProject = partnerProjetRepository
                .findAll().stream().map(partnerProjectMapper::asExcelDto);

        try {
            beanToCsv.write(partenerProject);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }

    @Override
    public void linkManyStructure(List<StructureProjectDTO> partnerProjet) {
        var manyStructureEntities = partnerProjet.stream()
                .map(partnerProjectMapper::asEntity)
                .collect(Collectors.toList());
        partnerProjetRepository.saveAll(manyStructureEntities);

        log.info("PartnerProjet successfully added {}", manyStructureEntities);
    }

    @Override
    public List<ReportingByStructureDTO> getReportingByStructrucure(Long structureId) {
        var programmesByStructures = partnerProjetRepository.getProgrammeByStructrucure(structureId);
//        log.info("progammes {}", programmesByStructures.size());
        List<ReportingByStructureDTO> reportingList = new ArrayList<>();
        programmesByStructures.forEach(program -> {
            var report = ReportingByStructureDTO.builder()
                    .programme(program.getName())
                    .projectInformationDTOList(getProjecInformatons(program.getId(), structureId))
                    .build();
            reportingList.add(report);
        });
//        log.info("report {}", reportingList.size());
        return reportingList;
    }

    public List<ProjectInformationDTO> getProjecInformatons(Long programId, Long structureId) {
//        LocalDate currentDate = LocalDate.now();
        LocalDate currentDate = LocalDate.of(2024, 1, 1);
        List<ProjectInformationDTO>  projectInformationDTOList = new ArrayList<>();
        var projects = partnerProjetRepository.getProjectByStructrucureAndProgramme(structureId, programId);

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
