package org.oxerr.commons.ws.rs.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Version;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeanUtilsNullTest {

	private Bean dest;

	@BeforeEach
	public void setUp() throws Exception {
		dest = new Bean();
	}

	@Test
	void testPatchTObject() {
		assertNull(BeanUtils.patch(null, null));
		assertEquals(dest, BeanUtils.patch(dest, null));
	}

	@Test
	void testPatchTObjectStringArray() {
		assertNull(BeanUtils.patch(null, null, "f1"));
		assertEquals(dest, BeanUtils.patch(dest, null, "f1"));
	}

	@Test
	void testPatchExcludeTObjectStringArray() {
		assertNull(BeanUtils.patchExclude(null, null, "f2"));
		assertEquals(dest, BeanUtils.patchExclude(dest, null, "f2"));
	}

	@Test
	void testPatchExcludeTObjectSetOfClassOfQextendsAnnotation() {
		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);

		assertNull(BeanUtils.patchExclude(null, null, annotationTypes));
		assertEquals(dest, BeanUtils.patchExclude(dest, null, annotationTypes));
	}

	@Test
	void testPatchExcludeTObjectClassOfQArray() {
		assertNull(BeanUtils.patchExclude(null, null, Version.class));
		assertEquals(dest, BeanUtils.patchExclude(dest, null, Version.class));
	}

	@Test
	void testCopyProperties() {
		assertNull(BeanUtils.copyProperties(null, null, "f1"));
		assertEquals(dest, BeanUtils.copyProperties(dest, null, "f1"));
	}

}
