package jersey1;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;

@Provider
public class Example4MessageBodyReader implements MessageBodyReader<Example4> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Example4.class);
    }
    @Override
    public Example4 readFrom(Class<Example4> type, Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {

				String v = java.net.URLEncoder.encode((String)((java.util.List)httpHeaders.get("foo")).get(0));
				Example4 hello = new Example4(v, 1);
				return hello;
    }
}
