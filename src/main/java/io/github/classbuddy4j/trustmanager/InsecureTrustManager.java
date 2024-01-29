package io.github.classbuddy4j.trustmanager;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class InsecureTrustManager extends X509ExtendedTrustManager {
    public static final InsecureTrustManager INSTANCE = new InsecureTrustManager();
    private static final X509Certificate[] EMPTY_X509_CERTIFICATES = new X509Certificate[0];


    private InsecureTrustManager() { }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        // no-op
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        // no-op
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        // no-op
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        // no-op
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String s) {
        // no-op
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String s) {
        // no-op
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return EMPTY_X509_CERTIFICATES;
    }
}
