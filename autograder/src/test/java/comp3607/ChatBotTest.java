package comp3607;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChatBotTest {

    @Test
    public void testChatBotInitialization() {
        ChatBot chatBot = new ChatBot(1);
        assertEquals("LLaMa", chatBot.getChatBotName());
        assertEquals(0, chatBot.getNumResponsesGenerated());
    }

    @Test
    public void testResetCounters() {
        ChatBot.resetCounters();
        assertEquals(10, ChatBot.getTotalNumMessagesRemaining());
    }

    @Test
    public void testLimitReached() {
        for (int i = 0; i < 10; i++) {
            ChatBot chatBot = new ChatBot(1);
            chatBot.prompt("Test message");
        }
        assertTrue(ChatBot.limitReached());
    }

    @Test
    public void testGenerateResponse() {
        ChatBot chatBot = new ChatBot(2);
        String response = chatBot.prompt("Test message");
        assertTrue(response.contains("Message#"));
        assertTrue(response.contains("Response from Mistral7B"));
    }

    @Test
    public void testToString() {
        ChatBot chatBot = new ChatBot(3);
        chatBot.prompt("Message 1");
        assertEquals("ChatBot Name: Bard Number Messages Used: 1", chatBot.toString());
    }
}
