package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.ContactRequestEntity;
import com.webgram.dgpsn.entities.enums.Statut;
import com.webgram.dgpsn.entities.enums.TypeDemande;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.ContactRequestMapper;
import com.webgram.dgpsn.models.ContactRequestDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.ContactRequestRepository;
import com.webgram.dgpsn.services.ContactRequestService;
import com.webgram.dgpsn.services.DataStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ContactRequestServiceImpl implements ContactRequestService {

    private static final String MESSAGE_REQUEST_NOT_FOUND = "Aucun demande de contact trouvée avec cet ID";
    private final ContactRequestRepository contactRequestRepository;
    private final ContactRequestMapper contactRequestMapper;
    private final DocumentProperties documentProperties;
    private final DataStorageService dataStorageService;

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";
    static final String DOCUMENT_ROOT_DIRECTORY = "contact-requests";
    static final String DOCUMENT = "request-";
    private static final String REQUEST_NOT_FOUND_MESSAGE = "Invalid contact request ID: {}";
    private static final String FILE_NOT_FOUND_MESSAGE = "No file associated with contact request ID: {}";

    @Override
    public ContactRequestDTO create(MultipartFile file, ContactRequestDTO contactRequestDTO) throws IOException {
        var contactRequestEntity = contactRequestMapper.asEntity(contactRequestDTO);
        contactRequestEntity.setDateCreation(new Date());
        var savedRequest = contactRequestRepository.save(contactRequestEntity);

        if (Objects.nonNull(file)) {
            addFile(savedRequest.getId(), file);
        }

        contactRequestEntity.setStatut(Statut.NOUVELLE);

        log.info("Contact request successfully created: {}", savedRequest.getId());
        return contactRequestMapper.asDto(savedRequest);
    }

    @Override
    public ContactRequestDTO read(Long requestId) {
        var contactRequest = contactRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("ContactRequest", requestId));

        log.info("Reading contact request ID: {}", requestId);
        return contactRequestMapper.asDto(contactRequest);
    }

    @Override
    public Page<ContactRequestDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            TypeDemande requestType,
            String lastName,
            String firstName,
            String email,
            String phone,
            String organization,
            String subject,
            Statut statut,
            Date dateCreation,
            Long serviceId,
            String sortBy,
            Boolean ascending
    ) {
        return contactRequestRepository
                .readAllByFiltering(pageable, idsToIgnore, requestType, lastName, firstName, email, phone, organization, subject, statut, dateCreation, serviceId, sortBy, ascending)
                .map(contactRequestMapper::asDto);
    }

    @Override
    public void delete(Long requestId) {
        try {
            contactRequestRepository.deleteById(requestId);
            log.info("Contact request ID {} deleted", requestId);
        } catch (IllegalArgumentException ex) {
            log.info("The given ID to delete must not be null");
            throw new ResourceNotFoundException(MessageFormat.format(REQUEST_NOT_FOUND_MESSAGE, requestId));
        }
    }

    private ContactRequestDTO addFile(Long id, MultipartFile file) throws IOException {
        log.info("File uploaded");
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            try (var fileInputStream = file.getInputStream()) {
                log.info("File input stream created");
                ContactRequestEntity request = contactRequestRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(REQUEST_NOT_FOUND_MESSAGE, id)));

                request.setFilePath(dataStorageService.storeFileRelativePath(
                        DOCUMENT_ROOT_DIRECTORY,
                        DOCUMENT + request.getId(),
                        FilenameUtils.getExtension(file.getOriginalFilename()),
                        fileInputStream
                ));

                ContactRequestDTO updatedRequest = contactRequestMapper.asDto(contactRequestRepository.save(request));

                log.info("File added to contact request ID: {}", request.getId());
                log.trace("File added - updated request: {}", updatedRequest);

                return updatedRequest;
            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(REQUEST_NOT_FOUND_MESSAGE, id));
            }
        } else {
            throw new InvalidParameterException(MessageFormat.format(
                    INVALID_EXTENSION_MESSAGE,
                    file.getOriginalFilename(),
                    documentProperties.getAcceptFileExtensions()
            ));
        }
    }

    // Méthode pour Chage Statut
    @Override
    public void changeStatut(Long requestId, Statut requestStatut) {
        var contactRequest = contactRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException(MESSAGE_REQUEST_NOT_FOUND));
        contactRequest.setStatut(requestStatut);
        contactRequestRepository.save(contactRequest);
    }

    @Override
    public Resource downloadFile(Long requestId) throws IOException {
        var contactRequest = contactRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("ContactRequest", requestId));

        if (Objects.isNull(contactRequest.getFilePath())) {
            throw new ResourceNotFoundException(MessageFormat.format(FILE_NOT_FOUND_MESSAGE, requestId));
        }

        Resource fileResource = dataStorageService.loadFileRelativePath(contactRequest.getFilePath());
        log.info("File retrieved for contact request ID: {}", requestId);
        return fileResource;
    }
}