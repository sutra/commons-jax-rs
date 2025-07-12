package org.oxerr.commons.ws.rs.exceptionmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

class ObjectOptimisticLockingFailureExceptionMapperTest {

	private final ObjectOptimisticLockingFailureExceptionMapper objectOptimisticLockingFailureExceptionMapper = new ObjectOptimisticLockingFailureExceptionMapper();

	@Test
	void testToResponse() {
		ObjectOptimisticLockingFailureException objectOptimisticLockingFailureException = new ObjectOptimisticLockingFailureException("Optimistic locking failure", new Exception());
		Response response = this.objectOptimisticLockingFailureExceptionMapper.toResponse(objectOptimisticLockingFailureException);
		assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
		ErrorEntity entity = (ErrorEntity) response.getEntity();
		assertEquals(objectOptimisticLockingFailureException.getMessage(), entity.getMessage());
	}

}
