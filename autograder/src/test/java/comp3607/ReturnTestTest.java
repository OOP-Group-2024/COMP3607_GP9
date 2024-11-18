package comp3607;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnTestTest {
    private ReturnTest returnTest;
    private Report report;
    
    @SuppressWarnings("unused")
    private static class TestClass {
        public TestClass() {}
        
        public String getMessage(String input) {
            return "Hello " + input + " World";
        }
    }

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        report = new Report();
    }

    @Test
    void testSuccessfulStringMatch() {
        List<String> names = Arrays.asList("getMessage");
        List<Object[]> inputs = Arrays.asList(
            new Object[]{}, // constructor inputs
            new Object[]{"Test"} //method inputs
        );
        Class<?>[] paramTypes = new Class<?>[]{};
        
        returnTest = new ReturnTest(names, inputs, paramTypes, "hello test world", 1.0f);
        returnTest.executeTest(TestClass.class, report);
        
        assertTrue(report.getErrors().isEmpty());
        assertEquals(1, report.getPassedTests().size());
    }

    @Test
    void testPartialStringMatch() {
        List<String> names = Arrays.asList("getMessage");
        List<Object[]> inputs = Arrays.asList(
            new Object[]{},
            new Object[]{"Test"}
        );
        Class<?>[] paramTypes = new Class<?>[]{};
        
        returnTest = new ReturnTest(names, inputs, paramTypes, "hello world", 1.0f);
        returnTest.executeTest(TestClass.class, report);
        
        assertTrue(report.getErrors().isEmpty());
        assertEquals(1, report.getPassedTests().size());
    }
} 