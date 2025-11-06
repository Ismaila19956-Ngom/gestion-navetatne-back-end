package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.AgentMapper;
import com.webgram.dgpsn.models.AgentCountByDirectionDTO;
import com.webgram.dgpsn.models.AgentDTO;
import com.webgram.dgpsn.models.AgentDashboardDTO;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.AgentRepository;
import com.webgram.dgpsn.repositories.UserRepository;
import com.webgram.dgpsn.services.AgentService;
import com.webgram.dgpsn.services.CongeService;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.DirectionService;
import com.webgram.dgpsn.services.modelExcel.AgentExcelDTO;
import com.webgram.dgpsn.services.utils.DownloadFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;
    private final WorkbookService workbookService;

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "agent-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;

    private String AGENT_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id agent: {}";

    private final UserRepository userRepository;
    private final CongeService congeService;
    private final DirectionService directionService;

    @Override
    public AgentDTO create(MultipartFile file, String agent) throws IOException {
        var agentDTO = objectMapper.readValue(agent, AgentDTO.class);
        var savedAgent = agentRepository.save(agentMapper.asEntity(agentDTO));
            if(Objects.nonNull(file)){
                addFile(savedAgent.getId(), file);
            }
        log.info("agent successfully added {}", savedAgent);

        return agentMapper.asDto(savedAgent);
    }

    @Override
    public AgentDTO update(MultipartFile file, AgentDTO agentDTO) throws IOException {
        var agent = agentMapper.asEntity(agentDTO);
        agent.setSrc(agentRepository.findById(agentDTO.getId()).get().getSrc());
        var updatedAgent = agentMapper.asDto(agentRepository.save(agent));
        if(Objects.nonNull(file)){
            addFile(updatedAgent.getId(), file);
        }

        log.info("agent successfully updated {} ", updatedAgent.getId());

        return updatedAgent;
    }

    @Override
    public AgentDTO read(Long agentId) {
        var agent = agentRepository
                .findById(agentId)
                .orElseThrow(()-> new ResourceNotFoundException("Agent", agentId));

        log.info("reading agent id {}", agentId);

        return agentMapper.asDto(agent);
    }

    @Override
    public void delete(Long agentId) {
        try {
            agentRepository.deleteById(agentId);
            log.info("The agent id {} is deleted", agentId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<AgentDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            TypeStructure typeStructure,
            String nom,
            String prenom,
            String adresse,
            String email,
            String telephone,
            Date dateCreation,
            Long structureId,
            Long fonctionId,
            Long directionId,
            String sortBy,
            Boolean ascending
    ) {
        return agentRepository
                .readAllByFiltering(pageable, idsToIgnore, typeStructure, nom, prenom, adresse, email, telephone, dateCreation, structureId, fonctionId, directionId, sortBy, ascending)
                .map(agentMapper::asDto);
    }

    @Override
    public void importAgent(MultipartFile file) {
        try (Workbook workbook = workbookService.findWorkBook(file.getInputStream(), ExcelContentType.fromContentType(file.getContentType()))) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelBean<AgentExcelDTO> agentExcelDTOExcelBean = new ExcelBeanBuilder<>(sheet, AgentExcelDTO.class)
                    .skipLines(0)
                    .build();

            List<AgentExcelDTO> agentExcelDTOS = agentExcelDTOExcelBean.parse();

            List<AgentEntity> agents = agentExcelDTOS.stream()
                    .map(agentMapper::asEntity)
                    .collect(Collectors.toList());

            agentRepository.saveAll(agents);

            log.info("importActor end ok");
            log.trace("importActor end ok - projects: {}", agents);
        } catch (IOException e) {
            log.info("Exceptions handle import file =============== {0}", e);
            throw new InvalidParameterException(MessageFormat.format("Exceptions handle import file ", "Banner", "idexists"));
        }
    }

    @Override
    public void exportAgent(PrintWriter writer) {
        /* Creating header */
        writer.append(Arrays.stream(AgentExcelDTO.class.getDeclaredFields())
                .filter(f -> Objects.nonNull(f.getAnnotation(CsvBindByPosition.class)) && Objects.nonNull(f.getAnnotation(CsvBindByName.class)))
                .sorted(Comparator.comparing(f -> f.getAnnotation(CsvBindByPosition.class).position()))
                .map(f -> f.getAnnotation(CsvBindByName.class).column())
                .collect(Collectors.joining(";"))).append("\n");

        StatefulBeanToCsv<AgentExcelDTO> beanToCsv = new StatefulBeanToCsvBuilder<AgentExcelDTO>(writer)
                .withSeparator(';')
                .withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .build();

        var agents = agentRepository
                .findAll().stream().map(agentMapper::asExcelDto);

        try {
            beanToCsv.write(agents);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("error");
//            throw new ValidateCassetteException("Export error");
        }
    }

    @Override
    public DownloadFile readFile(Long id) {

        AgentDTO agent = read(id);

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(agent.getSrc());

        log.info("readFile end ok - agentId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    public AgentDTO addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                AgentEntity agent = agentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(AGENT_IDENTIFIER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                agent.setSrc(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+agent.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                AgentDTO agentUpdated = agentMapper.asDto(agentRepository.save(agent));

                log.info("addFile end ok - agentId: {}", agent.getId());
                log.trace("addFile end ok - agent: {}", agentUpdated);

                return agentUpdated;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(AGENT_IDENTIFIER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }

    @Override
    public List<AgentDTO> getAgentNotInUsers() {
        var users = userRepository.findAll();
        var agents = agentRepository.findAll()
        .stream()
                .filter(agent -> (users.stream().noneMatch(user -> agent.getId().equals(user.getAgent().getId()))))
                .map(agentMapper::asDto)
                .collect(Collectors.toList());
        return agents;
    }

    @Override
    public AgentDashboardDTO getAgentDashboard() {
        Long totalAgents = agentRepository.count();
        Long totalAgentsEnConges = (long) congeService.readAll().size();
        Long totalAgentsParDirection = agentRepository.countAgentsByDirection()
                .stream()
                .mapToLong(AgentCountByDirectionDTO::getTotalAgents)
                .sum();
        return new AgentDashboardDTO(totalAgents, totalAgentsEnConges, totalAgentsParDirection);
    }

    @Override
    public List<AgentCountByDirectionDTO> AgentCountByDirection() {
        return agentRepository.countAgentsByDirection();
    }

}
