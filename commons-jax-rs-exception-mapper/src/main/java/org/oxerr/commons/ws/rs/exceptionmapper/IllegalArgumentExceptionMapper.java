package org.oxerr.commons.ws.rs.exceptionmapper;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps {@link IllegalArgumentException} to {@link Response}.
 */
@Provider
@Singleton
public class IllegalArgumentExceptionMapper
	implements ExceptionMapper<IllegalArgumentException> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response toResponse(IllegalArgumentException exception) {
		return Response.status(Response.Status.BAD_REQUEST)
			.entity(new ErrorEntity(null, exception.getMessage()))
			.build();
	}

}
