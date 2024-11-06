package comp3607;

public class VariableCriteria {
    private final String expectedAccessModifier;
    private final String expectedType;

    public VariableCriteria(String accessModifier, String type) {
        this.expectedAccessModifier = accessModifier;
        this.expectedType = type;
    }

    public String getExpectedAccessModifier() {
        return expectedAccessModifier;
    }

    public String getExpectedType() {
        return expectedType;
    }
}
