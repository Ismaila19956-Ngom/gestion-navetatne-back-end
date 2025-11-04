package com.webgram.dgpsn.services.Impl;

import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.LabelEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.ReferentielType;
import com.webgram.dgpsn.repositories.CourrierRepository;
import com.webgram.dgpsn.repositories.LabelRepository;
import com.webgram.dgpsn.services.CourrierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CourrierServiceImpl implements CourrierService {

    private final CourrierRepository courrierRepository;
    private final LabelRepository labelRepository;

    @Override
    public CourrierEntity create(CourrierEntity courrier) {
        log.info("Création d'un nouveau courrier {} : {}", courrier.getType(), courrier.getReference());

        // Vérifier si la référence existe déjà
        if (courrierRepository.findByReference(courrier.getReference()).isPresent()) {
            throw new RuntimeException("Un courrier avec cette référence existe déjà");
        }

        // Définir les valeurs par défaut selon le TYPE
        if (courrier.getType() == CourrierType.ARRIVER) {
            courrier.setDateReception(LocalDateTime.now());
        }

        // S'assurer que archive est false par défaut
        if (courrier.getArchive() == null) {
            courrier.setArchive(false);
        }

        return courrierRepository.save(courrier);
    }

    @Override
    public CourrierEntity update(CourrierEntity courrier) {
        log.info("Mise à jour du courrier : {}", courrier.getId());

        if (!courrierRepository.existsById(courrier.getId())) {
            throw new RuntimeException("Courrier non trouvé avec l'ID: " + courrier.getId());
        }

        return courrierRepository.save(courrier);
    }

    @Override
    public void delete(Long id) {
        log.info("Suppression du courrier : {}", id);

        if (!courrierRepository.existsById(id)) {
            throw new RuntimeException("Courrier non trouvé avec l'ID: " + id);
        }

        courrierRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourrierEntity> readAll(Pageable pageable, String keyword,
                                        CourrierType type, ReferentielType nature,
                                        ReferentielType statut, Boolean archive) { // MODIFICATION : Ajout du paramètre archive
        log.info("Lecture des courriers avec filtres");

        // Utiliser Specification pour des filtres dynamiques
        Specification<CourrierEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtre par mot-clé
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                Predicate keywordPredicate = cb.or(
                        cb.like(cb.lower(root.get("reference")), likePattern),
                        cb.like(cb.lower(root.get("correspondant")), likePattern),
                        cb.like(cb.lower(root.get("objet")), likePattern));
                predicates.add(keywordPredicate);
            }

            // Filtre par type
            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            // Filtre par nature (via LabelEntity)
            if (nature != null) {
                predicates.add(cb.equal(root.get("nature").get("referentielType"), nature));
            }

            // Filtre par statut (via LabelEntity)
            if (statut != null) {
                predicates.add(cb.equal(root.get("statut").get("referentielType"), statut));
            }

            // FILTRE PAR ARCHIVE (NOUVEAU)
            if (archive != null) {
                predicates.add(cb.equal(root.get("archive"), archive));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return courrierRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourrierEntity> read(Long id) {
        log.info("Lecture du courrier : {}", id);
        return courrierRepository.findById(id);
    }

    @Override
    public CourrierEntity archiver(Long id) {
        log.info("Archivage du courrier : {}", id);

        CourrierEntity courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé avec l'ID: " + id));

        // NOUVELLE APPROCHE : Utiliser l'attribut archive
        courrier.setArchive(true);
        courrier.setDateTraitement(LocalDateTime.now());

        return courrierRepository.save(courrier);
    }

    @Override
    public CourrierEntity desarchiver(Long id) {
        log.info("Désarchivage du courrier : {}", id);

        CourrierEntity courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé avec l'ID: " + id));

        courrier.setArchive(false);
        return courrierRepository.save(courrier);
    }

    @Override
    public CourrierEntity changerStatut(Long id, ReferentielType nouveauStatut) {
        log.info("Changement de statut du courrier {} vers {}", id, nouveauStatut);

        CourrierEntity courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé avec l'ID: " + id));

        // Récupérer le label correspondant au nouveau statut
        List<LabelEntity> labels = labelRepository.findByReferentielType(nouveauStatut);
        if (labels.isEmpty()) {
            throw new RuntimeException("Aucun label trouvé pour le type: " + nouveauStatut);
        }

        LabelEntity nouveauLabel = labels.get(0);
        courrier.setStatut(nouveauLabel);

        // Mettre à jour les dates selon le TYPE et le code du statut
        if (courrier.getType() == CourrierType.ARRIVER && "TRAITE".equals(nouveauLabel.getCode())) {
            courrier.setDateTraitement(LocalDateTime.now());
        } else if (courrier.getType() == CourrierType.DEPART && "ENVOYE".equals(nouveauLabel.getCode())) {
            courrier.setDateEnvoi(LocalDateTime.now());
        }

        return courrierRepository.save(courrier);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByTypeAndNatureAndStatut(CourrierType type, ReferentielType nature,
                                              ReferentielType statut) {
        Specification<CourrierEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            if (nature != null) {
                predicates.add(cb.equal(root.get("nature").get("referentielType"), nature));
            }

            if (statut != null) {
                predicates.add(cb.equal(root.get("statut").get("referentielType"), statut));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return courrierRepository.count(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCourriersActifs(CourrierType type) {
        Specification<CourrierEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            // Compter seulement les courriers non archivés
            predicates.add(cb.equal(root.get("archive"), false));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return courrierRepository.count(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourrierEntity> findByType(CourrierType type, Pageable pageable) {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("type"), type);
        return courrierRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourrierEntity> findByNature(ReferentielType nature, Pageable pageable) {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("nature").get("referentielType"), nature);
        return courrierRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByNature(ReferentielType nature) {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("nature").get("referentielType"), nature);
        return courrierRepository.count(spec);
    }

    // NOUVELLES MÉTHODES IMPLÉMENTÉES
    @Override
    @Transactional(readOnly = true)
    public Page<CourrierEntity> findArchives(Pageable pageable) {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("archive"), true);
        return courrierRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourrierEntity> findNonArchives(Pageable pageable) {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("archive"), false);
        return courrierRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public long countArchives() {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("archive"), true);
        return courrierRepository.count(spec);
    }

    @Override
    @Transactional(readOnly = true)
    public long countNonArchives() {
        Specification<CourrierEntity> spec = (root, query, cb) ->
                cb.equal(root.get("archive"), false);
        return courrierRepository.count(spec);
    }
}