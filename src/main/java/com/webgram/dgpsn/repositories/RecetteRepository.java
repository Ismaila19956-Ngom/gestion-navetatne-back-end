package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.RecetteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface RecetteRepository extends JpaRepository<RecetteEntity, Long>, QuerydslPredicateExecutor<RecetteEntity> {
    List<RecetteEntity> findByEntrepriseId(Long entrepriseId);
}