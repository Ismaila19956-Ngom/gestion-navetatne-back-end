package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.DocumentEntity;
import com.webgram.dgpsn.entities.QDocumentEntity;
import com.webgram.dgpsn.entities.enums.CategoryDocument;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.DocumentMapper;
import com.webgram.dgpsn.models.DocumentDto;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.DocumentRepository;
import com.webgram.dgpsn.repositories.EntrepriseRepository;
import com.webgram.dgpsn.repositories.ManagementUnitRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.DocumentService;
import com.webgram.dgpsn.services.utils.DownloadFileUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    static final String FOLDER_NOT_FOUND_MESSAGE = "[Document] Not found Document {0}";

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "document-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;
    private final ManagementUnitRepository managementUnitRepository;
    private final EntrepriseRepository entrepriseRepository;

    private String DOCUMENT_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id document: {}";

    @Override
    @Transactional
    @Journal(actionType = ActionType.ADD_DOCUMENT)
    public DocumentDto createDocument(MultipartFile file, String document) throws IOException {
        var documentDTO = objectMapper.readValue(document, DocumentDto.class);
        var documentEntity = documentMapper.asEntity(documentDTO);
//        if(CategoryDocument.PROGRAMME.equals(documentDTO.getCategory()) || CategoryDocument.PROJECT.equals(documentDTO.getCategory())){
//            var linked = managementUnitRepository.findById(documentDTO.getCategoryId())
//                            .orElseThrow();
//            documentEntity.setCategoryLibelle(linked.getName());
//        }else if(CategoryDocument.ENTREPRISE.equals(documentDTO.getCategory())) {
//            var linked = entrepriseRepository.findById(documentDTO.getCategoryId())
//                    .orElseThrow();
//            documentEntity.setCategoryLibelle(linked.getDenomination());
//        }
        var createdDocument = documentRepository.save(documentEntity);
        if(Objects.nonNull(file)) {
            addFile(createdDocument.getId(), file);
        }
        log.info("createdDocument end ok - create createdDocumentId: {}", createdDocument.getId());
        log.trace("createdDocument end ok - create createdDocument: {}", createdDocument);

        return documentMapper.asDto(createdDocument);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_DOCUMENT)
    public DocumentDto updateDocument(MultipartFile file, DocumentDto documentDTO) {
        if(!documentRepository.existsById(documentDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(DOCUMENT_IDENTIFIER_NOT_FOUND_MESSAGE, documentDTO.getId()));
        }
        var documentEntity = documentMapper.asEntity(documentDTO);
//        if(CategoryDocument.PROGRAMME.equals(documentDTO.getCategory()) || CategoryDocument.PROJECT.equals(documentDTO.getCategory())){
//            var linked = managementUnitRepository.findById(documentDTO.getCategoryId())
//                    .orElseThrow();
//            documentEntity.setCategoryLibelle(linked.getName());
//        }else if(CategoryDocument.ENTREPRISE.equals(documentDTO.getCategory())) {
//            var linked = entrepriseRepository.findById(documentDTO.getCategoryId())
//                    .orElseThrow();
//            documentEntity.setCategoryLibelle(linked.getDenomination());
//        }
        documentEntity.setPath(documentRepository.findById(documentDTO.getId()).get().getPath());
        var updatedDocument = documentRepository.save(documentEntity);

        if(Objects.nonNull(file)){
            addFile(updatedDocument.getId(),file);
        }

        log.info("updatedDocument end ok - updatedDocumentId: {}", updatedDocument.getId());
        log.trace("updatedDocument end ok - updatedDocument: {}", updatedDocument);

        return documentMapper.asDto(updatedDocument);
    }

    @Override
    @Journal(actionType = ActionType.READ_DOCUMENT)
    public DocumentDto readDocument(Long id) {
        var document = documentRepository
                .findById(id)
                .orElseThrow();

        log.info("readDocument end ok - Id: {}", id);
        log.trace("readDocument end ok - document: {}", document);;

        return documentMapper.asDto(document);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_DOCUMENT)
    public void deleteDocument(Long id) {
        if(!documentRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(DOCUMENT_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        documentRepository.deleteById(id);
        log.info("deleteDocument ok id {}", id);
    }

    @Override
    @Journal(actionType = ActionType.READ_DOCUMENT)
    public Page<DocumentDto> readAllDocument(Map<String,String> searchParams, Pageable pageable) throws ParseException {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC,"id"));
        return documentRepository
                .findAll(booleanBuilder, pageRequest)

                .map(documentMapper::asDto);
//
    }

    @Override
    public DownloadFile readFile(Long id) {

        DocumentDto document = readDocument(id);

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(document.getPath());

        log.info("readFile end ok - documentId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    public DocumentDto addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                DocumentEntity documentEntity = documentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                documentEntity.setPath(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+documentEntity.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                DocumentDto documentUpdated = documentMapper.asDto(documentRepository.save(documentEntity));

                log.info("addFile end ok - documentId: {}", documentEntity.getId());
                log.trace("addFile end ok - document: {}", documentUpdated);

                return documentUpdated;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(FOLDER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }
    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder){
        if (Objects.nonNull(searchParams)){
            var qEntity = QDocumentEntity.documentEntity;
            if(searchParams.containsKey("libelle"))
                booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));
            if(searchParams.containsKey("category"))
                booleanBuilder.and(qEntity.category.eq(CategoryDocument.valueOf(searchParams.get("category"))));
            if(searchParams.containsKey("categoryId"))
                booleanBuilder.and(qEntity.categoryId.eq(Long.valueOf(searchParams.get("categoryId"))));
            if(searchParams.containsKey("documentType"))
                booleanBuilder.and(qEntity.documentType.libelle.containsIgnoreCase(searchParams.get("documentType")));
            if(searchParams.containsKey("documentTypeId"))
                booleanBuilder.and(qEntity.documentType.id.eq(Long.valueOf(searchParams.get("documentTypeId"))));

        }

    }

}
