package comp3607;

import java.util.Collections;
import java.util.List;

public class ChatBotTest extends FileTest{

    private final TestGroup chatBot;
    protected final Object[] none = List.of().toArray();


    public ChatBotTest(){
        this.chatBot = new TestGroup();
    }

    @Override
    public void setUp(Report report){
        List<Class<?>> noParams = Collections.emptyList();
        addVariableTest("private", "String", "chatBotName", chatBot, 1.0f);
        addVariableTest("private", "int", "numResponsesGenerated", chatBot, 1.0f);
        addVariableTest("static", "int", "messageLimit", chatBot, 3.0f);
        addVariableTest("static", "int", "messageNumber", chatBot, 2.0f);

        addConstructorTest(List.of(), List.of(), chatBot, 1.0f);
        addInstantiationTest("chatBotName", List.of(), "ChatGPT-3.5", List.of(), chatBot, 2.0f);
        addConstructorTest(List.of(int.class), List.of(1), chatBot, 1.0f);
        addInstantiationTest("chatBotName", List.of(1), "LLaMa", List.of(int.class), chatBot, 2.0f);     

        addMethodTest("public", "java.lang.String", List.of(), "getChatBotName", chatBot, 0.5f);
        addBehaviourTest(List.of("getChatBotName", "chatBotName"), List.of(none, none), noParams,  "ChatGPT-3.5", "ChatGPT-3.5", chatBot, 0.5f);
        addMethodTest("public", "int", List.of(), "getNumResponsesGenerated", chatBot, 0.5f);
        addBehaviourTest(List.of("getNumResponsesGenerated", "numResponsesGenerated"), List.of(none, none), List.of(), 0, 0,chatBot, 0.5f);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesGenerated", chatBot, 1.0f);
        addBehaviourTest(List.of("getTotalNumResponsesGenerated","messageNumber"), List.of(none,none), List.of(),0 , 0, chatBot, 1.0f);
        addMethodTest("public", "int", List.of(), "getTotalNumMessagesRemaining", chatBot, 1.0f);
        addBehaviourTest(List.of("getTotalNumMessagesRemaining","messageNumber"), List.of(none,none), List.of(),0, 10, chatBot, 2.0f);
        addMethodTest("public", "boolean", List.of(), "limitReached", chatBot, 1.0f);
        addBehaviourTest(List.of("limitReached","messageNumber"), List.of(none,none), List.of(), 0, false, chatBot, 2.0f);
        addMethodTest("private", "java.lang.String", List.of(), "generateResponse", chatBot, 2.0f);
        addBehaviourTest(List.of("generateResponse","numResponsesGenerated"), List.of(none,none), List.of(), 1, "1 ChatGPT-3.5" , chatBot, 2.0f); //messageNumber++
        addMethodTest("public", "java.lang.String", List.of(String.class), "prompt", chatBot, 1.0f); 
        addBehaviourTest(List.of("prompt","messageNumber"), List.of(none,List.of("hello").toArray()), Collections.emptyList(), 3 , "3 ChatGPT-3.5", chatBot, 2.0f); //messageNumber + 2 for returnTest invocation and valueTest invocation
        addMethodTest("public", "java.lang.String", List.of(), "toString", chatBot, 4.0f);

        chatBot.executeTest(ChatBot.class, report);
    }

    
}
