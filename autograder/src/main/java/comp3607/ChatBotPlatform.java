package comp3607;

import java.util.ArrayList;
import java.util.List;

public class ChatBotPlatform {

    // List to hold the chatbots
    private List<ChatBot> chatBots;

    // Constructor
    public ChatBotPlatform() {
        chatBots = new ArrayList<>();
    }

    // Method to add a ChatBot
    public boolean addChatBot(ChatBot chatBot) {
        chatBots.add(chatBot);
        return true;
    }

    // Method to get the list of all chatbots
    public String getChatBotList() {
        return "Should Fail";
    }

    // Method to interact with a specific bot
    public String interactWithBot(String botName, String userInput) {
        
        return "Bot not found.";
    }

    // Method to generate a new ChatBot with predefined settings (example)
    public String generateChatBot(String name) {
        String str = "This should fail";
        return str;
    }
}