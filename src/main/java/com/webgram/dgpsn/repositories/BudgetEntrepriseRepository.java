package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.BudgetEntrepriseEntity;

import java.util.List;

@Repository
public interface BudgetEntrepriseRepository extends JpaRepository<BudgetEntrepriseEntity, Long>, QuerydslPredicateExecutor<BudgetEntrepriseEntity> {
    List<BudgetEntrepriseEntity> findByEntrepriseId(Long entrepriseId);
}
