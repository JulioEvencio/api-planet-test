package com.github.julioevencio.apiplanettest.domain.dto.planet;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class PlanetRequestDTO {

	@NotBlank(message = "{field.planet.name}")
	@Length(max = 255, min = 1, message = "{field.planet.name}")
	private String name;

	@NotBlank(message = "{field.planet.climate}")
	@Length(max = 255, min = 1, message = "{field.planet.climate}")
	private String climate;

	@NotBlank(message = "{field.planet.terrain}")
	@Length(max = 255, min = 1, message = "{field.planet.terrain}")
	private String terrain;

	public PlanetRequestDTO(
			@NotBlank(message = "{field.planet.name}") @Length(max = 255, min = 1, message = "{field.planet.name}") String name,
			@NotBlank(message = "{field.planet.climate}") @Length(max = 255, min = 1, message = "{field.planet.climate}") String climate,
			@NotBlank(message = "{field.planet.terrain}") @Length(max = 255, min = 1, message = "{field.planet.terrain}") String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

}
