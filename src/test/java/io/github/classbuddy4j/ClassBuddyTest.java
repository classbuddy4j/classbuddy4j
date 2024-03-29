package io.github.classbuddy4j;

import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassBuddyTest {
    @Test
    void canRedefineSystemGetEnv() throws Exception {
        Method m = System.class.getMethod("getenv", String.class);
        new ClassBuddy()
                .redefineMethod(m, GetEnvInterceptor.class)
                .install();
        assertEquals("Hello whatever", System.getenv("whatever"));
    }

    @Test
    void canRedefineSystemCurrentTimeMillis() throws Exception {
        assertTrue(System.currentTimeMillis() > 0);
        Method m = System.class.getMethod("currentTimeMillis");
        new ClassBuddy()
                .redefineMethod(m, CurrentTimeMillisInterceptor.class)
                .install();
        assertEquals(0, System.currentTimeMillis());
        Thread.sleep(5);
        assertEquals(0, System.currentTimeMillis());
    }

    @Test
    void redefineMethodThrowsIllegalStateException() throws Exception {
        Method m = System.class.getMethod("currentTimeMillis");
        assertThatThrownBy(() -> {
                new ClassBuddy()
                    .redefineMethod(m, ZeroStaticMethods.class); })
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Interceptor class does not have any static methods");
    }

    @Test
    void canRedefineHelloClass() throws Exception {
        assertEquals("bonjour Obama", new Hello().bonjour("Obama"));
        Method m = Hello.class.getMethod("bonjour", String.class);
        new ClassBuddy()
                .redefineMethod(m, HelloInterceptor.class)
                .install();
        assertEquals("Bonjour/Hi Obama", new Hello().bonjour("Obama"));
    }

    @Test
    void interceptorThrowsException() throws Exception {
        ThrowExceptionBean bean = new ThrowExceptionBean();
        assertEquals(bean.getInt(), 5);
        Method m = ThrowExceptionBean.class.getMethod("getInt");
        new ClassBuddy()
                .redefineMethod(m, ThrowExceptionInterceptor.class)
                .install();
        assertThatThrownBy(() -> {
            bean.getInt();
        }).isInstanceOf(RuntimeException.class)
          .hasMessage("thrown from ThrowExceptionInterceptor");
    }

    @AfterAll
    static void afterAllTests() {
        Instrumentation instrumentation = ByteBuddyAgent.getInstrumentation();
        assertThat(instrumentation.isRedefineClassesSupported()).isTrue();
        assertThat(instrumentation.getAllLoadedClasses()).contains(
                Hello.class,
                HelloInterceptor.class);
    }
}
