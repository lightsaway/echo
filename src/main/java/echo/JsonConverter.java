package echo;


import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

public class JsonConverter {

    public static JsonObjectBuilder convertResponse(final HttpServletRequest request ) throws IOException, ServletException {

        final JsonObjectBuilder json = Json.createObjectBuilder();


        addIfNotEmpty( json , "method",         request.getMethod().toUpperCase( Locale.ENGLISH ));
        addIfNotEmpty( json , "protocol",       request.getProtocol());
        addIfNotEmpty( json , "scheme",         request.getScheme());
        addIfNotEmpty( json , "encoding",       request.getCharacterEncoding());
        addIfNotEmpty( json , "requestURI",     request.getRequestURI());
        addIfNotEmpty( json , "requestURL",     request.getRequestURL().toString());
        addIfNotEmpty( json ,  "contentType",    request.getContentType() == null ? "" : request.getContentType());
        addIfNotEmpty( json ,  "queryString",    request.getQueryString() == null ? "" : request.getQueryString());
        addIfNotEmpty( json ,  "authType",       request.getAuthType());
        addIfNotEmpty( json ,  "pathInfo",       request.getPathInfo());
        addIfNotEmpty( json ,  "pathTranslated", request.getPathTranslated());
        addIfNotEmpty( json ,  "remoteUser",     request.getRemoteUser());
        addIfNotEmpty( json ,  "remoteAddress",  request.getRemoteAddr());
        addIfNotEmpty( json ,  "remoteHost",     request.getRemoteHost());
        addIfNotEmpty( json ,  "remotePort",     String.valueOf(request.getRemotePort()));


        return json;
    }

    private static void addIfNotEmpty( JsonObjectBuilder appender, String key, String value){
        if (value != null) appender.add(  key, value);
    }


}
