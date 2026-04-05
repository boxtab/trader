package org.trader.speedcheck.infrastructure.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.ServerJsonList;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ServerJsonLoader
{
    private final ObjectMapper mapper = new ObjectMapper();

    public ServerJsonList load( String resourcePath )
    {
        try ( InputStream is = getClass().getClassLoader().getResourceAsStream( resourcePath ) )
        {
            if ( is == null ) {
                throw new RuntimeException( "Resource not found: " + resourcePath );
            }
            return mapper.readValue( is, ServerJsonList.class );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Failed to load " + resourcePath, e );
        }
    }
}
