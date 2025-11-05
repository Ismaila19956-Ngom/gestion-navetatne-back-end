package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.annotations.Journal;
import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.entities.enums.TypePlanComptable;
import com.webgram.dgpsn.exceptions.PlanComptableException;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.PlanComptableElementMapper;
import com.webgram.dgpsn.models.PlanComptableElementDTO;
import com.webgram.dgpsn.repositories.PlanComptableElementRepository;
import com.webgram.dgpsn.services.PlanComptableElementService;
import com.webgram.dgpsn.tools.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanComptableElementServiceImpl implements PlanComptableElementService {

    private final PlanComptableElementRepository repository;
    private final PlanComptableElementMapper mapper;

    @Override
    @Journal(actionType = ActionType.CREATE_CLASSE)
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
    @Journal(actionType = ActionType.UPDATE_CLASSE)
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
    @Journal(actionType = ActionType.READ_CLASSE)
    public PlanComptableElementDTO read(Long elementId) {
        var entity = repository.findById(elementId)
                .orElseThrow(() -> new ResourceNotFoundException("PlanComptableElement", elementId));
        return mapper.asDto(entity);
    }

    @Override
    @Journal(actionType = ActionType.DELETE_CLASSE)
    public void delete(Long elementId) {
        if (!repository.existsById(elementId)) {
            throw new ResourceNotFoundException("PlanComptableElement", elementId);
        }
        repository.deleteById(elementId);
        log.info("PlanComptableElement deleted - id: {}", elementId);
    }

    @Override
    @Journal(actionType = ActionType.READ_CLASSE)
    public Page<PlanComptableElementDTO> readAll(
            Pageable pageable,
            List<Long> idsToIgnore,
            String code,
            String libelle,
            TypePlanComptable type,
            Long parentId,
            String sortBy,
            Boolean ascending
    ) {
        return repository
                .readAllByFiltering(pageable, idsToIgnore, code, libelle, type, parentId, sortBy, ascending)
                .map(mapper::asDto);
    }


    @Override
    public List<PlanComptableElementDTO> getRealisationsByRubriqueId(Long rubriqueId) {
        log.info("Récupération des réalisations pour la rubrique id {}", rubriqueId);
        repository.findByIdAndType(rubriqueId, TypePlanComptable.RUBRIQUE)
                .orElseThrow(() -> new ResourceNotFoundException("Rubrique", rubriqueId));
        var realisations = repository.findByParentIdAndType(rubriqueId, TypePlanComptable.REALISATION);
        return realisations.stream()
                .map(mapper::asDto)
                .collect(Collectors.toList());
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
            case REALISATION:
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

    @Override
    public List<PlanComptableElementDTO> getRubriquesByClasseId(Long classeId) {
        log.info("Récupération des rubriques pour la classe ID: {}", classeId);
        repository.findByIdAndType(classeId, TypePlanComptable.CLASSE)
                .orElseThrow(() -> new ResourceNotFoundException("Classe", classeId));
        List<PlanComptableElementEntity> rubriques = repository.findRubriquesByClasseId(classeId);
        return rubriques.stream()
                .map(mapper::asDto)
                .collect(Collectors.toList());
    }

    @Override
    @Journal(actionType = ActionType.READ_CLASSE)
    public Page<PlanComptableElementDTO> getClasses(Pageable pageable) {
        log.info("Récupération de toutes les classes");
        return readAll(pageable, null, null, null, TypePlanComptable.CLASSE, null, null, null);
    }
}
