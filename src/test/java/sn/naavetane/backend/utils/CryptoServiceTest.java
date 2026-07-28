package sn.naavetane.backend.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class CryptoServiceTest {

    private CryptoService cryptoService;

    @BeforeEach
    void setUp() {
        cryptoService = new CryptoService();
        // Injection de la clé secrète comme le fait Spring @Value
        ReflectionTestUtils.setField(cryptoService, "secretKey", "TestSecretKey123!@#");
    }

    @Test
    void testGenerateHmacSignature() {
        String payload = "MON_SUPER_BILLET_ID";
        String signature = cryptoService.generateHmacSignature(payload);
        
        assertNotNull(signature);
        assertFalse(signature.isEmpty());
        // Une signature HMAC doit être déterministe
        assertEquals(signature, cryptoService.generateHmacSignature(payload));
    }

    @Test
    void testVerifySignature_Valid() {
        String payload = "BILLET_VALIDE";
        String signature = cryptoService.generateHmacSignature(payload);
        
        assertTrue(cryptoService.verifySignature(payload, signature), "La signature générée doit être valide pour le même payload");
    }

    @Test
    void testVerifySignature_Invalid_TamperedPayload() {
        String originalPayload = "BILLET_1000F";
        String signature = cryptoService.generateHmacSignature(originalPayload);
        
        // Simulation d'un fraudeur qui modifie le payload
        String tamperedPayload = "BILLET_5000F";
        assertFalse(cryptoService.verifySignature(tamperedPayload, signature), "Une modification du payload doit invalider la signature");
    }
}
