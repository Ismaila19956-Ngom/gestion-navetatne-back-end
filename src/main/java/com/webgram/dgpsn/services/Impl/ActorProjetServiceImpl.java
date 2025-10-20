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
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.ActorProjetEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ActorProjetMapper;
import com.webgram.dgpsn.models.ActorProjetDTO;
import com.webgram.dgpsn.repositories.ActorProjetRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.ActorProjetService;
import com.webgram.dgpsn.services.modelExcel.ActorProjectExcelDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ActorProjetServiceImpl implements ActorProjetService {
    private final ActorProjetRepository actorProjetRepository;
    private final ActorProjetMapper actorProjetMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType=ActionType.ADD_ACTEUR)
    public ActorProjetDTO create(ActorProjetDTO actorProjet) {
         var savedActorProjet = actorProjetRepository
                 .save(actorProjetMapper.asEntity(actorProjet));

        log.info("actorProject successfully added {}", savedActorProjet);

        return actorProjetMapper.asDto(savedActorProjet);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_ACTEUR)
    public ActorProjetDTO update(ActorProjetDTO actorProjet) {
        var actorProjectEntity = actorProjetMapper.asEntity(actorProjet);

        var updatedActorProject = actorProjetMapper.asDto(actorProjetRepository.save(actorProjectEntity));

        log.info("actorProject successfully updated {} ", updatedActorProject.getId());

        return updatedActorProject;
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public ActorProjetDTO read(Long actorProjectId) {
        var actorProjet = actorProjetRepository
                .findById(actorProjectId)
                .orElseThrow(()-> new ResourceNotFoundException("ActorProjet", actorProjectId));

        log.info("reading actorProject id {}", actorProjectId);

        return actorProjetMapper.asDto(actorProjet);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_ACTEUR)
    public void delete(Long actorProjetId) {
        try {
            actorProjetRepository.deleteById(actorProjetId);
            log.info("The actorProject id {} is deleted", actorProjetId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_ACTEUR)
    public Page<ActorProjetDTO> readAll(Pageable pageable, Long projectId, Long agentId, Long roleId) {
        return actorProjetRepository
                .readAllByFiltering(pageable, projectId, agentId, roleId)
                .map(actorProjetMapper::asDto);
    }

    @Override
    @Journal(actionType = ActionType.IMPORT_ACTEUR)
    public void importActor(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<ActorProjectExcelDTO> actorProjectExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, ActorProjectExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<ActorProjectExcelDTO> actorProjectExcelDTOS = actorProjectExcelDTOExcelBean.parse();

            var projet = managementUnitRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));

            List<ActorProjetEntity> actorProjets = actorProjectExcelDTOS.stream()
                    .map(actorProjetMapper::asEntity)
                    .map(actorProjet -> actorProjet.setProjet(projet))
                    .collect(Collectors.toList());

            actorProjetRepository.saveAll(actorProjets);

            log.info("importActor end ok");
            log.trace("importActor end ok - projects: {}", actorProjets);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType = ActionType.EXPORT_ACTEUR_TO_EXCEL)
    public void exportActor(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(ActorProjectExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<ActorProjectExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ActorProjectExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var actorProject = actorProjetRepository
                .findAll().stream().map(actorProjetMapper::asExcelDto);

        try {
            beanToCsv.write(actorProject);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }
}
