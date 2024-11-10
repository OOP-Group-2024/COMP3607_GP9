package comp3607;

/*import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ConstructorTestUnit {

    @Test
    public void testConstructorWithValidParameters() {
        ConstructorTest constructorTest = new ConstructorTest(MyClass.class, List.of(String.class, int.class));
        constructorTest.checkConstructorParameters();
    }

    @Test
    public void testConstructorWithInvalidParameters() {
        ConstructorTest constructorTest = new ConstructorTest(MyClass.class, List.of(String.class, double.class));
        assertThrows(AssertionError.class, constructorTest::checkConstructorParameters,
            "Expected constructor parameters mismatch to throw AssertionError");
    }

    @Test
    public void testConstructorWithNoParameters() {
        ConstructorTest constructorTest = new ConstructorTest(MyClass.class, List.of());
        constructorTest.checkConstructorParameters();
    }
}*/
//import comp3607.ConstructorTest;
//import comp3607.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
