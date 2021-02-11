package org.oxerr.commons.ws.rs.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

class OffsetPageRequestTest {

	@Test
	void testGetSort() {
		PageRequest pr = PageRequest.of(0, 10);
		assertEquals(Sort.unsorted(), pr.getSort());

		OffsetPageRequest opr = new OffsetPageRequest();
		assertEquals(Sort.unsorted(), opr.getSort());
	}

	@Test
	void testNext() {
		Pageable p = new OffsetPageRequest();
		assertEquals(0, p.getPageNumber());
		assertEquals(10, p.getPageSize());
		assertEquals(0, p.getOffset());

		Pageable next = p.next();
		assertEquals(1, next.getPageNumber());
		assertEquals(10, next.getPageSize());
		assertEquals(10, next.getOffset());
	}

	@Test
	void defaultSort() {
		OffsetPageRequest opr = new OffsetPageRequest(5, 1, null);
		assertEquals(1L, opr.getOffset());
		Pageable p = opr.defaultSort(Direction.DESC, "createdDate");
		assertEquals(1L, p.getOffset());
	}

}
