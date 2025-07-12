package org.oxerr.commons.ws.rs.exceptionmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

class IllegalArgumentExceptionMapperTest {

	private final IllegalArgumentExceptionMapper illegalArgumentExceptionMapper = new IllegalArgumentExceptionMapper();

	@Test
	void testToResponse() {
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid name");
		Response response = this.illegalArgumentExceptionMapper.toResponse(illegalArgumentException);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		ErrorEntity entity = (ErrorEntity) response.getEntity();
		assertEquals(illegalArgumentException.getMessage(), entity.getMessage());
	}

}
