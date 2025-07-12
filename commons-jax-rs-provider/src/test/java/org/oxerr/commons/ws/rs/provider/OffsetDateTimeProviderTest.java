package org.oxerr.commons.ws.rs.provider;

import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import javax.ws.rs.ext.ParamConverter;

import org.apache.cxf.jaxrs.provider.JavaTimeTypesParamConverterProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OffsetDateTimeProviderTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testUnsupportedRawType() {
		OffsetDateTimeProvider offsetDateTimeProvider = new OffsetDateTimeProvider();
		assertNull(offsetDateTimeProvider.getConverter(String.class, null, null));
	}

	@Test
	void testFromString() {
		OffsetDateTimeProvider offsetDateTimeProvider = new OffsetDateTimeProvider(ZoneOffset.UTC);
		ParamConverter<OffsetDateTime> c = offsetDateTimeProvider.getConverter(OffsetDateTime.class, null, null);

		assertEquals(OffsetDateTime.MIN, c.fromString("-999999999-01-01T00:00:00+18:00"));
		assertEquals(OffsetDateTime.MAX, c.fromString("+999999999-12-31T23:59:59.999999999-18:00"));
	}

	@Test
	void testToString() {
		OffsetDateTimeProvider offsetDateTimeProvider = new OffsetDateTimeProvider(ZoneOffset.UTC);
		ParamConverter<OffsetDateTime> c = offsetDateTimeProvider.getConverter(OffsetDateTime.class, null, null);

		assertEquals("-999999999-01-01T00:00:00+18:00", c.toString(OffsetDateTime.MIN));
		assertEquals("+999999999-12-31T23:59:59.999999999-18:00", c.toString(OffsetDateTime.MAX));
	}

	@Test
	void testJavaTimeTypesParamConverterProvider() {
		JavaTimeTypesParamConverterProvider p = new JavaTimeTypesParamConverterProvider();
		ParamConverter<OffsetDateTime> c = p.getConverter(OffsetDateTime.class, null, null);

		assertEquals(OffsetDateTime.MIN, c.fromString("-999999999-01-01T00:00+18:00"));
		assertEquals(OffsetDateTime.MAX, c.fromString("+999999999-12-31T23:59:59.999999999-18:00"));

		assertEquals("-999999999-01-01T00:00:00+18:00", c.toString(OffsetDateTime.MIN));
		assertEquals("+999999999-12-31T23:59:59.999999999-18:00", c.toString(OffsetDateTime.MAX));
	}

}
