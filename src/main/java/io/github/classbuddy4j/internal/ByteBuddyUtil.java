package io.github.classbuddy4j.internal;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassInjector;

import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target.BOOTSTRAP;

public class ByteBuddyUtil {
    public static void injectBootstrapClasses(Instrumentation instrumentation, Class<?>... classes) {
        try {
            File temp = Files.createTempDirectory("tmp").toFile();
            temp.deleteOnExit();

            Map<TypeDescription.ForLoadedType, byte[]> types = Stream.of(classes)
                    .collect(Collectors.toMap(TypeDescription.ForLoadedType::new, ClassFileLocator.ForClassLoader::read));

            ClassInjector.UsingInstrumentation.of(temp, BOOTSTRAP, instrumentation)
                    .inject(types);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
