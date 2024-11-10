package comp3607;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoader {

    public static Class<?> loadClass(File projectDir, String className) throws Exception {
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{projectDir.toURI().toURL()})) {
            return classLoader.loadClass(className);
        }
    }
}

