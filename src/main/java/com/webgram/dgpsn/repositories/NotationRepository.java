package com.webgram.dgpsn.repositories;

import com.webgram.dgpsn.entities.NotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotationRepository extends JpaRepository<NotationEntity,Long>{

    List<NotationEntity> findByCandidatId(Long candidatId);
}
