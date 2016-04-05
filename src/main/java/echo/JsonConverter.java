package echo;


import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.list;
import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;

public class JsonConverter {

    public static JsonObjectBuilder convertResponse(final HttpServletRequest request ) throws IOException, ServletException {

        final JsonObjectBuilder json = createObjectBuilder();


        addIfNotEmpty( json , "method",         request.getMethod().toUpperCase( Locale.ENGLISH ));
        addIfNotEmpty( json , "protocol",       request.getProtocol());
        addIfNotEmpty( json , "scheme",         request.getScheme());
        addIfNotEmpty( json , "encoding",       request.getCharacterEncoding());
        addIfNotEmpty( json , "requestURI",     request.getRequestURI());
        addIfNotEmpty( json , "requestURL",     request.getRequestURL().toString());
        addIfNotEmpty( json , "contentType",    request.getContentType() == null ? "" : request.getContentType());
        addIfNotEmpty( json , "queryString",    request.getQueryString() == null ? "" : request.getQueryString());
        addIfNotEmpty( json , "authType",       request.getAuthType());
        addIfNotEmpty( json , "pathInfo",       request.getPathInfo());
        addIfNotEmpty( json , "pathTranslated", request.getPathTranslated());
        addIfNotEmpty( json , "remoteUser",     request.getRemoteUser());
        addIfNotEmpty( json , "remoteAddress",  request.getRemoteAddr());
        addIfNotEmpty( json , "remoteHost",     request.getRemoteHost());
        addIfNotEmpty( json , "remotePort",     String.valueOf(request.getRemotePort()));

        JsonArrayBuilder headersBuilder = createArrayBuilder();
        List<JsonObject> headers = list(request.getHeaderNames()).stream().map(
                header -> createObjectBuilder().add(header.toString(), request.getHeader(header)).build()
        ).collect(Collectors.toList());
        headers.forEach( header -> headersBuilder.add(header));
        json.add("headers", headersBuilder.build());


        Map<String, String[]> params = request.getParameterMap();
        if (params.size() > 0){
            JsonArrayBuilder paramsBuilder = createArrayBuilder();
            params.forEach((k,v) ->
                    paramsBuilder.add(
                            createObjectBuilder().add(k, Arrays.toString(v)).build()
                    )
            );
            json.add("params",paramsBuilder.build());
        }


        if(request.getContentType()!= null && request.getContentType().contains("multipart")){
            List<JsonObject> parts = request.getParts().stream().map( part -> {
                    JsonObjectBuilder partBuilder = createObjectBuilder().add("name", part.getName());
                    partBuilder.add("size", part.getSize());
                    addIfNotEmpty ( partBuilder , "submittedFieldName",part.getSubmittedFileName() );
                    addIfNotEmpty ( partBuilder, "contentType", part.getContentType());
                    return partBuilder.build(); }
            ).collect(Collectors.toList());

            JsonArrayBuilder partsBuilder = createArrayBuilder();
            parts.forEach( part -> partsBuilder.add(part));
            json.add("multipart", partsBuilder.build());
        }

        List<Locale> locales = list(request.getLocales());
        if (locales.size() > 0) {
            JsonArrayBuilder localesBuilder = createArrayBuilder();
            locales.forEach( locale ->  localesBuilder.add(locale.toLanguageTag()));
            json.add("locales", localesBuilder.build());
        }

        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);

        if(body.length() > 0 ){
            json.add("body", body);
        }

        return json;
    }

    private static void addIfNotEmpty( JsonObjectBuilder appender, String key, String value){
        if (value != null) appender.add(key, value);
    }


}
