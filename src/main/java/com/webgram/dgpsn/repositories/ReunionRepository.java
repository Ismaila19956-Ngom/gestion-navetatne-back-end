package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.ReunionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface ReunionRepository extends JpaRepository<ReunionEntity, Long>, QuerydslPredicateExecutor<ReunionEntity> {
    List<ReunionEntity> findByConseiladministratifId(Long conseiladministratifId);
}