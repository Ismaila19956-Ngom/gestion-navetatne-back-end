package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CategoriebudgetaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Repository
public interface CategoriebudgetaireRepository extends JpaRepository<CategoriebudgetaireEntity, Long>, QuerydslPredicateExecutor<CategoriebudgetaireEntity> {
}