package comp3607;

import java.util.List;

public class ChatBotTest extends FileTest{

    private TestGroup chatBot;
    protected final Object[] none = List.of().toArray();


    public ChatBotTest(){
        this.chatBot = new TestGroup();
    }

    public void setUp(Report report){

        addVariableTest("private", "String", "chatBotName", chatBot);
        addVariableTest("private", "int", "numResponsesGenerated", chatBot);
        addVariableTest("private final", "int", "messageLimit", chatBot);
        addVariableTest("private", "int", "messageNumber", chatBot);

        addConstructorTest(List.of(), List.of(), chatBot);
        addInstantiationTest("chatBotName", List.of(), "ChatGPT-3.5", List.of(), chatBot);
        addConstructorTest(List.of(int.class), List.of(1), chatBot);
        addInstantiationTest("chatBotName", List.of(1), "LLaMa", List.of(int.class), chatBot);     

        addMethodTest("public", "java.lang.String", List.of(), "getChatBotName", chatBot);
        addBehaviourTest(List.of("getChatBotName", "chatBotName"), List.of(none, none), List.of(), "ChatGPT-3.5", "ChatGPT-3.5", chatBot);
        addMethodTest("public", "int", List.of(), "getNumResponsesGenerated", chatBot);
        addBehaviourTest(List.of("getNumResponsesGenerated", "numResponsesGenerated"), List.of(none, none), List.of(), 0, 0,chatBot);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesGenerated", chatBot);
        addBehaviourTest(List.of("getTotalNumResponsesGenerated","messageNumber"), List.of(none,none), List.of(),0 , 0, chatBot);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesRemaining", chatBot);
        addBehaviourTest(List.of("getTotalNumResponsesRemaining","messageNumber"), List.of(none,none), List.of(),0, 10, chatBot);
        addMethodTest("public", "boolean", List.of(), "limitReached", chatBot);
        addBehaviourTest(List.of("limitReached","messageNumber"), List.of(none,none), List.of(), 0, false, chatBot);
        addMethodTest("private", "java.lang.String", List.of(), "generateResponse", chatBot);
        addBehaviourTest(List.of("generateResponse","numResponsesGenerated"), List.of(none,none), List.of(), 1, "" , chatBot);
        addMethodTest("public", "java.lang.String", List.of(String.class), "prompt", chatBot);
        addBehaviourTest(List.of("prompt","messageNumber"), List.of(none,List.of("hello").toArray()), List.of(),1 , "", chatBot);
        addMethodTest("public", "java.lang.String", List.of(), "toString", chatBot);

        chatBot.executeTest(ChatBot.class, report);
    }

    
}
