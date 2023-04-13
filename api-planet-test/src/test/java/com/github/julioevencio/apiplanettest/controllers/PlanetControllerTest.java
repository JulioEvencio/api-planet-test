package com.github.julioevencio.apiplanettest.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetRequestDTO;
import com.github.julioevencio.apiplanettest.domain.dto.planet.PlanetResponseDTO;
import com.github.julioevencio.apiplanettest.domain.exception.planet.PlanetNotFoundException;
import com.github.julioevencio.apiplanettest.domain.services.PlanetService;
import com.github.julioevencio.apiplanettest.utils.PlanetUtil;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PlanetService planetService;

	private PlanetRequestDTO PLANET_REQUEST_DTO;
	private PlanetRequestDTO PLANET_REQUEST_DTO_NULL;
	private PlanetRequestDTO PLANET_REQUEST_DTO_EMPTY;

	private PlanetResponseDTO PLANET_RESPONSE_DTO;
	private List<PlanetResponseDTO> PLANET_RESPONSE_DTO_LIST;

	private final String BASE_URL = "/v1/planets";

	@BeforeEach
	public void setup() {
		PLANET_REQUEST_DTO = PlanetUtil.makePlanetRequestDTO();
		PLANET_REQUEST_DTO_NULL = PlanetUtil.makePlanetRequestDTONull();
		PLANET_REQUEST_DTO_EMPTY = PlanetUtil.makePlanetRequestDTOEmpty();

		PLANET_RESPONSE_DTO = PlanetUtil.makePlanetResponseDTO();
		PLANET_RESPONSE_DTO_LIST = PlanetUtil.makePlanetResponseDTOList();
	}

	@Test
	public void createPlanet_WithValidData_ReturnCreated() throws Exception {
		when(planetService.create(PLANET_REQUEST_DTO)).thenReturn(PLANET_RESPONSE_DTO);
		
		mockMvc
	        .perform(
	            post(BASE_URL)
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isCreated());
	}
	
	@Test
	public void createPlanet_WithInvalidData_ReturnUnprocessableEntity() throws Exception {
		mockMvc
	        .perform(
	            post(BASE_URL)
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO_NULL))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isUnprocessableEntity());

		mockMvc
	        .perform(
	            post(BASE_URL)
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO_EMPTY))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void createPlanet_WithExistingName_ReturnConflict() throws Exception {
		when(planetService.create(any(PlanetRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc
	        .perform(
	            post(BASE_URL)
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isConflict());
	}

	@Test
	public void getPlanet_ByExistingId_ReturnOk() throws Exception {
		when(planetService.findById(1L)).thenReturn(PLANET_RESPONSE_DTO);
		
		mockMvc
	        .perform(get(BASE_URL + "/1"))
	        .andExpect(status().isOk());
	}
	
	@Test
	public void getPlanet_ByUnexistingId_ReturnNotFound() throws Exception {
		when(planetService.findById(anyLong())).thenThrow(PlanetNotFoundException.class);

		mockMvc
			.perform(get(BASE_URL + "/1"))
	        .andExpect(status().isNotFound());
	}
	
	@Test
	public void getPlanet_ByExistingName_ReturnOk() throws Exception {
		when(planetService.findByName(PLANET_RESPONSE_DTO.getName())).thenReturn(PLANET_RESPONSE_DTO);
		
		mockMvc
	        .perform(get(BASE_URL + "/name/" + PLANET_RESPONSE_DTO.getName()))
	        .andExpect(status().isOk());
	}
	
	@Test
	public void getPlanet_ByUnexistingName_ReturnNotFound() throws Exception {
		when(planetService.findByName(anyString())).thenThrow(PlanetNotFoundException.class);
		
		mockMvc
			.perform(get(BASE_URL + "/name/Planet"))
	        .andExpect(status().isNotFound());
	}
	
	@Test
	public void listPlanet_All_ReturnListPlanetResponseDTO() throws Exception {
		when(planetService.findAll()).thenReturn(PLANET_RESPONSE_DTO_LIST);
		
		mockMvc
			.perform(get(BASE_URL))
			.andExpect(status().isOk());
	}
	
	@Test
	public void putPlanet_WithValidData_ReturnOk() throws Exception {
		when(planetService.update(1L, PLANET_REQUEST_DTO)).thenReturn(PLANET_RESPONSE_DTO);
		
		mockMvc
	        .perform(
	            put(BASE_URL + "/1")
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isOk());
	}
	
	@Test
	public void putPlanet_WithInvalidData_ReturnUnprocessableEntity() throws Exception {
		mockMvc
	        .perform(
	        	put(BASE_URL + "/1")
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO_NULL))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isUnprocessableEntity());

		mockMvc
	        .perform(
	        	put(BASE_URL + "/1")
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO_EMPTY))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void putPlanet_WithExistingName_ReturnConflict() throws Exception {
		when(planetService.update(anyLong(), any(PlanetRequestDTO.class))).thenThrow(DataIntegrityViolationException.class);
		
		mockMvc
	        .perform(
	        	put(BASE_URL + "/1")
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isConflict());
	}
	
	@Test
	public void putPlanet_ByUnexistingId_ReturnNotFound() throws Exception {
		when(planetService.update(anyLong(), any(PlanetRequestDTO.class))).thenThrow(PlanetNotFoundException.class);

		mockMvc
			.perform(
	        	put(BASE_URL + "/1")
	            .content(objectMapper.writeValueAsString(PLANET_REQUEST_DTO))
	            .contentType(MediaType.APPLICATION_JSON)
	        )
	        .andExpect(status().isNotFound());
	}
	
	@Test
	public void deletePlanet_ByExistingId_ReturnNoContent() throws Exception {
		mockMvc
			.perform(delete(BASE_URL + "/1"))
	        .andExpect(status().isNoContent());
	}
	
	@Test
	public void deletePlanet_ByUnexistingId_ReturnNotFound() throws Exception {
		doThrow(new PlanetNotFoundException("Planet not found")).when(planetService).delete(anyLong());

		mockMvc
			.perform(delete(BASE_URL + "/1"))
	        .andExpect(status().isNotFound());
	}

}
