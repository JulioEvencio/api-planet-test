package com.github.julioevencio.apiplanettest.domain.dto.planet;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class PlanetRequestDTO {

	@NotBlank(message = "{planet.field.name.empty}")
	@Length(max = 255, min = 1, message = "{planet.field.name.lenght}")
	private String name;

	@NotBlank(message = "{planet.field.climate.empty}")
	@Length(max = 255, min = 1, message = "{planet.field.climate.lenght}")
	private String climate;

	@NotBlank(message = "{planet.field.terrain.empty}")
	@Length(max = 255, min = 1, message = "{planet.field.terrain.lenght}")
	private String terrain;

	public PlanetRequestDTO(
			@NotBlank(message = "{planet.field.name.empty}") @Length(max = 255, min = 1, message = "{planet.field.name.lenght}") String name,
			@NotBlank(message = "{planet.field.climate.empty}") @Length(max = 255, min = 1, message = "{planet.field.climate.lenght}") String climate,
			@NotBlank(message = "{planet.field.terrain.empty}") @Length(max = 255, min = 1, message = "{planet.field.terrain.lenght}") String terrain) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
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
