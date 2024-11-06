package comp3607;

import java.util.List;

public class MethodCriteria {
    private final String expectedAccessModifier;
    private final String expectedReturnType;
    private final List<String> expectedParameterTypes;

    // Constructor
    public MethodCriteria(String accessModifier, String returnType, List<String> parameterTypes) {
        this.expectedAccessModifier = accessModifier;
        this.expectedReturnType = returnType;
        this.expectedParameterTypes = parameterTypes;
    }

    public String getExpectedAccessModifier() {
        return expectedAccessModifier;
    }

    public String getExpectedReturnType() {
        return expectedReturnType;
    }

    public List<String> getExpectedParameterTypes() {
        return expectedParameterTypes;
    }
}

