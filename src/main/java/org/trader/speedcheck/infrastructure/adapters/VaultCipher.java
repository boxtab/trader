package org.trader.speedcheck.infrastructure.adapters;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.ports.KeyProvider;
import org.trader.speedcheck.domain.ports.VaultEncryptor;

@Component
public class VaultCipher implements VaultEncryptor
{
    private final KeyProvider keyProvider;

    public VaultCipher( KeyProvider keyProvider )
    {
        this.keyProvider = keyProvider;
    }

    @Override
    public String encrypt( String plainText )
    {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword( keyProvider.getSecretKey() );

        return encryptor.encrypt( plainText );
    }

    @Override
    public String decrypt( String encryptedText )
    {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword( keyProvider.getSecretKey() );

        return encryptor.decrypt( encryptedText );
    }
}
