package comp3607;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodTest extends Test {
    private final TestCriteria testCriteria;
    
    public MethodTest(TestCriteria testCriteria) {
        this.testCriteria = testCriteria;
    }


/**
 * Executes the test for the given class and report.
 * 
 * This method iterates over the expected methods defined in the test criteria
 * and verifies each method's access modifier, return type, and parameter count
 * against the actual methods declared in the given class. Any discrepancies
 * are added as errors to the report, while matches are logged as passed tests.
 * 
 * @param clazz The class to test.
 * @param report The report to add errors and passed tests to.
 */
    @Override
    protected void executeTest(Class<?> clazz, Report report) {
       // Iterate over the expected methods defined in the test criteria
       for(String methodName : testCriteria.getExpectedMethods().keySet()) {
           MethodCriteria methodCriteria = testCriteria.getExpectedMethod(methodName);

           try {
               // Get the actual method declared in the given class
               Method method = clazz.getDeclaredMethod(methodName, methodCriteria.getExpectedParameterTypes().stream()
                       .map(this::getClassForName).toArray(Class[]::new));

               // Verify the method's access modifier
               if(!Modifier.toString(method.getModifiers()).equals(methodCriteria.getExpectedAccessModifier())) {
                   report.addError("Method: " + methodName + " has incorrect access modifier");
               } else {
                   report.addPassedTest("Method: " + methodName + " has correct access modifier");
               }

               // Verify the method's return type
               if(!method.getReturnType().getName().equals(methodCriteria.getExpectedReturnType())) {
                   report.addError("Method: " + methodName + " has incorrect return type");
               } else {
                   report.addPassedTest("Method: " + methodName + " has correct return type");
               }

               // Verify the method's parameter count (redundancy check may be unnecessary)
               if(method.getParameterCount() != methodCriteria.getExpectedParameterTypes().size()) {
                   report.addError("Method: " + methodName + " has incorrect number of parameters");
               } else {
                   report.addPassedTest("Method: " + methodName + " has correct number of parameters");
               }    
           } catch (NoSuchMethodException e) {
               report.addError("Method: " + methodName + " does not exist");
           }
       }
    }

    /**
     * Returns the Class object associated with the given type name.
     * 
     * The given type name can be either a primitive type name (e.g. "int", "float", etc.),
     * the name of a non-primitive type (e.g. "String", "java.util.List", etc.), or the
     * name of a type variable (e.g. "T", "K", etc.).
     * 
     * If the given type name is not recognized, this method throws a RuntimeException.
     * 
     * @param typeName The name of the type to get the Class object for.
     * @return The Class object associated with the given type name.
     */
    private Class<?> getClassForName(String typeName) {
        Class<?> typeClass;
        switch (typeName) {
            case "int" -> typeClass = int.class;
            case "float" -> typeClass = float.class;
            case "double" -> typeClass = double.class;
            case "boolean" -> typeClass = boolean.class;
            case "char" -> typeClass = char.class;
            case "String" -> typeClass = String.class;
            case "void" -> typeClass = void.class;
            case "short" -> typeClass = short.class;
            case "long" -> typeClass = long.class;
            default -> {
                try {
                    typeClass = Class.forName(typeName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Unknown type: " + typeName, e);
                }
            }
        }
        return typeClass;
    }

}
