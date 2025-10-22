package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PreselectionnedFileMapper;
import com.webgram.dgpsn.models.PreselectionnedFileDTO;
import com.webgram.dgpsn.repositories.PreselectionnedFileRepository;
import com.webgram.dgpsn.services.PreselectionnedFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PreselectionnedFileImpl implements PreselectionnedFileService {

    private static final String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "id n'existe pas";
    private  final PreselectionnedFileRepository preselectionnedFileRepository;
    private  final PreselectionnedFileMapper preselectionnedFileMapper;


    @Override
    public List<PreselectionnedFileDTO> createPreselectionnedFile(List<PreselectionnedFileDTO> preselectionnedFileDTO) {
       // preselectionnedFileDTO.setWinner(false);
        var savedPreselectionnedFile = preselectionnedFileRepository.saveAll(preselectionnedFileMapper.parseToEntity(preselectionnedFileDTO));

        log.info("Structure successfully added {}", savedPreselectionnedFile);

        return preselectionnedFileMapper.parse(savedPreselectionnedFile);
    }

    @Override
    public PreselectionnedFileDTO updatePreselectionnedFile(PreselectionnedFileDTO preselectionnedFileDTO) {
        if(!preselectionnedFileRepository.existsById(preselectionnedFileDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, preselectionnedFileDTO.getId()));
        }

        var preselectionnedFile = preselectionnedFileMapper.asEntity(preselectionnedFileDTO);

        var updatedpreselectionnedFile = preselectionnedFileMapper.asDto(preselectionnedFileRepository.save(preselectionnedFile));

        log.info("Structure successfully updated {} ", updatedpreselectionnedFile.getId());

        return updatedpreselectionnedFile;
    }

    @Override
    public PreselectionnedFileDTO readPreselectionnedFile(Long id) {
        var preselectionnedFile = preselectionnedFileRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("le marche n'existe", id));

        log.info("reading Passation de Plan id {}", id);

        return preselectionnedFileMapper.asDto(preselectionnedFile);
    }

    @Override
    public void deletePreselectionnedFile(Long id) {
        try {
            preselectionnedFileRepository.deleteById(id);
            log.info("The plan of passation market id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PreselectionnedFileDTO> readAllPreselectionnedFile(Pageable pageable,Long passationMarketId, String sortBy, Boolean ascending) {
        return preselectionnedFileRepository.readAllByFiltering(pageable,passationMarketId,sortBy,ascending)
                .map(preselectionnedFileMapper::asDto);
    }
}
