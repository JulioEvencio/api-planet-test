package com.github.julioevencio.apiplanettest.domain.exception.planet;

public class PlanetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlanetNotFoundException(String message) {
		super(message);
	}

}
