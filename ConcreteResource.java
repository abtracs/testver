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

public class ConcreteResource implements AbstractResource{
    private Logger logger = Logger.getLogger(getClass().getName());

    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;

    public static class CustomObject {
        public String name;
        public List<String> aliases;
        public int age;
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



    public String getIt() {
        return "Got it!";
    }

    @QueryParam("bad")
    private String bad1;

    private String good1 = "nothing";

    public String second() {
        return "hello<b>hello</b>bad: " + bad1;  // CWEID 80 
    }


    public String body1(String reqbody) {
        return "Body: " + reqbody;      // CWEID 80
    }

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

    public Response badresp1() {
        return Response.ok("badresp1: " + bad1, "text/html").build();       // CWEID 80
    }



    public Response redir() {
        return Response.seeOther(URI.create("https://www.veracode.com")).build();
    }

    public Response badredir1() {
        return Response.seeOther(URI.create(bad1)).build();     // CWEID 601
    }


    public StreamingOutput badstream1() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(bad1);       // CWEID 80
            }
        };
    }

    public StreamingOutput badstream2() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(bad1);       // CWEID 80
            }
        };
    }

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


}
