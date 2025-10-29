package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.TacheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface TacheRepository extends JpaRepository<TacheEntity, Long>, QuerydslPredicateExecutor<TacheEntity> {
    List<TacheEntity> findByActiviteId(Long activiteId);
}
