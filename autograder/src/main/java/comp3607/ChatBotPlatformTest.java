package comp3607;

import java.util.List;

public class ChatBotPlatformTest extends FileTest{

    private final TestGroup chatBotPlatform;
    protected final Object[] none = List.of().toArray();

    public ChatBotPlatformTest(){
        this.chatBotPlatform = new TestGroup();
    }

    @Override
    public void setUp(Report report, Class<?> clazz){

        addVariableTest("", "ArrayList", "bots", chatBotPlatform, 2.0f);

        addConstructorTest(List.of(), List.of(), chatBotPlatform, 1.0f);
        addInstantiationTest("bots", List.of(), List.of() , List.of(), chatBotPlatform, 1.0f);

        addMethodTest("public", "boolean", List.of(int.class), "addChatBot", chatBotPlatform, 2.0f);
        addBehaviourTest(List.of("addChatBot", "bots"), List.of(none, List.of(1).toArray()), List.of(), "[ChatBot Name: LLaMa Number Messages Used: 0]", true, chatBotPlatform, 3.0f);
        addMethodTest("public", "java.lang.String", List.of(), "getChatBotList", chatBotPlatform, 2.0f);
        addBehaviourTest(List.of("getChatBotList", "bots"), List.of(none, none), List.of(), "[]", "Total Messages 0 Remaining 10", chatBotPlatform, 4.0f);
        addMethodTest("public", "java.lang.String", List.of(int.class, String.class), "interactWithBot", chatBotPlatform, 2.0f);
        addBehaviourTest(List.of("interactWithBot","bots"), List.of(none,List.of(1,"Hello").toArray()), List.of(), "[]", "Incorrect Bot Number (1) Selected. try Again", chatBotPlatform, 3.0f);
        chatBotPlatform.executeTest(clazz, report); 
    }
}
