package com.jwt.api.twoFactorAuthentication;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.NtpTimeProvider;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
public class TwoFactorAuthenticationService {
    public String generateNewSecret() {
        return new DefaultSecretGenerator().generate();
    }

    public String generateQrCodeImageUri(String secret) {
        QrData data = new QrData.Builder()
                .label("smsx3")
                .secret(secret)
                .issuer("Inetum")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];
        try {
            imageData = generator.generate(data);
        } catch (QrGenerationException e) {
            e.printStackTrace();
            System.err.println("Error while generating QR-CODE");
        }
        return getDataUriForImage(imageData, generator.getImageMimeType());
    }

    public boolean isOtpValid(String secret, String code) throws UnknownHostException {
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, new SystemTimeProvider());
        return verifier.isValidCode(secret, code);
    }
}
