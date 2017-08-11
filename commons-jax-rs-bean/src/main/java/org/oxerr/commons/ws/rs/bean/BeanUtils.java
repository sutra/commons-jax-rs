package org.oxerr.commons.ws.rs.bean;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public final class BeanUtils {

	private BeanUtils() {
	}

	public static <T> T patch(T dest, Object orig) {
		if (dest == null || orig == null) {
			return dest;
		}

		try {
			NullAwareBeanUtilsBean.getInstance().copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

		return dest;
	}

	public static <T> T patch(T dest, Object orig, String... properties) {
		final BeanWrapper destBeanWrapper = new BeanWrapperImpl(dest);
		final BeanWrapper origBeanWrapper = new BeanWrapperImpl(orig);

		for (final String property : properties) {
			final Object value = origBeanWrapper.getPropertyValue(property);
			if (value != null) {
				destBeanWrapper.setPropertyValue(property, value);
			}
		}

		return dest;
	}

	public static <T> T patchExclude(T dest, Object orig,
			String... excludedProperties) {
		final List<String> excludedPropertyList = Arrays.asList(excludedProperties);

		final BeanWrapper destBeanWrapper = new BeanWrapperImpl(dest);
		final BeanWrapper origBeanWrapper = new BeanWrapperImpl(orig);

		for (final PropertyDescriptor pd : destBeanWrapper.getPropertyDescriptors()) {
			if (pd.getWriteMethod() != null && !excludedPropertyList.contains(pd.getName())) {
				final Object value = origBeanWrapper.getPropertyValue(pd.getName());
				if (value != null) {
					destBeanWrapper.setPropertyValue(pd.getName(), value);
				}
			}
		}

		return dest;
	}

	public static <T> T patchExclude(T dest, Object orig,
			Set<Class<? extends Annotation>> excludedAnnotationTypes) {
		final BeanWrapper destBeanWrapper = new BeanWrapperImpl(dest);
		final BeanWrapper origBeanWrapper = new BeanWrapperImpl(orig);

		for (final PropertyDescriptor pd : destBeanWrapper.getPropertyDescriptors()) {
			if (pd.getWriteMethod() != null) {
				final Set<Annotation> annotations = new HashSet<>();
				annotations.addAll(Arrays.asList(pd.getReadMethod().getAnnotations()));
				annotations.addAll(Arrays.asList(pd.getWriteMethod().getAnnotations()));
				try {
					Field field = dest.getClass().getDeclaredField(pd.getName());
					annotations.addAll(Arrays.asList(field.getAnnotations()));
				} catch (NoSuchFieldException | SecurityException e) {
				}

				final List<Class<? extends Annotation>> annotationTypes = annotations
					.stream().map(annotation -> annotation.annotationType())
					.collect(Collectors.toList());
				annotationTypes.retainAll(excludedAnnotationTypes);

				if (annotationTypes.isEmpty()) {
					final Object value = origBeanWrapper.getPropertyValue(pd.getName());
					if (value != null) {
						destBeanWrapper.setPropertyValue(pd.getName(), value);
					}
				}
			}
		}

		return dest;
	}

	@SuppressWarnings("unchecked")
	public static <T> T patchExclude(T dest, Object orig,
			Class<?>... excludedAnnotationTypes) {
		patchExclude(dest, orig,
			Arrays.asList(excludedAnnotationTypes).stream()
				.map(e -> (Class<? extends Annotation>) e)
				.collect(Collectors.toSet()));
		return dest;
	}

	public static <T> T copyProperties(T dest, Object orig, String... properties) {
		final BeanWrapper destBeanWrapper = new BeanWrapperImpl(dest);
		final BeanWrapper origBeanWrapper = new BeanWrapperImpl(orig);
		for (final String property : properties) {
			final Object value = origBeanWrapper.getPropertyValue(property);
			destBeanWrapper.setPropertyValue(property, value);
		}
		return dest;
	}

}
