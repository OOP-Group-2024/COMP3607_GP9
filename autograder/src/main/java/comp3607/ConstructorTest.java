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

    private final float totalMarks;
    
    private int checks = 0;
    private int checksPassed = 0;


    public ConstructorTest(List<Class<?>> parameterTypes, List<Object> inputs, float totalMarks) {
         
        this.expectedParameterTypes = parameterTypes;
        this.expectedInputs = inputs; 
        this.totalMarks = totalMarks; 
 
    }


    //Catch assertion errors if check fails and add results to report

    public void checkParameterTypes(Constructor<?> constructor, Report report) {
        checks++;
        List<Class<?>> parameterTypes = Arrays.asList(constructor.getParameterTypes());
        try{
            Collections.sort(parameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            List<Class<?>> mutableParameterTypes = new ArrayList<>(expectedParameterTypes);
            Collections.sort(mutableParameterTypes, (c1, c2) -> c1.getName().compareTo(c2.getName()));
            Assertions.assertEquals(mutableParameterTypes, parameterTypes);
            report.addPassedTest(String.format("Constructor: %-25s Correct parameter types", constructorName));    
            checksPassed++;
        }catch (AssertionError e){
            report.addError(String.format("Constructor: %-25s Incorrect parameter types. Expected - %s, Declared - %s", constructorName, expectedParameterTypes, parameterTypes));
        }
    }

    public void checkConstructorInvocation(Constructor<?> constructor, Report report, Object... args) {
        checks++;
        try{
            Object instance = constructor.newInstance(args);
            //Object instance = constructor.newInstance(int.class);
            Assertions.assertNotNull(instance);
            report.addPassedTest(String.format("Constructor: %-25s Successfully created an instance", constructorName));
            checksPassed++;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
            //report.addError("Constructor for " + constructorName + " threw exception: " + e.getMessage());
            report.addError(String.format("Constructor: %-25s threw exception: " + e.getMessage() + ", when instantiated", constructorName));

        }
    }


    //First check for constructor with correct params(1), if not found look for a one with the same name(2)
    //Conduct tests on either (1) or (2)

    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        
        Constructor<?> correctConstructor = null;
        Constructor<?> defaultConstructor = null;
        constructorName = clazz.getSimpleName();
       
        try{
            correctConstructor = clazz.getDeclaredConstructor(expectedParameterTypes.toArray(new Class<?>[0]));
        }catch(NoSuchMethodException e){
            correctConstructor = null;
        }

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for(Constructor<?> declaredConstructor : constructors){
            if(declaredConstructor.getName().equals(clazz.getName())){
                defaultConstructor = declaredConstructor;
                break;
            }
        }

        if (defaultConstructor == null && correctConstructor == null){
            report.addError(String.format("Constructor: %-25s Does not exist", clazz.getSimpleName()));
            return;
        }
        if(correctConstructor == null){
            checkParameterTypes(defaultConstructor, report); 
            checkConstructorInvocation(defaultConstructor, report, expectedInputs.toArray());
        }
        else{
            checkParameterTypes(correctConstructor, report); 
            checkConstructorInvocation(correctConstructor, report, expectedInputs.toArray());
        }

        float obtainedMarks = (checksPassed/checks) * totalMarks;
        report.addMarks(obtainedMarks);
        report.addSummary(String.format("Constructor: %-25s(%-25s)  Passed %d/%d Tests, Obtained %.2f marks", constructorName, expectedParameterTypes.toString(), checksPassed, checks, obtainedMarks));
    }
}
