package com.webgram.dgpsn.services.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SubSectorMapper;
import com.webgram.dgpsn.models.SubSectorDTO;
import com.webgram.dgpsn.repositories.SubSectorRepository;
import com.webgram.dgpsn.services.SubSectorService;
import java.text.MessageFormat;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SubSectorServiceImpl implements SubSectorService {
    private final SubSectorRepository subSectorRepository;
    private final SubSectorMapper subSectorMapper;


   private final String SUB_SECTOR_IDENTIFIER_NOT_FOUND_MESSAGE = "In valide id subSector {} ";


    @Override
    public SubSectorDTO createSubSector(SubSectorDTO subSectorDTO) {
        var subSectorEntity = subSectorMapper.asEntity(subSectorDTO);
        var subSectorCreated = subSectorRepository.save(subSectorEntity);
        log.info("create subSector ok id {}", subSectorCreated.getId());
        log.trace("create subSector ok  {}", subSectorCreated);
        return subSectorMapper.asDto(subSectorCreated);

    }

    @Override
    public SubSectorDTO updateSubSector(SubSectorDTO subSectorDTO) {
        if(!subSectorRepository.existsById(subSectorDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(SUB_SECTOR_IDENTIFIER_NOT_FOUND_MESSAGE, subSectorDTO.getId()));
        }
        var updatedSubSector = subSectorRepository.save(subSectorMapper.asEntity(subSectorDTO));
        log.info("update subSector ok id {}", updatedSubSector.getId());
        log.trace("update subSector ok  {}", updatedSubSector);
        return subSectorMapper.asDto(updatedSubSector);
    }

    @Override
    public SubSectorDTO readSubSector(Long id) {
        var subSector = subSectorRepository.findById(id)
                .map(subSectorMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SUB_SECTOR_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read subSector end ok - Id: {}", id);
        log.trace("read subSector end ok - SubSector: {}", subSector);
        return subSector;
    }

    @Override
    public SubSectorDTO readSubSectorByCode(String code) {
        var subSector = subSectorRepository.findByCode(code)
                .map(subSectorMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SUB_SECTOR_IDENTIFIER_NOT_FOUND_MESSAGE, code)));
        log.info("read subSector end ok - Code: {}", code);
        log.trace("read subSector end ok - subSector: {}", subSector);
        return subSector;
    }

    @Override
    public void deleteSubSector(Long id) {
        if(!subSectorRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(SUB_SECTOR_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        subSectorRepository.deleteById(id);
        log.info("delete subSector ok id {}", id);
    }

    @Override
    public Page<SubSectorDTO> readAllSubSector(Pageable pageable, String code, String libelle, Long sectorId) {
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("libelle").ascending());
        var subSectors = subSectorRepository.readAllByFilters(pageRequest, code, libelle, sectorId)
                .map(subSectorMapper::asDto);
        log.trace("list subSector get ok {}", subSectors);
        return subSectors;
    }


}
