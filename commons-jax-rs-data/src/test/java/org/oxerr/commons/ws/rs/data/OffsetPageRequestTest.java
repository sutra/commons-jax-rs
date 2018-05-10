package org.oxerr.commons.ws.rs.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.data.domain.Pageable;

public class OffsetPageRequestTest {

	@Test
	public void testNext() {
		Pageable p = new OffsetPageRequest();
		assertEquals(0, p.getPageNumber());

		Pageable next = p.next();
		assertEquals(1, next.getPageNumber());
	}

}
