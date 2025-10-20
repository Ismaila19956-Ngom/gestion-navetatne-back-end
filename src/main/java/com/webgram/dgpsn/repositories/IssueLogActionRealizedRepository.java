package com.webgram.dgpsn.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.IssueLogActionRealizedEntity;
import com.webgram.dgpsn.entities.IssueLogEntity;

import java.util.List;

@Repository
public interface IssueLogActionRealizedRepository extends JpaRepository<IssueLogActionRealizedEntity, Long>, QuerydslPredicateExecutor<IssueLogActionRealizedEntity> {

  List<IssueLogActionRealizedEntity> findAllByIssueLog(IssueLogEntity issueLog);
}
