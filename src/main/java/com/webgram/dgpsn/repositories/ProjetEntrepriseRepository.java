package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.ProjetEntrepriseEntity;
import com.webgram.dgpsn.entities.QProjetEntrepriseEntity;

import java.util.Objects;

@Repository
public interface ProjetEntrepriseRepository extends JpaRepository<ProjetEntrepriseEntity, Long>, QuerydslPredicateExecutor<ProjetEntrepriseEntity> {

    default Page<ProjetEntrepriseEntity> readAllByFiltering(Pageable pageable, Long projectId, Long entrepriseId, Long roleEntrepriseId,Long flagId) {
        var booleanBuider = new BooleanBuilder();

        if(Objects.nonNull(projectId)) {
            booleanBuider.and(QProjetEntrepriseEntity.projetEntrepriseEntity.projet.id.eq(projectId));
        }
        if(Objects.nonNull(entrepriseId)) {
            booleanBuider.and(QProjetEntrepriseEntity.projetEntrepriseEntity.entreprise.id.eq(entrepriseId));
        }
        if(Objects.nonNull(roleEntrepriseId)) {
            booleanBuider.and(QProjetEntrepriseEntity.projetEntrepriseEntity.roleEntreprise.id.eq(roleEntrepriseId));
        }
        if(Objects.nonNull(flagId)) {
            booleanBuider.and(QProjetEntrepriseEntity.projetEntrepriseEntity.flag.id.eq(flagId));
        }

        return findAll(booleanBuider, pageable);
    }
}
