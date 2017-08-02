package org.oxerr.commons.ws.rs.exceptionmapper;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Maps {@link SecurityException} to {@link Response}.
 */
@Provider
@Singleton
public class SecurityExceptionMapper
		implements ExceptionMapper<SecurityException> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response toResponse(SecurityException exception) {
		return Response.status(Response.Status.UNAUTHORIZED)
			.entity(new ErrorEntity(null, exception.getMessage()))
			.build();
	}

}
