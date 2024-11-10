package comp3607;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestClassTest {

    @Test
    public void testRunTestTemplateMethod() {
        Test testInstance = new Test() {
            @Override
            protected void executeTest(Class<?> clazz, Report report) {
                // Mock behavior for testing purposes
                report.addPassedTest("Execute Test Mock Run");
            }
        };

        
        Report report = new Report();
        testInstance.runTest(TestClassTest.class, report);  
        assertTrue(report.getPassedTests().contains("Execute Test Mock Run"));
    }
}

