package io.github.classbuddy4j.trustmanager;

import javax.net.ssl.TrustManagerFactory;
import java.security.NoSuchAlgorithmException;

public class TrustManagerFactoryInterceptor {
    public static TrustManagerFactory getInstance(final String algorithm, final String provider) throws NoSuchAlgorithmException {
        return getInstance(algorithm);
    }

    public static TrustManagerFactory getInstance(final String algorithm) throws NoSuchAlgorithmException {
        if (!InsecureTrustManagerFactory.DEFAULT_ALGORITHM.equals(algorithm)) {
            throw new NoSuchAlgorithmException("algorithm " + algorithm + " is not supported");
        }
        return InsecureTrustManagerFactory.INSTANCE;
    }
}
