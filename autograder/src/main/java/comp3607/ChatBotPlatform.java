package comp3607;

import java.util.ArrayList;

public class ChatBotPlatform {

    // For Testing
    private ArrayList<ChatBot> bots;
    
    public ChatBotPlatform() {
        bots = new ArrayList<ChatBot>();
    }

    public boolean addChatBot(int num) {
        ChatBot bot= new ChatBot();
        bots.add(bot);
        return true;
    }

    public String getChatBotList() {
        return "Test String";
    }

    public String InteractWithBot(int num, String userInput) {
        return "Test String";
    }

    public String generateChatBot(String name) {
        return "Test String";
    }
}