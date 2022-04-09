package org.oxerr.commons.ws.rs.exceptionmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class ErrorEntityTest {

	@Test
	void testErrorEntity() throws JsonProcessingException {
		ErrorEntity errorEntity = new ErrorEntity();
		assertNull(errorEntity.getCode());

		errorEntity = new ErrorEntity(10001, "The error message");
		assertNull(errorEntity.getException());

		errorEntity = new ErrorEntity(10001, "The error message.", new Exception());

		errorEntity.setCode(10002);
		assertEquals(10002, errorEntity.getCode());

		errorEntity.setMessage("some error occurred");
		assertEquals("some error occurred", errorEntity.getMessage());

		errorEntity.setException(new RuntimeException("some runtime exception"));
		assertEquals("some runtime exception", errorEntity.getException().getMessage());

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(errorEntity);
		assertTrue(json.contains("\"exception\":"));
	}

}
