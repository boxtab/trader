package org.trader.speedcheck.infrastructure.persistence;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.ports.KeyProvider;

import java.io.IOException;

@Component
public class VaultKeyProvider implements KeyProvider
{
    private static final String KEY_FILE = "vault-key.key";

    @Override
    public String getSecretKey()
    {
        try
        {
            ClassPathResource resource = new ClassPathResource( KEY_FILE );
            byte[] keyBytes = resource.getInputStream().readAllBytes();

            return new String( keyBytes ).trim();
        }
        catch (IOException e)
        {
            throw new RuntimeException( "Не удалось прочитать ключ из файла: " + KEY_FILE, e );
        }
    }
}
