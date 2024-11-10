package comp3607;

import java.util.ArrayList;

public class ChatBotPlatform {

    // For Testing
    private ArrayList<ChatBot> bots;
    
    public ChatBotPlatform() {
        bots = new ArrayList<>();
    }

    public boolean addChatBot(int num) {
        ChatBot bot= new ChatBot(num);
        bots.add(bot);
        return true;
    }

    public String getChatBotList() {
        StringBuilder botListBuilder = new StringBuilder();
        int i = 0;
        for (ChatBot bot : bots) {
            botListBuilder.append("Bot Number: " + i + " ").append(bot.toString()).append("\n");
            i++;
        }
        
        return botListBuilder.toString();
    }

    public String InteractWithBot(int num, String message) {
        if (num < 0 || num >= bots.size()) {
            return "Incorrect Bot Number (" + num + ") Selected. Try again";
        }

        ChatBot selectedBot = bots.get(num);
        return selectedBot.prompt(message);
    }
}