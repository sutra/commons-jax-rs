## About commons-jax-rs-data

### How to use

The server side, read `Pageable` parameters as `@BeanParam`:

```java
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;

import org.oxerr.commons.ws.rs.data.OffsetPageRequest
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

	@GET
	public Page<Post> getPosts(@BeanParam OffsetPageRequest pageable) {

		// The page size, same as limit.
		int pageSize = pageable.getPageSize();

		// Zero-based offset.
		long offset = pageable.getOffset();

		// Zero-based page number.
		int pageNumber = pageable.getPageNumber();

		Sort sort = pageable.getSort();

	}

}
```

and the client side could pass the parameters as follows:

```shell
curl 'http://localhost:8080/posts?limit=10&offset=0&sort%5B%5D=field1,asc&sort%5B%5D=field2,desc'
```

### How to use in Spring @RestController

```java
import org.springframework.data.domain.Page;;
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public PostController {

	@GetMapping
	public Page<Post> getPosts(@RequestParam(value = "q", required = false) String q, SpaceDelimitedOffsetPageRequest pageable) {
		// ...
	}

}
```

and the client side could pass the parameters as follows:

```shell
curl 'http://localhost:8080/posts?limit=10&offset=0&sort=field1%20asc&sort=field2%20desc'
```

### How to change the default behavior

Extends and override the relevant methods to change the default behavior, for example:

```java
import javax.ws.rs.QueryParam;

import org.oxerr.commons.ws.rs.data.OffsetPageRequest;

public class SpaceDelimitedOffsetPageRequest extends OffsetPageRequest {

	/**
	 * Change the soring paramter name.
	 */
	@Override
	@QueryParam("sort")
	public void setSort(List<String> sorts) {
		super.setSort(sorts);
	}

	/**
	 * Change the delimiter in the sort string between the property and the sort direction.
	 */
	@Override
	protected String getPropertyDelimiter() {
		return " ";
	}

	@Override
	protected int getMaxLimit() {
		return 1000;
	}

	// ...

}
```
