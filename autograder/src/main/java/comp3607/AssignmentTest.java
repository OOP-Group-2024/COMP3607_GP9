package comp3607;

import java.util.List;

public class AssignmentTest {
    private TestGroup chatBotPlatform = new TestGroup();
    private TestGroup chatBot = new TestGroup();
    private TestGroup chatBotGenerator = new TestGroup();
    private Report report = new Report();


    public void setUp() {

        //Defining tests for ChatBotPlatform
        addVariableTest("private", "ArrayList", "bots", chatBotPlatform);
        addConstructorTest(List.of(), List.of(), chatBotPlatform);
        addInstantiationTest("bots", List.of(), List.of() , List.of(), chatBotPlatform);
        addMethodTest("public", "boolean", List.of(int.class), "addChatBot", chatBotPlatform);
        addMethodTest("public", "java.lang.String", List.of(), "getChatBotList", chatBotPlatform);
        addMethodTest("public", "java.lang.String", List.of(int.class, String.class), "InteractWithBot", chatBotPlatform);
        chatBotPlatform.executeTest(ChatBotPlatform.class, report); 

        

        //Defining tests for ChatBot
        addVariableTest("private", "String", "chatBotName", chatBot);
        addVariableTest("private", "int", "numResponsesGenerated", chatBot);
        addVariableTest("private", "int", "messageLimit", chatBot);
        addVariableTest("private", "int", "messageNumber", chatBot);
        addConstructorTest(List.of(), List.of(), chatBot);
        addInstantiationTest("chatBotName", List.of(), "ChatGPT-3.5", List.of(), chatBot);
        addConstructorTest(List.of(int.class), List.of(1), chatBot);
        addInstantiationTest("chatBotName", List.of(1), "LLaMa", List.of(int.class), chatBot);
        addMethodTest("public", "java.lang.String", List.of(), "getChatBotName", chatBot);
        addMethodTest("public", "int", List.of(), "getNumResponsesGenerated", chatBot);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesGenerated", chatBot);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesRemaining", chatBot);
        addMethodTest("public", "boolean", List.of(), "limitReached", chatBot);
        addMethodTest("public", "java.lang.String", List.of(String.class), "prompt", chatBot);
        addMethodTest("public", "java.lang.String", List.of(), "toString", chatBot);
        chatBot.executeTest(ChatBot.class, report);


        //Defining tests for ChatBotGenerator
        addMethodTest("public static", "java.lang.String", List.of(int.class), "generateChatBotLLM", chatBotGenerator);
        chatBotGenerator.executeTest(ChatBotGenerator.class, report);
    }



    //Method to add variable test
    private void addVariableTest(String accessModifier, String type, String variableName, TestGroup testGroup){
        VariableCriteria criteria = new VariableCriteria(accessModifier, type);
        VariableTest test = new VariableTest(variableName, criteria);
        testGroup.addTest(test);
    }

    //Method to add a method test
    private void addMethodTest(String accessModifier, String returnType, 
                                List<Class<?>> parameters, String methodName, TestGroup testGroup){
        MethodCriteria criteria = new MethodCriteria(accessModifier, returnType, parameters);
        MethodTest test = new MethodTest(methodName, criteria);
        testGroup.addTest(test);
    }

    //Method to add a constructor test
    private void addConstructorTest(List<Class<?>> parameters, List<Object> inputs, TestGroup testGroup){
        ConstructorTest test = new ConstructorTest(parameters, inputs);
        testGroup.addTest(test);
    }

    //InstantiationTest testing = new InstantiationTest("chatBotPlatform", List.of().toArray(), "ArrayList<>", params);

    private void addInstantiationTest(String fieldName, List<Object> args, Object expectedValue, List<Class<?>> parameters, TestGroup testGroup){
        Class<?>[] params = parameters.toArray(new Class<?>[0]);
        InstantiationTest test = new InstantiationTest(fieldName, args.toArray(), expectedValue, params);
        testGroup.addTest(test);
    }
    
    //Print the report
    public void printReport(){
        System.out.println(report.generateReport());
    }
}
