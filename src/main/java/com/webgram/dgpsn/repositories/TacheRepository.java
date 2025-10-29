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
}
