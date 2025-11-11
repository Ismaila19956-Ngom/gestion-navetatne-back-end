package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.TacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface TacheRepository extends JpaRepository<TacheEntity, Long>, QuerydslPredicateExecutor<TacheEntity> {
    List<TacheEntity> findByActiviteId(Long activiteId);

    @Modifying
    @Query("UPDATE TacheEntity t SET t.statut = :nouveauStatut WHERE t.id = :id")
    int updateStatut(@Param("id") Long id, @Param("nouveauStatut") TacheEntity.StatutTache nouveauStatut);

    /**
     * Trouve toutes les tâches d'une activité
     */
    @Query("SELECT t FROM TacheEntity t WHERE t.activite.id = :activiteId")
    List<TacheEntity> findByActiviteIdPerso(@Param("activiteId") Long activiteId);

    /**
     * Trouve toutes les tâches d'une activité pour un trimestre donné
     */
    @Query("SELECT t FROM TacheEntity t WHERE t.activite.id = :activiteId AND t.trimestre = :trimestre")
    List<TacheEntity> findByActiviteIdAndTrimestre(@Param("activiteId") Long activiteId, @Param("trimestre") Integer trimestre);

    /**
     * Trouve toutes les tâches d'un indicateur
     */
    @Query("SELECT t FROM TacheEntity t WHERE t.indicator.id = :indicatorId")
    List<TacheEntity> findByIndicatorId(@Param("indicatorId") Long indicatorId);

    /**
     * Trouve toutes les tâches pour une liste d'activités en une seule requête.
     */
    @Query("SELECT t FROM TacheEntity t WHERE t.activite.id IN :activiteIds")
    List<TacheEntity> findByActiviteIdIn(@Param("activiteIds") List<Long> activiteIds);

//    findByIndicatorIdIn
    @Query("SELECT t FROM TacheEntity t WHERE t.indicator.id IN :indicatorIds")
    List<TacheEntity> findByIndicatorIdIn(@Param("indicatorIds") List<Long> indicatorIds);
}

