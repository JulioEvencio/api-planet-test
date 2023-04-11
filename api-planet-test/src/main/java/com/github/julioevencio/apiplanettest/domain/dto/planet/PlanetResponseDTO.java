package com.github.julioevencio.apiplanettest.domain.dto.planet;

public class PlanetResponseDTO {

	private Long id;
	private String name;
	private String climate;
	private String terrain;

	public PlanetResponseDTO(Long id, String name, String climate, String terrain) {
		this.id = id;
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getClimate() {
		return climate;
	}

	public String getTerrain() {
		return terrain;
	}

}
