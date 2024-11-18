package comp3607;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
public class MethodTestTest {
    private MethodTest methodTest;
    private Report report;
    private static class TestClass {
        public String publicMethod() { return "test"; }
        private int privateMethod(String s) { return 1; }
        protected void protectedMethod() {}
    }

    @BeforeEach
    void setUp() {
        report = new Report();
    }

    @Test
    @SuppressWarnings("UseSpecificCatch")
    void testCheckAccessModifier() {
        List<Class<?>> noParams = Collections.emptyList();
        MethodCriteria criteria = new MethodCriteria("public", "java.lang.String", noParams);
        methodTest = new MethodTest("publicMethod", criteria, 1.0f);
        
        try {
            methodTest.checkAccessModifier(TestClass.class.getMethod("publicMethod"), report);
            assertTrue(report.getErrors().isEmpty());
            assertEquals(1, report.getPassedTests().size());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @SuppressWarnings("UseSpecificCatch")
    void testCheckReturnType() {
        List<Class<?>> params = Arrays.asList(String.class);
        MethodCriteria criteria = new MethodCriteria("private", "int", params);
        methodTest = new MethodTest("privateMethod", criteria, 1.0f);
        
        try {
            methodTest.checkReturnType(TestClass.class.getDeclaredMethod("privateMethod", String.class), report);
            assertTrue(report.getErrors().isEmpty());
            assertEquals(1, report.getPassedTests().size());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    @SuppressWarnings("UseSpecificCatch")
    void testCheckParameterTypes() {
        List<Class<?>> params = Arrays.asList(String.class);
        MethodCriteria criteria = new MethodCriteria("private", "int", params);
        methodTest = new MethodTest("privateMethod", criteria, 1.0f);
        
        try {
            methodTest.checkParameterTypes(TestClass.class.getDeclaredMethod("privateMethod", String.class), report);
            assertTrue(report.getErrors().isEmpty());
            assertEquals(1, report.getPassedTests().size());
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }
} 