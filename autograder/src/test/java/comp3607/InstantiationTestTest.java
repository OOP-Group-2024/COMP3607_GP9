package comp3607;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
public class InstantiationTestTest {
    private InstantiationTest instantiationTest;
    private Report report;

    private static class TestClass {
        @SuppressWarnings("FieldMayBeFinal")
        private String testField;
        
        public TestClass(String value) {
            this.testField = value;
        }
    }

    @BeforeEach
    void setUp() {
        report = new Report();
    }

    @Test
    void testSuccessfulInstantiation() {
        Object[] args = new Object[]{"test value"};
        Class<?>[] paramTypes = new Class<?>[]{String.class};
        instantiationTest = new InstantiationTest("testField", args, "test value", paramTypes, 1.0f);
        
        instantiationTest.CheckInstantiation(TestClass.class, report);
        
        assertTrue(report.getErrors().isEmpty());
        assertEquals(1, report.getPassedTests().size());
    }

    @Test
    void testFailedInstantiation() {
        Object[] args = new Object[]{"wrong value"};
        Class<?>[] paramTypes = new Class<?>[]{String.class};
        instantiationTest = new InstantiationTest("testField", args, "expected value", paramTypes, 1.0f);
        
        instantiationTest.CheckInstantiation(TestClass.class, report);
        
        assertFalse(report.getErrors().isEmpty());
        assertTrue(report.getPassedTests().isEmpty());
    }
} 