package org.oxerr.commons.ws.rs.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.provider.JavaTimeTypesParamConverterProvider;

/**
 * {@code ParamConverterProvider} for converting between a {@code String} and
 * {@link OffsetDateTime}.
 *
 * @deprecated Use org.apache.cxf.jaxrs.provider.JavaTimeTypesParamConverterProvider instead.
 */
@Provider
@Singleton
@Deprecated
public class OffsetDateTimeProvider implements ParamConverterProvider {

	private final JavaTimeTypesParamConverterProvider javaTimeTypesParamConverterProvider = new JavaTimeTypesParamConverterProvider();

	/**
	 * @since 2.3.1
	 */
	public OffsetDateTimeProvider() {
		this(ZoneOffset.UTC);
	}

	public OffsetDateTimeProvider(ZoneOffset offset) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType,
			Type genericType, Annotation[] annotations) {
		return javaTimeTypesParamConverterProvider.getConverter(rawType, genericType, annotations);
	}

}
