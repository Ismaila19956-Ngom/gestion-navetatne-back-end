package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CessionacquisitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface CessionacquisitionRepository extends JpaRepository<CessionacquisitionEntity, Long>, QuerydslPredicateExecutor<CessionacquisitionEntity> {
    List<CessionacquisitionEntity> findByEntrepriseId(Long entrepriseId);
}