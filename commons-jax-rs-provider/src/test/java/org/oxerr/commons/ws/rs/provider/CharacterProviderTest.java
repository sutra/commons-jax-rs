package org.oxerr.commons.ws.rs.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.ws.rs.ext.ParamConverter;

import org.junit.jupiter.api.Test;

class CharacterProviderTest {

	private final CharacterProvider characterProvider = new CharacterProvider();

	@Test
	void testUnsupportedRawType() {
		// Unsupported raw type
		assertNull(this.characterProvider.getConverter(String.class, null, null));
	}

	@Test
	void testFromString() {
		ParamConverter<Character> c = this.characterProvider.getConverter(Character.class, null, null);

		// null string
		assertThrows(IllegalArgumentException.class, () -> c.fromString(null));

		// empty string
		assertNull(c.fromString(""));

		// One character
		assertEquals(Character.valueOf('a'), c.fromString("a"));

		// First character should be return.
		assertEquals(Character.valueOf('a'), c.fromString("ab"));

	}

	@Test
	void testToString() {
		ParamConverter<Character> c = this.characterProvider.getConverter(Character.class, null, null);

		// null
		assertThrows(IllegalArgumentException.class, () -> c.toString(null));

		// char
		assertEquals("a", c.toString('a'));

		// Character
		assertEquals("a", c.toString(Character.valueOf('a')));
	}

}
