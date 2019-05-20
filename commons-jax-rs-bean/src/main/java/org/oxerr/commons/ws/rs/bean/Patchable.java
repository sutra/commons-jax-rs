package org.oxerr.commons.ws.rs.bean;

/**
 * Marks as the object is patchable.
 *
 * @param <T> the argument type.
 */
public interface Patchable<T> {

	/**
	 * Patch this bean with the specified bean.
	 *
	 * @param <S> the type of patching object.
	 * @param patch the patching object.
	 * @return the patched object, used to chain calls.
	 */
	<S extends T> T patch(S patch);

}
