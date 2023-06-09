package com.github.julioevencio.apiplanettest.domain.exception;

import java.io.Serializable;
import java.util.List;

public class ApiMessageError implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private List<String> errors;

	public ApiMessageError(String message, List<String> errors) {
		this.message = message;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
