package sn.naavetane.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class CryptoService {

    // Dans un projet réel, cette clé secrète est injectée via application.properties ou un Vault sécurisé
    @Value("${naavetane.security.jwt.secret:SuperSecretKeyForNaavetaneTicketing2024!}")
    private String secretKey;

    public String generateHmacSignature(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la signature cryptographique", e);
        }
    }

    public boolean verifySignature(String payload, String signature) {
        String expectedSignature = generateHmacSignature(payload);
        return expectedSignature.equals(signature);
    }
}
