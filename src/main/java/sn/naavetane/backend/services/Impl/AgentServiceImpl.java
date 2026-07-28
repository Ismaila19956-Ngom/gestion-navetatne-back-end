package sn.naavetane.backend.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;











import sn.naavetane.backend.entities.AgentEntity;
import sn.naavetane.backend.entities.enums.SituationMatrimoniale;
import sn.naavetane.backend.entities.enums.TypeStructure;
import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.AgentMapper;

import sn.naavetane.backend.models.AgentDTO;

import sn.naavetane.backend.models.DownloadFile;
import sn.naavetane.backend.properties.DocumentProperties;
import sn.naavetane.backend.repositories.AgentRepository;
import sn.naavetane.backend.repositories.UserRepository;
import sn.naavetane.backend.services.AgentService;

import sn.naavetane.backend.services.DataStorageService;


import sn.naavetane.backend.services.utils.DownloadFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;


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
    

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "agent-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;

    private String AGENT_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id agent: {}";

    private final UserRepository userRepository;
    
    

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
            Boolean ascending,
            SituationMatrimoniale situationMatrimoniale
    ) {
        return agentRepository
                .readAllByFiltering(pageable, idsToIgnore, typeStructure, nom, prenom, adresse, email, telephone,
                        dateCreation, structureId, fonctionId, directionId, sortBy, ascending, situationMatrimoniale)
                .map(agentMapper::asDto);

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



}





