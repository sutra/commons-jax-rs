package org.oxerr.commons.ws.rs.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Version;

import org.junit.Before;
import org.junit.Test;

public class BeanUtilsNullTest {

	private Bean dest;

	@Before
	public void setUp() throws Exception {
		dest = new Bean();
	}

	@Test
	public void testPatchTObject() {
		assertNull(BeanUtils.patch(null, null));
		assertEquals(dest, BeanUtils.patch(dest, null));
	}

	@Test
	public void testPatchTObjectStringArray() {
		assertNull(BeanUtils.patch(null, null, "f1"));
		assertEquals(dest, BeanUtils.patch(dest, null, "f1"));
	}

	@Test
	public void testPatchExcludeTObjectStringArray() {
		assertNull(BeanUtils.patchExclude(null, null, "f2"));
		assertEquals(dest, BeanUtils.patchExclude(dest, null, "f2"));
	}

	@Test
	public void testPatchExcludeTObjectSetOfClassOfQextendsAnnotation() {
		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);

		assertNull(BeanUtils.patchExclude(null, null, annotationTypes));
		assertEquals(dest, BeanUtils.patchExclude(dest, null, annotationTypes));
	}

	@Test
	public void testPatchExcludeTObjectClassOfQArray() {
		assertNull(BeanUtils.patchExclude(null, null, Version.class));
		assertEquals(dest, BeanUtils.patchExclude(dest, null, Version.class));
	}

	@Test
	public void testCopyProperties() {
		assertNull(BeanUtils.copyProperties(null, null, "f1"));
		assertEquals(dest, BeanUtils.copyProperties(dest, null, "f1"));
	}

	class Bean {

		private String f1;
		private String f2;

		public String getF1() {
			return f1;
		}

		public void setF1(String f1) {
			this.f1 = f1;
		}

		public String getF2() {
			return f2;
		}

		public void setF2(String f2) {
			this.f2 = f2;
		}

	}

}
