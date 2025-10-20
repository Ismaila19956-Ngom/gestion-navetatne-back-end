package com.webgram.dgpsn.services.Impl;

import com.khoutech.openexcel.services.WorkbookService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ParticipantMissionMapper;
import com.webgram.dgpsn.models.ParticipantMissionDTO;
import com.webgram.dgpsn.repositories.AssignmentRepository;
import com.webgram.dgpsn.repositories.ParticipantMissionRepository;
import com.webgram.dgpsn.services.ParticipantMissionService;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ParticipantMissionServiceImpl implements ParticipantMissionService {
    private final ParticipantMissionRepository participantMissionRepository;
    private final ParticipantMissionMapper participantMissionMapper;
    private final AssignmentRepository assignmentRepository;
    private final WorkbookService workbookService;

    @Override
    @Journal(actionType = ActionType.ADD_PARTICIPANT)
    public ParticipantMissionDTO create(ParticipantMissionDTO participantMissionDTO) {
         var savedParticipant= participantMissionRepository
                 .save(participantMissionMapper.asEntity(participantMissionDTO));

        log.info("participantMission successfully added {}", savedParticipant);

        return participantMissionMapper.asDto(savedParticipant);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_PARTICIPANT)
    public ParticipantMissionDTO update(ParticipantMissionDTO participantMissionDTO) {
        var participantEntity = participantMissionMapper.asEntity(participantMissionDTO);

        var updatedParticipant= participantMissionMapper.asDto(participantMissionRepository.save(participantEntity));

        log.info("participantMission successfully updated {} ", updatedParticipant.getId());

        return updatedParticipant;
    }

    @Override
    @Journal(actionType = ActionType.READ_PARTICIPANT)
    public ParticipantMissionDTO read(Long participantId) {
        var participant = participantMissionRepository
                .findById(participantId)
                .orElseThrow(()-> new ResourceNotFoundException("participantMission", participantId));

        log.info("reading participantMission id {}", participant);

        return participantMissionMapper.asDto(participant);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_PARTICIPANT)
    public void delete(Long participantId) {
        try {
            participantMissionRepository.deleteById(participantId);
            log.info("The participantMission id {} is deleted", participantId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_PARTICIPANT)
    public Page<ParticipantMissionDTO> readAll(Pageable pageable, Long assignmentId, Long actorId, Long roleId) {
        return participantMissionRepository
                .readAllByFiltering(pageable, assignmentId,actorId,roleId)
                .map(participantMissionMapper::asDto);
    }

//    @Override
//    @Journal(actionType = ActionType.IMPORT_ACTEUR)
//    public void importActor(MultipartFile file, Long projectId) {
//        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
//            Sheet sheet = workbook.getSheetAt(0);
//            ExcelBean<ActorProjectExcelDTO> actorProjectExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, ActorProjectExcelDTO.class)
//                    .skipLines(0)
//                    .build();
//            List<ActorProjectExcelDTO> actorProjectExcelDTOS = actorProjectExcelDTOExcelBean.parse();
//
//            var projet = managementUnitRepository.findById(projectId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Project avec id {} introuvable"));
//
//            List<ActorProjetEntity> actorProjets = actorProjectExcelDTOS.stream()
//                    .map(actorProjetMapper::asEntity)
//                    .map(actorProjet -> actorProjet.setProjet(projet))
//                    .collect(Collectors.toList());
//
//            actorProjetRepository.saveAll(actorProjets);
//
//            log.info("importActor end ok");
//            log.trace("importActor end ok - projects: {}", actorProjets);
//        } catch (IOException e) {
//            log.info("Exceptions handle import file =============== {0}", e);
//            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
//        }
//    }
//
//    @Override
//    @Journal(actionType = ActionType.EXPORT_ACTEUR_TO_EXCEL)
//    public void exportActor(PrintWriter writer) {
//        /* Creating header */
//        writer.append(Arrays.stream(ActorProjectExcelDTO.class.getDeclaredFields())
//                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
//                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
//                .map(f -> f.getAnnotation(CsvBindByName.class).column())
//                .collect(Collectors.joining(";"))).append("\n");
//
//        StatefulBeanToCsv<ActorProjectExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<ActorProjectExcelDTO>(writer)
//                .withSeparator(';')
//                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
//                .build();
//
//        var actorProject = actorProjetRepository
//                .findAll().stream().map(actorProjetMapper::asExcelDto);
//
//        try {
//            beanToCsv.write(actorProject);
//        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
//            log.error("error");
////            throw new ValidateCassetteException("Export error");
//        }
//    }
}
