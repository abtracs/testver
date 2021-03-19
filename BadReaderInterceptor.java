package jersey1;
import javax.ws.rs.ext.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ContainerResponseFilter;
import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.*;


public class BadReaderInterceptor implements ReaderInterceptor {
    public Object aroundReadFrom(ReaderInterceptorContext ctx)  throws java.io.IOException, javax.ws.rs.WebApplicationException {
        return ctx.proceed();
    }
}
