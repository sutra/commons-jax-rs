package org.oxerr.commons.ws.rs.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

class OffsetPageRequestTest {

	@Test
	void testOffsetPageRequestIntLongSort() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new OffsetPageRequest(1, 0, null));
	}

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
	void testWithPage() {
		Pageable p = new OffsetPageRequest();
		assertEquals(0, p.getPageNumber());
		assertEquals(10, p.getPageSize());
		assertEquals(0, p.getOffset());

		Pageable p0 = p.withPage(0);
		assertEquals(0, p0.getPageNumber());
		assertEquals(10, p0.getPageSize());
		assertEquals(0, p0.getOffset());

		Pageable p1 = p0.withPage(1);

		assertEquals(p.withPage(1), p1);

		assertEquals(1, p1.getPageNumber());
		assertEquals(10, p1.getPageSize());
		assertEquals(10, p1.getOffset());

		Pageable p2 = p1.withPage(2);

		assertEquals(p.withPage(2), p2);

		assertEquals(2, p2.getPageNumber());
		assertEquals(10, p2.getPageSize());
		assertEquals(20, p2.getOffset());
	}

	@Test
	void defaultSort() {
		OffsetPageRequest opr = new OffsetPageRequest(5, 1, Sort.unsorted());
		assertEquals(1L, opr.getOffset());
		Pageable p = opr.defaultSort(Direction.DESC, "createdDate");
		assertEquals(1L, p.getOffset());
	}

	@Test
	void testUnsort() {
		OffsetPageRequest opr = new OffsetPageRequest();
		assertEquals(Sort.unsorted(), opr.getSort());
	}

	@Test
	void testFilterProperty() {
		OffsetPageRequest r = new OffsetPageRequest();
		r.setSort(Arrays.asList("the'field"));
		assertEquals(Sort.by(Order.asc("the''field")), r.getSort());
	}

	@Test
	void testParseDirection() {
		OffsetPageRequest r = new OffsetPageRequest();
		assertEquals(Optional.empty(), r.parseDirection(null));
		assertEquals(Optional.of(Direction.ASC), r.parseDirection("asc"));
		assertEquals(Optional.of(Direction.DESC), r.parseDirection("desc"));
		assertEquals(Optional.empty(), r.parseDirection("unknown"));
	}

	@Test
	void testParseSort() {
		OffsetPageRequest r = new OffsetPageRequest();

		r.setSort(Arrays.asList((String) null));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList(""));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList(","));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList(", "));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList(" ,"));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList(" , "));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList("field1"));
		assertEquals(Sort.by(Order.asc("field1")), r.getSort());

		r.setSort(Arrays.asList("field2,"));
		assertEquals(Sort.by(Order.asc("field2")), r.getSort());

		r.setSort(Arrays.asList("field3, "));
		assertEquals(Sort.by(Order.asc("field3")), r.getSort());

		r.setSort(Arrays.asList("field4,desc"));
		assertEquals(Sort.by(Order.desc("field4")), r.getSort());

		r.setSort(Arrays.asList("field5,field6"));
		assertEquals(Sort.by(Order.asc("field5"), Order.asc("field6")), r.getSort());

		r.setSort(Arrays.asList("asc"));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList("desc"));
		assertEquals(Sort.unsorted(), r.getSort());

		r.setSort(Arrays.asList("asc,desc"));
		assertEquals(Sort.by(Order.desc("asc")), r.getSort());
	}

}
