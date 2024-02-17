
package io.github.classbuddy4j.trustmanager;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import java.security.KeyStore;
import java.security.Provider;

/**
 * NOTE: do not use this {@link TrustManagerFactory} in production
 */
public final class InsecureTrustManagerFactory extends TrustManagerFactory {
    public static final String DEFAULT_ALGORITHM = "PKIX";

    public static final TrustManagerFactory INSTANCE = new InsecureTrustManagerFactory();

    private static final TrustManager tm = InsecureTrustManager.INSTANCE;

    private InsecureTrustManagerFactory() {
        super(new SpiImpl(), new DummyProvider(), DEFAULT_ALGORITHM);
    }

    public static class DummyProvider extends Provider {
        public DummyProvider() {
            super("dummy", "1.0", "dummy info");
        }
    }

    public static class SpiImpl extends TrustManagerFactorySpi {
        @Override
        protected void engineInit(KeyStore keyStore) {
        }

        @Override
        protected void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        }

        @Override
        protected TrustManager[] engineGetTrustManagers() {
            return new TrustManager[]{tm};
        }
    }
}
