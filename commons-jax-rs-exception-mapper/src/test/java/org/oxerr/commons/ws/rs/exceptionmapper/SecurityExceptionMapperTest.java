package org.oxerr.commons.ws.rs.exceptionmapper;

import static org.junit.jupiter.api.Assertions.*;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecurityExceptionMapperTest {

	private final SecurityExceptionMapper securityExceptionMapper = new SecurityExceptionMapper();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testToResponse() {
		SecurityException securityException = new SecurityException("Not allowed");
		Response response = this.securityExceptionMapper.toResponse(securityException);
		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
		ErrorEntity entity = (ErrorEntity) response.getEntity();
		assertEquals(securityException.getMessage(), entity.getMessage());
	}

}
