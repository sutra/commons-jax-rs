package org.oxerr.commons.ws.rs.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

class OffsetPageRequestTest {

	@Test
	void testOffsetPageRequestIntLongSort() {
		assertThrows(IllegalArgumentException.class, () -> new OffsetPageRequest(1, 0, null));
	}

	@Test
	void testOf() {
		OffsetPageRequest p = new OffsetPageRequest();

		assertEquals(p, OffsetPageRequest.of());
		assertEquals(p, OffsetPageRequest.of(10, 0));
		assertEquals(p, OffsetPageRequest.of(10, 0, Sort.unsorted()));
	}

	@Test
	void testDefaultSort() {
		OffsetPageRequest p = new OffsetPageRequest();
		OffsetPageRequest p0 = p.defaultSort(Direction.DESC, "f1");

		assertEquals(Sort.by(Direction.DESC, "f1"), p0.getSort());
		assertEquals(Sort.by(Direction.DESC, "f1"), p0.defaultSort(Direction.ASC, "f2").getSort());
	}

	@Test
	void testSetLimit() {
		OffsetPageRequest p = new OffsetPageRequest();
		p.setLimit(p.getMaxLimit());
		assertEquals(p.getMaxLimit(), p.getPageSize());

		int exceededLimit = p.getMaxLimit() + 1;
		assertThrows(IllegalArgumentException.class, () -> p.setLimit(exceededLimit));
	}

	@Test
	void testSetOffset() {
		OffsetPageRequest p = new OffsetPageRequest();
		p.setOffset(1);
		assertEquals(1, p.getOffset());
	}

	@Test
	void testSetSort() {
		OffsetPageRequest p = new OffsetPageRequest();

		p.setSort(Collections.emptyList());
		assertEquals(Sort.unsorted(), p.getSort());

		p.setSort(Arrays.asList(""));
		assertEquals(Sort.unsorted(), p.getSort());

		p.setSort(Arrays.asList("f1"));
		assertEquals(Sort.by(Direction.ASC, "f1"), p.getSort());

		p.setSort(Arrays.asList("f1,desc"));
		assertEquals(Sort.by(Direction.DESC, "f1"), p.getSort());
	}

	@Test
	void testGetPageNumber() {
		OffsetPageRequest p = new OffsetPageRequest();
		assertEquals(10, p.getPageSize());
		assertEquals(0, p.getPageNumber());

		p.setOffset(9);
		assertEquals(0, p.getPageNumber());

		p.setOffset(10);
		assertEquals(1, p.getPageNumber());

		p.setOffset(11);
		assertEquals(1, p.getPageNumber());

		p.setOffset(19);
		assertEquals(1, p.getPageNumber());

		p.setOffset(20);
		assertEquals(2, p.getPageNumber());

		p.setOffset(21);
		assertEquals(2, p.getPageNumber());

		p.setLimit(0);
		assertEquals(0, p.getPageNumber());

		p.setLimit(-1);
		assertEquals(0, p.getPageNumber());
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
	void testPrevious() {
		OffsetPageRequest p = new OffsetPageRequest();
		assertEquals(p, p.previous());

		assertEquals(p, p.next().previous());

		p.setOffset(10);
		assertEquals(p, p.next().previous());
	}

	@Test
	void testPreviousOrFirst() {
		OffsetPageRequest p = new OffsetPageRequest();
		assertEquals(p, p.previousOrFirst());

		assertEquals(p, p.next().previousOrFirst());

		p.setOffset(10);
		assertEquals(p, p.next().previousOrFirst());
	}

	@Test
	void testFirst() {
		Pageable p = new OffsetPageRequest();
		assertEquals(p, p.first());
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

	@Test
	void testHashCode() {
		OffsetPageRequest p = new OffsetPageRequest();
		assertEquals(p.hashCode(), OffsetPageRequest.of().hashCode());
	}

	@Test
	void testEquals() {
		OffsetPageRequest p = new OffsetPageRequest();
		assertTrue(() -> p.equals(p));
		assertFalse(() -> p.equals(null));
		assertFalse(() -> p.equals(new Object()));
		assertFalse(() -> p.equals(new OffsetPageRequest(11, 0)));
		assertFalse(() -> p.equals(new OffsetPageRequest(10, 1)));
		assertFalse(() -> p.equals(new OffsetPageRequest().defaultSort(Direction.DESC, "f1")));
		assertTrue(() -> p.equals(new OffsetPageRequest()));
	}

	@Test
	void testToString() {
		OffsetPageRequest p = new OffsetPageRequest();
		assertEquals("Page request [number: 0, size 10, sort: UNSORTED]", p.toString());
	}

}
