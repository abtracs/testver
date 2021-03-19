package jersey1;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
 
@Provider
@Produces("text/html")
public class Example1MessageBodyWriter implements MessageBodyWriter<Example1> {
  
    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        System.out.println("isWriteable called...");
        return type == Example1.class;
    }
  
    @Override
    public long getSize(Example1 myBool, Class type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return 0;
    }
  
    @Override
    public void writeTo(Example1 myBool,
                        Class type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap httpHeaders,
                        OutputStream entityStream)
                        throws IOException, WebApplicationException {
  
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>").append(myBool.toString()).append("</body></html>");
        DataOutputStream dos = new DataOutputStream(entityStream);
        dos.writeUTF(sb.toString()); //CWEID 80
    }
}
