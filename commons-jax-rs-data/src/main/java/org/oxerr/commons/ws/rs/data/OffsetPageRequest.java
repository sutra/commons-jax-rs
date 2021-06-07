package org.oxerr.commons.ws.rs.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

/**
 * {@link Pageable} implementation using limit-offset mode.
 */
public class OffsetPageRequest implements Pageable, Serializable {

	public static OffsetPageRequest of() {
		return new OffsetPageRequest();
	}

	public static OffsetPageRequest of(int limit, long offset) {
		return new OffsetPageRequest(limit, offset);
	}

	public static OffsetPageRequest of(int limit, long offset, Sort sort) {
		return new OffsetPageRequest(limit, offset, sort);
	}

	private static final long serialVersionUID = 2017071101L;

	private static final int DEFAULT_MAX_LIMIT = 500;
	private static final int DEFAULT_LIMIT = 10;
	private static final String DEFAULT_LIMIT_STRING = "10";
	private static final String DEFAULT_PROPERTY_DELIMITER = ",";

	private int maxLimit = DEFAULT_MAX_LIMIT;

	private String propertyDelimiter = DEFAULT_PROPERTY_DELIMITER;

	private int limit;

	private long offset;

	private Sort sort;

	public OffsetPageRequest() {
		this.limit = DEFAULT_LIMIT;
	}

	public OffsetPageRequest(int limit, long offset) {
		this(limit, offset, null);
	}

	public OffsetPageRequest(int limit, long offset, Sort sort) {
		this.check(limit);

		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
	}

	public OffsetPageRequest defaultSort(Direction direction, String... properties) {
		final OffsetPageRequest ret;
		final Sort s = getSort();

		if (s != null && s.iterator().hasNext()) {
			ret = this;
		} else {
			ret = new OffsetPageRequest(getPageSize(), getOffset(), Sort.by(direction, properties));
		}

		return ret;
	}

	/**
	 * Sets limit.
	 *
	 * @param limit the size of the page to be returned.
	 */
	@QueryParam("limit")
	@DefaultValue(DEFAULT_LIMIT_STRING)
	public void setLimit(int limit) {
		this.check(limit);

		this.limit = limit;
	}

	/**
	 * Sets offset.
	 *
	 * @param offset number of records to be skipped from the result set.
	 */
	@QueryParam("offset")
	@DefaultValue("0")
	public void setOffset(long offset) {
		this.offset = offset;
	}

	/**
	 * Sets sorts.
	 *
	 * @param sorts the sorting strings.
	 * One sorting string is a comma separated string of property and direction,
	 * such as {@literal property,asc}, {@literal property,desc}.
	 */
	@QueryParam("sort[]")
	public void setSort(List<String> sorts) {
		sort = parseSort(sorts);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPageNumber() {
		return (int) (limit > 0 ? offset / limit : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPageSize() {
		return limit;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getOffset() {
		return offset;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Sort getSort() {
		return sort != null ? sort : Sort.unsorted();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pageable next() {
		return new OffsetPageRequest(limit, offset + limit, sort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pageable previousOrFirst() {
		return new OffsetPageRequest(limit, offset - limit, sort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Pageable first() {
		return new OffsetPageRequest(limit, 0, sort);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPrevious() {
		return offset > 0;
	}

	protected int getMaxLimit() {
		return this.maxLimit;
	}

	protected String getPropertyDelimiter() {
		return this.propertyDelimiter;
	}

	private List<Order> parseOrders(List<String> source) {
		final String delimiter = this.getPropertyDelimiter();
		final List<Order> allOrders = new ArrayList<>();

		for (final String part : source) {

			if (part == null) {
				continue;
			}

			final String[] elements = part.split(delimiter);
			final Direction direction = elements.length == 0 ? null : Direction.fromOptionalString(elements[elements.length - 1]).orElse(null);

			for (int i = 0; i < elements.length; i++) {

				if (i == elements.length - 1 && direction != null) {
					continue;
				}

				final String property = elements[i];

				if (!StringUtils.hasText(property)) {
					continue;
				}

				allOrders.add(new Order(direction, property));
			}
		}

		return allOrders;
	}

	private Sort parseSort(List<String> sorts) {
		final List<Order> orders = parseOrders(sorts);
		return orders.isEmpty() ? null : Sort.by(orders);
	}

	private void check(int limit) {
		if (limit > this.getMaxLimit()) {
			throw new IllegalArgumentException("Limit is exceeded.");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + limit;
		result = prime * result + Long.hashCode(offset);
		result = prime * result + ((sort == null) ? 0 : sort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OffsetPageRequest other = (OffsetPageRequest) obj;
		if (limit != other.limit) {
			return false;
		}
		if (offset != other.offset) {
			return false;
		}
		if (sort == null) {
			if (other.sort != null) {
				return false;
			}
		} else if (!sort.equals(other.sort)) {
			return false;
		}
		return true;
	}

}
