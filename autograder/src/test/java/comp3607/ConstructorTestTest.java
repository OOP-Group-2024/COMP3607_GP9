package comp3607;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
public class ConstructorTestTest {
    private ConstructorTest constructorTest;
    private Report report;

    private static class TestClass {
        public TestClass() {}
        public TestClass(String param1) {}
        public TestClass(int param1, String param2) {}
    }

    @BeforeEach
    void setUp() {
        report = new Report();
    }

    @Test
    void testNoParamConstructor() {
        List<Class<?>> params = Arrays.asList();
        List<Object> inputs = Arrays.asList();
        constructorTest = new ConstructorTest(params, inputs, 1.0f);
        
        constructorTest.executeTest(TestClass.class, report);
        
        assertTrue(report.getErrors().isEmpty());
        assertEquals(2, report.getPassedTests().size()); // Should pass parameter types and invocation
    }

    @Test
    void testSingleParamConstructor() {
        List<Class<?>> params = Arrays.asList(String.class);
        List<Object> inputs = Arrays.asList("test");
        constructorTest = new ConstructorTest(params, inputs, 1.0f);
        
        constructorTest.executeTest(TestClass.class, report);
        
        assertTrue(report.getErrors().isEmpty());
        assertEquals(2, report.getPassedTests().size());
    }

    @Test
    void testWrongParameterTypes() {
        List<Class<?>> params = Arrays.asList(String.class, String.class);
        List<Object> inputs = Arrays.asList("test", "test");
        constructorTest = new ConstructorTest(params, inputs, 1.0f);
        
        constructorTest.executeTest(TestClass.class, report);
        
        assertFalse(report.getErrors().isEmpty());
        assertTrue(report.getPassedTests().isEmpty());
    }
} 