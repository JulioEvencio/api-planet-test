package com.github.julioevencio.apiplanettest.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.julioevencio.apiplanettest.domain.entities.PlanetEntity;

@Repository
public interface PlanetRepository extends JpaRepository<PlanetEntity, Long> {

	Optional<PlanetEntity> findByName(String name);

	List<PlanetEntity> findByClimate(String climate);

	List<PlanetEntity> findByTerrain(String terrain);

}
