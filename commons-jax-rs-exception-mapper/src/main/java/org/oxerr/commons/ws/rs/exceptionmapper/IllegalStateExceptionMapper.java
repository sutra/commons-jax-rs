package org.oxerr.commons.ws.rs.exceptionmapper;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps {@link IllegalStateException} to {@link Response}.
 */
@Provider
@Singleton
public class IllegalStateExceptionMapper
		implements ExceptionMapper<IllegalStateException> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response toResponse(IllegalStateException exception) {
		return Response.status(Response.Status.CONFLICT)
			.entity(new ErrorEntity(null, exception.getMessage()))
			.build();
	}

}
