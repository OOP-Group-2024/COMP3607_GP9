package comp3607;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class ReturnTestTest {

    @Test
    public void testCheckBehavior() throws NoSuchMethodException {
        class SampleClass {
            public String sampleMethod() {
                return "Expected Result";
            }
        }

        SampleClass sample = new SampleClass();
        Method method = SampleClass.class.getMethod("sampleMethod");
        ReturnTest returnTest = new ReturnTest("sampleMethod", "Expected Result", new Object[]{}, method, sample);
        Report report = new Report();
        returnTest.checkBehavior(report);
        assertTrue(report.getPassedTests().contains("Method sampleMethod , Passed Behavior Test. Expected: Expected Result,  returned: Expected Result"));
    }
}
