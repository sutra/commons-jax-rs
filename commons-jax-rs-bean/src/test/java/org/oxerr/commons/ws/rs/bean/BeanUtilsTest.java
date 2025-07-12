package org.oxerr.commons.ws.rs.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Version;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;

class BeanUtilsTest {

	@Test
	void testPatch() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		BeanUtils.patch(bean1, bean2);

		assertEquals("world", bean1.getName());
		assertEquals(0, bean1.getVersion());
	}

	@Test
	void testPatchWithIllegalAccessException() {
		// Mock
		Bean bean1 = PowerMockito.mock(Bean.class);
		Bean bean2 = PowerMockito.mock(Bean.class);

		when(bean2.getF1()).thenAnswer(invocation -> {
			throw new IllegalAccessException();
		});

		// Invoke and assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> BeanUtils.patch(bean1, bean2));
	}

	@Test
	void testPatchWithInvocationTargetException() {
		// Mock
		Bean bean1 = PowerMockito.mock(Bean.class);
		Bean bean2 = PowerMockito.mock(Bean.class);

		when(bean2.getF1()).thenAnswer(invocation -> {
			throw new InvocationTargetException(new Exception());
		});

		// Invoke and assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> BeanUtils.patch(bean1, bean2));
	}

	@Test
	void testPatchProperties() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		BeanUtils.patch(bean1, bean2, "name");

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	void testPatchExclude() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		BeanUtils.patchExclude(bean1, bean2, "version");

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	void testPatchExcludeAnnotationOnField() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);
		BeanUtils.patchExclude(bean1, bean2, annotationTypes);

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	void testPatchExcludeAnnotationOnGetter() {
		VersionedGetterBean bean1 = new VersionedGetterBean("hello", 1);
		VersionedGetterBean bean2 = new VersionedGetterBean("world", 0);

		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);
		BeanUtils.patchExclude(bean1, bean2, annotationTypes);

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	void testPatchExcludeWithOrigNoSuchFieldException() {
		// Mock
		VersionedGetterBean bean1 = new VersionedGetterBean("hello", 1);
		VersionedGetterBean bean2 = mock(VersionedGetterBean.class);

		PowerMockito.when(bean2.getName()).thenAnswer(invocation -> {
			throw new NoSuchFieldException();
		});

		// Invoke
		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);
		BeanUtils.patchExclude(bean1, bean2, annotationTypes);

		// Assert
		assertEquals("hello", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	void testPatchExcludeAnnotation() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		BeanUtils.patchExclude(bean1, bean2, Version.class);

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	void testCopyProperties() {
		Bean bean1 = new Bean("b1f1", "b1f2");
		Bean bean2 = new Bean("b2f1", "b2f2");

		BeanUtils.copyProperties(bean1, bean2, "f1");

		assertEquals("b2f1", bean1.getF1());
		assertEquals("b1f2", bean1.getF2());
	}

}
