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
public class Example5MessageBodyReader implements MessageBodyReader<Example5> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Example5.class);
    }
    @Override
    public Example5 readFrom(Class<Example5> type, Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {

				String v = (String)((java.util.List)httpHeaders.get("foo")).get(0);
				Example5 hello = new Example5(v, 1);
				return hello;
    }
}
