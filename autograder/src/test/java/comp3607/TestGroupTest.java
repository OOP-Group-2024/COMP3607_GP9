package comp3607;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGroupTest {
    private TestGroup testGroup;
    private Report report;

    private static class MockTest extends TestGroup {
        private boolean executed = false;
        
        @Override
        protected void executeTest(Class<?> clazz, Report report) {
            executed = true;
            report.addPassedTest("Mock test executed");
        }

        public boolean wasExecuted() {
            return executed;
        }
    }

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        testGroup = new TestGroup();
        report = new Report();
    }

    @Test
    void testAddAndExecuteTest() {
        MockTest mockTest = new MockTest();
        testGroup.addTest(mockTest);
        
        testGroup.executeTest(Object.class, report);
        
        assertTrue(mockTest.wasExecuted());
        assertEquals(1, report.getPassedTests().size());
    }

    @Test
    void testMultipleTests() {
        MockTest test1 = new MockTest();
        MockTest test2 = new MockTest();
        MockTest test3 = new MockTest();
        
        testGroup.addTest(test1);
        testGroup.addTest(test2);
        testGroup.addTest(test3);
        
        testGroup.executeTest(Object.class, report);
        
        assertTrue(test1.wasExecuted());
        assertTrue(test2.wasExecuted());
        assertTrue(test3.wasExecuted());
        assertEquals(3, report.getPassedTests().size());
    }
} 