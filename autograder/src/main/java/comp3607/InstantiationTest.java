package comp3607;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Assertions;

public class InstantiationTest extends Test {
    
    private final String fieldName;
    private final Object[] args;
    private final Object expectedValue;
    private final Class<?>[] parameterTypes;

    public InstantiationTest(String fieldName, Object[] args, Object expectedValue, Class<?>[] parameterTypes) {
        this.fieldName = fieldName;
        this.args = args;
        this.expectedValue = expectedValue;
        this.parameterTypes = parameterTypes;
    }

    public void CheckInstantiation(Class<?> clazz, Report report) {
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            report.addError("Constructor does not exist");
        }
        if(constructor != null) {
            Object actualValue = null;
            try 
            {
                Object instance = constructor.newInstance(args);
                Field field = constructor.getDeclaringClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                actualValue = field.get(instance);
                Assertions.assertEquals(expectedValue, actualValue);
                report.addPassedTest(String.format("Constructor : %s ,Passed test. Expected: %s,  returned: %s", constructor.toString(), expectedValue, actualValue));
            }catch (AssertionError e) {
                report.addError(String.format("Constructor : %s ,Failed test: Expected %s,  returned: %s", constructor.toString(), expectedValue, actualValue));
            }catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
                report.addError(String.format("Constructor: %s Failed test: %s", constructor.toString(),e.getMessage()));
            }
        }
    }
    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        try {
             CheckInstantiation(clazz, report);
        } catch (Exception e) {
            report.addError(String.format("Class: %s Failed constructor test: %s", clazz.getName(),e.getMessage()));
        }

    
    }
}
