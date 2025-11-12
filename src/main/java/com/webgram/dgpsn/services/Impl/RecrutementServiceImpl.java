package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.ManagementUnitEntity;
import com.webgram.dgpsn.entities.QRecrutementEntity;
import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.entities.enums.StatutType;
import com.webgram.dgpsn.entities.enums.TypeContrat;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CandidatMapper;
import com.webgram.dgpsn.mappers.RecrutementMapper;
import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.models.RecrutementDTO;
import com.webgram.dgpsn.repositories.RecrutementRepository;
import com.webgram.dgpsn.services.AlerteService;
import com.webgram.dgpsn.services.CandidatService;
import com.webgram.dgpsn.services.RecrutementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RecrutementServiceImpl implements RecrutementService {
    private final RecrutementRepository recrutementRepository;
    private final RecrutementMapper recrutementMapper;
    final CandidatService candidatService;
    final AlerteServiceImpl alerteService;
    private static final String RECRUTEMENT_NOT_FOUND_MESSAGE = "Recrutement non trouvé avec l'ID {0}";


    String ROLE_IDENTIFIER_NOT_FOUND_MESSAGE = "Invalide id recrutement {0}";

//    @Override
//    public RecrutementDTO createRecrutement(RecrutementDTO recrutementDTO) {
//        var entity = recrutementMapper.asEntity(recrutementDTO);
//        var savedEntity = recrutementRepository.save(entity);
//        return recrutementMapper.asDto(savedEntity);
//    }

    @Override
    public CandidatDTO addCandidat(Long idRecrutement, CandidatDTO dto){
        dto.setRecrutementId(idRecrutement);
        return candidatService.saveCandidat(dto);
    }



    @Override
    public List<CandidatDTO> getCandidatsRecrutements(Long idRecrutement){
        var recrutement = recrutementRepository.findById(idRecrutement).orElseThrow(
                () -> new ResourceNotFoundException("Recrutement not found"));
        return  recrutement.getCandidats().stream().map(
                CandidatMapper::toDTO).toList();
    }


    @Override
    public CandidatDTO updateCandidat(Long idRecrutement, Long idCandidat, CandidatDTO dto) {
        var recrutement = recrutementRepository.findById(idRecrutement)
                .orElseThrow(() -> new ResourceNotFoundException("Recrutement non trouvé avec id : " + idRecrutement));

        boolean candidatAppartient = recrutement.getCandidats().stream()
                .anyMatch(c -> Objects.equals(c.getId(), idCandidat));

        if (!candidatAppartient) {
            throw new ResourceNotFoundException("Le candidat n'appartient pas à ce recrutement.");
        }
        dto.setRecrutementId(idRecrutement);
        return candidatService.updateCandidat(idCandidat, dto);
    }


    @Override
    @Transactional
    public RecrutementDTO createRecrutement(RecrutementDTO recrutementDTO) {
        log.info("Création d'un nouveau recrutement : {}", recrutementDTO.getLibelle());

        RecrutementEntity entity = recrutementMapper.asEntity(recrutementDTO);

        // Assurer la cohérence de la relation bidirectionnelle
        if (entity.getCaracteristiques() != null) {
            entity.getCaracteristiques().forEach(caracteristique -> caracteristique.setRecrutement(entity));
        }

        // Définir le statut initial par défaut
        entity.setStatutType(StatutType.TRAITEMENT_ENCOUR);

        RecrutementEntity savedEntity = recrutementRepository.save(entity);
        log.info("Recrutement créé avec succès, ID : {}", savedEntity.getId());
        alerteService.generateAlertNouveauRecrutement(recrutementMapper.asDto(savedEntity));
        return recrutementMapper.asDto(savedEntity);
    }

    @Override
    @Transactional
    public RecrutementDTO updateRecrutement(RecrutementDTO recrutementDTO) {
        log.info("Mise à jour du recrutement avec l'ID : {}", recrutementDTO.getId());

        RecrutementEntity existingEntity = recrutementRepository.findById(recrutementDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        MessageFormat.format(RECRUTEMENT_NOT_FOUND_MESSAGE, recrutementDTO.getId())
                ));

        // Mapper le DTO vers une nouvelle entité pour obtenir les nouvelles valeurs
        RecrutementEntity updatedData = recrutementMapper.asEntity(recrutementDTO);

        // Mettre à jour les champs de l'entité existante
        existingEntity.setLibelle(updatedData.getLibelle());
        existingEntity.setTypeContrat(updatedData.getTypeContrat());
        existingEntity.setDateRecrutement(updatedData.getDateRecrutement());

        // Gérer la mise à jour de la collection de caractéristiques
        existingEntity.getCaracteristiques().clear();
        if (updatedData.getCaracteristiques() != null) {
            updatedData.getCaracteristiques().forEach(car -> {
                car.setRecrutement(existingEntity); // Important pour la relation bidirectionnelle
                existingEntity.getCaracteristiques().add(car);
            });
        }

        RecrutementEntity savedEntity = recrutementRepository.save(existingEntity);
        log.info("Recrutement mis à jour avec succès, ID : {}", savedEntity.getId());

        return recrutementMapper.asDto(savedEntity);
    }


    @Override
    public void deleteRecrutement(Long id) {
        if(!recrutementRepository.existsById(id)){
            throw new ResourceNotFoundException(MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }
        recrutementRepository.deleteById(id);
        log.info("delete recrutement ok id {}", id);
    }

    @Override
    public RecrutementDTO readRecrutement(Long id) {
        var entity = recrutementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return recrutementMapper.asDto(entity);    }

    @Override
    public Page<RecrutementDTO> readAllRecrutement(Map<String, String> searchParams, Pageable pageable) {
        var booleanBuilder = new BooleanBuilder();
        buildSearch(searchParams, booleanBuilder);
        Page<RecrutementEntity> entities = recrutementRepository.findAll(pageable);
        return entities.map(recrutementMapper::asDto);
    }

    private void buildSearch(Map<String, String> searchParams, BooleanBuilder booleanBuilder) {
        if (Objects.nonNull(searchParams)) {
            var qEntity = QRecrutementEntity.recrutementEntity;
            if (searchParams.containsKey("libelle"))
                booleanBuilder.and(qEntity.libelle.containsIgnoreCase(searchParams.get("libelle")));


            if (searchParams.containsKey("dateRecrutement")) {
                Date dateRecrutement = null;
                try {
                    dateRecrutement = new SimpleDateFormat("yyyy-MM-dd").parse(searchParams.get("dateRecrutement"));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                booleanBuilder.and(qEntity.dateRecrutement.eq(dateRecrutement));
            }

            if (searchParams.containsKey("typeContrat"))
                booleanBuilder.and(qEntity.typeContrat.libelle.equalsIgnoreCase(searchParams.get("typeContrat"))
                        .or(qEntity.typeContrat.code.equalsIgnoreCase(searchParams.get("typeContrat"))));

        }


    }

    @Override
    @Transactional
    public RecrutementDTO updateStatut(Long id, StatutType statutType) {
        log.info("Mise à jour du statut pour le recrutement ID={} vers {}", id, statutType);

        if (id == null || statutType == null) {
            throw new IllegalArgumentException("L'ID du recrutement et le statut ne peuvent pas être nuls.");
        }

        RecrutementEntity entity = recrutementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(RECRUTEMENT_NOT_FOUND_MESSAGE, id)));

        if (entity.getStatutType() == statutType) {
            log.warn("Le statut du recrutement ID={} est déjà {}. Aucune mise à jour effectuée.", id, statutType);
            return recrutementMapper.asDto(entity);
        }

        log.info("Changement de statut pour le recrutement ID={}: de {} à {}", id, entity.getStatutType(), statutType);
        entity.setStatutType(statutType);

        RecrutementEntity updatedEntity = recrutementRepository.save(entity);
        return recrutementMapper.asDto(updatedEntity);
    }
}
