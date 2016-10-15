package org.oxerr.commons.ws.rs.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.inject.Singleton;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * {@code ParamConverterProvider} for converting between a {@code String} and
 * {@link Character}.
 */
@Provider
@Singleton
public class CharacterProvider implements ParamConverterProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType,
			Type genericType, Annotation[] annotations) {
		return (rawType != Character.class) ? null : new ParamConverter<T>() {

			@Override
			public T fromString(final String value) {
				if (value == null) {
					throw new IllegalArgumentException("value cannot be null");
				}

				final T ret;
				if (value.isEmpty()) {
					ret = null;
				} else {
					ret = rawType.cast(Character.valueOf(value.charAt(0)));
				}

				return ret;
			}

			@Override
			public String toString(final T value)
					throws IllegalArgumentException {
				if (value == null) {
					throw new IllegalArgumentException("value cannot be null.");
				}
				return value.toString();
			}
		};
	}

}
