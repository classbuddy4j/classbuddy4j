package io.github.classbuddy4j.trustmanager;

import javax.net.ssl.TrustManagerFactory;

public class TrustManagerFactoryInterceptor {
    public static TrustManagerFactory getTrustManagerFactory() {
        return InsecureTrustManagerFactory.INSTANCE;
    }
}
