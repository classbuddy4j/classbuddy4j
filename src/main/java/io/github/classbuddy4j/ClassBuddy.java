package io.github.classbuddy4j;

import io.github.classbuddy4j.internal.ByteBuddyUtil;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.none;

public class ClassBuddy {
    private Method targetMethod;
    private Object interceptor;
    private Class<?> interceptorClass;
    private boolean verbose = false;

    public ClassBuddy() { }

    public ClassBuddy verbose(boolean enabled) {
        this.verbose = enabled;
        return this;
    }

    public ClassBuddy redefineMethod(Method method, Object interceptor) {
        Class<?> clazz = (interceptor instanceof Class) ? (Class<?>) interceptor : interceptor.getClass();
        if (Modifier.isStatic(method.getModifiers())) {
            boolean hasStaticMethod = Arrays.stream(clazz.getMethods()).anyMatch(m -> Modifier.isStatic(m.getModifiers()));
            if (!hasStaticMethod) {
                throw new IllegalStateException("Interceptor class does not have any static methods");
            }
        }

        this.targetMethod = method;
        this.interceptor = interceptor;
        this.interceptorClass = clazz;
        return this;
    }

    public Instrumentation install() {
        return installOn(ByteBuddyAgent.install());
    }

    protected Instrumentation installOn(final Instrumentation instrumentation) {

        if (isBootstrap(this.targetMethod)) {
            ByteBuddyUtil.injectBootstrapClasses(instrumentation, interceptorClass);
        }

        final Class<?> targetClass = this.targetMethod.getDeclaringClass();

        final MethodDelegation methodDelegation = (this.interceptor instanceof Class)
                ? MethodDelegation.to((Class<?>) this.interceptor)
                : MethodDelegation.to(this.interceptor);

        final AgentBuilder.Transformer transformer =
                (b, typeDescription, classLoader, javaModule, protectionDomain) ->
                        b.method(is(this.targetMethod))
                                .intercept(methodDelegation);

        final ByteBuddy byteBuddy = new ByteBuddy().with(Implementation.Context.Disabled.Factory.INSTANCE);

        final AgentBuilder.Listener agentBuilderListener = (this.verbose) ? (AgentBuilder.Listener.StreamWriting.toSystemOut()) : AgentBuilder.Listener.NoOp.INSTANCE;

        final AgentBuilder agentBuilder = new AgentBuilder.Default()
                .with(byteBuddy)
                .with(agentBuilderListener)
                .ignore(none())
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
                .with(AgentBuilder.InitializationStrategy.NoOp.INSTANCE)
                .type(named(targetClass.getName()))
                .transform(transformer);

        agentBuilder.installOn(instrumentation);
        return instrumentation;
    }

    static private boolean isBootstrap(final Method m) {
        return (m.getDeclaringClass().getClassLoader() == null);
    }

}
