package comp3607;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class ValueTest extends Test {

    private final String methodName;
    private final Object[] constructorInput;
    private final Object[] methodInput;
    private final Object expectedValue;
    private final Class<?>[] parameterTypes;
    private final String fieldName;
    private final float totalMarks;
    
    private int checks = 0;
    private int checksPassed = 0;
    
    public ValueTest(List<String> names, List<Object[]> input, Class<?>[] parameterTypes, Object expectedValue, float totalMarks) {

        this.methodName = names.get(0);
        this.fieldName = names.get(1);
        this.constructorInput = input.get(0);
        this.methodInput = input.get(1);
        this.expectedValue = expectedValue;
        this.parameterTypes = parameterTypes;
        this.totalMarks = totalMarks;
    }


    public void checkValue(Class<?> clazz, Report report, Constructor<?> constructor, Method method) {
        checks++;
        Object actualValue = null;
        try{
            Object instance = constructor.newInstance(constructorInput);
            method.setAccessible(true);
            method.invoke(instance, methodInput);
            Field field = constructor.getDeclaringClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            actualValue = field.get(instance);
            Assertions.assertEquals(actualValue.toString(), expectedValue.toString());
            report.addPassedTest(String.format("Behaviour: %-27s Passed Test. %s: %s", methodName, fieldName, actualValue));
            checksPassed++;    
        }catch (AssertionError e) {
            report.addError(String.format("Behaviour: %-27s Failed test: Expected %s,  %s: %s", methodName, expectedValue, fieldName, actualValue));
        }catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            report.addError(String.format("Behaviour: %-27s Failed test: %s", methodName, e.getMessage()));
        }
    }



    @Override
    protected void executeTest(Class<?> clazz, Report report) {

        Method method = null;
        Constructor<?> constructor = null;

        //Check for a constructor
        try {
            constructor = clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            report.addError(String.format("Behaviour: %-27s Constructor does not exist", methodName));
            return;
        }  

        //check for a method
        Method[] methods = clazz.getDeclaredMethods();
        for (Method declaredMethod : methods) {
            if (declaredMethod.getName().toLowerCase().equals(methodName.toLowerCase())) {
                method = declaredMethod;
                break;
            }
        }
        if (method == null) {
            report.addError(String.format("Behaviour: %-27s Does not exist", methodName));
            return;
        }

        checkValue(clazz, report, constructor, method);
        float obtainedMarks = (checksPassed/checks) * totalMarks;
        report.addMarks(obtainedMarks);
        report.addSummary(String.format("Method %-27s Behaviour:  Passed %d/%d Tests. Obtained: %.2f marks", methodName, checksPassed, checks, obtainedMarks));
    }
    
}
