package org.oxerr.commons.ws.rs.exceptionmapper;

import java.io.Serializable;

/**
 * Response entity for exceptions.
 */
public class ErrorEntity implements Serializable {

	private static final long serialVersionUID = 2016123001L;

	private Integer code;
	private String message;
	private Throwable exception;

	public ErrorEntity() {
	}

	public ErrorEntity(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public ErrorEntity(Integer code, String message, Throwable exception) {
		this.code = code;
		this.message = message;
		this.exception = exception;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

}
