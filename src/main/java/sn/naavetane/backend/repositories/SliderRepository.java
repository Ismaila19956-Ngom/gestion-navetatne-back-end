package sn.naavetane.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.naavetane.backend.entities.SliderEntity;
import java.util.UUID;
import java.util.List;

@Repository
public interface SliderRepository extends JpaRepository<SliderEntity, UUID> {
    List<SliderEntity> findByActifTrueOrderByOrdreAsc();
}
