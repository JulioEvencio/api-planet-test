package com.github.julioevencio.apiplanettest.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetRequestDTO;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetResponseDTO;
import com.github.julioevencio.apiplanettest.domain.entities.PlanetEntity;

public class PlanetUtil {

	public static PlanetEntity makePlanetValid() {
		return new PlanetEntity(null, "Planet", "Climate", "Terrain");
	}

	public static PlanetEntity makePlanetNull() {
		return new PlanetEntity(null, null, null, null);
	}

	public static List<PlanetEntity> makePlanetList() {
		List<PlanetEntity> planets = new ArrayList<>();

		planets.add(new PlanetEntity(null, "Planet 1", "Climate 1", "Terrain 1"));
		planets.add(new PlanetEntity(null, "Planet 2", "Climate 2", "Terrain 2"));
		planets.add(new PlanetEntity(null, "Planet 3", "Climate 3", "Terrain 3"));

		return planets;
	}

	public static PlanetRequestDTO makePlanetRequestDTO() {
		return new PlanetRequestDTO("Planet Request", "Climate Request", "Terrain Request");
	}

	public static PlanetRequestDTO makePlanetRequestDTONull() {
		return new PlanetRequestDTO(null, null, null);
	}

	public static PlanetRequestDTO makePlanetRequestDTOEmpty() {
		return new PlanetRequestDTO("", "", "");
	}

	public static PlanetResponseDTO makePlanetResponseDTO() {
		return new PlanetResponseDTO(1L, "Planet Response", "Climate Response", "Terrain Response");
	}

	public static List<PlanetResponseDTO> makePlanetResponseDTOList() {
		List<PlanetResponseDTO> planets = new ArrayList<>();

		planets.add(new PlanetResponseDTO(1L, "Planet 1", "Climate 1", "Terrain 1"));
		planets.add(new PlanetResponseDTO(2L, "Planet 2", "Climate 2", "Terrain 2"));
		planets.add(new PlanetResponseDTO(3L, "Planet 3", "Climate 3", "Terrain 3"));

		return planets;
	}

}
