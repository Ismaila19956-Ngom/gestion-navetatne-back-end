package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.naavetane.backend.entities.CategorieEntity;

import java.util.List;
import java.util.UUID;

public interface CategorieRepository extends JpaRepository<CategorieEntity, UUID> {

    /** Retrouver toutes les catégories d'une journée donnée */
    List<CategorieEntity> findByJourneeId(UUID journeeId);
}
