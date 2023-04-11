package com.github.julioevencio.apiplanettest.domain.dto.planet;

import com.github.julioevencio.apiplanettest.domain.entities.PlanetEntity;

public class PlanetMapperDTO {

	public static PlanetEntity fromDTO(PlanetRequestDTO dto) {
		return new PlanetEntity(null, dto.getName(), dto.getClimate(), dto.getTerrain());
	}

	public static PlanetResponseDTO fromEntity(PlanetEntity entity) {
		return new PlanetResponseDTO(entity.getId(), entity.getName(), entity.getClimate(), entity.getTerrain());
	}

}
