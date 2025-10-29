package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.CaracteristiqueRecrutementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristiqueRecrutementRepository extends JpaRepository<CaracteristiqueRecrutementEntity, Long>, QuerydslPredicateExecutor<CaracteristiqueRecrutementEntity> {
}