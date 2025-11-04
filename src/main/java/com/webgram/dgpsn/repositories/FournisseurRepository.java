    package com.webgram.dgpsn.repositories;

    import com.querydsl.core.BooleanBuilder;
    import com.webgram.dgpsn.entities.FournisseurEntity;
    import com.webgram.dgpsn.entities.enums.StatutFournisseur;
    import org.apache.commons.lang3.StringUtils;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.querydsl.QuerydslPredicateExecutor;
    import org.springframework.stereotype.Repository;
    import com.webgram.dgpsn.entities.QFournisseurEntity;

    import java.util.Objects;

    @Repository
    public interface FournisseurRepository extends JpaRepository<FournisseurEntity, Long>, QuerydslPredicateExecutor<FournisseurEntity> {

        default Page<FournisseurEntity> readAllByFiltering(
                Pageable pageable,
                String raisonSociale,
                String codeFournisseur,
                String ninea,
                String categorieFournisseur,
                String statut,
                String sortBy,
                Boolean ascending
        ) {
            var booleanBuider = new BooleanBuilder();
            Sort sort = Sort.unsorted();

            if (StringUtils.isNotEmpty(raisonSociale)) {
                booleanBuider.and(QFournisseurEntity.fournisseurEntity.raisonSociale.containsIgnoreCase(raisonSociale));
            }
            if (StringUtils.isNotEmpty(codeFournisseur)) {
                booleanBuider.and(QFournisseurEntity.fournisseurEntity.codeFournisseur.containsIgnoreCase(codeFournisseur));
            }
            if (StringUtils.isNotEmpty(ninea)) {
                booleanBuider.and(QFournisseurEntity.fournisseurEntity.ninea.containsIgnoreCase(ninea));
            }

            if (StringUtils.isNotEmpty(categorieFournisseur)) {
                 booleanBuider.and(QFournisseurEntity.fournisseurEntity.categorieFournisseur.eq(categorieFournisseur));
            }

            if (StringUtils.isNotEmpty(statut)) {
                StatutFournisseur statutEnum = StatutFournisseur.valueOf(statut.toUpperCase());
            }


            if (StringUtils.isNotEmpty(sortBy)) {
                sort = Sort.by(sortBy);
            }
            if ((Objects.nonNull(ascending) && ascending)) {
                sort = sort.ascending();
            } else if (Objects.nonNull(ascending) && !ascending) {
                sort = sort.descending();
            }

            var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

            return findAll(booleanBuider, pageRequest);
            // Simulation du retour
            //return Page.empty(pageRequest);
        }
    }