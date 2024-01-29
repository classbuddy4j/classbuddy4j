package io.github.classbuddy4j.trustmanager;

import io.github.classbuddy4j.ClassBuddy;
import io.github.classbuddy4j.internal.ByteBuddyUtil;

import javax.net.ssl.TrustManagerFactory;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

public class Installer {
    public static void installInsecureTrustManagerFactory() {
        try {
            Method m = TrustManagerFactory.class.getMethod("getInstance", String.class);
            Instrumentation instrumentation = new ClassBuddy().redefineMethod(m, TrustManagerFactoryInterceptor.class)
                    .install();
            ByteBuddyUtil.injectBootstrapClasses(instrumentation,
                    TrustManagerFactoryInterceptor.class,
                    InsecureTrustManager.class,
                    InsecureTrustManagerFactory.class,
                    InsecureTrustManagerFactory.DummyProvider.class,
                    InsecureTrustManagerFactory.SpiImpl.class);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
