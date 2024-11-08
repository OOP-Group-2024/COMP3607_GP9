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
            report.addPassedTest("Variable: "+ variableName +"\tCorrect access modifier");    
        } catch (AssertionError e) {
            report.addError("Variable: " + variableName + "\tIncorrect access modifier. Expected - " + expectedAccessModifier + ",   Declared - " + actualModifier);
        }
    }

    public void checkType(Field field, Report report) {
        String actualType = field.getType().getSimpleName();
        String expectedType = variableCriteria.getExpectedType();
        try{
            Assertions.assertEquals(expectedType, actualType);
            report.addPassedTest("Variable: " + variableName + "\tCorrect type");    
        } catch (AssertionError e) {
            report.addError("Variable: " + variableName + "\tIncorrect type. Expected - " + variableCriteria.getExpectedType() + ",   Declared - " + actualType);
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
