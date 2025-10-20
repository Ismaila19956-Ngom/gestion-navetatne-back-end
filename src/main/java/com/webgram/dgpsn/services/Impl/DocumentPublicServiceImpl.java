package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.entities.StructureEntity;
import com.webgram.dgpsn.entities.SubSectorEntity;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.DocumentPublicMapper;
import com.webgram.dgpsn.models.DocumentPublicDTO;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.DocumentPublicRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.DocumentPublicService;
import com.webgram.dgpsn.services.utils.DownloadFileUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentPublicServiceImpl implements DocumentPublicService {
    private final DocumentPublicRepository documentPublicRepository;
    private final DocumentPublicMapper documentPublicMapper;
    private final ObjectMapper objectMapper;
    private final DocumentProperties documentProperties;
    private final DataStorageService dataStorageService;
    static final String FOLDER_NOT_FOUND_MESSAGE = "[Document] Not found Document {0}";

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "document-public-";

    @Override
    public DocumentPublicDTO create(String documentPublicDTO, MultipartFile file) throws IOException {
        var documentDTO = objectMapper.readValue(documentPublicDTO, DocumentPublicDTO.class);

        var document = documentPublicMapper.asEntity(documentDTO);
        document.setPublish(false);

        var createdDocument = documentPublicRepository.save(document);

        if(Objects.nonNull(file)) {
            addFile(createdDocument.getId(), file);
        }

        log.info("createdDocument end ok - create createdDocumentId: {}", createdDocument.getId());
        log.trace("createdDocument end ok - create createdDocument: {}", createdDocument);

        return documentPublicMapper.asDto(createdDocument);
    }

    @Override
    public DocumentPublicDTO update(Long id, DocumentPublicDTO documentPublicDTO) {
        var document = documentPublicMapper.asEntity(documentPublicDTO);
        document.setId(id);
        document.setPublish(false);
        var savedDocument = documentPublicRepository.save(document);
        return documentPublicMapper.asDto(savedDocument);
    }

    @Override
    public DocumentPublicDTO read(Long documentPublicId) {
        return documentPublicMapper.asDto(
                documentPublicRepository.findById(documentPublicId)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Impossible de trouver le document avec id %d", documentPublicId))));
    }

    @Override
    public DownloadFile readFile(Long id) {

        DocumentPublicDTO document = read(id);

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(document.getPath());

        log.info("readFile end ok - documentId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    @Override
    public void delete(Long documentPublicId) {
      try {
          if(!documentPublicRepository.existsById(documentPublicId)) {
              throw new RuntimeException(String.format("Impossible de trouver le document avec id %d", documentPublicId));
          }
          documentPublicRepository.deleteById(documentPublicId);
      } catch (IllegalArgumentException ex) {
          throw new RuntimeException(String.format("Impossible de trouver le document avec id %d", documentPublicId));
      }
    }

    @Override
    public Page<DocumentPublicDTO> readAll(
            Pageable pageable, String titre, String date, String authors,
            String themes, Long documentTypeId, Long folderId, Boolean publish, Long partnerId, Long sectorId)
    {
        var documentPage = documentPublicRepository.readAllByFilters(pageable, titre, date, authors, themes, documentTypeId, folderId, publish);

        var documents = documentPage.getContent();

        if(Objects.nonNull(partnerId)) {
            documents = documents.stream()
                    .filter(documentPublic -> checkPartners(documentPublic.getPartners(), partnerId))
                    .collect(Collectors.toList());
        }
        if(Objects.nonNull(sectorId)) {
            documents = documents.stream()
                    .filter(documentPublic -> checkSectors(documentPublic.getSectors(), sectorId))
                    .collect(Collectors.toList());
        }

        return documentPublicMapper.asPage(documentPage, documents);
    }

    @Override
    public Page<DocumentPublicDTO> readPublishedDocument(Pageable pageable, String titre, String date, String authors, String themes, String documentType, String partner, String sector) {
        var documentPage = documentPublicRepository.filterPublishedDocuments(pageable, titre, date, authors, themes, documentType);

        var documents = documentPage.getContent();

        if(Objects.nonNull(partner)) {
            documents = documents.stream()
                    .filter(documentPublic -> checkPartners(documentPublic.getPartners(), partner))
                    .collect(Collectors.toList());
        }
        if(Objects.nonNull(sector)) {
            documents = documents.stream()
                    .filter(documentPublic -> checkSectors(documentPublic.getSectors(), sector))
                    .collect(Collectors.toList());
        }

        return documentPublicMapper.asPage(documentPage, documents);
    }

    @Override
    public void publishOrUnpublish(Long documentId) {
        var document = documentPublicRepository.findById(documentId);

        if(document.isPresent()) {
            var check = document.get().isPublish() ? false : true;
            document.get().setPublish(check);
        } else {
            throw new RuntimeException(String.format("Un document avec l'id %d n'existe pas", documentId));
        }
    }


    public DocumentPublicDTO addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                var document = documentPublicRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                document.setPath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+document.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                var createdDocument = documentPublicRepository.save(document);

                log.info("addFile end ok - documentId: {}", createdDocument.getId());
                log.trace("addFile end ok - document: {}", createdDocument.getId());

                return documentPublicMapper.asDto(document);

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }

    private boolean checkPartners(Set<StructureEntity> partners, Long partnerId) {
        var check = partners.stream().noneMatch(partner -> partner.getId().equals(partnerId));
        return !check;
    }

    private boolean checkSectors(Set<SubSectorEntity> sectors, Long sectorId) {
        var check = sectors.stream().noneMatch(sector -> sector.getId().equals(sectorId));
        return !check;
    }

    private boolean checkPartners(Set<StructureEntity> partners, String partner) {
        var check = partners.stream().noneMatch(pad -> pad.getCode().equalsIgnoreCase(partner) || pad.getNom().equalsIgnoreCase(partner));
        return !check;
    }

    private boolean checkSectors(Set<SubSectorEntity> sectors, String sector) {
        var check = sectors.stream().noneMatch(sec -> sec.getCode().equalsIgnoreCase(sector) || sec.getLibelle().equalsIgnoreCase(sector));
        return !check;
    }

}
