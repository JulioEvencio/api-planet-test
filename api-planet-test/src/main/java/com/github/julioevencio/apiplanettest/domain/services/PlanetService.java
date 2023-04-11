package com.github.julioevencio.apiplanettest.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetMapperDTO;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetRequestDTO;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetResponseDTO;
import com.github.julioevencio.apiplanettest.domain.entities.PlanetEntity;
import com.github.julioevencio.apiplanettest.domain.exception.planet.PlanetNotFoundException;
import com.github.julioevencio.apiplanettest.domain.repositories.PlanetRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanetService {

	private PlanetRepository planetRepository;

	public PlanetService(PlanetRepository planetRepository) {
		this.planetRepository = planetRepository;
	}
	
	public List<PlanetResponseDTO> findAll() {
		return planetRepository.findAll().stream().map(PlanetMapperDTO::fromEntity).toList();
	}
	
	public PlanetResponseDTO findById(Long id) {
		return PlanetMapperDTO.fromEntity(planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException("Planet Not Found")));
	}
	
	public PlanetResponseDTO findByName(String name) {
		return PlanetMapperDTO.fromEntity(planetRepository.findByName(name).orElseThrow(() -> new PlanetNotFoundException("Planet Not Found")));
	}

	public List<PlanetResponseDTO> findByClimate(String climate) {
		return planetRepository.findByClimate(climate).stream().map(PlanetMapperDTO::fromEntity).toList();
	}

	public List<PlanetResponseDTO> findByTerrain(String terrain) {
		return planetRepository.findByTerrain(terrain).stream().map(PlanetMapperDTO::fromEntity).toList();
	}
	
	@Transactional
	public PlanetResponseDTO create(PlanetRequestDTO dto) {
		return PlanetMapperDTO.fromEntity(planetRepository.save(PlanetMapperDTO.fromDTO(dto)));
	}
	
	@Transactional
	public PlanetResponseDTO update(Long id, PlanetRequestDTO dto) {
		PlanetEntity planet = planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException("Planet Not Found"));
		
		planet.setName(dto.getName());
		planet.setClimate(dto.getClimate());
		planet.setTerrain(dto.getTerrain());
		
		return PlanetMapperDTO.fromEntity(planetRepository.save(planet));
	}
	
	@Transactional
	public void delete(Long id) {
		PlanetEntity planet = planetRepository.findById(id).orElseThrow(() -> new PlanetNotFoundException("Planet Not Found"));
		
		planetRepository.delete(planet);
	}

}
