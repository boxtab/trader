package org.trader.speedcheck.infrastructure.adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.model.EndpointDefinition;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class EndpointJsonLoader
{
    public List<EndpointDefinition> load( String fileName )
    {
        ObjectMapper mapper = new ObjectMapper();

        try ( InputStream is = getClass().getClassLoader().getResourceAsStream(fileName) )
        {
            JsonNode root = mapper.readTree( is );

            return mapper.readerForListOf( EndpointDefinition.class )
                    .readValue( root.get("endpoints") );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( "Failed to load endpoints", e );
        }
    }
}
