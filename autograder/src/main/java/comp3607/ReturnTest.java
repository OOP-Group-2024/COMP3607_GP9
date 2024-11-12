package comp3607;

//import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class ReturnTest extends Test {

    private final String methodName;
    private final Object expectedReturn;
    private final Object[] constructorInput;
    private final Object[] methodInput;    
    private final Class<?>[] parameterTypes;

    //public ReturnTest(String methodName, Object expectedReturn, Object[] args, Method method, Object instance) {
    public ReturnTest(List<String> names, List<Object[]> input, Class<?>[] parameterTypes, Object expectedReturn) {
        this.methodName = names.get(0);
        this.expectedReturn = expectedReturn;
        this.parameterTypes = parameterTypes;
        this.constructorInput = input.get(0);
        this.methodInput = input.get(1);
    }

    public void checkBehavior(Class<?> clazz, Report report, Constructor<?> constructor, Method method) {
        Object actualReturn = null;
        try {
            Object instance = constructor.newInstance(constructorInput);
            method.setAccessible(true);
            actualReturn = method.invoke(instance, methodInput);
            boolean contains = checkString(actualReturn.toString().toLowerCase(), expectedReturn.toString().toLowerCase());
            Assertions.assertTrue(contains);
            report.addPassedTest(String.format("Behaviour: %-27s Passed Test. Expected - %s,  Returned - %s", methodName, expectedReturn, actualReturn));
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            report.addError(String.format("Behaviour: %s Failed test: %s", methodName, e.getMessage()));
        } catch (AssertionError e) {
            report.addError(String.format("Behaviour: %s Failed test: Expected %s,  returned: %s ", methodName, expectedReturn, actualReturn));
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

        checkBehavior(clazz, report, constructor, method);
    }
    
    private boolean checkString(String actual, String expectedWords){
        String[] words = expectedWords.split(" ");
        for(String word : words){
            if(!actual.contains(word)){
                return false;
            }
        }
        return true;
    }

}

