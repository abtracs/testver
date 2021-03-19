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
public class Example2MessageBodyReader implements MessageBodyReader<Example2> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return (type == Example2.class);
    }
    @Override
    public Example2 readFrom(Class<Example2> type, Type genericType,
                          Annotation[] annotations, MediaType mediaType,
                          MultivaluedMap<String, String> httpHeaders,
                          InputStream inputStream)
            throws IOException, WebApplicationException
    {

				
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            String name = br.readLine();
						name = name.replaceAll("name=", "");
						name = java.net.URLEncoder.encode(name); 
            Example2 hello = new Example2(name, 1);
            return hello;
        }
    }
}
