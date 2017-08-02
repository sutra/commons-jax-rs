package org.oxerr.commons.ws.rs.exceptionmapper;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

/**
 * Maps {@link ObjectOptimisticLockingFailureException} to {@link Response}.
 */
@Provider
@Singleton
public class ObjectOptimisticLockingFailureExceptionMapper
		implements ExceptionMapper<ObjectOptimisticLockingFailureException> {

	@Override
	public Response toResponse(ObjectOptimisticLockingFailureException exception) {
		return Response.status(Response.Status.CONFLICT)
			.entity(new ErrorEntity(null, exception.getMessage()))
			.build();
	}

}
