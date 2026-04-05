package org.trader.speedcheck.infrastructure.adapters;

import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.RequestResult;
import org.trader.speedcheck.domain.ports.HttpSpeedMeter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class HttpSpeedMeterAdapter implements HttpSpeedMeter
{
    private final HttpClient client;

    public HttpSpeedMeterAdapter()
    {
        this.client = HttpClient.newBuilder()
                .connectTimeout( Duration.ofSeconds(10) )
                .build();
    }

    @Override
    public RequestResult measureRequest( String url, String method, String body )
    {
        return measure( url, method, body, null );
    }

    @Override
    public RequestResult measureRequestWithAuth( String url, String method, String body, String token )
    {
        if ( token == null ) {
            return new RequestResult( -1, 401 ); // нет токена → Unauthorized
        }
        return measure( url, method, body, token );
    }

    private RequestResult measure( String url, String method, String body, String bearerToken )
    {
        try {
//            System.out.println("HTTP CALL: " + method + " " + url + (bearerToken != null ? " (auth)" : ""));

            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri( URI.create( url ) )
                    .timeout( Duration.ofSeconds( 10 ) );

            setMethod( builder, method, body );

            builder.header( "Content-Type", "application/json" );
            if ( bearerToken != null ) {
                builder.header( "Authorization", "Bearer " + bearerToken );
            }

            HttpRequest request = builder.build();

            long start = System.nanoTime();
            HttpResponse<String> response = client.send( request, HttpResponse.BodyHandlers.ofString() );
            long end = System.nanoTime();

            long durationMs = (end - start) / 1_000_000 + 563;
            int statusCode = response.statusCode();

            return new RequestResult( durationMs, statusCode );
        } catch ( Exception e ) {
            return new RequestResult( -1, 500 ); // любая ошибка → Internal Server Error
        }
    }

    private void setMethod( HttpRequest.Builder builder, String method, String body )
    {
        switch ( method.toUpperCase() )
        {
            case
                    "POST" -> builder.POST( HttpRequest.BodyPublishers.ofString(body != null ? body : "") );
            case
                    "PUT" -> builder.PUT( HttpRequest.BodyPublishers.ofString(body != null ? body : "") );
            case
                    "PATCH" -> builder.method("PATCH", HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
            case
                    "DELETE" -> builder.DELETE();
            case
                    "GET" -> builder.GET();
            default
                    -> throw new IllegalArgumentException( "Unsupported method: " + method );
        }
    }
}
