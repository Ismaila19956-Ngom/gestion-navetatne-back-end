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
import com.webgram.dgpsn.mappers.SpecificNatureMapper;
import com.webgram.dgpsn.models.SpecificNatureDTO;
import com.webgram.dgpsn.repositories.SpecificNatureRepository;
import com.webgram.dgpsn.services.SpecificNatureService;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SpecificNatureServiceImpl implements SpecificNatureService {
    private final SpecificNatureRepository specificNatureRepository;
    private final SpecificNatureMapper specificNatureMapper;


    private final String SPECIFIC_NATURE_IDENTIFIER_NOT_FOUND_MESSAGE = "In valide id specificNature {} ";


    @Override
    public SpecificNatureDTO createSpecificNature(SpecificNatureDTO specificNatureDTO) {
        var specificNatureEntity = specificNatureMapper.asEntity(specificNatureDTO);
        var specificNatureCreated = specificNatureRepository.save(specificNatureEntity);
        log.info("create specificNature ok id {}", specificNatureCreated.getId());
        log.trace("create specificNature ok  {}", specificNatureCreated);
        return specificNatureMapper.asDto(specificNatureCreated);

    }

    @Override
    public SpecificNatureDTO updateSpecificNature(SpecificNatureDTO specificNatureDTO) {
        if(!specificNatureRepository.existsById(specificNatureDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(SPECIFIC_NATURE_IDENTIFIER_NOT_FOUND_MESSAGE, specificNatureDTO.getId()));
        }
        var updatedSpecificNature = specificNatureRepository.save(specificNatureMapper.asEntity(specificNatureDTO));
        log.info("update specificNature ok id {}", updatedSpecificNature.getId());
        log.trace("update specificNature ok  {}", updatedSpecificNature);
        return specificNatureMapper.asDto(updatedSpecificNature);
    }

    @Override
    public SpecificNatureDTO readSpecificNature(Long id) {
        var specificNature = specificNatureRepository.findById(id)
                .map(specificNatureMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SPECIFIC_NATURE_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read specificNature end ok - Id: {}", id);
        log.trace("read specificNature end ok - SpecificNature: {}", specificNature);
        return specificNature;
    }

    @Override
    public SpecificNatureDTO readSpecificNatureByCode(String code) {
        var specificNature = specificNatureRepository.findByCode(code)
                .map(specificNatureMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SPECIFIC_NATURE_IDENTIFIER_NOT_FOUND_MESSAGE, code)));
        log.info("read specificNature end ok - Code: {}", code);
        log.trace("read specificNature end ok - specificNature: {}", specificNature);
        return specificNature;
    }

    @Override
    public void deleteSpecificNature(Long id) {
        if(!specificNatureRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(SPECIFIC_NATURE_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        specificNatureRepository.deleteById(id);
        log.info("delete specificNature ok id {}", id);
    }

    @Override
    public Page<SpecificNatureDTO> readAllSpecificNature(Pageable pageable, String code, String libelle, Long natureId) {
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("libelle").ascending());
        var specificNatures = specificNatureRepository.readAllByFilters(pageRequest, code, libelle, natureId)
                .map(specificNatureMapper::asDto);
        log.trace("list specificNature get ok {}", specificNatures);
        return specificNatures;
    }


}
