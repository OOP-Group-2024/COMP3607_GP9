package comp3607;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class MethodTest extends Test {

    private final MethodCriteria methodCriteria;
    private final String methodName;
    private final float totalMarks;
    
    private int checks = 0;
    private int checksPassed = 0;

    public MethodTest(String methodName, MethodCriteria methodCriteria, float totalMarks) {

        this.methodCriteria = methodCriteria;
        this.methodName = methodName;
        this.totalMarks = totalMarks;
    }


    //Catch assertion errors if check fails and add results to report

    public void checkAccessModifier(Method method, Report report) {
        checks++;
        String actualModifier = Modifier.toString(method.getModifiers());
        String expectedAccessModifier = methodCriteria.getExpectedAccessModifier();
        try{
            //Assertions.assertEquals(expectedAccessModifier, actualModifier);
            Assertions.assertTrue(actualModifier.contains(expectedAccessModifier));
            report.addPassedTest(String.format("Method: %-30s Correct access modifier", methodName));    
            checksPassed++;
        }catch (AssertionError e){
            report.addError(String.format("Method: %-30s Incorrect access modifier. Expected - %s, Declared - %s", methodName, expectedAccessModifier, actualModifier));
        }
    }
    public void checkReturnType(Method method, Report report) {
        checks++;
        String actualReturnType = method.getReturnType().getName();
        String expectedReturnType = methodCriteria.getExpectedReturnType();
        try{
            Assertions.assertEquals(expectedReturnType, actualReturnType);
            report.addPassedTest(String.format("Method: %-30s Correct return type", methodName));    
            checksPassed++;
        }catch (AssertionError e){
            report.addError(String.format("Method: %-30s Incorrect return type. Expected - %s, Declared - %s", methodName, expectedReturnType, actualReturnType));
        }
    }

    public void checkParameterTypes(Method method, Report report) {
        checks++;
        List<Class<?>> expectedParameterTypes = methodCriteria.getExpectedParameterTypes();
        List<Class<?>> parameterTypes = Arrays.asList(method.getParameterTypes());
        try{
            Collections.sort(parameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            List<Class<?>> mutableParameterTypes = new ArrayList<>(expectedParameterTypes);
            Collections.sort(mutableParameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            Assertions.assertEquals(mutableParameterTypes, parameterTypes);
            report.addPassedTest(String.format("Method: %-30s Correct paramter types", methodName));    
            checksPassed++;
        }catch(AssertionError e){
            report.addError(String.format("Method: %-30s Incorrect parameter types. Expected - %s, Declared - %s", methodName, expectedParameterTypes, parameterTypes));
        }
    }
   
    //Ignore params and search through declared method names to match the appropriate expected method name 
    //before conducting checks

    @Override
    public void executeTest(Class<?> clazz, Report report) {
        Method method = null;
        
        Method[] methods = clazz.getDeclaredMethods();

        for (Method declaredMethod : methods) {
            if (declaredMethod.getName().toLowerCase().equals(methodName.toLowerCase())) {
                method = declaredMethod;
                break;
            }
        }
        if (method == null) {
            report.addError(String.format("Method: %-30s Does not exist", methodName));
            return;
        }
        
        checkAccessModifier(method, report);
        checkReturnType(method, report);
        checkParameterTypes(method, report);

        float obtainedMarks = (checksPassed/checks) * totalMarks;
        report.addMarks(obtainedMarks);

        report.addSummary(String.format("Method: %-30s Passed %d/%d Tests, Obtained %.2f marks", methodName, checksPassed, checks, obtainedMarks));
    }
}
