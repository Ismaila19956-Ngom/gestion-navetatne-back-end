package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.enums.CategoryDocument;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CategorieDocumentMapper;
import com.webgram.dgpsn.models.CategorieDocumentDTO;
import com.webgram.dgpsn.repositories.CategorieDocumentRepository;
import com.webgram.dgpsn.services.CategorieDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategorieDocumentServiceImpl implements CategorieDocumentService {

    final private CategorieDocumentMapper categorieDocumentMapper;
    final private CategorieDocumentRepository categorieDocumentRepository;

    @Override
    public CategorieDocumentDTO create(CategorieDocumentDTO categorieDocumentDTO) {
        var documentEntity = categorieDocumentMapper.asEntity(categorieDocumentDTO);
        var label = categorieDocumentRepository.save(documentEntity);

        log.info("label successfully added {}", label.getId());

        return categorieDocumentMapper.asDto(label);
    }

    @Override
    public CategorieDocumentDTO update(CategorieDocumentDTO categorieDocumentDTO) {
        var label = categorieDocumentMapper.asEntity(categorieDocumentDTO);

        var updatedLabel = categorieDocumentMapper.asDto(categorieDocumentRepository.save(label));

        log.info("label successfully updated {} ", updatedLabel.getId());

        return updatedLabel;
    }

    @Override
    public CategorieDocumentDTO read(Long docId) {
        var label = categorieDocumentRepository
                .findById(docId)
                .orElseThrow(()-> new ResourceNotFoundException("Label", docId));

        log.info("reading label id {}", docId);

        return categorieDocumentMapper.asDto(label);
    }

    @Override
    public void delete(Long docId) {
        try {
            categorieDocumentRepository.deleteById(docId);
            log.info("The label id {} is deleted", docId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<CategorieDocumentDTO> readAll(Pageable pageable, CategoryDocument categoryDocument) {
        return categorieDocumentRepository
                .readByFiltering(pageable, categoryDocument)
                .map(categorieDocumentMapper::asDto);
    }
}
