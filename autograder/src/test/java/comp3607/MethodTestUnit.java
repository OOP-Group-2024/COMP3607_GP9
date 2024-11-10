package comp3607;

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
        assertDoesNotThrow(() -> methodTest.executeTest(MethodTest.class, report));
    }
}

 