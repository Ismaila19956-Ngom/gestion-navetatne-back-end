package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CourrierEntity;
import com.webgram.dgpsn.entities.enums.CourrierType;
import com.webgram.dgpsn.entities.enums.NatureCourrier;
import com.webgram.dgpsn.entities.enums.StatutCourrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourrierRepository extends JpaRepository<CourrierEntity, Long> {

    Optional<CourrierEntity> findByReference(String reference);

    // Filtres par TYPE
    Page<CourrierEntity> findByType(CourrierType type, Pageable pageable);

    // Filtres par NATURE
    Page<CourrierEntity> findByNature(NatureCourrier nature, Pageable pageable);

    // Filtres par STATUT
    Page<CourrierEntity> findByStatut(StatutCourrier statut, Pageable pageable);

    // Filtres combinés
    Page<CourrierEntity> findByTypeAndNature(CourrierType type, NatureCourrier nature, Pageable pageable);

    Page<CourrierEntity> findByTypeAndStatut(CourrierType type, StatutCourrier statut, Pageable pageable);

    Page<CourrierEntity> findByNatureAndStatut(NatureCourrier nature, StatutCourrier statut, Pageable pageable);

    Page<CourrierEntity> findByTypeAndNatureAndStatut(CourrierType type, NatureCourrier nature, StatutCourrier statut, Pageable pageable);

    // Recherche avancée
    @Query("SELECT c FROM CourrierEntity c WHERE " +
            "(LOWER(c.reference) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.correspondant) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.objet) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:type IS NULL OR c.type = :type) " +
            "AND (:nature IS NULL OR c.nature = :nature) " +
            "AND (:statut IS NULL OR c.statut = :statut)")
    Page<CourrierEntity> search(
            @Param("keyword") String keyword,
            @Param("type") CourrierType type,
            @Param("nature") NatureCourrier nature,
            @Param("statut") StatutCourrier statut,
            Pageable pageable);

    // Comptages
    long countByTypeAndNatureAndStatut(CourrierType type, NatureCourrier nature, StatutCourrier statut);

    long countByNature(NatureCourrier nature);

    @Query("SELECT COUNT(c) FROM CourrierEntity c WHERE c.type = :type AND c.statut != 'ARCHIVE'")
    long countCourriersActifsByType(@Param("type") CourrierType type);

    List<CourrierEntity> findByDateCourrierBetween(LocalDateTime start, LocalDateTime end);
}