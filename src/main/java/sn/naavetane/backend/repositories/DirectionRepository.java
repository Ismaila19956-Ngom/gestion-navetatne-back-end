package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.DirectionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DirectionRepository extends JpaRepository<DirectionEntity, Long>, QuerydslPredicateExecutor<DirectionEntity> {
    List<DirectionEntity> findByParentId(Long parentId);
    List<DirectionEntity> findByParentIsNull();
    Optional<DirectionEntity> findByCode(String code);
}
