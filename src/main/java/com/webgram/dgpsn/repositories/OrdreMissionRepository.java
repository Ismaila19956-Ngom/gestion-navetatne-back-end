package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import  com.webgram.dgpsn.entities.AgentEntity;
import  com.webgram.dgpsn.entities.OrdreMissionEntity;
import  com.webgram.dgpsn.entities.QOrdreMissionEntity;
import  com.webgram.dgpsn.entities.enums.TypeGroupe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface OrdreMissionRepository extends JpaRepository<OrdreMissionEntity, Long>, QuerydslPredicateExecutor<OrdreMissionEntity> {


    @Modifying
    @Query(value = "DELETE FROM ordre_mission_document WHERE document_id = :documentId", nativeQuery = true)
    void unlinkOrdreMission(@Param("documentId") Long documentId);
    Optional<OrdreMissionEntity> findByOrdreMission(String nom);

    @Query(value = "SELECT agent_id FROM ordre_mission_linked_agent WHERE ordre_mission_id = :ordreMissionId", nativeQuery = true)
    List<Long> findAgentIdsByOrdreMissionId(@Param("ordreMissionId") Long ordreMissionId);

    default Page<OrdreMissionEntity> readAllByFiltering(
            Pageable pageable,
            String groupe,
            String indice,
            String objectMission,
            String priseEnCharge,
            String frais,
            Long ordreMissionId,
            Long agentId,
            String sortBy,
            Boolean ascending,
            List<Long> agentIds
    ) {
        var booleanBuider = new BooleanBuilder();
        var filterBuilder = new  BooleanBuilder();
        if(Objects.nonNull(agentIds) && agentIds.size()> 0) {
            agentIds.forEach(id -> {
                filterBuilder.or(QOrdreMissionEntity.ordreMissionEntity.agent.contains(AgentEntity.builder().id(id).build()));
            });
        }
        booleanBuider.and(filterBuilder);
        Sort sort = Sort.unsorted();


        if(Objects.nonNull(groupe)) {
            TypeGroupe typeGroupe = TypeGroupe.valueOf(groupe);
            booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.groupe.eq(typeGroupe));
        }



        if(StringUtils.isNotEmpty(indice)) {
            booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.indice.containsIgnoreCase(indice));
        }
        if(StringUtils.isNotEmpty( objectMission)) {
            booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.objectMission.containsIgnoreCase(objectMission));
        }

        if(StringUtils.isNotEmpty(priseEnCharge)) {
          //  booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.priseEnCharge..containsIgnoreCase(priseEnCharge));
        }

        if(StringUtils.isNotEmpty(frais)) {
           // booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.frais.containsIgnoreCase(frais));
        }

        if(Objects.nonNull(ordreMissionId)) {
            booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.id.eq(ordreMissionId));
        }
        if (Objects.nonNull(agentId)) {
            booleanBuider.and(QOrdreMissionEntity.ordreMissionEntity.agent.any().id.eq(agentId));
        }


        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);


        return findAll(booleanBuider, pageRequest);
    }
}
