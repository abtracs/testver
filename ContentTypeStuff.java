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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("contenttype")
public class ContentTypeStuff {
    private Logger logger = Logger.getLogger(getClass().getName());

    @QueryParam("bad") private String badvar;

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("good1")
    public String good1() {
        return "xxx good: " + badvar;
    }

    @Produces("text/plain")
    @GET
    @Path("good2")
    public String good2() {
        return "xxx good: " + badvar;
    }

    @Produces("image/png")
    @GET
    @Path("good3")
    public String good3() {
        return "xxx good: " + badvar;
    }


    @GET
    @Path("bad1")
    public String bad2() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED)
    @GET
    @Path("bad3")
    public String bad4() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM)
    @GET
    @Path("bad5")
    public String bad6() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.APPLICATION_SVG_XML)
    @GET
    @Path("bad7")
    public String bad8() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.APPLICATION_XHTML_XML)
    @GET
    @Path("bad9")
    public String bad10() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.WILDCARD)
    @GET
    @Path("bad13")
    public String bad14() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
    @GET
    @Path("bad15")
    public String bad16() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.SERVER_SENT_EVENTS)
    @GET
    @Path("bad17")
    public String bad18() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.TEXT_HTML)
    @GET
    @Path("bad19")
    public String bad20() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
    @GET
    @Path("bad21")
    public String bad22() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/svg+xml")
    @GET
    @Path("bad23")
    public String bad24() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/octet-stream")
    @GET
    @Path("bad25")
    public String bad26() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/x-www-form-urlencoded")
    @GET
    @Path("bad27")
    public String bad28() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application.application/xhtml+xml")
    @GET
    @Path("bad29")
    public String bad30() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("*/*")
    @GET
    @Path("bad31")
    public String bad32() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("multipart/form-data")
    @GET
    @Path("bad35")
    public String bad36() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("text/html")
    @GET
    @Path("bad37")
    public String bad38() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/html")
    @GET
    @Path("bad39")
    public String bad40() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/html+xml")
    @GET
    @Path("bad41")
    public String bad42() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/xml")
    @GET
    @Path("bad43")
    public String bad44() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces("application/javascript")
    @GET
    @Path("bad45")
    public String bad46() {
        return "xxx: " + badvar;    // CWEID 80
    }
    @Produces("text/javascript")
    @GET
    @Path("bad47")
    public String bad48() {
        return "xxx: " + badvar;    // CWEID 80
    }



    @GET
    @Path("newbad1")
    public String newbad2() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED})
    @GET
    @Path("newbad3")
    public String newbad4() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM})
    @GET
    @Path("newbad5")
    public String newbad6() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.APPLICATION_SVG_XML})
    @GET
    @Path("newbad7")
    public String newbad8() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.APPLICATION_XHTML_XML})
    @GET
    @Path("newbad9")
    public String newbad10() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.WILDCARD})
    @GET
    @Path("newbad13")
    public String newbad14() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA})
    @GET
    @Path("newbad15")
    public String newbad16() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.SERVER_SENT_EVENTS})
    @GET
    @Path("newbad17")
    public String newbad18() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.TEXT_HTML})
    @GET
    @Path("newbad19")
    public String newbad20() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", javax.ws.rs.core.MediaType.APPLICATION_XML})
    @GET
    @Path("newbad21")
    public String newbad22() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/svg+xml"})
    @GET
    @Path("newbad23")
    public String newbad24() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/octet-stream"})
    @GET
    @Path("newbad25")
    public String newbad26() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/x-www-form-urlencoded"})
    @GET
    @Path("newbad27")
    public String newbad28() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application.application/xhtml+xml"})
    @GET
    @Path("newbad29")
    public String newbad30() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "*/*"})
    @GET
    @Path("newbad31")
    public String newbad32() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "multipart/form-data"})
    @GET
    @Path("newbad35")
    public String newbad36() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "text/html"})
    @GET
    @Path("newbad37")
    public String newbad38() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/html"})
    @GET
    @Path("newbad39")
    public String newbad40() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/html+xml"})
    @GET
    @Path("newbad41")
    public String newbad42() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/xml"})
    @GET
    @Path("newbad43")
    public String newbad44() {
        return "xxx: " + badvar;    // CWEID 80
    }

    @Produces({"text/plain", "image/png", "application/javascript"})
    @GET
    @Path("newbad45")
    public String newbad46() {
        return "xxx: " + badvar;    // CWEID 80
    }
    @Produces({"text/plain", "image/png", "text/javascript"})
    @GET
    @Path("newbad47")
    public String newbad48() {
        return "xxx: " + badvar;    // CWEID 80
    }





}


