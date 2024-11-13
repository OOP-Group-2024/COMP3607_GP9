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
    private final float totalMarks;
    
    private int checks = 0;
    private int checksPassed = 0;

    public InstantiationTest(String fieldName, Object[] args, Object expectedValue, Class<?>[] parameterTypes, float totalMarks) {
        this.fieldName = fieldName;
        this.args = args;
        this.expectedValue = expectedValue;
        this.parameterTypes = parameterTypes;
        this.totalMarks = totalMarks;
    }

    public void CheckInstantiation(Class<?> clazz, Report report) {
        checks++;
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            report.addError(String.format("Instantiation: %-22s Does not exist", clazz.getSimpleName()));
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
                report.addPassedTest(String.format("Instantiation: %-23s Passed test. %s: %s", clazz.getSimpleName(), fieldName, actualValue));
                checksPassed++;
            }catch (AssertionError e) {
                report.addError(String.format("Instantiation: %-23s Failed test: Expected %s,  returned: %s", clazz.getSimpleName(), expectedValue, actualValue));
            }catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
                report.addError(String.format("Instantiation: %-23s Failed test: %s", clazz.getSimpleName(), e.getMessage()));
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

        float obtainedMarks = (checksPassed/checks) * totalMarks;
        report.addMarks(obtainedMarks);
        report.addSummary(String.format("Instantiation: %-22s Passed %d/%d Tests, Obtained %.2f marks", clazz.getSimpleName(), checksPassed, checks, obtainedMarks));

    
    }
}
