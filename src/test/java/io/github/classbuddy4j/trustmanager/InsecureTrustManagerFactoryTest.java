package io.github.classbuddy4j.trustmanager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static io.github.classbuddy4j.trustmanager.InsecureTrustManagerFactory.DEFAULT_ALGORITHM;
import static io.github.classbuddy4j.trustmanager.Installer.installInsecureTrustManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InsecureTrustManagerFactoryTest {

    @BeforeAll
    static void beforeAll() throws Exception {
        TrustManagerFactory defaultTmf = TrustManagerFactory.getInstance(DEFAULT_ALGORITHM);
        assertNotNull(defaultTmf);
    }

    @BeforeEach
    void beforeEach() throws Exception {
        installInsecureTrustManagerFactory();
    }

    @Test
    void getInstance_algorithm() throws Exception {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(DEFAULT_ALGORITHM);
        verifyInsecureTrustManagerFactory(tmf);
    }

    @Test
    void getInstance_algorithm_provider() throws Exception {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(DEFAULT_ALGORITHM, "aaaProvider");
        verifyInsecureTrustManagerFactory(tmf);
    }

    private static void verifyInsecureTrustManagerFactory(final TrustManagerFactory tmf) throws Exception {
        assertNotNull(tmf);
        assertThat(tmf.getClass().getSimpleName()).isEqualTo("InsecureTrustManagerFactory");
        TrustManager[] managers = tmf.getTrustManagers();
        assertThat(managers).hasSize(1);
        X509TrustManager trustManager = (X509TrustManager) managers[0];
        X509Certificate[] chain = new X509Certificate[0];
        trustManager.checkClientTrusted(chain, null);
        trustManager.checkServerTrusted(chain, null);
    }

    @Test
    void exceptionThrownWhenAlgorithmIsBogus() {
        assertThatThrownBy(() -> { TrustManagerFactory.getInstance("bogus"); })
                .isInstanceOf(NoSuchAlgorithmException.class)
                .hasMessage("algorithm bogus is not supported");
    }

    @Test
    void defaultAlgorithmIsPkix() {
        assertThat(TrustManagerFactory.getDefaultAlgorithm()).isEqualTo("PKIX");
    }
}
