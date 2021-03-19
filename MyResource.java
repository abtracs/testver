package jersey1;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import javax.ws.rs.PathParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.CookieParam;
import java.net.URI;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.PathSegment;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("myresource")
public class MyResource {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;
    @Context private UriInfo uriinfo;
    @Context private HttpHeaders hheaders;

    public static class CustomObject {
        public String name;
        public List<String> aliases;
        public int age;
    }

    @POST
    @Produces("application/json")
    @Path("getobj1/{id}")
    public CustomObject getobj1() {
        CustomObject obj = new CustomObject();
        obj.name = "foo";
        obj.aliases = Arrays.asList(new String[] { "baz", "quux" });
        obj.age = 1337;
        return obj;

    }

    @POST
    @Path("body3")
    @Produces("text/html")
    public String body3(CustomObject obj) {
        if(obj.age > 100) {
            return "wow! " + obj.name;      // CWEID 80
        } else {
            StringBuffer sb = new StringBuffer("aliases:");
            if(obj.aliases != null) {
                for(String alias: obj.aliases) {
                    sb.append(" ").append(alias);
                }
            }
            return sb.toString();           // CWEID 80
        }
    }

    @POST
    @Path("body4")
    @Produces("text/html")
    public String body4(CustomObject obj) {
        return helper1(obj);        // CWEID 80
    }


    public String helper1(CustomObject obj) {
        if(obj.age > 100) {
            return "wow! " + obj.name;
        } else {
            StringBuffer sb = new StringBuffer("aliases:");
            if(obj.aliases != null) {
                for(String alias: obj.aliases) {
                    sb.append(" ").append(alias);
                }
            }
            return sb.toString();
        }
    }
    public String helper2(CustomObject obj) {
        if(obj.age > 100) {
            return "wow! " + obj.name;
        } else {
            StringBuffer sb = new StringBuffer("aliases:");
            if(obj.aliases != null) {
                for(String alias: obj.aliases) {
                    sb.append(" ").append(alias);
                }
            }
            return sb.toString();
        }
    }



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @QueryParam("bad")
    private String bad1;

    private String good1 = "nothing";

    @GET
    @Path("second")
    @Produces(MediaType.TEXT_HTML)
    public String second() {
        return "hello<b>hello</b>bad: " + bad1;  // CWEID 80 
    }

    @GET
    @Path("responsesink1/{id}")
    public void responsesink1(@PathParam("id") String pp) {
        try {
            response.getWriter().print("responsesink1: ");
            response.getWriter().println(pp);   // CWEID 80
        } catch (IOException ioe) {
        }
    }


    @POST
    @Path("body1/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String body1(String reqbody) {
        return "Body: " + reqbody;      // CWEID 80
    }

    @POST
    @Path("body2/{id}")
    @Produces("text/html")
    public String body2(@PathParam("id") String idval, String reqbody) {
        if(idval.contains("something")) {
            return "id: " + idval;         // CWEID 80
        } else {
            return "Body: " + reqbody;    // CWEID 80
        }
    }


    @GET
    @Path("inputstream1/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String inputstream1(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String body = reader.readLine();
            return "body: " + body;     // CWEID 80
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "inputstream1", ioe);
            return "error in inputstream1";
        }
    }

    @POST
    @Path("reader1")
    @Produces(MediaType.TEXT_HTML)
    public String reader1(Reader reader) {
        try {
            BufferedReader rd = new BufferedReader(reader);
            String here = rd.readLine();
            return here;        // CWEID 80
        } catch (IOException ignore) {
            return "error";
        }
    }

    @GET
    @Path("redir1")
    @Produces("text/html")
    public Response redir1(@QueryParam("foo") String foo) {
        if(bad1.contains("TOK1")) {
            return Response.status(302).contentLocation(URI.create(foo)).build();        // CWEID 601
        } else if(bad1.contains("TOK3")) {
            return Response.accepted().contentLocation(URI.create(foo)).build();        // CWEID 601
        } else if(bad1.contains("TOK4")) {
            return Response.ok().contentLocation(URI.create(foo)).build();        // CWEID 601
        } else if(bad1.contains("TOK5")) {
            return Response.noContent().contentLocation(URI.create(foo)).build();        // CWEID 601
        } else if(bad1.contains("TOK6")) {
            return Response.notModified().contentLocation(URI.create(foo)).build();        // CWEID 601
        } else if(bad1.contains("TOK7")) {
            return Response.serverError().contentLocation(URI.create(foo)).build();        // CWEID 601
        } else if(bad1.contains("TOK8")) {
            return Response.temporaryRedirect(URI.create(foo)).build();        // CWEID 601
        } else {
            return Response.ok("ok").build();
        }
    }

    @GET
    @Path("xr1")
    @Produces("text/html")
    public Response xr1(@QueryParam("foo") String foo) {
        if(bad1.contains("TOK1")) {
            return Response.accepted(foo).build();        // CWEID 80
        } else if(bad1.contains("TOK2")) {
            return Response.ok(foo).build();        // CWEID 80
        } else if(bad1.contains("TOK3")) {
            return Response.ok(foo, MediaType.TEXT_HTML).build();        // CWEID 80
        } else if(bad1.contains("TOK4")) {
            return Response.ok(foo, "text/html").build();        // CWEID 80
        } else {
            return Response.ok("nothing").build();
        }
    }

    @GET
    @Path("redir2")
    @Produces("text/html")
    public Response redir2(@QueryParam("foo") String foo) {
        return Response.status(302).contentLocation(URI.create(foo)).build();   // CWEID 601
    }


    @GET
    @Path("badresp1")
    public Response badresp1() {
        return Response.ok("badresp1: " + bad1, "text/html").build();       // CWEID 80
    }

    @GET
    @Path("badresp2/{id}")
    public Response badresp2(@PathParam("id") String whatever) {
        return Response.ok("badresp2: " + whatever, "text/html").build();       // CWEID 80
    }


    @GET
    @Path("redir")
    public Response redir() {
        return Response.seeOther(URI.create("https://www.veracode.com")).build();
    }

    @GET
    @Path("badredir1")
    public Response badredir1() {
        return Response.seeOther(URI.create(bad1)).build();     // CWEID 601
    }

    @GET
    @Path("badredir2")
    public Response badredir2(@PathParam("url") String u) {
        return Response.seeOther(URI.create(u)).build();        // CWEID 601
    }

    @GET
    @Path("badstream1")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream1() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(bad1);       // CWEID 80
            }
        };
    }

    @GET
    @Path("badstream2")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream2() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(bad1);       // CWEID 80
            }
        };
    }

    @GET
    @Path("badstream3/{id}")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream3(final @PathParam("id") String pp) {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(pp);       // CWEID 80
            }
        };
    }

    @GET
    @Path("badstream4/{id}")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream4(final @PathParam("id") String pp) {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                helperWrite2(os, pp);
            }
        };
    }
    @GET
    @Path("goodstream1")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput goodstream1() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                helperWrite1(os, "foo");
            }
        };
    }
    private void helperWrite1(OutputStream os, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(value);
        } catch (IOException ignore) {
        }
    }

    private void helperWrite2(OutputStream os, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(value);        // CWEID 80
        } catch (IOException ignore) {
        }
    }

    @GET
    @Path("badpathsegment1/{model}")
    @Produces("text/html")
    public String badPathSegment1(@PathParam("model") PathSegment m) {
        return m.getPath();     // CWEID 80
    }

    @GET
    @Path("badpathsegment2/{model}")
    @Produces("text/html")
    public String badPathSegment2(@PathParam("model") PathSegment m) {
        String path = m.getPath();
        if(path.contains("aa")) {
            return m.getMatrixParameters().getFirst("foo");     // CWEID 80
        } else if(path.contains("bb")) {
            return m.getMatrixParameters().values().toArray()[0].toString();     // CWEID 80
        } else {
            return m.getMatrixParameters().get("foo").get(0);     // CWEID 80
        }
    }


    @GET
    @Path("matrix")
    @Produces("text/html")
    public String matrix(@MatrixParam("hmm") String mat) {
        return mat;     // CWEID 80
    }

    @POST
    @Path("form")
    @Produces("text/html")
    public String formparam(@FormParam("code") String code) {
        return "code: " + code;     // CIWEID 80
    }

    @GET
    @Produces("text/html")
    @Path("hdr")
    public String hdr(@HeaderParam("Referer") String referer) {
        return "Referer: " + referer;       // CWEID 80
    }

    @GET
    @Produces("text/html")
    @Path("cookie1")
    public String cookie1(@CookieParam("sid") String sid) {
        return "cookie: " + sid;        // CWEID 80
    }

    @GET
    @Produces("text/html")
    @Path("cookie2")
    public String cookie2(@CookieParam("sid") Cookie sid) {
        return "cookie: " + sid.getValue();     // CWEID 80
    }


    @GET
    @Produces("text/html")
    @Path("hdr2")
    public String hdr2() {
        if(bad1.contains("TOK1")) {
            return hheaders.getCookies().get("cook1").getName();    // CWEID 80
        } else if(bad1.contains("TOK2")) {
            return hheaders.getCookies().get("cook1").getValue();    // CWEID 80
        } else if(bad1.contains("TOK3")) {
            return hheaders.getCookies().get("cook1").toString();    // CWEID 80
        } else if(bad1.contains("TOK4")) {
            return hheaders.getHeaderString("Referer");    // CWEID 80
        } else if(bad1.contains("TOK5")) {
            return hheaders.getRequestHeader("Referer").get(0);    // CWEID 80
        } else if(bad1.contains("TOK6")) {
            return hheaders.getRequestHeaders().getFirst("Referer");    // CWEID 80
        } else if(bad1.contains("TOK7")) {
            for(String refcook: hheaders.getRequestHeaders().get("Referer")) {
                if(refcook != null) {
                    return refcook;     // CWEID 80
                }
            }
        } else if(bad1.contains("TOK8")) {
            for(String refcook: hheaders.getRequestHeader("Referer")) {
                if(refcook != null) {
                    return refcook;     // CWEID 80
                }
            }
        }
        return "nothing";
    }

    @GET
    @Path("ar1")
    @Produces("text/html")
    public void ar1(final @Suspended AsyncResponse resp) {
        new Thread() {
            public void run() {
                resp.resume("hello");
            }
        }.start();
    }

    @GET
    @Path("ar2")
    @Produces("text/html")
    public void ar2(final @Suspended AsyncResponse resp) {
        new Thread() {
            public void run() {
                resp.resume(bad1);      // CWEID 80
            }
        }.start();
    } 

    @GET
    @Path("ar3")
    @Produces("text/html")
    public void ar3(final @Suspended AsyncResponse resp) {
        new Thread() {
            public void run() {
                Response r = Response.ok(bad1).build();     // CWEID 80
                resp.resume(r);
            }
        }.start();
    }






    @GET
    @Path("uriinfo")
    @Produces("text/html")
    public String uriinfo1() {
        if(bad1.contains("hmm1")) {
            return uriinfo.getAbsolutePath().getRawAuthority();        // CWEID 80
        } else if(bad1.contains("hmm2")) {
            return uriinfo.getAbsolutePath().getRawFragment();        // CWEID 80
        } else if(bad1.contains("hmm3")) {
            return uriinfo.getAbsolutePath().getRawPath();        // CWEID 80
        } else if(bad1.contains("hmm4")) {
            return uriinfo.getAbsolutePath().getRawQuery();        // CWEID 80
        } else if(bad1.contains("hmm5")) {
            return uriinfo.getAbsolutePath().getRawSchemeSpecificPart();        // CWEID 80
        } else if(bad1.contains("hmm6")) {
            return uriinfo.getAbsolutePath().getRawUserInfo();        // CWEID 80
        } else if(bad1.contains("hmm7")) {
            return uriinfo.getAbsolutePath().toASCIIString();        // CWEID 80
        } else if(bad1.contains("hmm8")) {
            return uriinfo.getAbsolutePath().toString();        // CWEID 80
        } else if(bad1.contains("hmm9")) {
            return uriinfo.getBaseUri().getRawAuthority();        // CWEID 80
        } else if(bad1.contains("hmm10")) {
            return uriinfo.getBaseUri().getRawFragment();        // CWEID 80
        } else if(bad1.contains("hmm11")) {
            return uriinfo.getBaseUri().getRawPath();        // CWEID 80
        } else if(bad1.contains("hmm12")) {
            return uriinfo.getBaseUri().getRawQuery();        // CWEID 80
        } else if(bad1.contains("hmm13")) {
            return uriinfo.getBaseUri().getRawSchemeSpecificPart();        // CWEID 80
        } else if(bad1.contains("hmm14")) {
            return uriinfo.getBaseUri().getRawUserInfo();        // CWEID 80
        } else if(bad1.contains("hmm15")) {
            return uriinfo.getBaseUri().toASCIIString();        // CWEID 80
        } else if(bad1.contains("hmm16")) {
            return uriinfo.getBaseUri().toString();        // CWEID 80
        } else if(bad1.contains("hmm17")) {
            return uriinfo.getRequestUri().getRawAuthority();        // CWEID 80
        } else if(bad1.contains("hmm18")) {
            return uriinfo.getRequestUri().getRawFragment();        // CWEID 80
        } else if(bad1.contains("hmm19")) {
            return uriinfo.getRequestUri().getRawPath();        // CWEID 80
        } else if(bad1.contains("hmm20")) {
            return uriinfo.getRequestUri().getRawQuery();        // CWEID 80
        } else if(bad1.contains("hmm21")) {
            return uriinfo.getRequestUri().getRawSchemeSpecificPart();        // CWEID 80
        } else if(bad1.contains("hmm22")) {
            return uriinfo.getRequestUri().getRawUserInfo();        // CWEID 80
        } else if(bad1.contains("hmm23")) {
            return uriinfo.getRequestUri().toASCIIString();        // CWEID 80
        } else if(bad1.contains("hmm24")) {
            return uriinfo.getRequestUri().toString();        // CWEID 80
        } else if(bad1.contains("hmm25")) {
            return uriinfo.getPath();        // CWEID 80
        } else if(bad1.contains("hmm26")) {
            return uriinfo.getPath(true);        // CWEID 80
        } else if(bad1.contains("hmm27")) {
            return uriinfo.getQueryParameters().getFirst("foo");        // CWEID 80
        } else if(bad1.contains("hmm28")) {
            return uriinfo.getQueryParameters(true).getFirst("foo");        // CWEID 80
        } else if(bad1.contains("hmm29")) {
            for(String param: uriinfo.getQueryParameters().get("foo")) {
                if(param.contains("f")) {
                    return param;        // CWEID 80
                }
            }
        } else if(bad1.contains("hmm30")) {
            for(String param: uriinfo.getQueryParameters(true).get("foo")) {
                if(param.contains("f")) {
                    return param;        // CWEID 80
                }
            }
        }
        return "nothing";
    }


}
