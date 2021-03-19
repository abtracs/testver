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
public class ExampleMessageBodyReader implements MessageBodyReader<Example> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Example.class);
    }
    @Override
    public Example readFrom(Class<Example> type, Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            String name = br.readLine();
            Example hello = new Example(name, 1);
            return hello;
        }
    }
}
