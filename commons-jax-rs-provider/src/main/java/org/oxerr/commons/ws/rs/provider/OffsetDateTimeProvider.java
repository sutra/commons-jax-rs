package org.oxerr.commons.ws.rs.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

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

	private final ZoneOffset offset;

	/**
	 * @since 2.3.1
	 */
	public OffsetDateTimeProvider() {
		this(ZoneOffset.UTC);
	}

	public OffsetDateTimeProvider(ZoneOffset offset) {
		this.offset = offset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType,
			Type genericType, Annotation[] annotations) {
		return (rawType != OffsetDateTime.class) ? null : new ParamConverter<T>() {

			@Override
			public T fromString(final String value) {
				return rawType.cast(InstantProvider.fromString(value).atOffset(offset));
			}

			@Override
			public String toString(final T value) throws IllegalArgumentException {
				return InstantProvider.<T>toString(value);
			}
		};
	}

}
