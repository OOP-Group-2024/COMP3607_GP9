package comp3607;

import java.util.List;

public class TestExample {
    TestGroup classTestGroup = new TestGroup();

    public void setUp() {
        

    // Example: Adding a method test for a method with specific criteria
        MethodCriteria methodCriteria = new MethodCriteria( "public", "void", List.of(String.class));
        MethodTest mTest = new MethodTest("MethodName", methodCriteria);
        classTestGroup.addTest(mTest);

        // Example: Adding a variable test for a variable with specific criteria
        VariableCriteria variableCriteria = new VariableCriteria( "private", "int");
        VariableTest variableTest = new VariableTest("VariableName", variableCriteria);
        classTestGroup.addTest(variableTest);

        // Example: Adding a constructor test for a constructor with specific criteria
        ConstructorTest constructorTest = new ConstructorTest(List.of(String.class, int.class), List.of("String", 1));
        classTestGroup.addTest(constructorTest);

        // Run all tests in the group
        classTestGroup.executeTest(ChatBot.class, new Report());

}
}
