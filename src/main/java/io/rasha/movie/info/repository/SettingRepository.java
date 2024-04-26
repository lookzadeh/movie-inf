package io.rasha.movie.info.repository;

import io.rasha.movie.info.model.Award;
import io.rasha.movie.info.model.Setting;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {
  Optional<Setting> findByName(String name);
}
