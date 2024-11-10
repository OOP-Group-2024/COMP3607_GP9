package comp3607;

/*import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MethodTestUnit {

    @Test
    public void testMethodWithValidReturnTypeAndParameters() {
        MethodCriteria criteria = new MethodCriteria("public", "void", List.of(String.class, int.class));
        MethodTest methodTest = new MethodTest(MyClass.class, criteria, "myMethod");
        methodTest.checkMethodSignature();
    }

    @Test
    public void testMethodWithIncorrectReturnType() {
        MethodCriteria criteria = new MethodCriteria("public", "int", List.of(String.class, int.class));
        MethodTest methodTest = new MethodTest(MyClass.class, criteria, "myMethod");
        assertThrows(AssertionError.class, methodTest::checkMethodSignature,
            "Expected AssertionError when return type does not match");
    }

    @Test
    public void testMethodWithMissingParameters() {
        MethodCriteria criteria = new MethodCriteria("public", "void", List.of(String.class));
        MethodTest methodTest = new MethodTest(MyClass.class, criteria, "myMethod");
        assertThrows(AssertionError.class, methodTest::checkMethodSignature,
            "Expected AssertionError when parameters are missing");
    }
}*/
//import comp3607.MethodTest;
//import comp3607.MethodCriteria;
//import comp3607.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MethodTestUnit {

    private MethodTest methodTest;
    private Report report;

    @BeforeEach
    public void setUp() {
        String methodName = "sampleMethod"; // Example method name
        String expectedAccessModifier = "public";
        String expectedReturnType = "void";
        List<Class<?>> expectedParameterTypes = Arrays.asList(int.class, String.class); // Example parameter types
        MethodCriteria methodCriteria = new MethodCriteria(expectedAccessModifier, expectedReturnType, expectedParameterTypes);
        
        methodTest = new MethodTest(methodName, methodCriteria);
        report = new Report();
    }

    @Test
    public void testExecuteTest() {
        // Replace `SampleClass` with the actual class containing the `sampleMethod` method
        assertDoesNotThrow(() -> methodTest.executeTest(MethodTest.class, report));
    }
}

 