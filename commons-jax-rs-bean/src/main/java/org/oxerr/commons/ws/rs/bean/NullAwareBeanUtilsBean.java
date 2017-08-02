package org.oxerr.commons.ws.rs.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ContextClassLoaderLocal;

public class NullAwareBeanUtilsBean extends BeanUtilsBean {

	/**
	 * Contains <code>NullAwareBeanUtilsBean</code> instances indexed by context classloader.
	 */
	private static final ContextClassLoaderLocal<NullAwareBeanUtilsBean>
		BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal<NullAwareBeanUtilsBean>() {
			// Creates the default instance used when the context classloader is unavailable
			@Override
			protected NullAwareBeanUtilsBean initialValue() {
				return new NullAwareBeanUtilsBean();
			}
		};

	/**
	 * Gets the instance which provides the functionality for {@link BeanUtils}.
	 * This is a pseudo-singleton - an single instance is provided per (thread) context classloader.
	 * This mechanism provides isolation for web apps deployed in the same container.
	 *
	 * @return The (pseudo-singleton) BeanUtils bean instance
	 */
	public static NullAwareBeanUtilsBean getInstance() {
		return BEANS_BY_CLASSLOADER.get();
	}

	@Override
	public void copyProperty(Object dest, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		if (value == null) {
			return;
		}

		super.copyProperty(dest, name, value);
	}

}
