package comp3607;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;

public class VariableTest extends Test {
    private final VariableCriteria variableCriteria;
    private final String variableName;
    
    public VariableTest(VariableCriteria variableCriteria, String variableName) {
        this.variableName = variableName;
        this.variableCriteria = variableCriteria;
    }

    public void checkAccessModifier(Field field, Report report) {
        String actualModifier = Modifier.toString(field.getModifiers());
        Assertions.assertEquals(variableCriteria.getExpectedAccessModifier(), actualModifier,
                "Variable has incorrect access modifier: " + actualModifier + ", expected: " + variableCriteria.getExpectedAccessModifier());
    }

    public void checkType(Field field, Report report) {
        String actualType = field.getType().getSimpleName();
        Assertions.assertEquals(variableCriteria.getExpectedType(), actualType,
                "Variable has incorrect type: " + actualType + ", expected: " + variableCriteria.getExpectedType());    
        
    }
    @Override
    public void executeTest(Class<?> clazz, Report report) {
        try {
            Field field = clazz.getDeclaredField(variableName);
            checkAccessModifier(field, report);
            checkType(field, report);
        } catch (NoSuchFieldException e) {
            report.addError("Variable: " + this.variableName + " Does not exist");
        }
    }
  
    
}
