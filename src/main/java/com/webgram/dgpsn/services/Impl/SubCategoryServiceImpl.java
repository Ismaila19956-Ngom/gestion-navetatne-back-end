package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.SubCategoryMapper;
import com.webgram.dgpsn.models.SubCategoryDTO;
import com.webgram.dgpsn.repositories.SubCategoryRepository;
import com.webgram.dgpsn.services.SubCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;


    private final String SUB_CATEGORY_IDENTIFIER_NOT_FOUND_MESSAGE = "In valide id subCategory {} ";


    @Override
    public SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO) {
        var subCategoryEntity = subCategoryMapper.asEntity(subCategoryDTO);
        var subCategoryCreated = subCategoryRepository.save(subCategoryEntity);
        log.info("create subCategory ok id {}", subCategoryCreated.getId());
        log.trace("create subCategory ok  {}", subCategoryCreated);
        return subCategoryMapper.asDto(subCategoryCreated);

    }

    @Override
    public SubCategoryDTO updateSubCategory(SubCategoryDTO subCategoryDTO) {
        if(!subCategoryRepository.existsById(subCategoryDTO.getId())){
            throw new ResourceNotFoundException(MessageFormat.format(SUB_CATEGORY_IDENTIFIER_NOT_FOUND_MESSAGE, subCategoryDTO.getId()));
        }
        var updatedsubCategory = subCategoryRepository.save(subCategoryMapper.asEntity(subCategoryDTO));
        log.info("update subCategory ok id {}", updatedsubCategory.getId());
        log.trace("update subCategory ok  {}", updatedsubCategory);
        return subCategoryMapper.asDto(updatedsubCategory);
    }

    @Override
    public SubCategoryDTO readSubCategory(Long id) {
        var subCategory = subCategoryRepository.findById(id)
                .map(subCategoryMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SUB_CATEGORY_IDENTIFIER_NOT_FOUND_MESSAGE, id)));
        log.info("read subCategory end ok - Id: {}", id);
        log.trace("read subCategory end ok - subCategory: {}", subCategory);
        return subCategory;
    }

    @Override
    public SubCategoryDTO readSubCategoryByCode(String code) {
        var subCategory = subCategoryRepository.findByCode(code)
                .map(subCategoryMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SUB_CATEGORY_IDENTIFIER_NOT_FOUND_MESSAGE, code)));
        log.info("read subCategory end ok - Code: {}", code);
        log.trace("read subCategory end ok - subCategory: {}", subCategory);
        return subCategory;
    }

    @Override
    public void deleteSubCategory(Long id) {
        if(!subCategoryRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(SUB_CATEGORY_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        subCategoryRepository.deleteById(id);
        log.info("delete subCategory ok id {}", id);
    }

    @Override
    public Page<SubCategoryDTO> readAllSubCategory(Pageable pageable, String code, String libelle) {
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("libelle").ascending());
        var subCategories = subCategoryRepository.readAllByFilters(pageRequest, code, libelle)
                .map(subCategoryMapper::asDto);
        log.trace("list subCategory get ok {}", subCategories);
        log.info("list subCategory get ok {}", subCategories);
        return subCategories;
    }


}
