package comp3607;

import java.util.List;

public class ChatBotPlatformTest {
    
    TestGroup chatBotPlatform = new TestGroup();

    public void setUp() {


        VariableCriteria botsCritera = new VariableCriteria("private", "ArrayList<ChatBot>");
        VariableTest bots = new VariableTest("bots", botsCritera);
        chatBotPlatform.addTest(bots);

        ConstructorTest constructorTest = new ConstructorTest(List.of(), List.of());
        chatBotPlatform.addTest(constructorTest);

        MethodCriteria addChatBotCriteria = new MethodCriteria( "public", "boolean", List.of(int.class));
        MethodTest addChatBot = new MethodTest("addChatBot", addChatBotCriteria);
        chatBotPlatform.addTest(addChatBot);

        MethodCriteria getChatBotListCriteria = new MethodCriteria("public", "String", List.of());
        MethodTest getChatBotList = new MethodTest("getChatBotList", getChatBotListCriteria);
        chatBotPlatform.addTest(getChatBotList);

        MethodCriteria InteractWithBotCriteria = new MethodCriteria("public", "String", List.of(int.class, String.class));
        MethodTest InteractWithBot = new MethodTest("InteractWithBot", InteractWithBotCriteria);
        chatBotPlatform.addTest(InteractWithBot);

        Report report = new Report();
        chatBotPlatform.executeTest(ChatBotPlatform.class, report);
        System.out.println(report.generateReport());
    }
}
