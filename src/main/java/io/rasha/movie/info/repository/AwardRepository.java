package io.rasha.movie.info.repository;

import io.rasha.movie.info.model.Award;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, UUID> {
  @Query("select a from Award a where a.nominee=:nominee and a.deleted=false")
  Optional<Award> findByNominee(@Param("nominee") String nominee);
}
