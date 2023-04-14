package com.github.julioevencio.apiplanettest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetResponseDTO;
import com.github.julioevencio.apiplanettest.utils.PlanetUtil;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PlanetIT {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private PlanetResponseDTO PLANET_RESPONSE_DTO;
	
	private final String BASE_URL = "/v1/planets";
	
	@BeforeEach
	public void setup() {
		PLANET_RESPONSE_DTO = PlanetUtil.makePlanetResponseDTO();
	}
	
	@Test
	@Order(1)
	public void createPlanet_WithValidData_ReturnCreated() {
		ResponseEntity<PlanetResponseDTO> sut = testRestTemplate
				.postForEntity(BASE_URL, PLANET_RESPONSE_DTO, PlanetResponseDTO.class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(sut.getBody().getId()).isNotNull();
		assertThat(sut.getBody().getName()).isEqualTo(PLANET_RESPONSE_DTO.getName());
		assertThat(sut.getBody().getClimate()).isEqualTo(PLANET_RESPONSE_DTO.getClimate());
		assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET_RESPONSE_DTO.getTerrain());
	}
	
	@Test
	@Order(2)
	public void getPlanetByExistingId_ReturnOk() {
		ResponseEntity<PlanetResponseDTO> sut = testRestTemplate
				.getForEntity(BASE_URL + "/" + PLANET_RESPONSE_DTO.getId(), PlanetResponseDTO.class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(sut.getBody().getId()).isEqualTo(PLANET_RESPONSE_DTO.getId());
	}
	
	@Test
	@Order(3)
	public void getPlanetByExistingName_ReturnOk() {
		ResponseEntity<PlanetResponseDTO> sut = testRestTemplate
				.getForEntity(BASE_URL + "/name/" + PLANET_RESPONSE_DTO.getName(), PlanetResponseDTO.class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(sut.getBody().getName()).isEqualTo(PLANET_RESPONSE_DTO.getName());
	}
	
	@Test
	@Order(4)
	public void listPlanet_All_ReturnOk() {
		ResponseEntity<PlanetResponseDTO[]> sut = testRestTemplate
				.getForEntity(BASE_URL, PlanetResponseDTO[].class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(sut.getBody()).hasSize(1);
	}
	
	@Test
	@Order(5)
	public void listPlanet_ByClimate_ReturnOk() {
		ResponseEntity<PlanetResponseDTO[]> sut = testRestTemplate
				.getForEntity(BASE_URL + "/climate/" + PLANET_RESPONSE_DTO.getClimate(), PlanetResponseDTO[].class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(sut.getBody()).hasSize(1);
	}
	
	@Test
	@Order(6)
	public void listPlanet_ByTerrain_ReturnOk() {
		ResponseEntity<PlanetResponseDTO[]> sut = testRestTemplate
				.getForEntity(BASE_URL + "/terrain/" + PLANET_RESPONSE_DTO.getTerrain(), PlanetResponseDTO[].class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(sut.getBody()).hasSize(1);
	}
	
	@Test
	@Order(7)
	public void deletePlanet_WithExistingId_ReturnNoContent() {
		ResponseEntity<Void> sut = testRestTemplate
				.exchange(BASE_URL + "/" + PLANET_RESPONSE_DTO.getId(), HttpMethod.DELETE, null, Void.class);
		
		assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
	
}
