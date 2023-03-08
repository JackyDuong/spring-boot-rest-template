package ch.duong.jmd.#APP_ABBREVIATION.repository;

import ch.duong.jmd.#APP_ABBREVIATION.entity.AppConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppConfigurationRepository extends JpaRepository<AppConfiguration, Integer> {
    Optional<AppConfiguration> findByKey(String key);
}
