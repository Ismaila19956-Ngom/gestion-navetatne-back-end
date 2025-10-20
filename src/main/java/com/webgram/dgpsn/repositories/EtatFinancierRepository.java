package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.EtatFinancierEntity;

import java.util.List;

@Repository
public interface EtatFinancierRepository extends JpaRepository<EtatFinancierEntity, Long>, QuerydslPredicateExecutor<EtatFinancierEntity> {
    List<EtatFinancierEntity> findByEntrepriseId(Long entrepriseId);
}