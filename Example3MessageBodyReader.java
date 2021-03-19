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
public class Example3MessageBodyReader implements MessageBodyReader<Example3> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Example3.class);
    }
    @Override
    public Example3 readFrom(Class<Example3> type, Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {

				String v = (String)((java.util.List)httpHeaders.get("foo")).get(0);
				Example3 hello = new Example3(v, 1);
				return hello;
    }
}
