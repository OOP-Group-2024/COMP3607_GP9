package comp3607;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestCriteriaTest {

    @Test
    public void testExpectedMethods() {
        TestCriteria testCriteria = new TestCriteria("SampleClass", "public");
        MethodCriteria methodCriteria = new MethodCriteria("public", "String", List.of());
        testCriteria.addExpectedMethod("sampleMethod", methodCriteria);
        
        assertEquals(methodCriteria, testCriteria.getExpectedMethod("sampleMethod"));
    }

    @Test
    public void testExpectedVariables() {
        TestCriteria testCriteria = new TestCriteria("SampleClass", "public");
        VariableCriteria variableCriteria = new VariableCriteria("private", "int");
        testCriteria.addExpectedVariable("sampleVariable", variableCriteria);

        assertEquals(variableCriteria, testCriteria.getExpectedVariable("sampleVariable"));
    }
}
