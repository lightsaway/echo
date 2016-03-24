package echo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static echo.JsonConverter.convertResponse;
import static java.lang.String.valueOf;
import static javax.json.Json.createObjectBuilder;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@RestController
@EnableAutoConfiguration
public class EchoController {

    private final static Logger LOG = Logger.getLogger( EchoController.class.getName());
    private final static ObjectMapper ow = new ObjectMapper();
    private static long requestCounter = 0 ;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping( "/echo" )
    @ResponseBody
    String echo() throws IOException, ServletException {
        requestCounter+=1;
        response.setContentType(valueOf(APPLICATION_JSON));
        long time = System.currentTimeMillis();

        JsonObjectBuilder builder = convertResponse(request);
        builder.add("recievedTime", valueOf(time));
        String json = builder.build().toString();
        LOG.info( json );
        return json;
    }

    @RequestMapping( "/stats" )
    @ResponseBody
    String stats() throws IOException, ServletException {

        final JsonObjectBuilder json = createObjectBuilder().add("totalEchoRequests", valueOf(requestCounter));

        return json.build().toString();
    }

}