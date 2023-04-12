package com.github.julioevencio.apiplanettest.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetRequestDTO;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetResponseDTO;
import com.github.julioevencio.apiplanettest.domain.entities.PlanetEntity;
import com.github.julioevencio.apiplanettest.domain.exception.planet.PlanetNotFoundException;
import com.github.julioevencio.apiplanettest.domain.repositories.PlanetRepository;
import com.github.julioevencio.apiplanettest.utils.PlanetUtil;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {

	@InjectMocks
	private PlanetService planetService;

	@Mock
	private PlanetRepository planetRepository;

	private PlanetEntity PLANET;
	private List<PlanetEntity> PLANET_LIST;

	private PlanetRequestDTO PLANET_REQUEST_DTO;

	@BeforeEach
	public void setup() {
		PLANET = PlanetUtil.makePlanetValid();
		PLANET.setId(1L);

		PLANET_LIST = PlanetUtil.makePlanetList();

		PLANET_REQUEST_DTO = PlanetUtil.makePlanetRequestDTO();
	}

	@Test
	public void createPlanet_WithValidData_ReturnPlanetResponseDTO() {
		when(planetRepository.save(any(PlanetEntity.class))).thenReturn(PLANET);
		
		PlanetResponseDTO sut = planetService.create(PLANET_REQUEST_DTO);

		assertThat(sut.getId()).isEqualTo(PLANET.getId());
		assertThat(sut.getName()).isEqualTo(PLANET.getName());
		assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
		assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
	}

	@Test
	public void createPlanet_WithInvalidData_ThrowsExecption() {
		when(planetRepository.save(any(PlanetEntity.class))).thenThrow(RuntimeException.class);

		assertThatThrownBy(() -> planetService.create(PLANET_REQUEST_DTO)).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void getPlanet_ByExistingId_ReturnPlanetResponseDTO() {
		when(planetRepository.findById(PLANET.getId())).thenReturn(Optional.of(PLANET));

		PlanetResponseDTO sut = planetService.findById(PLANET.getId());

		assertThat(sut.getId()).isEqualTo(PLANET.getId());
		assertThat(sut.getName()).isEqualTo(PLANET.getName());
		assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
		assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
	}

	@Test
	public void getPlanet_ByUnexistingId_ThrowsExecption() {
		when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> planetService.findById(anyLong())).isInstanceOf(PlanetNotFoundException.class);
	}

	@Test
	public void getPlanet_ByExistingName_ReturnPlanetResponseDTO() {
		when(planetRepository.findByName(PLANET.getName())).thenReturn(Optional.of(PLANET));

		PlanetResponseDTO sut = planetService.findByName(PLANET.getName());

		assertThat(sut.getId()).isEqualTo(PLANET.getId());
		assertThat(sut.getName()).isEqualTo(PLANET.getName());
		assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
		assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
	}

	@Test
	public void getPlanet_ByUnexistingName_ThrowsExecption() {
		when(planetRepository.findByName(anyString())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> planetService.findByName(anyString())).isInstanceOf(PlanetNotFoundException.class);
	}

	@Test
	public void listPlanet_All_ReturnListPlanetResponseDTO() {
		when(planetRepository.findAll()).thenReturn(PLANET_LIST);

		List<PlanetResponseDTO> planets = planetService.findAll();

		assertThat(planets).isNotEmpty();
		assertThat(planets).hasSize(PLANET_LIST.size());
	}

	@Test
	public void listPlanet_All_ReturnListEmpty() {
		when(planetRepository.findAll()).thenReturn(Collections.emptyList());

		List<PlanetResponseDTO> planets = planetService.findAll();

		assertThat(planets).isEmpty();
	}

	@Test
	public void listPlanet_ByExistingClimate_ReturnListPlanetResponseDTO() {
		when(planetRepository.findByClimate(anyString())).thenReturn(PLANET_LIST);

		List<PlanetResponseDTO> planets = planetService.findByClimate(anyString());

		assertThat(planets).isNotEmpty();
		assertThat(planets).hasSize(PLANET_LIST.size());
	}

	@Test
	public void listPlanet_ByUnexistingClimate_ReturnListEmpty() {
		when(planetRepository.findByClimate(anyString())).thenReturn(Collections.emptyList());

		List<PlanetResponseDTO> planets = planetService.findByClimate(anyString());

		assertThat(planets).isEmpty();
	}

	@Test
	public void listPlanet_ByExistingTerrain_ReturnListPlanetResponseDTO() {
		when(planetRepository.findByTerrain(anyString())).thenReturn(PLANET_LIST);

		List<PlanetResponseDTO> planets = planetService.findByTerrain(anyString());

		assertThat(planets).isNotEmpty();
		assertThat(planets).hasSize(PLANET_LIST.size());
	}

	@Test
	public void listPlanet_ByUnexistingTerrain_ReturnListEmpty() {
		when(planetRepository.findByTerrain(anyString())).thenReturn(Collections.emptyList());

		List<PlanetResponseDTO> planets = planetService.findByTerrain(anyString());

		assertThat(planets).isEmpty();
	}

	@Test
	public void updatePlanet_WithValidData_ReturnPlanetResponseDTO() {
		when(planetRepository.findById(PLANET.getId())).thenReturn(Optional.of(PLANET));
		when(planetRepository.save(PLANET)).thenReturn(PLANET);

		PlanetResponseDTO sut = planetService.update(PLANET.getId(), PLANET_REQUEST_DTO);

		assertThat(sut.getId()).isEqualTo(PLANET.getId());
		assertThat(sut.getName()).isEqualTo(PLANET.getName());
		assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
		assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
	}

	@Test
	public void updatePlanet_WithInvalidData_ThrowsExecption() {
		when(planetRepository.findById(PLANET.getId())).thenReturn(Optional.of(PLANET));
		when(planetRepository.save(any(PlanetEntity.class))).thenThrow(RuntimeException.class);

		assertThatThrownBy(() -> planetService.update(PLANET.getId(), PLANET_REQUEST_DTO)).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void updatePlanet_WithUnexistingId_ThrowsExecption() {
		when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatThrownBy(() -> planetService.update(anyLong(), PLANET_REQUEST_DTO)).isInstanceOf(PlanetNotFoundException.class);
	}

	@Test
	public void deletePlanet_WithExistingId_DeletePlanet() {
		when(planetRepository.findById(PLANET.getId())).thenReturn(Optional.of(PLANET));
		
		assertThatCode(() -> planetService.delete(PLANET.getId())).doesNotThrowAnyException();
	}

	@Test
	public void deletePlanet_WithUnexistingId_ThrowsExecption() {
		when(planetRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> planetService.delete(anyLong())).isInstanceOf(PlanetNotFoundException.class);
	}

}
