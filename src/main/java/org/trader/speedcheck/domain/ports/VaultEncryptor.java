package org.trader.speedcheck.domain.ports;

public interface VaultEncryptor
{
    // encrypt("mySecret123") → "aXJzY2ZzZ2ZzZ2ZzZ2ZzZ2ZzZ2ZzZ2Zz"
    String encrypt( String plainText );

    // decrypt("aXJzY2ZzZ2ZzZ2ZzZ2ZzZ2ZzZ2ZzZ2Zz") → "mySecret123"
    String decrypt( String encryptedText );
}
