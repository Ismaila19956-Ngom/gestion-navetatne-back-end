package com.webgram.dgpsn.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.MediathequeEntity;
import com.webgram.dgpsn.entities.enums.MediathequeType;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.MediathequeMapper;
import com.webgram.dgpsn.models.DownloadFile;
import com.webgram.dgpsn.models.MediathequeDTO;
import com.webgram.dgpsn.properties.DocumentProperties;
import com.webgram.dgpsn.repositories.MediathequeRepository;
import com.webgram.dgpsn.services.DataStorageService;
import com.webgram.dgpsn.services.MediathequeService;
import com.webgram.dgpsn.services.utils.DownloadFileUtils;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MediathequeServiceImpl implements MediathequeService {
    private final MediathequeRepository mediathequeRepository;
    private final MediathequeMapper mediathequeMapper;

    private String MEDIATHEQUE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id mediatheque: {}";

    static final String INVALID_EXTENSION_MESSAGE = "File: {0} does not match expected extension: {1}";

    static final String DOCUMENT_ROOT_DIRECTORY = "documents";

    static final String DOCUMENT = "image-";

    final DocumentProperties documentProperties;

    final DataStorageService dataStorageService;

    private final ObjectMapper objectMapper;

    @Override
    @Journal(actionType = ActionType.ADD_MEDIATHEQUE)
    public MediathequeDTO create(MultipartFile file, String mediatheque) throws IOException {
        var mediathequeDTO = objectMapper.readValue(mediatheque, MediathequeDTO.class);
         var savedMediatheque = mediathequeRepository.save(mediathequeMapper.asEntity(mediathequeDTO));
        if(Objects.nonNull(file)){
            addFile(savedMediatheque.getId(), file);
        }

        log.info("Mediatheque successfully added {}", savedMediatheque);

        return mediathequeMapper.asDto(savedMediatheque);
    }

    @Override
    @Journal(actionType = ActionType.UPDATE_MEDIATHEQUE)
    public MediathequeDTO update(MultipartFile file, MediathequeDTO mediathequeDTO) throws IOException {
        try{
            System.out.println("id "+ mediathequeDTO.getId());
            if(mediathequeRepository.existsById(mediathequeDTO.getId())) {
                var madiatheque = mediathequeMapper.asEntity(mediathequeDTO);
                if("IMAGE".equals(mediathequeDTO.getMediathequeType())){
                    madiatheque.setSrc(mediathequeRepository.findById(mediathequeDTO.getId()).get().getSrc());
                }
                var updatedMadiatheque = mediathequeMapper.asDto(mediathequeRepository.save(madiatheque));
                if(Objects.nonNull(file)){
                    addFile(updatedMadiatheque.getId(), file);
                }
                log.info("Madiatheque successfully updated {} ", updatedMadiatheque.getId());

                return updatedMadiatheque;
            } else {
                throw new ResourceNotFoundException("Mediatheque", mediathequeDTO.getId());
            }
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Mediatheque", mediathequeDTO.getId());
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_MEDIATHEQUE)
    public MediathequeDTO read(Long mediathequeId) {
        var mediatheque = mediathequeRepository
                .findById(mediathequeId)
                .orElseThrow(()-> new ResourceNotFoundException("Mediatheque", mediathequeId));

        log.info("reading mediatheque id {}", mediatheque);

        return mediathequeMapper.asDto(mediatheque);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_MEDIATHEQUE)
    public void delete(Long mediathequeId) {
        try {
            mediathequeRepository.deleteById(mediathequeId);
            log.info("The mediatheque id {} is deleted", mediathequeId);
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException("Mediatheque", mediathequeId);
        }
    }

    @Override
    @Journal(actionType = ActionType.READ_MEDIATHEQUE)
    public Page<MediathequeDTO> readAll(
            Pageable pageable,
            String libelle,
            String date,
            MediathequeType mediathequeType,
            Long projetId,
            Long entrepriseId
    ) throws ParseException {
        return mediathequeRepository
                .readAllByFilters(pageable, libelle, date, mediathequeType, projetId,entrepriseId)
                .map(mediathequeMapper::asDto);
    }


    @Override
    public DownloadFile readFile(Long id) {

        MediathequeDTO mediatheque = read(id);

        /* Getting downloadFile */
        DownloadFile downloadFile = DownloadFileUtils.generateDownloadFile(mediatheque.getSrc());

        log.info("readFile end ok - mediathequeId: {}", id);
        log.trace("readFile end ok - downloadFile: {}", downloadFile);

        return downloadFile;
    }

    public MediathequeDTO addFile(Long id, MultipartFile file) {

        /* Checking file extension */
        if (documentProperties.getAcceptFileExtensions().contains(FilenameUtils.getExtension(file.getOriginalFilename()))) {

            try(var fileInputStream = file.getInputStream()) {

                MediathequeEntity mediatheque = mediathequeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(MEDIATHEQUE_IDENTIFIER_NOT_FOUND_MESSAGE, id)));


                /* Storing  file document */
                mediatheque.setSrc(dataStorageService.storeFile(DOCUMENT_ROOT_DIRECTORY, DOCUMENT+mediatheque.getId(), FilenameUtils.getExtension(file.getOriginalFilename()), fileInputStream));

                MediathequeDTO mediathequeUpdated = mediathequeMapper.asDto(mediathequeRepository.save(mediatheque));

                log.info("addFile end ok - mediathequeId: {}", mediathequeUpdated.getId());
                log.trace("addFile end ok - mediatheque: {}", mediathequeUpdated);

                return mediathequeUpdated;

            } catch (IOException e) {
                log.error(MessageFormat.format("An error occurred with file: {0}", file.getOriginalFilename()), e);
                throw new ResourceNotFoundException(MessageFormat.format(MEDIATHEQUE_IDENTIFIER_NOT_FOUND_MESSAGE, id));
            }

        } else {
            throw new InvalidParameterException(MessageFormat.format(INVALID_EXTENSION_MESSAGE, file.getOriginalFilename(), documentProperties.getAcceptFileExtensions()));
        }
    }
}
