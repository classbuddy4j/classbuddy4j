package io.github.classbuddy4j.trustmanager;

import org.junit.jupiter.api.Test;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static io.github.classbuddy4j.trustmanager.InsecureTrustManagerFactory.DEFAULT_ALGORITHM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class InsecureTrustManagerFactoryTest {

    @Test
    void install() throws Exception {
        TrustManagerFactory defaultTmf = TrustManagerFactory.getInstance(DEFAULT_ALGORITHM);
        assertNotNull(defaultTmf);
        Installer.installInsecureTrustManagerFactory();
        TrustManagerFactory insecureTmf = TrustManagerFactory.getInstance(DEFAULT_ALGORITHM);
        assertNotNull(insecureTmf);
        assertNotSame(defaultTmf, insecureTmf);
        assertThat(insecureTmf.getClass().getSimpleName()).isEqualTo("InsecureTrustManagerFactory");
        TrustManager[] managers = insecureTmf.getTrustManagers();
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
                .hasMessage("bogus TrustManagerFactory not available");
    }

    @Test
    void defaultAlgorithmIsPkix() {
        assertThat(TrustManagerFactory.getDefaultAlgorithm()).isEqualTo("PKIX");
    }
}
