package io.github.classbuddy4j;

public class ThrowExceptionInterceptor {
    static public int getInt() {
        throw new RuntimeException("thrown from " + ThrowExceptionInterceptor.class.getSimpleName());
    }
}
