package sn.naavetane.backend.services.Impl;





import sn.naavetane.backend.entities.StructureEntity;
import sn.naavetane.backend.entities.enums.TypeStructure;
import sn.naavetane.backend.exceptions.ResourceNotFoundException;
import sn.naavetane.backend.mappers.StructureMapper;
import sn.naavetane.backend.models.StructureDTO;
import sn.naavetane.backend.repositories.StructureRepository;
import sn.naavetane.backend.services.StructureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StructureServiceImpl implements StructureService {
    private final StructureRepository structureRepository;
    private final StructureMapper structureMapper;

    private String STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id structure: {}";

    @Override
    public StructureDTO createStructure(StructureDTO structureDTO) {
        var savedStructure = structureRepository.save(structureMapper.asEntity(structureDTO));

        log.info("Structure successfully added {}", savedStructure);

        return structureMapper.asDto(savedStructure);
    }

    @Override
    public StructureDTO updateStructure(StructureDTO structureDTO) {
        if(!structureRepository.existsById(structureDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(STRUCTURE_IDENTIFIER_NOT_FOUND_MESSAGE, structureDTO.getId()));
        }

        var structure = structureMapper.asEntity(structureDTO);

        var updatedStructure = structureMapper.asDto(structureRepository.save(structure));

        log.info("Structure successfully updated {} ", updatedStructure.getId());

        return updatedStructure;
    }

    @Override
    public StructureDTO readStructure(Long id) {
        var structure = structureRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Structure", id));

        log.info("reading structure id {}", id);

        return structureMapper.asDto(structure);
    }

    @Override
    public void deleteStructure(Long id) {
        try {
            structureRepository.deleteById(id);
            log.info("The structure id {} is deleted", id);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<StructureDTO> readAllStructure(Pageable pageable, String code, String nom, TypeStructure typeStructure, List<Long> idsToIgnore, List<TypeStructure> structureTypeList, String sortBy, Boolean ascending) {
        return structureRepository
                .readAllByFiltering(pageable, code, nom, typeStructure, idsToIgnore, structureTypeList, sortBy, ascending)
                .map(structureMapper::asDto);
    }

    @Override
    public StructureDTO readStructure(String code) {
        var structure = structureRepository
                .findByCode(code)
                .orElseThrow(()-> new ResourceNotFoundException("Aucune structure trouvée pour ce code", code));

        log.info("reading structure code {}", code);

        return structureMapper.asDto(structure);
    }


}

