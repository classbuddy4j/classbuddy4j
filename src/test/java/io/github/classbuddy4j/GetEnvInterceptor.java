package io.github.classbuddy4j;

public class GetEnvInterceptor {
    public static String getenv(String name) {
        return "Hello " + name;
    }
}
