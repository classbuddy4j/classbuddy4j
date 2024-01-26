package io.github.classbuddy4j;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
}
