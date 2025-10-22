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
import com.webgram.dgpsn.entities.IssueLogEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.IssueLogMapper;
import com.webgram.dgpsn.models.IssueLogDTO;
import com.webgram.dgpsn.repositories.*;
import com.webgram.dgpsn.services.AlerteService;
import com.webgram.dgpsn.services.IssueLogService;
import com.webgram.dgpsn.services.modelExcel.IssueLogExcelDTO;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IssueLogServiceImpl implements IssueLogService {

    private WorkbookService workbookService;
    private final IssueLogRepository issueLogRepository;
    private final IssueLogMapper issueLogMapper;
    private final RiskRepository riskRepository;
    private final StructureRepository structureRepository;
    private final LabelRepository resolveChannelRepository;

    private final AlerteService alerteService;

    @Override
    @Journal(actionType= ActionType.ADD_PROBLEMES_TO_AVANCEMENT)
    public IssueLogDTO create(IssueLogDTO issueLogDTO) {

        var savedIssuelog = saveIssuelog(issueLogDTO);

//        alerteService.generateAlertCreateIssueLog(savedIssuelog);

        log.info("IssueLog {} successfully added", savedIssuelog.getId());

        return savedIssuelog;
    }

    @Override
    @Journal(actionType= ActionType.UPDATE_PROBLEMES_TO_AVANCEMENT)
    public IssueLogDTO update(IssueLogDTO issueLogDTO) {
        try{
            if(issueLogRepository.existsById(issueLogDTO.getId())) {
                var savedIssuelog = saveIssuelog(issueLogDTO);

                log.info("IssueLog successfully updated {} ", savedIssuelog.getId());

                return savedIssuelog;
            } else {
                throw new ResourceNotFoundException("IssueLog", issueLogDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("IssueLog", issueLogDTO.getId());
        }
    }

    @Override
    @Journal(actionType= ActionType.READ_PROBLEMES_TO_AVANCEMENT)
    public IssueLogDTO read(Long issueLogId) {
        var issueLog = issueLogRepository
                .findById(issueLogId)
                .orElseThrow(()-> new ResourceNotFoundException("IssueLog", issueLogId));

        log.info("reading issueLog id {}", issueLogId);

        return issueLogMapper.asDto(issueLog);
    }

    @Override
    @Journal(actionType= ActionType.DELETE_PROBLEMES_TO_AVANCEMENT)
    public void delete(Long issueLogId) {
        try {
            issueLogRepository.deleteById(issueLogId);
            log.info("The issueLog id {} is deleted", issueLogId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("IssueLog", issueLogId);
        }
    }

    @Override
    @Journal(actionType= ActionType.READ_PROBLEMES_TO_AVANCEMENT)
    public Page<IssueLogDTO> readAll(
            Pageable pageable,
            String libelle,
            String description,
            String author,
            Date identificationDate,
            Date deadline,
            Date resolutionDate,
            Long projetId,
            Long  assignmentId,
            Long criticityId,
            Long delayImpactId,
            Long financialImpactId,
            Long statusId,
            Long natureId
    ) {
       return issueLogRepository
                .readAllByFilters(
                        pageable, libelle, description, author, identificationDate, deadline, resolutionDate,
                        projetId,assignmentId, criticityId, delayImpactId, financialImpactId, statusId, natureId)
                .map(issueLogMapper::asDto);

    }



    public void generateAlerte(IssueLogEntity entity) {
     /*   final TypeAlerte typeAlerte = TypeAlerte.CREATION_PROBLEME;
        final var template = templateRepository.findByTypeAlerte(typeAlerte).orElseThrow();
        final var projet = projetRepository.findById(entity.getProjet().getId()).orElseThrow();
        final var criticity = criticityRepository.findById(entity.getCriticity().getId()).orElseThrow();
        String message = null;

        message = template.getMessage();

        message = StringUtils.isNotEmpty(entity.getLibelle())? message.replace("[LIBELLE]", entity.getLibelle()): message.replace("[LIBELLE]", "");
        message = StringUtils.isNotEmpty(entity.getDescription())? message.replace("[DESCRIPTION]", entity.getDescription()): message.replace("[DESCRIPTION]", "");
        message = Objects.nonNull(entity.getIdentificationDate())? message.replace("[DATE_IDENTIFICATION]", entity.getIdentificationDate().toString()): message.replace("[DATE_IDENTIFICATION]", "");
        message = Objects.nonNull(entity.getDeadline())? message.replace("[DATE_ECHEANCE]", entity.getDeadline().toString()): message.replace("[DATE_ECHEANCE]", "");
        message = StringUtils.isNotEmpty(projet.getLibelle())? message.replace("[NOM_PROJET]", projet.getLibelle()): message.replace("[NOM_PROJET]", "");
        message = StringUtils.isNotEmpty(criticity.getLibelle())? message.replace("[CRITICITE]", criticity.getLibelle()): message.replace("[CRITICITE]", "");

        var alerte = AlerteEntity.builder()
                .message(message)
                .date(new Date())
                .read(false)
                .template(template)
                .build();

        var savedAlerte = alerteRepository.save(alerte);

        var profileTemplate =  profileTemplateRepository.findByTemplate(template);

        profileTemplate
                .stream()
                .forEach(profileTemplateEntity -> profileTemplateEntity.setAlerte(savedAlerte));

      */
    }

    @Transactional
    @Journal(actionType= ActionType.ADD_PROBLEMES_TO_AVANCEMENT)
    public IssueLogDTO saveIssuelog(IssueLogDTO issueLogDTO) {
        var issueLog = issueLogMapper.asEntity(issueLogDTO);

        var savedIssueLog = issueLogRepository.save(issueLog);

        return issueLogMapper.asDto(savedIssueLog);
    }

    @Override
    @Journal(actionType= ActionType.IMPORT_PROBLEMES_TO_AVANCEMENT)
    public void importIssuelog(MultipartFile file, Long projectId) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<IssueLogExcelDTO> issuelogExcelDTOBean = new ExcelBeanBuilder<>(sheet, IssueLogExcelDTO.class)
                    .skipLines(0)
                    .build();
            List<IssueLogExcelDTO> issuelogExcelDTOS = issuelogExcelDTOBean.parse();
            List<IssueLogEntity> issuelogs = issuelogExcelDTOS.stream()
                    .map(issuelogExcelDTO -> issuelogExcelDTO.setProjectId(projectId))
                    .map(issueLogMapper::asEntity)
                    .collect(Collectors.toList());
            issueLogRepository.saveAll(issuelogs);
            log.info("importIssuelog end ok");
            log.trace("importIssuelog end ok - issuelog: {}", issuelogs);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    @Journal(actionType= ActionType.EXPORT_PROBLEMES_TO_AVANCEMENT)
    public void export(PrintWriter writer) {

        /* Creating header */
        writer.append(Arrays.stream(IssueLogExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<IssueLogExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<IssueLogExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        List<IssueLogExcelDTO> issuelogExcelDTOS = issueLogRepository.findAll().stream()
                .map(issueLogMapper::asExcelDto)
                .collect(Collectors.toList());

        try {
            beanToCsv.write(issuelogExcelDTOS);
            log.info("export ok");
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
            // throw new ValidateCassetteException("Export error");
        }

    }

    @Override
    public List<IssueLogDTO> readListIssueLog(Long projectId) {
        return issueLogRepository.readListIssueLog(projectId)
                .stream()
                .map(issueLogMapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    public void valid(IssueLogDTO issueLogDTO) {
        log.info("issuelog {}", issueLogDTO.getValidComment());
        var issueLog = issueLogRepository
                .findById(issueLogDTO.getId())
                .orElseThrow(()-> new ResourceNotFoundException("IssueLog", issueLogDTO.getId()));
        issueLog.setValid(!issueLog.getValid());
        issueLog.setValidComment(issueLogDTO.getValidComment());
        issueLogRepository.save(issueLog);
    }

}

