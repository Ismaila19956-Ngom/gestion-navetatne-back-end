package com.webgram.dgpsn.repositories;


import com.webgram.dgpsn.entities.FormationExterieurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationExterieurRepository extends JpaRepository<FormationExterieurEntity, Long>, QuerydslPredicateExecutor<FormationExterieurEntity>, JpaSpecificationExecutor<FormationExterieurEntity> {
}
