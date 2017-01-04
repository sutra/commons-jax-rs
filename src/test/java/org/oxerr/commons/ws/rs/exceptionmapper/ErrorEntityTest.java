package org.oxerr.commons.ws.rs.exceptionmapper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorEntityTest {

	@Test
	public void testErrorEntity() throws JsonProcessingException {
		ErrorEntity errorEntity = new ErrorEntity(10001, "The error message.", new Exception());
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(errorEntity);
		assertTrue(json.contains("\"exception\":"));
	}

}
