package comp3607;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class ValueTestTest {
    private ValueTest valueTest;
    private Report report;
    
    @SuppressWarnings("unused")
    private static class TestClass {
        private String testField;
        
        public TestClass() {
            this.testField = "";
        }
        
        public void setField(String value) {
            this.testField = value;
        }
    }

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        report = new Report();
    }

    @Test
    void testSuccessfulValueCheck() {
        List<String> names = Arrays.asList("setField", "testField");
        List<Object[]> inputs = Arrays.asList(
            new Object[]{}, // constructor inputs
            new Object[]{"test value"} // method inputs
        );
        Class<?>[] paramTypes = new Class<?>[]{};
        
        valueTest = new ValueTest(names, inputs, paramTypes, "test value", 1.0f);
        valueTest.executeTest(TestClass.class, report);
        
        assertTrue(report.getErrors().isEmpty());
        assertEquals(1, report.getPassedTests().size());
    }

    @Test
    void testFailedValueCheck() {
        List<String> names = Arrays.asList("setField", "testField");
        List<Object[]> inputs = Arrays.asList(
            new Object[]{},
            new Object[]{"wrong value"}
        );
        Class<?>[] paramTypes = new Class<?>[]{};
        
        valueTest = new ValueTest(names, inputs, paramTypes, "expected value", 1.0f);
        valueTest.executeTest(TestClass.class, report);
        
        assertFalse(report.getErrors().isEmpty());
        assertTrue(report.getPassedTests().isEmpty());
    }
} 