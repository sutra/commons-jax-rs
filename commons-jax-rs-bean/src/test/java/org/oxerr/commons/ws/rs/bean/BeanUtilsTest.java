package org.oxerr.commons.ws.rs.bean;

import static org.junit.Assert.assertEquals;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Version;

import org.junit.Test;

public class BeanUtilsTest {

	@Test
	public void testPatchExclude() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		BeanUtils.patchExclude(bean1, bean2, "version");

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	public void testPatchExcludeAnnotationOnField() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);
		BeanUtils.patchExclude(bean1, bean2, annotationTypes);

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	public void testPatchExcludeAnnotationOnGetter() {
		VersionedGetterBean bean1 = new VersionedGetterBean("hello", 1);
		VersionedGetterBean bean2 = new VersionedGetterBean("world", 0);

		Set<Class<? extends Annotation>> annotationTypes = new HashSet<>();
		annotationTypes.add(Version.class);
		BeanUtils.patchExclude(bean1, bean2, annotationTypes);

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

	@Test
	public void testPatchExcludeAnnotation() {
		VersionedFieldBean bean1 = new VersionedFieldBean("hello", 1);
		VersionedFieldBean bean2 = new VersionedFieldBean("world", 0);

		BeanUtils.patchExclude(bean1, bean2, Version.class);

		assertEquals("world", bean1.getName());
		assertEquals(1, bean1.getVersion());
	}

}


class VersionedFieldBean {

	private String name;

	@Version
	private long version;

	public VersionedFieldBean(String name, long version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}

class VersionedGetterBean {

	private String name;
	private long version;

	public VersionedGetterBean(String name, long version) {
		this.name = name;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
