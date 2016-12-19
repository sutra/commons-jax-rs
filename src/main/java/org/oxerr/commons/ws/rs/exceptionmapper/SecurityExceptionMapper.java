package org.oxerr.commons.ws.rs.exceptionmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Maps {@link SecurityException} to {@link Response}.
 */
public class SecurityExceptionMapper
		implements ExceptionMapper<SecurityException> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Response toResponse(SecurityException exception) {
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}

}
