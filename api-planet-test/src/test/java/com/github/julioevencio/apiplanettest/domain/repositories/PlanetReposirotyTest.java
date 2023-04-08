package com.github.julioevencio.apiplanettest.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.github.julioevencio.apiplanettest.domain.entities.PlanetEntity;
import com.github.julioevencio.apiplanettest.utils.PlanetUtil;

@DataJpaTest
public class PlanetReposirotyTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private PlanetRepository planetRepository;

	private PlanetEntity PLANET;
	private PlanetEntity PLANET_NULL;
	private List<PlanetEntity> PLANET_LIST;

	@BeforeEach
	public void setup() {
		PLANET = PlanetUtil.makePlanetValid();
		PLANET_NULL = PlanetUtil.makePlanetNull();
		PLANET_LIST = PlanetUtil.makePlanetList();
	}

	@Test
	public void createPlanet_WithValidData_ReturnPlanet() {
		PlanetEntity planet = planetRepository.save(PLANET);

		PlanetEntity sut = testEntityManager.find(PlanetEntity.class, planet.getId());

		assertThat(sut).isNotNull();
		assertThat(sut.getName()).isEqualTo(PLANET.getName());
		assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
		assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
	}

	@Test
	public void createPlanet_WithInvalidData_ThrowsExecption() {
		assertThatThrownBy(() -> planetRepository.save(PLANET_NULL)).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void createPlanet_WithExistingName_ThrowsException() {
		PlanetEntity planet = testEntityManager.persistFlushFind(PLANET);
		testEntityManager.detach(planet);
		planet.setId(null);

		assertThatThrownBy(() -> planetRepository.save(PLANET_NULL)).isInstanceOf(RuntimeException.class);
	}

	@Test
	public void getPlanet_ByExistingId_ReturnPlanet() {
		PlanetEntity planet = testEntityManager.persistFlushFind(PLANET);

		Optional<PlanetEntity> planetOpt = planetRepository.findById(planet.getId());

		assertThat(planetOpt).isNotEmpty();
		assertThat(planetOpt.get()).isEqualTo(planet);
	}

	@Test
	public void getPlanet_ByUnexistingId_ReturnEmpty() {
		Optional<PlanetEntity> planetOpt = planetRepository.findById(anyLong());

		assertThat(planetOpt).isEmpty();
	}

	@Test
	public void getPlanet_ByExistingName_ReturnPlanet() {
		PlanetEntity planet = testEntityManager.persistFlushFind(PLANET);

		Optional<PlanetEntity> planetOpt = planetRepository.findByName(planet.getName());

		assertThat(planetOpt).isNotEmpty();
		assertThat(planetOpt.get()).isEqualTo(planet);
	}

	@Test
	public void getPlanet_ByUnexistingName_ReturnEmpty() {
		Optional<PlanetEntity> planetOpt = planetRepository.findByName(anyString());

		assertThat(planetOpt).isEmpty();
	}

	@Test
	public void listPlanet_All_ReturnListPlanet() {
		testEntityManager.persistFlushFind(PLANET_LIST.get(0));
		testEntityManager.persistAndFlush(PLANET_LIST.get(1));
		testEntityManager.persistAndFlush(PLANET_LIST.get(2));

		List<PlanetEntity> planets = planetRepository.findAll();

		assertThat(planets).isNotEmpty();
		assertThat(planets).hasSize(PLANET_LIST.size());
	}

	@Test
	public void listPlanet_All_ReturnListEmpty() {
		List<PlanetEntity> planets = planetRepository.findAll();

		assertThat(planets).isEmpty();
	}

	@Test
	public void listPlanet_ByExistingClimate_ReturnListPlanet() {
		PlanetEntity planet = testEntityManager.persistFlushFind(PLANET_LIST.get(0));
		testEntityManager.persistAndFlush(PLANET_LIST.get(1));
		testEntityManager.persistAndFlush(PLANET_LIST.get(2));

		List<PlanetEntity> planets = planetRepository.findByClimate(planet.getClimate());

		assertThat(planets).isNotEmpty();
	}

	@Test
	public void listPlanet_ByUnexistingClimate_ReturnListEmpty() {
		List<PlanetEntity> planets = planetRepository.findByClimate(anyString());

		assertThat(planets).isEmpty();
	}

	@Test
	public void listPlanet_ByExistingTerrain_ReturnListPlanet() {
		PlanetEntity planet = testEntityManager.persistFlushFind(PLANET_LIST.get(0));
		testEntityManager.persistAndFlush(PLANET_LIST.get(1));
		testEntityManager.persistAndFlush(PLANET_LIST.get(2));

		List<PlanetEntity> planets = planetRepository.findByTerrain(planet.getTerrain());

		assertThat(planets).isNotEmpty();
	}

	@Test
	public void listPlanet_ByUnexistingTerrain_ReturnListEmpty() {
		List<PlanetEntity> planets = planetRepository.findByTerrain(anyString());

		assertThat(planets).isEmpty();
	}

	@Test
	public void deletePlanet_WithExistingId_DeletePlanet() {
		PlanetEntity planet = testEntityManager.persistFlushFind(PLANET);

		planetRepository.delete(planet);

		PlanetEntity planetDeleted = testEntityManager.find(PlanetEntity.class, planet.getId());

		assertThat(planetDeleted).isNull();
	}

}
