package com.webgram.dgpsn.services.Impl;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PlanComptableElementMapper;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import com.webgram.dgpsn.repositories.PlanComptableElementRepository;
import com.webgram.dgpsn.services.PlanComptableElementService;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Transactional @RequiredArgsConstructor @Slf4j
public class PlanComptableElementServiceImpl implements PlanComptableElementService {
    private final PlanComptableElementRepository repository;
    private final PlanComptableElementMapper mapper;

    @Override
    public PlanComptableElementDTO create(PlanComptableElementDTO dto) {
        var entity = repository.save(mapper.asEntity(dto));
        log.info("PlanComptableElement successfully added {}", entity.getId());
        return mapper.asDto(entity);
    }

    @Override
    public PlanComptableElementDTO update(PlanComptableElementDTO dto) {
        var entity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("PlanComptableElement", dto.getId()));
        mapper.asEntity(dto);
        var updatedEntity = repository.save(entity);
        log.info("PlanComptableElement successfully updated {} ", updatedEntity.getId());
        return mapper.asDto(updatedEntity);
    }

    @Override
    public PlanComptableElementDTO read(Long elementId) {
        var entity = repository
                .findById(elementId)
                .orElseThrow(()-> new ResourceNotFoundException("PlanComptableElement", elementId));
        log.info("reading PlanComptableElement id {}", elementId);
        return mapper.asDto(entity);
    }

    @Override
    public void delete(Long elementId) {
        try {
            repository.deleteById(elementId);
            log.info("The PlanComptableElement id {} is deleted", elementId);
        } catch (IllegalArgumentException ex) {
            log.info("The given id to delete must not be null");
        }
    }

    @Override
    public Page<PlanComptableElementDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            String code,
            String libelle,
            TypePlanComptable type,
            String sortBy,
            Boolean ascending
    ) {
        return repository
                .readAllByFiltering(pageable, idsToIgnore, code, libelle, type, sortBy, ascending)
                .map(mapper::asDto);
    }
}