package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.AutreDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface AutreDocumentRepository extends JpaRepository<AutreDocumentEntity, Long>, QuerydslPredicateExecutor<AutreDocumentEntity> {
    Optional<List<AutreDocumentEntity>> findByDocumentId(Long documentId);

//    @Query(value = "select ad.aut_doc_id from autre_document ad where ad.aut_agent_id = :agentId ", nativeQuery = true)
//    List<Long> findDocumentIdByAgentId(@Param("agentId") Long agentId);
}
