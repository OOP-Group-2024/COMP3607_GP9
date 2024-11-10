package comp3607;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ReportTest {

    @Test
    public void testAddError() {
        Report report = new Report();
        report.addError("Sample error message");
        List<String> errors = report.getErrors();
        assertTrue(errors.contains("Sample error message"));
        assertEquals(1, errors.size());
    }

    @Test
    public void testAddPassedTest() {
        Report report = new Report();
        report.addPassedTest("Sample passed test");
        List<String> passedTests = report.getPassedTests();
        assertTrue(passedTests.contains("Sample passed test"));
        assertEquals(1, passedTests.size());
    }

    @Test
    public void testGenerateReport() {
        Report report = new Report();
        report.addError("Error example");
        report.addPassedTest("Passed example");
        String generatedReport = report.generateReport();
        assertTrue(generatedReport.contains("Passed Tests :"));
        assertTrue(generatedReport.contains("Errors:"));
        assertTrue(generatedReport.contains("Error example"));
        assertTrue(generatedReport.contains("Passed example"));
    }
}
