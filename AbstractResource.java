package jersey1;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.*;
import java.util.logging.*;
import javax.ws.rs.QueryParam;
import java.io.*;
import javax.ws.rs.PathParam;
import java.net.URI;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("/absresource")
public interface AbstractResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt();

    @GET
    @Path("second")
    @Produces(MediaType.TEXT_HTML)
    public String second();


    @POST
    @Path("body1/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String body1(String reqbody); 


    @GET
    @Path("inputstream1/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String inputstream1(InputStream is); 

    @GET
    @Path("badresp1")
    public Response badresp1(); 


    @GET
    @Path("redir")
    public Response redir(); 

    @GET
    @Path("badredir1")
    public Response badredir1(); 

    @GET
    @Path("badstream1")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream1(); 

    @GET
    @Path("badstream2")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream2(); 

    @GET
    @Path("goodstream1")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput goodstream1(); 

}
