package com.github.julioevencio.apiplanettest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetRequestDTO;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetResponseDTO;
import com.github.julioevencio.apiplanettest.domain.services.PlanetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/planets")
public class PlanetController {

	private PlanetService planetService;

	public PlanetController(PlanetService planetService) {
		this.planetService = planetService;
	}

	@GetMapping
	public ResponseEntity<List<PlanetResponseDTO>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PlanetResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<PlanetResponseDTO> findByName(@PathVariable String name) {
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findByName(name));
	}

	@GetMapping("/climate/{climate}")
	public ResponseEntity<List<PlanetResponseDTO>> findByClimate(@PathVariable String climate) {
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findByClimate(climate));
	}

	@GetMapping("/terrain/{terrain}")
	public ResponseEntity<List<PlanetResponseDTO>> findByTerrain(@PathVariable String terrain) {
		return ResponseEntity.status(HttpStatus.OK).body(planetService.findByTerrain(terrain));
	}

	@PostMapping
	public ResponseEntity<PlanetResponseDTO> create(@RequestBody @Valid PlanetRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(planetService.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PlanetResponseDTO> update(@PathVariable Long id, @RequestBody @Valid PlanetRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(planetService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		planetService.delete(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
