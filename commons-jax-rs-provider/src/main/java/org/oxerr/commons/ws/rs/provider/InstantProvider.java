package org.oxerr.commons.ws.rs.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.format.DateTimeParseException;

import javax.inject.Singleton;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;

/**
 * {@code ParamConverterProvider} for converting between a {@code String} and
 * {@link Instant}.
 */
@Provider
@Singleton
public class InstantProvider implements ParamConverterProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType,
			Type genericType, Annotation[] annotations) {
		return (rawType != Instant.class) ? null : new ParamConverter<T>() {

			@Override
			public T fromString(final String value) {
				return rawType.cast(InstantProvider.fromString(value));
			}

			@Override
			public String toString(final T value) throws IllegalArgumentException {
				return InstantProvider.<T>toString(value);
			}
		};
	}

	public static Instant fromString(final String value) {
		if (value == null) {
			throw new IllegalArgumentException("value cannot be null");
		}

		try {
			final BigDecimal d = new BigDecimal(value);
			long seconds = d.longValue();
			int nanoseconds = DecimalUtils.extractNanosecondDecimal(d, seconds);
			return Instant.ofEpochSecond(seconds, nanoseconds);
		} catch (Exception e) {
			// ignore
		}

		try {
			return Instant.parse(value);
		} catch (DateTimeParseException ex) {
			throw new ProcessingException(ex);
		}
	}

	public static <T> String toString(final T value)
			throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("value cannot be null.");
		}
		return value.toString();
	}

}
