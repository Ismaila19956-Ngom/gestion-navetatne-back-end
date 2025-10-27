package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.PlanComptableElementEntity;
import com.webgram.dgpsn.exceptions.PlanComptableException;
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
        log.info("Creating PlanComptableElement with type: {} and code: {}", dto.getType(), dto.getCode());

        // Validation du code unique
        if (repository.existsByCode(dto.getCode())) {
            throw new PlanComptableException("Un élément avec le code '" + dto.getCode() + "' existe déjà");
        }

        // Validation et normalisation selon la logique métier
        validateAndNormalizePlanLogic(dto, null);

        // Conversion et sauvegarde
        var entity = mapper.asEntity(dto);
        var savedEntity = repository.save(entity);

        log.info("PlanComptableElement successfully created with id: {}", savedEntity.getId());
        return mapper.asDto(savedEntity);
    }

    @Override
    public PlanComptableElementDTO update(PlanComptableElementDTO dto) {
        log.info("Updating PlanComptableElement with id: {}", dto.getId());

        // Vérification de l'existence
        var existingEntity = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("PlanComptableElement", dto.getId()));

        // Validation du code unique (en excluant l'élément actuel)
        if (repository.existsByCodeAndIdNot(dto.getCode(), dto.getId())) {
            throw new PlanComptableException("Un autre élément avec le code '" + dto.getCode() + "' existe déjà");
        }

        // Validation et normalisation selon la logique métier
        validateAndNormalizePlanLogic(dto, dto.getId());

        // Mise à jour des propriétés simples
        existingEntity.setCode(dto.getCode());
        existingEntity.setLibelle(dto.getLibelle());
        existingEntity.setCommentaire(dto.getCommentaire());
        existingEntity.setType(dto.getType());

        // Mise à jour de la relation plan
        if (dto.getPlanId() != null) {
            var planEntity = repository.findById(dto.getPlanId())
                    .orElseThrow(() -> new ResourceNotFoundException("Plan comptable parent", dto.getPlanId()));
            existingEntity.setPlan(planEntity);
        } else {
            existingEntity.setPlan(null);
        }

        var updatedEntity = repository.save(existingEntity);
        log.info("PlanComptableElement successfully updated with id: {}", updatedEntity.getId());

        return mapper.asDto(updatedEntity);
    }

    @Override
    public PlanComptableElementDTO read(Long elementId) {
        log.info("Reading PlanComptableElement with id: {}", elementId);

        var entity = repository.findById(elementId)
                .orElseThrow(() -> new ResourceNotFoundException("PlanComptableElement", elementId));

        return mapper.asDto(entity);
    }

    @Override
    public void delete(Long elementId) {
        log.info("Deleting PlanComptableElement with id: {}", elementId);

        if (!repository.existsById(elementId)) {
            throw new ResourceNotFoundException("PlanComptableElement", elementId);
        }

        repository.deleteById(elementId);
        log.info("PlanComptableElement with id {} successfully deleted", elementId);
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
        log.info("Reading all PlanComptableElements with filters - type: {}, code: {}", type, code);

        return repository
                .readAllByFiltering(pageable, idsToIgnore, code, libelle, type, sortBy, ascending)
                .map(mapper::asDto);
    }

    /**
     * Valide et normalise le plan parent selon la logique métier :
     * - Si type = CLASSE : planId doit être null
     * - Si type != CLASSE : planId doit référencer une CLASSE existante
     *
     * @param dto Le DTO à valider
     * @param currentId L'ID de l'élément en cours de modification (null pour une création)
     * @throws PlanComptableException si les règles métier ne sont pas respectées
     */
    private void validateAndNormalizePlanLogic(PlanComptableElementDTO dto, Long currentId) {
        // Validation du type
        if (dto.getType() == null) {
            throw new PlanComptableException("Le type du plan comptable est obligatoire");
        }

        if (TypePlanComptable.CLASSE.equals(dto.getType())) {
            // Règle 1 : Pour une CLASSE, le plan parent doit être null
            if (dto.getPlanId() != null) {
                log.warn("Plan parent {} ignoré pour un élément de type CLASSE", dto.getPlanId());
                dto.setPlanId(null); // Force à null
            }
            log.debug("Element de type CLASSE - aucun plan parent requis");

        } else {
            // Règle 2 : Pour les autres types, un plan parent CLASSE est OBLIGATOIRE
            if (dto.getPlanId() == null) {
                throw new PlanComptableException(
                        String.format("Un plan parent de type CLASSE est requis pour un élément de type %s",
                                dto.getType())
                );
            }

            // Vérification que le plan parent existe
            var planParent = repository.findById(dto.getPlanId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Plan comptable parent", dto.getPlanId()
                    ));

            // Vérification que le plan parent est bien de type CLASSE
            if (!TypePlanComptable.CLASSE.equals(planParent.getType())) {
                throw new PlanComptableException(
                        String.format("Le plan parent (id: %d) doit être de type CLASSE. Type actuel : %s",
                                dto.getPlanId(), planParent.getType())
                );
            }

            // Vérification qu'un élément ne se référence pas lui-même (lors d'une modification)
            if (Objects.equals(dto.getPlanId(), currentId)) {
                throw new PlanComptableException("Un élément ne peut pas être son propre parent");
            }

            log.debug("Element de type {} correctement lié à la CLASSE {} ({})",
                    dto.getType(), planParent.getCode(), dto.getPlanId());
        }
    }
}