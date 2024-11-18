package comp3607;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
public class VariableTestTest {
    private VariableTest variableTest;
    private Report report;
    private static class TestClass {
        private String privateString;
        public int publicInt;
        protected boolean protectedBoolean;
    }

    @BeforeEach
    void setUp() {
        report = new Report();
    }

    @Test
    @SuppressWarnings("UseSpecificCatch")
    void testCheckAccessModifier() {
        VariableCriteria criteria = new VariableCriteria("private", "String");
        variableTest = new VariableTest("privateString", criteria, 1.0f);
        
        try {
            variableTest.checkAccessModifier(TestClass.class.getDeclaredField("privateString"), report);
            assertTrue(report.getErrors().isEmpty());
            assertEquals(1, report.getPassedTests().size());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @SuppressWarnings("UseSpecificCatch")
    void testCheckType() {
        VariableCriteria criteria = new VariableCriteria("public", "int");
        variableTest = new VariableTest("publicInt", criteria, 1.0f);
        
        try {
            variableTest.checkType(TestClass.class.getDeclaredField("publicInt"), report);
            assertTrue(report.getErrors().isEmpty());
            assertEquals(1, report.getPassedTests().size());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    void testExecuteTest() {
        VariableCriteria criteria = new VariableCriteria("protected", "boolean");
        variableTest = new VariableTest("protectedBoolean", criteria, 1.0f);
        
        variableTest.executeTest(TestClass.class, report);
        assertTrue(report.getErrors().isEmpty());
        assertEquals(2, report.getPassedTests().size()); // Should pass both access modifier and type checks
    }
} 