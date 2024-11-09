package comp3607;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;

public class ValueTest extends Test {

    private final String variableName;
    private final Object expectedValue;
    private final Object instance;
    private final Field field;
    
    public ValueTest(String variableName, Object expectedValue, Object instance, Field field) {
        this.variableName = variableName;
        this.expectedValue = expectedValue;
        this.instance = instance;
        this.field = field;
    }

    public void checkValue(Report report) {
        Object actualValue = null;
        try{
            field.setAccessible(true);
            actualValue = field.get(instance);
            Assertions.assertEquals(expectedValue, actualValue);
            report.addPassedTest(String.format("Variable : %s ,Passed test. Expected: %s,  returned: %s", variableName, expectedValue, actualValue));
        }catch (AssertionError e) {
            report.addError(String.format("Variable : %s ,Failed test: Expected %s,  returned: %s", variableName, expectedValue, actualValue));
        }catch (IllegalAccessException e) {
            report.addError(String.format("Variable: %s Failed test: %s", variableName,e.getMessage()));
        }
    }



    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
