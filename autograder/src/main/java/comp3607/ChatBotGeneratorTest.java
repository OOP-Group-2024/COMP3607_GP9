package comp3607;

import java.util.List;

public class ChatBotGeneratorTest extends FileTest{
    private final TestGroup chatBotGenerator;
    

    public ChatBotGeneratorTest(){
        this.chatBotGenerator = new TestGroup();
    }


    @Override
    public void setUp(Report report){
        addMethodTest("public static", "java.lang.String", List.of(int.class), "generateChatBotLLM", chatBotGenerator, 7.0f);
        chatBotGenerator.executeTest(ChatBotGenerator.class, report);
    }
}
