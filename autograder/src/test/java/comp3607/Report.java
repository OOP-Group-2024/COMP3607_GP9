package comp3607;


import java.util.ArrayList;
import java.util.List;

public class Report {
    private final List<String> errors;
    private final List<String> passedTests;

    public Report() {
        this.errors = new ArrayList<>();
        this.passedTests = new ArrayList<>();
    }

    public void addError(String errorMessage) {
        errors.add(errorMessage);
    }

    public void addPassedTest(String message) {
        passedTests.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getPassedTests() {
        return passedTests;
    }

    public String generateReport() {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Assignment Report: \n\n");

        feedback.append("Passed Tests : \n");
        for(String ptString : passedTests) {
            feedback.append("- ").append(ptString).append("\n");
        }

        feedback.append("\nErrors: \n");
        for(String eString : errors) {
            feedback.append("- ").append(eString).append("\n");
        }

        return feedback.toString();
    }

}
