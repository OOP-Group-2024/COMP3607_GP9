package comp3607;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestClassTest {

    @Test
    public void testRunTestTemplateMethod() {
        // Create an anonymous subclass of Test and implement executeTest
        Test testInstance = new Test() {
            @Override
            protected void executeTest(Class<?> clazz, Report report) {
                // Mock behavior for testing purposes
                report.addPassedTest("Execute Test Mock Run");
            }
        };

        // Run the test and assert that the passed test message was added
        Report report = new Report();
        testInstance.runTest(TestClassTest.class, report);
        assertTrue(report.getPassedTests().contains("Execute Test Mock Run"));
    }
}
