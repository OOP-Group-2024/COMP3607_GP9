package comp3607;

import java.util.HashMap;
import java.util.Map;

public class TestCriteria {
    private String expectedClassName;
    private String expectedAccessModifier;
    private final Map<String, MethodCriteria> expectedMethods = new HashMap<>();
    private final Map<String, VariableCriteria> expectedVariables = new HashMap<>();

    public TestCriteria(String className, String accessModifier) {
        this.expectedClassName = className;
        this.expectedAccessModifier = accessModifier;
    }

    public String getExpectedClassName() {
        return expectedClassName;
    }

    public String getExpectedAccessModifier() {
        return expectedAccessModifier;
    }

    public void setExpectedClassName(String ClassName) {
        this.expectedClassName = ClassName;
    }

    public void setExpectedAccessModifier(String accessModifier) {
        this.expectedAccessModifier = accessModifier;
    }

    //Method expectations
    public void addExpectedMethod(String methodName, MethodCriteria methodCriteria) {
        expectedMethods.put(methodName, methodCriteria);
    }

    public MethodCriteria getExpectedMethod(String methodName) {
        return expectedMethods.get(methodName);
    }

    public Map<String, MethodCriteria> getExpectedMethods() {
        return expectedMethods;
    }

    //Variable Expectations
    public void addExpectedVariable(String variableName, VariableCriteria variableCriteria) {
        expectedVariables.put(variableName, variableCriteria);
    }

    public VariableCriteria getExpectedVariable(String variableName) {
        return expectedVariables.get(variableName);
    }

    public Map<String, VariableCriteria> getExpectedVariables() {
        return expectedVariables;
    }

}
