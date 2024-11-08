package comp3607;

import java.util.List;

public class ChatBotPlatformTest {
    
    TestGroup chatBotPlatform = new TestGroup();
    private Report report = new Report();


    public void setUp() {

        VariableCriteria botsCritera = new VariableCriteria("private", "ArrayList");
        VariableTest bots = new VariableTest("bots", botsCritera);
        chatBotPlatform.addTest(bots);

        ConstructorTest constructorTest = new ConstructorTest(List.of(), List.of());
        chatBotPlatform.addTest(constructorTest);

        MethodCriteria addChatBotCriteria = new MethodCriteria( "public", "boolean", List.of(int.class));
        MethodTest addChatBot = new MethodTest("addChatBot", addChatBotCriteria);
        chatBotPlatform.addTest(addChatBot);

        MethodCriteria getChatBotListCriteria = new MethodCriteria("public", "java.lang.String", List.of());
        MethodTest getChatBotList = new MethodTest("getChatBotList", getChatBotListCriteria);
        chatBotPlatform.addTest(getChatBotList);

        MethodCriteria InteractWithBotCriteria = new MethodCriteria("public", "java.lang.String", List.of(int.class, String.class));
        MethodTest InteractWithBot = new MethodTest("InteractWithBot", InteractWithBotCriteria);
        chatBotPlatform.addTest(InteractWithBot);

        chatBotPlatform.executeTest(ChatBotPlatform.class, report); 
    }

    public void printReport(){
        System.out.println(report.generateReport());
    }
}
