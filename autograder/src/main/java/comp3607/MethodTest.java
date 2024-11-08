package comp3607;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class MethodTest extends Test {

    private final MethodCriteria methodCriteria;
    private final String methodName;

    public MethodTest(MethodCriteria methodCriteria, String methodName) {

        this.methodCriteria = methodCriteria;
        this.methodName = methodName;
    }

    public void checkAccessModifier(Method method) { 
        String actualModifier = Modifier.toString(method.getModifiers());
        Assertions.assertEquals(methodCriteria.getExpectedAccessModifier(), actualModifier, 
            "Method has incorrect access modifier: " + actualModifier + ", expected: " + methodCriteria.getExpectedAccessModifier());

    }
    public void checkReturnType(Method method) {
        String actualReturnType = method.getReturnType().getName();
        Assertions.assertEquals(methodCriteria.getExpectedReturnType(), actualReturnType, 
            "Method has incorrect return type: " + actualReturnType + ", expected: " + methodCriteria.getExpectedReturnType());

    }
    public void checkParameterTypes(Method method) {
            List<Class<?>> parameterTypes = Arrays.asList(method.getParameterTypes());
            Collections.sort(parameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            List<Class<?>> expectedParameterTypes = methodCriteria.getExpectedParameterTypes();
            Collections.sort(expectedParameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            Assertions.assertEquals(expectedParameterTypes, parameterTypes, 
                "Method has incorrect parameter types: " + parameterTypes + ", expected: " + expectedParameterTypes);
           
    }
   
    @Override
    public void executeTest(Class<?> clazz, Report report) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, (Class<?>[]) methodCriteria.getExpectedParameterTypes().toArray());
            checkAccessModifier(method);
            checkReturnType(method);
            checkParameterTypes(method);

        // report.addPassedTest("Method: " + this.methodName + " passed all checks");
        } catch (NoSuchMethodException e) {
            report.addError("Method: " + this.methodName + " Does not exist");
        }
    
    }

}
