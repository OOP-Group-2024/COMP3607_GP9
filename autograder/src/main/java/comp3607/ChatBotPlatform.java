package comp3607;

import java.util.ArrayList;
//import java.util.List;

public class ChatBotPlatform {

    // List to hold the chatbots
    private ArrayList<ChatBot> bots;

    // Constructor
    public ChatBotPlatform() {
        bots = new ArrayList<ChatBot>();
    }

    // Method to add a ChatBot
    public boolean addChatBot(int num) {
        return true;
    }

    // Method to get the list of all chatbots
    public String getChatBotList() {
        return "Test String";
    }

    // Method to interact with a specific bot
    public String InteractWithBot(int num, String userInput) {
        
        return "Test String";
    }

    // Method to generate a new ChatBot with predefined settings (example)
    public String generateChatBot(String name) {
        return "Test String";
    }
}