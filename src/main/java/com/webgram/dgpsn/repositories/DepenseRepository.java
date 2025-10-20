package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.DepenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface DepenseRepository extends JpaRepository<DepenseEntity, Long>, QuerydslPredicateExecutor<DepenseEntity> {
    List<DepenseEntity> findByEntrepriseId(Long entrepriseId);
}