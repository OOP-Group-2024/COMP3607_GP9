package comp3607;

import java.util.List;

public class ChatBotPlatformTest extends FileTest{

    private TestGroup chatBotPlatform;
    protected final Object[] none = List.of().toArray();

    public ChatBotPlatformTest(){
        this.chatBotPlatform = new TestGroup();
    }

    public void setUp(Report report){

        addVariableTest("private", "ArrayList", "bots", chatBotPlatform);

        addConstructorTest(List.of(), List.of(), chatBotPlatform);
        addInstantiationTest("bots", List.of(), List.of() , List.of(), chatBotPlatform);

        addMethodTest("public", "boolean", List.of(int.class), "addChatBot", chatBotPlatform);
        addBehaviourTest(List.of("addChatBot", "bots"), List.of(none, List.of(1).toArray()), List.of(), "[ChatBot Name: LLaMa Number Messages Used: 0]", true, chatBotPlatform);
        addMethodTest("public", "java.lang.String", List.of(), "getChatBotList", chatBotPlatform);
        addBehaviourTest(List.of("getChatBotList", "bots"), List.of(none, none), List.of(), "[]", "Total Messages 0 Remaining 10", chatBotPlatform);
        addMethodTest("public", "java.lang.String", List.of(int.class, String.class), "interactWithBot", chatBotPlatform);
        addBehaviourTest(List.of("interactWithBot","bots"), List.of(none,List.of(1,"Hello").toArray()), List.of(), "[]", "Incorrect Bot Number (1) Selected. try Again", chatBotPlatform);
        chatBotPlatform.executeTest(ChatBotPlatform.class, report); 
    }
}
