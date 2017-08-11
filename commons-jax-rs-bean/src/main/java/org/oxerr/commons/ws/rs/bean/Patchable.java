package org.oxerr.commons.ws.rs.bean;

/**
 * Marks as the object is patchable.
 *
 * @param <T> the argument type.
 */
public interface Patchable<T> {

	/**
	 * Returns the patched object, for method chaining.
	 *
	 * @param t the patching object.
	 * @return the patched object.
	 */
	T patch(T t);

}
