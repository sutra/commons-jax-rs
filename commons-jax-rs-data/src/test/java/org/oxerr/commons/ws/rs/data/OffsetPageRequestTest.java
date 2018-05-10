package org.oxerr.commons.ws.rs.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.data.domain.Pageable;

public class OffsetPageRequestTest {

	@Test
	public void testNext() {
		Pageable p = new OffsetPageRequest();
		assertEquals(0, p.getPageNumber());
		assertEquals(10, p.getPageSize());
		assertEquals(0, p.getOffset());

		Pageable next = p.next();
		assertEquals(1, next.getPageNumber());
		assertEquals(10, next.getPageSize());
		assertEquals(10, next.getOffset());
	}

}
