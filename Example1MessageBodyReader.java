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
public class Example1MessageBodyReader implements MessageBodyReader<Example1> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Example1.class);
    }
    @Override
    public Example1 readFrom(Class<Example1> type, Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            String name = br.readLine();
						name = name.replaceAll("name=", "");
						name = java.net.URLEncoder.encode(name); // Cleanses for XSS but won't fix the redirect
            Example1 hello = new Example1(name, 1);
            return hello;
        }
    }
}
