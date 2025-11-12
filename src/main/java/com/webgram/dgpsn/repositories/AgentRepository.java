package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import com.webgram.dgpsn.models.AgentCountByDirectionDTO;
import com.webgram.dgpsn.models.RetraiteProjectionDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.enums.TypeStructure;
import com.webgram.dgpsn.entities.AgentEntity;
import com.webgram.dgpsn.entities.QAgentEntity;
import com.webgram.dgpsn.entities.enums.SituationMatrimoniale;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long>, QuerydslPredicateExecutor<AgentEntity> {

    Optional<AgentEntity> findByMatricule(String matricule);

    default Page<AgentEntity> readAllByFiltering(
            Pageable pageable,
            List<Long> idsToIgnore,
            TypeStructure typeStructure,
            String nom,
            String prenom,
            String adresse,
            String email,
            String telephone,
            Date dateCreation,
            Long structureId,
            Long fonctionId,
            Long directionId,
            String sortBy,
            Boolean ascending,
            SituationMatrimoniale situationMatrimoniale) {
        var booleanBuider = new BooleanBuilder();
        Sort sort = Sort.unsorted();

        if(Objects.nonNull(idsToIgnore)) {
            booleanBuider.and(QAgentEntity.agentEntity.id.notIn(idsToIgnore));
        }
        if(Objects.nonNull(typeStructure)) {
            booleanBuider.and(QAgentEntity.agentEntity.structure.typeStructure.eq(typeStructure));
        }
        if(StringUtils.isNotEmpty(nom)) {
            booleanBuider.and(QAgentEntity.agentEntity.nom.containsIgnoreCase(nom));
        }
        if(StringUtils.isNotEmpty(prenom)) {
            booleanBuider.and(QAgentEntity.agentEntity.prenom.containsIgnoreCase(prenom));
        }
        if(StringUtils.isNotEmpty(adresse)) {
            booleanBuider.and(QAgentEntity.agentEntity.adresse.containsIgnoreCase(adresse));
        }
        if(StringUtils.isNotEmpty(email)) {
            booleanBuider.and(QAgentEntity.agentEntity.email.containsIgnoreCase(email));
        }
        if(StringUtils.isNotEmpty(telephone)) {
            booleanBuider.and(QAgentEntity.agentEntity.telephone.containsIgnoreCase(telephone));
        }
        if(Objects.nonNull(dateCreation)) {
            booleanBuider.and(QAgentEntity.agentEntity.dateCreation.eq(dateCreation));
        }
        if(Objects.nonNull(structureId)) {
            booleanBuider.and(QAgentEntity.agentEntity.structure.id.eq(structureId));
        }
        if(Objects.nonNull(directionId)) {
            booleanBuider.and(QAgentEntity.agentEntity.direction.id.eq(directionId));
        }
        if(Objects.nonNull(fonctionId)) {
            booleanBuider.and(QAgentEntity.agentEntity.fonction.id.eq(fonctionId));
        }
        if(StringUtils.isNotEmpty(sortBy)) {
            sort = Sort.by(sortBy);
        }
        if((Objects.nonNull(ascending))) {
            sort.ascending();
        }
        if (Objects.nonNull(situationMatrimoniale)) {
            booleanBuider.and(QAgentEntity.agentEntity.situationMatrimoniale.eq(situationMatrimoniale));
        }

        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);


        return findAll(booleanBuider, pageRequest);
    }

    @Query("SELECT new com.webgram.dgpsn.models.AgentCountByDirectionDTO(d.libelle, COUNT(a)) " +
            "FROM AgentEntity a " +
            "JOIN a.direction d " +
            "GROUP BY d.libelle")
    List<AgentCountByDirectionDTO> countAgentsByDirection();

    @Query("""
                    SELECT new com.webgram.dgpsn.models.RetraiteProjectionDTO(
                        YEAR(a.dateNaissance) + 60,
                        d.libelle,
                        COUNT(a)
                    )
                    FROM AgentEntity a
                    JOIN a.direction d
                    WHERE :annee IS NULL OR (YEAR(a.dateNaissance) + 60) = :annee
                    GROUP BY YEAR(a.dateNaissance) + 60, d.libelle
                    ORDER BY YEAR(a.dateNaissance) + 60 ASC
            """)
    List<RetraiteProjectionDTO> countFutureRetraitesByDirection(@Param("annee") Integer annee);

}
