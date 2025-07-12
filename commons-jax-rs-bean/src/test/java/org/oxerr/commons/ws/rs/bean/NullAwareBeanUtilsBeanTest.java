package org.oxerr.commons.ws.rs.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

class NullAwareBeanUtilsBeanTest {

	@Test
	void testCopyPropertyObjectStringObjectNull() throws IllegalAccessException, InvocationTargetException {
		Bean bean = new Bean();
		bean.setF1("value0");

		NullAwareBeanUtilsBean.getInstance().copyProperty(bean, "f1", null);

		assertEquals("value0", bean.getF1());
	}

	@Test
	void testCopyPropertyObjectStringObject() throws IllegalAccessException, InvocationTargetException {
		Bean bean = new Bean();

		NullAwareBeanUtilsBean.getInstance().copyProperty(bean, "f1", "value1");

		assertEquals("value1", bean.getF1());
	}

}
