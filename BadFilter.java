package jersey1;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ContainerRequestFilter;
import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.container.ContainerRequestContext;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.*;

@Provider
@PreMatching
public class BadFilter implements ContainerRequestFilter {
    private Logger logger = Logger.getLogger(getClass().getName());
    public void filter(ContainerRequestContext ctx) throws IOException {
        String hdr = ctx.getHeaderString("Referer");
        if(hdr == null) {
            hdr = "";
        }
        if(hdr.contains("TOK1")) {
            ctx.abortWith(Response.ok(hdr).build());        // CWEID 80
        } else if(hdr.contains("TOK2")) {
            ctx.abortWith(Response.ok(ctx.getCookies().get("sid").getName()).build());        // CWEID 80
        } else if(hdr.contains("TOK3")) {
            ctx.abortWith(Response.ok(ctx.getCookies().get("sid").getValue()).build());        // CWEID 80
        } else if(hdr.contains("TOK4")) {
            ctx.abortWith(Response.ok(ctx.getCookies().get("sid").toString()).build());        // CWEID 80
        } else if(hdr.contains("TOK5")) {
            ctx.abortWith(Response.ok(ctx.getHeaders().getFirst("Referer")).build());            // CWEID 80
        } else if(hdr.contains("TOK6")) {
            ctx.abortWith(Response.ok(ctx.getHeaders().get("Referer").get(0)).build());            // CWEID 80
        } else if(hdr.contains("TOK7")) {
            ctx.abortWith(Response.ok("this is fine").build());
        } else if(hdr.contains("TOK8")) {
            ctx.abortWith(Response.ok(ctx.getUriInfo().getAbsolutePath().getRawPath()).build());                   // CWEID 80
        } else if(hdr.contains("TOK9")) {
            ctx.setRequestUri(URI.create(hdr));     // CWEID 80
        } else if(hdr.contains("TOK10")) {
            ctx.setRequestUri(URI.create("http://www.example.com"), URI.create(hdr));     // CWEID 80
        } else if(hdr.contains("TOK11")) {
            ctx.setRequestUri(URI.create(hdr), URI.create("http://www.example.com"));     // CWEID 80
        } else if(hdr.contains("TOK12")) {
            ctx.setRequestUri(URI.create("http://www.example.com"));
        } else if(hdr.contains("TOK13")) {
            ctx.setRequestUri(URI.create("http://www.example.com"), URI.create("http://www.example.com"));
        } else if(hdr.contains("TOK14")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ctx.getEntityStream()));
            ctx.abortWith(Response.ok(reader.readLine()).build());          // CWEID 80
        }
    }
}
