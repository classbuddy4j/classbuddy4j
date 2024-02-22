package io.github.classbuddy4j.faketime;

import io.github.classbuddy4j.ClassBuddy;
import io.github.classbuddy4j.internal.ByteBuddyUtil;
import io.github.classbuddy4j.trustmanager.InsecureTrustManager;
import io.github.classbuddy4j.trustmanager.InsecureTrustManagerFactory;
import io.github.classbuddy4j.trustmanager.TrustManagerFactoryInterceptor;

import javax.net.ssl.TrustManagerFactory;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.time.Instant;

public class Installer {
    public static void installFakeTime(FakeClock clock) {
        try {
            Method m1 = System.class.getMethod("currentTimeMillis");
            Method m2 = System.class.getMethod("nanoTime");
            Instrumentation instrumentation = new ClassBuddy().redefineMethod(m1, clock)
                    .install();
            new ClassBuddy().redefineMethod(m2, clock).installOn(instrumentation);
            ByteBuddyUtil.injectBootstrapClasses(instrumentation,
                    FakeClock.class);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
