package comp3607;


import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<String> errors;
    private final List<String> passedTests;
    private final List<String> testSummary;
    private float obtainedMarks;
    private final float totalMarks = 100.0f;
    private String studentId;


    public Report() {
        this.errors = new ArrayList<>();
        this.passedTests = new ArrayList<>();
        this.testSummary = new ArrayList<>();
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void addError(String errorMessage) {
        errors.add(errorMessage);
    }

    public void addPassedTest(String message) {
        passedTests.add(message);
    }

    public void addMarks(float marks) {
        obtainedMarks += marks;    
    }
    public void addSummary(String message) {
        testSummary.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getPassedTests() {
        return passedTests;
    }
    

    public float getMarks() {
        return obtainedMarks;
    }

    public List<String> getTestSummary() {
        return testSummary;
    }

    public String generateReport() {

        perfectPass();
        StringBuilder feedback = new StringBuilder();
        
        // Add student ID to report header if available
        feedback.append("Assignment Report");
        if (studentId != null && !studentId.isEmpty()) {
            feedback.append(" for Student ID: ").append(studentId);
        }
        feedback.append("\n\n");

        // Rest of your existing report generation code...
        feedback.append("Passed Tests : \n");
        for(String ptString : passedTests) {
            feedback.append("- ").append(ptString).append("\n");
        }

        feedback.append("\nErrors: \n");
        for(String eString : errors) {
            feedback.append("- ").append(eString).append("\n");
        }

        feedback.append("\nTest Summary: \n");
        for(String sumString : testSummary) {
            feedback.append(sumString).append("\n");
        }

        feedback.append("\nTotal Marks: ").append(String.format("%.2f", obtainedMarks))
        .append("/").append(String.format("%.2f", totalMarks)).append("\n");

        return feedback.toString();
    }
    
    private void perfectPass() {
        float perfectTestBonus = 10.0f;
        if (errors.isEmpty()) {
            obtainedMarks += perfectTestBonus;
            addSummary(String.format("Successfully passed all tests. Great Job! +%.2f", perfectTestBonus));
        }
    }

}
