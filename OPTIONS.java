package jersey1;

import java.lang.annotation.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("OPTIONS")
public @interface OPTIONS {
}
