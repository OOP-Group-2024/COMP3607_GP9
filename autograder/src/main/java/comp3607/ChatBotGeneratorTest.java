package comp3607;

import java.util.List;

public class ChatBotGeneratorTest extends FileTest{
    private TestGroup chatBotGenerator;
    

    public ChatBotGeneratorTest(){
        this.chatBotGenerator = new TestGroup();
    }


    public void setUp(Report report){
        addMethodTest("public static", "java.lang.String", List.of(int.class), "generateChatBotLLM", chatBotGenerator);
        chatBotGenerator.executeTest(ChatBotGenerator.class, report);
    }
}
