package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.RecrutementEntity;
import com.webgram.dgpsn.entities.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecrutementRepository extends JpaRepository<RecrutementEntity, Long>, QuerydslPredicateExecutor<RecrutementEntity> {
    @Override
    @Query(value = "SELECT DISTINCT r FROM RecrutementEntity r LEFT JOIN FETCH r.caracteristiques",
            countQuery = "SELECT COUNT(r) FROM RecrutementEntity r")
    Page<RecrutementEntity> findAll(Pageable pageable);

    /**
     * Récupère un recrutement par son ID en joignant et chargeant immédiatement
     * sa collection de caractéristiques.
     */
    @Query("SELECT r FROM RecrutementEntity r LEFT JOIN FETCH r.caracteristiques WHERE r.id = :id")
    Optional<RecrutementEntity> findByIdWithCaracteristiques(@Param("id") Long id);
}