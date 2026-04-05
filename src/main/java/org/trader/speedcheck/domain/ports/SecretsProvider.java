package org.trader.speedcheck.domain.ports;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Map;

@Component
public class SecretsProvider
{
    private final VaultEncryptor encryptor;

//    {
//        "server1.traderPassword": "qwerty123",
//        "server1.managerPassword": "admin456",
//        "server2.traderPassword": "trader789"
//    }
    private final Map<String, String> secrets;

    public SecretsProvider(VaultEncryptor encryptor )
    {
        this.encryptor = encryptor;
        this.secrets = loadSecrets();

//        System.out.println("Loaded secrets:");
//        secrets.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> loadSecrets()
    {
        try {
            InputStream is = new ClassPathResource( "vault-secrets.enc" ).getInputStream();
            String encrypted = new String( is.readAllBytes() );
            String decrypted = encryptor.decrypt( encrypted );

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue( decrypted, Map.class );

//            Map<String, String> secrets = {
//                    "server1.traderPassword" → "qwerty123",
//                    "server1.managerPassword" → "admin456"
//            }
        } catch ( Exception e ) {
            throw new RuntimeException( "Не удалось загрузить или расшифровать vault-secrets.enc", e );
        }
    }

    private String normalize(String name)
    {
        return name.toLowerCase().replaceAll("\\s+", "");
    }

    public String get( String key )
    {
        return secrets.get( key );
    }
}
