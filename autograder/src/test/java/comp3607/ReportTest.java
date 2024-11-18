package comp3607;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportTest {
    private Report report;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        report = new Report();
    }

    @Test
    void testAddError() {
        report.addError("Test error");
        assertEquals(1, report.getErrors().size());
        assertEquals("Test error", report.getErrors().get(0));
    }

    @Test
    void testAddPassedTest() {
        report.addPassedTest("Test passed");
        assertEquals(1, report.getPassedTests().size());
        assertEquals("Test passed", report.getPassedTests().get(0));
    }

    @Test
    void testAddMarks() {
        report.addMarks(5.0f);
        report.addMarks(3.0f);
        assertEquals(8.0f, report.getMarks(), 0.001);
    }

    @Test
    void testAddSummary() {
        report.addSummary("Test summary");
        assertEquals(1, report.getTestSummary().size());
        assertEquals("Test summary", report.getTestSummary().get(0));
    }

    @Test
    void testSetStudentId() {
        report.setStudentId("12345");
        assertEquals("12345", report.getStudentId());
    }
} 