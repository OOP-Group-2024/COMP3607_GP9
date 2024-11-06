package comp3607;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodTest extends Test {
    private final TestCriteria testCriteria;
    
    public MethodTest(TestCriteria testCriteria) {
        this.testCriteria = testCriteria;
    }

    @Override
    protected void executeTest(Class<?> clazz, Report report) {
       for(String methodName : testCriteria.getExpectedMethods().keySet()) {
           MethodCriteria methodCriteria = testCriteria.getExpectedMethod(methodName);
           try {
               Method method = clazz.getDeclaredMethod(methodName, methodCriteria.getExpectedParameterTypes().stream()
                       .map(this::getClassForName).toArray(Class[]::new));


                if(!Modifier.toString(method.getModifiers()).equals(methodCriteria.getExpectedAccessModifier())) {
                    report.addError("Method: " + methodName + " has incorrect access modifier");
                } else {
                    report.addPassedTest("Method: " + methodName + " has correct access modifier");
                }

                if(!method.getReturnType().getName().equals(methodCriteria.getExpectedReturnType())) {
                    report.addError("Method: " + methodName + " has incorrect return type");
                } else {
                    report.addPassedTest("Method: " + methodName + " has correct return type");
                }

                //Redundancy check may be unnecessary
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
