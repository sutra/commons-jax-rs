package org.oxerr.commons.ws.rs.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ext.ParamConverter;

import org.junit.jupiter.api.Test;

class InstantProviderTest {

	private final InstantProvider instantProvider = new InstantProvider();

	@Test
	void testUnsupportedRawType() {
		// Unsupported raw type
		assertNull(this.instantProvider.getConverter(String.class, null, null));
	}

	@Test
	void testFromString() {
		ParamConverter<Instant> c = this.instantProvider.getConverter(Instant.class, getClass(), null);

		// null string
		assertThrows(IllegalArgumentException.class, () -> c.fromString(null));

		// empty string
		assertThrows(ProcessingException.class, () -> c.fromString(""));

		// epoch
		assertEquals(Instant.EPOCH, c.fromString("0"));
		assertEquals(Instant.EPOCH, c.fromString("1970-01-01T00:00:00Z"));

		// min
		assertEquals(Instant.MIN, c.fromString("-31557014167219200"));
		assertEquals(Instant.MIN, c.fromString("-1000000000-01-01T00:00:00Z"));

		// max
		assertEquals(Instant.MAX, c.fromString("31556889864403199.999999999"));
		assertEquals(Instant.MAX, c.fromString("+1000000000-12-31T23:59:59.999999999Z"));
	}

	@Test
	void testToString() {
		ParamConverter<Instant> c = this.instantProvider.getConverter(Instant.class, getClass(), null);

		// null
		assertThrows(IllegalArgumentException.class, () -> c.toString(null));

		// epoch
		assertEquals("1970-01-01T00:00:00Z", c.toString(Instant.EPOCH));

		// min
		assertEquals("-1000000000-01-01T00:00:00Z", c.toString(Instant.MIN));

		// max
		assertEquals("+1000000000-12-31T23:59:59.999999999Z", c.toString(Instant.MAX));
	}

}
