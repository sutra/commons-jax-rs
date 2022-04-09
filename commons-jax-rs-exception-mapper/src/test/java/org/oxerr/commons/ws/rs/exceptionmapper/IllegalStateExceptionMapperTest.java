package org.oxerr.commons.ws.rs.exceptionmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

class IllegalStateExceptionMapperTest {

	private final IllegalStateExceptionMapper illegalStateExceptionMapper = new IllegalStateExceptionMapper();

	@Test
	void testToResponse() {
		IllegalStateException illegalStateException = new IllegalStateException("Illegal state");
		Response response = this.illegalStateExceptionMapper.toResponse(illegalStateException);
		assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
		ErrorEntity entity = (ErrorEntity) response.getEntity();
		assertEquals(illegalStateException.getMessage(), entity.getMessage());
	}

}
