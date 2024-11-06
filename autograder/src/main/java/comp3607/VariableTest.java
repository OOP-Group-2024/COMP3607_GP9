package comp3607;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class VariableTest extends Test {
    private final TestCriteria testCriteria;

    public VariableTest(TestCriteria testCriteria) {
        this.testCriteria = testCriteria;
    }

    /**
     * Execute the test for the given class and report.
     * 
     * @param clazz The class to test.
     * @param report The report to add errors to.
     */
    @Override
    protected void executeTest(Class<?> clazz, Report report) { 
       // Loop through all the variable names in the test criteria
       for(String variableName : testCriteria.getExpectedVariables().keySet()) {
           VariableCriteria variableCriteria = testCriteria.getExpectedVariable(variableName);
           
           try {
               // Get the field from the class
               Field field = clazz.getDeclaredField(variableName);
               
               // Check if the access modifier is correct
               if(!Modifier.toString(field.getModifiers()).equals(variableCriteria.getExpectedAccessModifier())) {
                   report.addError("Variable: " + variableName + " has incorrect access modifier");
               } else {
                   report.addPassedTest("Variable: " + variableName + " has correct access modifier");
               }

               // Check if the variable type is correct
               if(!field.getType().getSimpleName().equals(variableCriteria.getExpectedType())) {
                   report.addError("Variable: " + variableName + " has incorrect type");
               } else { 
                   report.addPassedTest("Variable: " + variableName + " has correct type");
               }
           } catch (NoSuchFieldException e) {
               // If the field does not exist, add an error to the report
               report.addError("Variable: " + variableName + " does not exist");
           }
       }
    }
    
}
