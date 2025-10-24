package com.webgram.dgpsn.services;

import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.NatureCourrier;
import com.webgram.dgpsn.entities.enums.StatutCourrier;
import com.webgram.dgpsn.repositories.CourrierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourrierService {

    private final CourrierRepository courrierRepository;

    public CourrierEntity create(CourrierEntity courrier) {
        log.info("Création d'un nouveau courrier {} : {}", courrier.getType(), courrier.getReference());

        // Vérifier si la référence existe déjà
        if (courrierRepository.findByReference(courrier.getReference()).isPresent()) {
            throw new RuntimeException("Un courrier avec cette référence existe déjà");
        }

        // Définir les valeurs par défaut selon le TYPE
        if (courrier.getType() == CourrierType.ARRIVER) {
            courrier.setStatut(StatutCourrier.NON_TRAITE);
            courrier.setDateReception(LocalDateTime.now());
        } else if (courrier.getType() == CourrierType.DEPART) {
            courrier.setStatut(StatutCourrier.BROUILLON);
        }

        return courrierRepository.save(courrier);
    }

    public CourrierEntity update(CourrierEntity courrier) {
        log.info("Mise à jour du courrier : {}", courrier.getId());

        if (!courrierRepository.existsById(courrier.getId())) {
            throw new RuntimeException("Courrier non trouvé avec l'ID: " + courrier.getId());
        }

        return courrierRepository.save(courrier);
    }

    public void delete(Long id) {
        log.info("Suppression du courrier : {}", id);

        if (!courrierRepository.existsById(id)) {
            throw new RuntimeException("Courrier non trouvé avec l'ID: " + id);
        }

        courrierRepository.deleteById(id);
    }

    public Page<CourrierEntity> readAll(Pageable pageable, String keyword,
                                        CourrierType type, NatureCourrier nature,
                                        StatutCourrier statut) {
        log.info("Lecture des courriers avec filtres");

        if (keyword != null && !keyword.trim().isEmpty()) {
            return courrierRepository.search(keyword.trim(), type, nature, statut, pageable);
        }

        // Filtres combinés
        if (type != null && nature != null && statut != null) {
            return courrierRepository.findByTypeAndNatureAndStatut(type, nature, statut, pageable);
        } else if (type != null && nature != null) {
            return courrierRepository.findByTypeAndNature(type, nature, pageable);
        } else if (type != null && statut != null) {
            return courrierRepository.findByTypeAndStatut(type, statut, pageable);
        } else if (nature != null && statut != null) {
            return courrierRepository.findByNatureAndStatut(nature, statut, pageable);
        } else if (type != null) {
            return courrierRepository.findByType(type, pageable);
        } else if (nature != null) {
            return courrierRepository.findByNature(nature, pageable);
        } else if (statut != null) {
            return courrierRepository.findByStatut(statut, pageable);
        } else {
            return courrierRepository.findAll(pageable);
        }
    }

    public Optional<CourrierEntity> read(Long id) {
        log.info("Lecture du courrier : {}", id);
        return courrierRepository.findById(id);
    }

    public CourrierEntity archiver(Long id) {
        log.info("Archivage du courrier : {}", id);

        CourrierEntity courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé avec l'ID: " + id));

        courrier.setStatut(StatutCourrier.ARCHIVE);
        return courrierRepository.save(courrier);
    }

    public CourrierEntity changerStatut(Long id, StatutCourrier nouveauStatut) {
        log.info("Changement de statut du courrier {} vers {}", id, nouveauStatut);

        CourrierEntity courrier = courrierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Courrier non trouvé avec l'ID: " + id));

        courrier.setStatut(nouveauStatut);

        // Mettre à jour les dates selon le TYPE
        if (courrier.getType() == CourrierType.ARRIVER && nouveauStatut == StatutCourrier.TRAITE) {
            courrier.setDateTraitement(LocalDateTime.now());
        } else if (courrier.getType() == CourrierType.DEPART && nouveauStatut == StatutCourrier.ENVOYE) {
            courrier.setDateEnvoi(LocalDateTime.now());
        }

        return courrierRepository.save(courrier);
    }

    public long countByTypeAndNatureAndStatut(CourrierType type, NatureCourrier nature, StatutCourrier statut) {
        return courrierRepository.countByTypeAndNatureAndStatut(type, nature, statut);
    }

    public long countCourriersActifs(CourrierType type) {
        return courrierRepository.countCourriersActifsByType(type);
    }

    public Page<CourrierEntity> findByType(CourrierType type, Pageable pageable) {
        return courrierRepository.findByType(type, pageable);
    }

    public Page<CourrierEntity> findByNature(NatureCourrier nature, Pageable pageable) {
        return courrierRepository.findByNature(nature, pageable);
    }

    // Méthode pour compter par nature uniquement (si nécessaire)
    public long countByNature(NatureCourrier nature) {
        return courrierRepository.countByNature(nature);
    }
}