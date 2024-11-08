package comp3607;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class ConstructorTest extends Test {
    private final List<Class<?>> expectedParameterTypes;
    private final List<Object> expectedInputs;



    public ConstructorTest(List<Class<?>> parameterTypes, List<Object> inputs) {    
        this.expectedParameterTypes = parameterTypes;
        this.expectedInputs = inputs;  
 
    }

    public void checkParameterTypes(Constructor<?> constructor, Report report) {
        List<Class<?>> parameterTypes = Arrays.asList(constructor.getParameterTypes());
        Collections.sort(parameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        List<Class<?>> mutableParameterTypes = new ArrayList<>(expectedParameterTypes);
        Collections.sort(mutableParameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        Assertions.assertEquals(mutableParameterTypes, parameterTypes,
         "Constructor has incorrect parameter types: " + parameterTypes + ", expected: " + expectedParameterTypes);

        //Collections.sort(expectedParameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        //Assertions.assertEquals(expectedParameterTypes, parameterTypes,
        // "Constructor has incorrect parameter types: " + parameterTypes + ", expected: " + expectedParameterTypes);
        
    }

    public void checkConstructorInvocation(Constructor<?> constructor, Report report, Object... args) {

        try{
            Object instance = constructor.newInstance(args);
            Assertions.assertNotNull(instance);
     
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
            report.addError("Constructor threw exception: " + e.getMessage());
        }
    }
    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(expectedParameterTypes.toArray(new Class<?>[0]) );
            checkParameterTypes(constructor, report);
            checkConstructorInvocation(constructor, report, expectedInputs.toArray());
        } catch (NoSuchMethodException e) {
            report.addError("Constructor does not exist");
        }
    }
    
}
