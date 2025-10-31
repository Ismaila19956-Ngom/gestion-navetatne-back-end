package com.webgram.dgpsn.services.Impl;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.entities.QRecrutementEntity;
import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.entities.enums.TypeContrat;
import com.webgram.dgpsn.exceptions.ResourceNotFoundException;
import com.webgram.dgpsn.mappers.CandidatMapper;
import com.webgram.dgpsn.mappers.RecrutementMapper;
import com.webgram.dgpsn.models.CandidatDTO;
import com.webgram.dgpsn.models.RecrutementDTO;
import com.webgram.dgpsn.repositories.RecrutementRepository;
import com.webgram.dgpsn.services.CandidatService;
import com.webgram.dgpsn.services.RecrutementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RecrutementServiceImpl implements RecrutementService {
    private final RecrutementRepository recrutementRepository;
    private final RecrutementMapper recrutementMapper;
    final CandidatService candidatService;


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
    public RecrutementDTO createRecrutement(RecrutementDTO recrutementDTO) {
        // 1. Conversion du DTO en Entité
        var entity = recrutementMapper.asEntity(recrutementDTO);

        // 2. Synchronisation de la relation bidirectionnelle
        // Pour chaque CaracteristiqueExigeEntity dans la liste,
        // on définit sa référence "recrutement" à l'entité parente.
        if (entity.getCaracteristiques() != null) {
            entity.getCaracteristiques().forEach(caracteristique -> caracteristique.setRecrutement(entity));
        }

        // 3. Sauvegarde de l'entité parente (avec cascade)
        var savedEntity = recrutementRepository.save(entity);
        return recrutementMapper.asDto(savedEntity);
    }

    @Override
    public RecrutementDTO updateRecrutement(RecrutementDTO recrutementDTO) {
        // ✅ Vérifier que le recrutement existe
        if (!recrutementRepository.existsById(recrutementDTO.getId())) {
            throw new ResourceNotFoundException(
                    MessageFormat.format(ROLE_IDENTIFIER_NOT_FOUND_MESSAGE, recrutementDTO.getId())
            );
        }

        var entity = recrutementMapper.asEntity(recrutementDTO);

        // ✅ Il est également crucial d'ajouter la même logique pour la mise à jour !
        if (entity.getCaracteristiques() != null) {
            entity.getCaracteristiques().forEach(caracteristique -> caracteristique.setRecrutement(entity));
        }

        var savedEntity = recrutementRepository.save(entity);
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
                booleanBuilder.and(qEntity.typeContrat.eq(TypeContrat.valueOf(searchParams.get("typeContrat"))));

        }
    }

}
