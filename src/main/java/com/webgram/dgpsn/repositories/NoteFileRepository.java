package com.webgram.dgpsn.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.webgram.dgpsn.entities.NoteFileEntity;
import com.webgram.dgpsn.entities.QNoteFileEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface NoteFileRepository extends JpaRepository<NoteFileEntity, Long>, QuerydslPredicateExecutor<NoteFileEntity> {
  Optional<NoteFileEntity> findById(long id);
    List<NoteFileEntity> findByMarketFileEntityId(long id);


    default Page<NoteFileEntity> readAllByFiltering(Pageable pageable, Double note, String sortBy, Boolean ascending) {
        var booleanBuider = new BooleanBuilder();

        Sort sort = Sort.unsorted();


        if(Objects.nonNull(note)) {
            booleanBuider.and(QNoteFileEntity.noteFileEntity.note.eq(note));
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
