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
    private String constructorName;


    public ConstructorTest(List<Class<?>> parameterTypes, List<Object> inputs) {  
        this.expectedParameterTypes = parameterTypes;
        this.expectedInputs = inputs;  
 
    }


    //Catch assertion errors if check fails and add results to report

    public void checkParameterTypes(Constructor<?> constructor, Report report) {
        List<Class<?>> parameterTypes = Arrays.asList(constructor.getParameterTypes());
        try{
            Collections.sort(parameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            List<Class<?>> mutableParameterTypes = new ArrayList<>(expectedParameterTypes);
            Collections.sort(mutableParameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            Assertions.assertEquals(mutableParameterTypes, parameterTypes);
            report.addPassedTest(String.format("Constructor: %-25s Correct paramter types", constructorName));    

        }catch (AssertionError e){
            report.addError(String.format("Constructor: %-25s Incorrect parameter types. Expected - %s, Declared - %s", constructorName, expectedParameterTypes, parameterTypes));
        }
    }

    public void checkConstructorInvocation(Constructor<?> constructor, Report report, Object... args) {

        try{
            Object instance = constructor.newInstance(args);
            Assertions.assertNotNull(instance);
            report.addPassedTest(String.format("Constructor: %-25s Successfully created an instance", constructorName));
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
            //report.addError("Constructor for " + constructorName + " threw exception: " + e.getMessage());
            report.addError(String.format("Constructor: %-25s threw exception: " + e.getMessage(), constructorName));

        }
    }


    //Ignore params and search through declared method names to match the appropriate expected method name 
    //before conducting checks

    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        Constructor<?> constructor = null;
        constructorName = clazz.getSimpleName();
        //constructor = clazz.getDeclaredConstructor(expectedParameterTypes.toArray(new Class<?>[0]) );
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for(Constructor<?> declaredConstructor : constructors){
            if(declaredConstructor.getName().equals(clazz.getName())){
                constructor = declaredConstructor;
                break;
            }
        }
        if(constructor == null){
            report.addError("Constructor does not exist ");
            return;
        }
        checkParameterTypes(constructor, report); 
        checkConstructorInvocation(constructor, report, expectedInputs.toArray());
    }
}
