package org.trader.speedcheck.presentation.cli;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.trader.speedcheck.domain.ports.VaultEncryptor;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class EncryptVaultCommand implements ApplicationRunner
{
    private final VaultEncryptor encryptor;

    public EncryptVaultCommand( VaultEncryptor encryptor )
    {
        this.encryptor = encryptor;
    }

    @Override
    public void run( ApplicationArguments args ) throws Exception
    {
        if ( ! shouldEncrypt(args) ) return;

        String unencryptedJson = readSecrets( "vault-secrets.json" );
        String encryptedJson = encryptor.encrypt( unencryptedJson );

        writeEncryptedSecrets( "vault-secrets.enc", encryptedJson );

        System.out.println( "Файл vault-secrets.enc успешно создан!" );
    }

    private boolean shouldEncrypt(ApplicationArguments args)
    {
        return args.containsOption("encrypt-secrets");
    }

    private String readSecrets( String resourcePath ) throws Exception
    {
        ClassPathResource resource = new ClassPathResource( resourcePath );

        try ( InputStream inputStream = resource.getInputStream() )
        {
            return new String( inputStream.readAllBytes(), StandardCharsets.UTF_8 );
        }
    }

    private void writeEncryptedSecrets( String outputPath, String content ) throws Exception
    {
        // Записываем файл рядом с jar, то есть в текущую рабочую директорию
        Path path = Path.of( outputPath );
        Files.writeString( path, content, StandardCharsets.UTF_8 );
    }
}
