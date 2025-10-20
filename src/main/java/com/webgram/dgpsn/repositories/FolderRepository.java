package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.FolderEntity;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long>, QuerydslPredicateExecutor<FolderEntity> {
}
