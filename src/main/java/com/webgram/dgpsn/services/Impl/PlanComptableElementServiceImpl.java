package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.exceptions.PlanComptableException;
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
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanComptableElementServiceImpl implements PlanComptableElementService {

    private final PlanComptableElementRepository repository;
    private final PlanComptableElementMapper mapper;

    @Override
    public PlanComptableElementDTO create(PlanComptableElementDTO dto) {
        log.info("Creating PlanComptableElement - type: {}, code: {}", dto.getType(), dto.getCode());

        if (repository.existsByCode(dto.getCode())) {
            throw new PlanComptableException("Un élément avec le code '" + dto.getCode() + "' existe déjà");
        }

        validateAndNormalizePlanHierarchy(dto, null);

        var entity = mapper.asEntity(dto);
        var savedEntity = repository.save(entity);

        log.info("PlanComptableElement created - id: {}, code: {}", savedEntity.getId(), savedEntity.getCode());
        return mapper.asDto(savedEntity);
    }

    @Override
    public PlanComptableElementDTO update(PlanComptableElementDTO dto) {
        log.info("Updating PlanComptableElement - id: {}", dto.getId());

        var existingEntity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("PlanComptableElement", dto.getId()));

        if (repository.existsByCodeAndIdNot(dto.getCode(), dto.getId())) {
            throw new PlanComptableException("Un autre élément avec le code '" + dto.getCode() + "' existe déjà");
        }

        validateAndNormalizePlanHierarchy(dto, dto.getId());

        existingEntity.setCode(dto.getCode());
        existingEntity.setLibelle(dto.getLibelle());
        existingEntity.setCommentaire(dto.getCommentaire());
        existingEntity.setType(dto.getType());

        if (dto.getParentId() != null) {
            var planEntity = repository.findById(dto.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Plan parent", dto.getParentId()));
            existingEntity.setParent(planEntity);
        } else {
            existingEntity.setParent(null);
        }

        var updatedEntity = repository.save(existingEntity);
        log.info("PlanComptableElement updated - id: {}", updatedEntity.getId());

        return mapper.asDto(updatedEntity);
    }

    @Override
    public PlanComptableElementDTO read(Long elementId) {
        var entity = repository.findById(elementId)
                .orElseThrow(() -> new ResourceNotFoundException("PlanComptableElement", elementId));
        return mapper.asDto(entity);
    }

    @Override
    public void delete(Long elementId) {
        if (!repository.existsById(elementId)) {
            throw new ResourceNotFoundException("PlanComptableElement", elementId);
        }
        repository.deleteById(elementId);
        log.info("PlanComptableElement deleted - id: {}", elementId);
    }

    @Override
    public Page<PlanComptableElementDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            String code,
            String libelle,
            TypePlanComptable type,
            Long plan,
            String sortBy,
            Boolean ascending
    ) {
        return repository
                .readAllByFiltering(pageable, idsToIgnore, code, libelle, type, plan, sortBy, ascending)
                .map(mapper::asDto);
    }

    /**
     * Valide et normalise la hiérarchie du plan comptable :
     * - CLASSE : plan = null
     * - COMPTE : plan = ID de la CLASSE
     * - SOUS_COMPTE : plan = ID du COMPTE
     * - RUBRIQUE : plan = ID du SOUS_COMPTE
     */
    private void validateAndNormalizePlanHierarchy(PlanComptableElementDTO dto, Long currentId) {
        if (dto.getType() == null) {
            throw new PlanComptableException("Le type du plan comptable est obligatoire");
        }

        switch (dto.getType()) {
            case CLASSE:
                validateClasse(dto);
                break;
            case COMPTE:
                validateCompte(dto, currentId);
                break;
            case SOUS_COMPTE:
                validateSousCompte(dto, currentId);
                break;
            case RUBRIQUE:
                validateRubrique(dto, currentId);
                break;
            case REALISATIONS:
                validateRealisations(dto, currentId);
                break;
            default:
                throw new PlanComptableException("Type de plan comptable non supporté: " + dto.getType());
        }
    }

    private void validateClasse(PlanComptableElementDTO dto) {
        if (dto.getParentId() != null) {
            log.warn("Plan parent ignoré pour CLASSE - code: {}", dto.getCode());
            dto.setParentId(null);
        }
    }

    private void validateCompte(PlanComptableElementDTO dto, Long currentId) {
        if (dto.getParentId() == null) {
            throw new PlanComptableException("Un COMPTE doit avoir une CLASSE comme parent");
        }

        var parent = repository.findById(dto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("CLASSE parent", dto.getParentId()));

        if (!TypePlanComptable.CLASSE.equals(parent.getType())) {
            throw new PlanComptableException(
                    "Le parent d'un COMPTE doit être une CLASSE (parent actuel: " + parent.getType() + ")"
            );
        }

        validateNoSelfReference(dto.getParentId(), currentId);
    }

    private void validateSousCompte(PlanComptableElementDTO dto, Long currentId) {
        if (dto.getParentId() == null) {
            throw new PlanComptableException("Un SOUS_COMPTE doit avoir un COMPTE comme parent");
        }

        var parent = repository.findById(dto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("COMPTE parent", dto.getParentId()));

        if (!TypePlanComptable.COMPTE.equals(parent.getType())) {
            throw new PlanComptableException(
                    "Le parent d'un SOUS_COMPTE doit être un COMPTE (parent actuel: " + parent.getType() + ")"
            );
        }

        validateNoSelfReference(dto.getParentId(), currentId);
    }

    private void validateRubrique(PlanComptableElementDTO dto, Long currentId) {
        if (dto.getParentId() == null) {
            throw new PlanComptableException("Une RUBRIQUE doit avoir un SOUS_COMPTE comme parent");
        }

        var parent = repository.findById(dto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("SOUS_COMPTE parent", dto.getParentId()));

        if (!TypePlanComptable.SOUS_COMPTE.equals(parent.getType())) {
            throw new PlanComptableException(
                    "Le parent d'une RUBRIQUE doit être un SOUS_COMPTE (parent actuel: " + parent.getType() + ")"
            );
        }

        validateNoSelfReference(dto.getParentId(), currentId);
    }
    private void validateRealisations(PlanComptableElementDTO dto, Long currentId) {
        if (dto.getParentId() == null) {
            throw new PlanComptableException("Une REALISATIONS doit avoir un RUBRIQUE comme parent");
        }

        var parent = repository.findById(dto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("RUBRIQUE parent", dto.getParentId()));

        if (!TypePlanComptable.RUBRIQUE.equals(parent.getType())) {
            throw new PlanComptableException(
                    "Le parent d'une REALISATIONS doit être une RUBRIQUE (parent actuel: " + parent.getType() + ")"
            );
        }

        validateNoSelfReference(dto.getParentId(), currentId);
    }

    private void validateNoSelfReference(Long planId, Long currentId) {
        if (Objects.equals(planId, currentId)) {
            throw new PlanComptableException("Un élément ne peut pas être son propre parent");
        }
    }
}