## About commons-jax-rs-data

### How to use

```java
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;

import org.oxerr.commons.ws.rs.data.OffsetPageRequest
import org.springframework.data.domain.Page;

@GET
public Page<Post> getPosts(@BeanParam OffsetPageRequest pageable) {
	...
}
```
