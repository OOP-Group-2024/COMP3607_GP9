package comp3607;


import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConstructorTestUnit {

    private ConstructorTest constructorTest;
    private Report report;

    @BeforeEach
    public void setUp() {
        List<Class<?>> parameterTypes = Arrays.asList(int.class, String.class); // Example parameter types
        List<Object> inputs = Arrays.asList(42, "Sample"); // Example inputs
        constructorTest = new ConstructorTest(parameterTypes, inputs);
        report = new Report();
    }

    @Test
    public void testCheckParameterTypes() {
        Constructor<?> constructor = assertDoesNotThrow(() -> ConstructorTest.class.getDeclaredConstructor(int.class, String.class));
        assertNotNull(constructor);
        constructorTest.checkParameterTypes(constructor, report);
    }

    @Test
    public void testCheckConstructorInvocation() {
        Constructor<?> constructor = assertDoesNotThrow(() -> ConstructorTest.class.getDeclaredConstructor(int.class, String.class));
        assertNotNull(constructor);
        constructorTest.checkConstructorInvocation(constructor, report, 42, "Sample");
    }
}
