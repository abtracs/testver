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
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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

@Path("mbresource")
public class MBResource {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;
    @Context private UriInfo uriinfo;
    @Context private HttpHeaders hheaders;

	  @GET
    @Path("example")
    @Produces({"text/html", MediaType.TEXT_PLAIN})
    public Example getExample(@QueryParam("foo") String foo) {
				Example e = new Example(foo, 2); 
        return e;  // THIS PROPAGATES TAINT FROM foo TO writeTo(_T_, ...) in ExampleMessageBodyWriter.
    }

		@GET
		@Path("example1")
		public Example1 getExample1(@QueryParam("foo") String foo) {
			Example1 e = new Example1(foo, 2);
			return e; // Propagating taint to writeTo(_T_, ..) in Example1MessagebodyWriter
		}

		@GET
		@Path("example2")
		public Example2 getExample2(@QueryParam("foo") String foo) {
			Example2 e = new Example2(foo, 2);
			return e; // Propagating taint to writeTo(_T_, ..) in Example2MessagebodyWriter
		}

		@POST
		@Path("example3")
		public String putExample3(Example e) {
			return e.toString(); //CWEID 80
		}

		@POST
		@Path("example8")
		public String putExample8(Example3 e) {
			return e.toString(); //CWEID 80
		}
		@POST
		@Path("example9")
		public String putExample9(Example4 e) {
			// Cleansed
			return e.toString();
		}
		@POST
		@Path("example10")
		public String putExample10(Example5 e) {
			// Cleansed
			return e.toString();
		}

		@POST
		@Path("example4")
		public Object putExample4(Example1 e) {
			return Response.seeOther(URI.create(e.getName())).build(); //CWEID 601
		}

		@POST
		@Path("example5")
		public String putExample4(Example2 e) {
			// Cleansed in the Example2MessageBodyReader
		return e.toString();
		}



		@POST
		@Consumes("application/xml")
		@Path("psource1")
		public String postSource(javax.xml.transform.Source source) {
			String ret = "";
			try {
				javax.xml.transform.TransformerFactory tFactory =
					javax.xml.transform.TransformerFactory.newInstance();
					javax.xml.transform.Transformer transformer = tFactory.newTransformer();
				StringWriter writer = new StringWriter();
				transformer.transform(source,
				  new javax.xml.transform.stream.StreamResult(writer));
				ret = writer.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret; //CWEID 611
   }
		@POST
		@Consumes("application/xml")
		@Path("psource2")
		public String postSource2(javax.xml.transform.Source source) {
			String ret = "";
			try {
				javax.xml.transform.TransformerFactory tFactory =
					javax.xml.transform.TransformerFactory.newInstance();
				javax.xml.transform.Transformer transformer =
					tFactory.newTransformer(
						new javax.xml.transform.stream.StreamSource("foo.xsl")
					);
				StringWriter writer = new StringWriter();
				transformer.transform(source,
				  new javax.xml.transform.stream.StreamResult(writer));
				ret = writer.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret;
   }
		@POST
		@Consumes("application/xml")
		@Path("psource3")
		public String postSource3(javax.xml.transform.Source source) {
			String ret = "";
			try {
				javax.xml.transform.stream.StreamSource sSrc = (javax.xml.transform.stream.StreamSource)source;
				java.io.BufferedInputStream bis = new java.io.BufferedInputStream(sSrc.getInputStream());
				byte b[] = new byte[32];
				bis.read(b, 0, 32);	
				String s = new String(b);
				ret = s;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret; //CWEID 80
   }

		@POST
		@Consumes("application/xml")
		@Path("psource4")
		public String postSource4(javax.xml.transform.Source source) {
			String ret = "";
			try {
				javax.xml.transform.stream.StreamSource sSrc = (javax.xml.transform.stream.StreamSource)source;
				java.io.Reader r = sSrc.getReader();
				if (r == null) {
					return "it was null?";
				}
				java.io.BufferedReader br = new java.io.BufferedReader(r);
				ret = br.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ret; //CWEID 80
   }

	@GET
	@Path("uribuilder1")
	@Produces("text/html")
	public Response uribuilder1(@QueryParam("foo") String foo) {
		if (foo.contains("hmm0")) {
			UriBuilder b = UriBuilder.fromPath(foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601

		} else if (foo.contains("hmm1")) {
			UriBuilder b = UriBuilder.fromUri(foo);
			b = b.userInfo("joe:shmo");
			URI u = b.build();
			System.out.println("u: "+u.toString());
			return Response.temporaryRedirect(u).build(); //CWEID 601

		} else if (foo.contains("hmm2")) {
			UriBuilder b = UriBuilder.fromMethod(MBResource.class, foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601

		} else if (foo.contains("hmm3")) {
			try {
			UriBuilder b = UriBuilder.fromUri(new URI(foo));
			b.port(8080);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
			} catch (Exception e) {}
	
		}	else if (foo.contains("hmm4")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.host(foo);
			b = b.port(8080);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601

		} else if (foo.contains("hmm5")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.fragment("pageoffset"+foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601

		} else if (foo.contains("hmm6")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.path(MBResource.class, foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601

		} else if (foo.contains("hmm7")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.matrixParam(foo, "yoh");
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmm8")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.path(MBResource.class, foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmm9")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.path(foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb0")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.queryParam(foo, (Object)"bar");
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb1")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.queryParam("bar", (Object)foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb2")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.queryParam("bar", (Object)foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb3")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.replaceMatrix(foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb4")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.replacePath(foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb5")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.replaceQuery(foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb6")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.replaceQueryParam("foo", foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb7")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.replaceQueryParam("foo", foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb8")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.resolveTemplate("foo", foo);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb9")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.resolveTemplate(foo, "foo");
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb010")) {
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.resolveTemplateFromEncoded(foo, "foo");
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb011")) {
			java.util.HashMap<String, Object> m = new java.util.HashMap<String, Object>();
			m.put(foo, (Object)foo);
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.resolveTemplates(m);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb012")) {
			java.util.HashMap<String, Object> m = new java.util.HashMap<String, Object>();
			m.put(foo, (Object)foo);
			UriBuilder b = UriBuilder.fromUri("http://hansel.com");
			b = b.resolveTemplates(m, false);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		} else if (foo.contains("hmb013")) {
			java.util.HashMap<String, Object> m = new java.util.HashMap<String, Object>();
			m.put("site", (Object)"lagos.com");
			UriBuilder b = UriBuilder.fromUri("http://hansel{site}.com");
			b = b.resolveTemplates(m, false);
			URI u = b.build();
			return Response.temporaryRedirect(u).build(); //CWEID 601
		}

		return Response.ok("ok").build();

	}

    @GET
		@Path("convertBean")
    @Produces("text/html")
    public String convertBean(@QueryParam("search") MyBean myBean) {
        return myBean.getValue(); //CWEID 80
    }

}
