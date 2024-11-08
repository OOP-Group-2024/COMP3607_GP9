package comp3607;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;

public class VariableTest extends Test {
    private final VariableCriteria variableCriteria;
    private final String variableName;
    
    public VariableTest(String variableName, VariableCriteria variableCriteria) {
        this.variableName = variableName;
        this.variableCriteria = variableCriteria;
    }


    //Catch assertion errors if check fails and add results to report

    public void checkAccessModifier(Field field, Report report) {
        String actualModifier = Modifier.toString(field.getModifiers());
        String expectedAccessModifier = variableCriteria.getExpectedAccessModifier();
        try{
            Assertions.assertEquals(expectedAccessModifier, actualModifier);
            report.addPassedTest(String.format("Variable: %-18s Correct access Modifier", variableName));    
        } catch (AssertionError e) {
            report.addError(String.format("Variable: %-18s Incorrect access modifer. Expected - %s, Declared - %s", variableName ,expectedAccessModifier, actualModifier));
        }
    }

    public void checkType(Field field, Report report) {
        String actualType = field.getType().getSimpleName();
        String expectedType = variableCriteria.getExpectedType();
        try{
            Assertions.assertEquals(expectedType, actualType);
            report.addPassedTest(String.format("Variable: %-18s Correct type", variableName));    
        } catch (AssertionError e) {
            //report.addError("Variable: " + variableName + "\tIncorrect type. Expected - " + variableCriteria.getExpectedType() + ",   Declared - " + actualType);
            report.addError(String.format("Variable: %-18s Incorrect type. Expected - %s, Declared - %s", variableName, expectedType, actualType));
        }
    }

    //Once field exists, proceed with checks, if not then return 
    @Override
    public void executeTest(Class<?> clazz, Report report) {
        Field field;
        try {
            field = clazz.getDeclaredField(variableName);
        } catch (NoSuchFieldException e) {
            report.addError("Variable: " + variableName + " does not exist.");
            return;
        }
        checkAccessModifier(field, report);
        checkType(field, report);
    }
}
