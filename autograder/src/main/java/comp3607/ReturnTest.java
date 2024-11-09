package comp3607;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;

public class ReturnTest extends Test {

    private final String methodName;
    private final Object expectedReturn;
    private final Object instance;
    private final Object[] args;
    private final Method method;

    public ReturnTest(String methodName, Object expectedReturn, Object[] args, Method method, Object instance) {
        this.methodName = methodName;
        this.expectedReturn = expectedReturn;
        this.method = method;
        this.instance = instance;
        this.args = args;
    }

    public void checkBehavior(Report report) {
        Object actualReturn = null;
        try {
            method.setAccessible(true);
            actualReturn = method.invoke(instance, args);
            Assertions.assertEquals(expectedReturn, actualReturn);
            report.addPassedTest(String.format("Method %s , Passed Behavior Test. Expected: %s,  returned: %s", methodName, expectedReturn, actualReturn));
        } catch (IllegalAccessException | InvocationTargetException e) {
            report.addError(String.format("Method %s Failed test: %s", methodName, e.getMessage()));
        } catch (AssertionError e) {
            report.addError(String.format("Method %s Failed test: Expected %s,  returned: %s ", methodName, expectedReturn, actualReturn));
        }
    }


    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        try {
            checkBehavior(report);
        } catch (Exception e) {
            report.addError(String.format(methodName + "Failed Behavioral Test : %s", e.getMessage()));
        }
    }
    
}
