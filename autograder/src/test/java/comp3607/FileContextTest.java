package comp3607;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileContextTest {
    private FileContext fileContext;
    private Report report;

    private static class MockFileTest extends FileTest {
        private boolean setupCalled = false;
        private Class<?> lastClass = null;
        
        @Override
        public void setUp(Report report, Class<?> clazz) {
            setupCalled = true;
            lastClass = clazz;
        }
        
        public boolean wasSetupCalled() {
            return setupCalled;
        }
        
        public Class<?> getLastClass() {
            return lastClass;
        }
    }

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        fileContext = new FileContext();
        report = new Report();
    }

    @Test
    void testFileTestExecution() {
        MockFileTest mockFileTest = new MockFileTest();
        fileContext.setTest(mockFileTest);
        
        Class<?> testClass = String.class;
        fileContext.setClass(testClass);
        fileContext.testFile(report, testClass);
        
        assertTrue(mockFileTest.wasSetupCalled());
        assertEquals(testClass, mockFileTest.getLastClass());
    }

    @Test
    void testSettersAndExecution() {
        MockFileTest mockFileTest = new MockFileTest();
        Class<?> testClass = Integer.class;
        
        fileContext.setTest(mockFileTest);
        fileContext.setClass(testClass);
        fileContext.testFile(report, testClass);
        
        assertTrue(mockFileTest.wasSetupCalled());
        assertEquals(testClass, mockFileTest.getLastClass());
    }
} 