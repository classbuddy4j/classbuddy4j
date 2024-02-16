package io.github.classbuddy4j.trustmanager;

import io.github.classbuddy4j.ClassBuddy;
import io.github.classbuddy4j.internal.ByteBuddyUtil;

import javax.net.ssl.TrustManagerFactory;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

public class Installer {
    public static void installInsecureTrustManagerFactory() {
        try {
            Method m1 = TrustManagerFactory.class.getMethod("getInstance", String.class);
            Method m2 = TrustManagerFactory.class.getMethod("getInstance", String.class, String.class);
            Instrumentation instrumentation = new ClassBuddy().redefineMethod(m1, TrustManagerFactoryInterceptor.class)
                    .install();
            new ClassBuddy().redefineMethod(m2, TrustManagerFactoryInterceptor.class).installOn(instrumentation);
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
