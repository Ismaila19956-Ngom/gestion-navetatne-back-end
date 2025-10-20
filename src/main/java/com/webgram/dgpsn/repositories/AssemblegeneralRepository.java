package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.AssemblegeneralEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface AssemblegeneralRepository extends JpaRepository<AssemblegeneralEntity, Long>, QuerydslPredicateExecutor<AssemblegeneralEntity> {
    List<AssemblegeneralEntity> findByEntrepriseId(Long entrepriseId);
}