package echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static echo.JsonConverter.convertResponse;
import static java.lang.String.valueOf;
import static java.lang.System.currentTimeMillis;
import static javax.json.Json.createObjectBuilder;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON;

@RestController
@EnableAutoConfiguration
@RequestMapping("/echo")
public class EchoController {

    private final static Logger LOG = Logger.getLogger( EchoController.class.getName());

    private static long requestCounter = 0 ;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    String echoDel() {
        return formResponse();
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    String echoGet() {
        return formResponse();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    String echoPost() {
        return formResponse();
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    String echoPut() {
        return formResponse();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    @ResponseBody
    String echoPatch() {
        return formResponse();
    }

    @RequestMapping(method = RequestMethod.HEAD)
    @ResponseBody
    String echoHead() {
        return formResponse();
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    @ResponseBody
    String echoOpts() {
        return formResponse();
    }

    @RequestMapping(method = RequestMethod.TRACE)
    @ResponseBody
    String echoTrace() {
        return formResponse();
    }


    @RequestMapping( "/stats" )
    @ResponseBody
    String stats() {

        final JsonObjectBuilder json = createObjectBuilder().add("totalEchoRequests", valueOf(requestCounter));

        return json.build().toString();
    }


    private String formResponse(){
        requestCounter+=1;
        response.setContentType(valueOf(APPLICATION_JSON));
        long time = currentTimeMillis();
        LOG.info("Received request from " + request.getRemoteHost() );
        String json;

        try {
            final JsonObjectBuilder builder = convertResponse(request);
            builder.add("recievedTime", valueOf(time));
            json = builder.build().toString();
        } catch (Exception e){
            LOG.warning("Some error appeared in forming request");
            json = Json.createObjectBuilder()
                    .add("error", "we're experiencing some problems")
                    .add("detailedMessage", e.getMessage() == null ? "" : e.getMessage())
                    .build().toString();
        }
        return json;
    }

}