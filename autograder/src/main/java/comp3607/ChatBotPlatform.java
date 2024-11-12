package comp3607;

import java.util.ArrayList;
public class ChatBotPlatform{
    private ArrayList<ChatBot> bots;
    
    public ChatBotPlatform(){
        if (bots == null){
            bots = new ArrayList<>();
            ChatBot.resetCounters();
        }
    }
    
    public boolean addChatBot(int LLMcode){
        if (ChatBot.limitReached()){
            return false;
        }
        ChatBot bot = new ChatBot(LLMcode);
        bots.add(bot);
        return true;
    }
    
    public String getChatBotList(){
        String Output = "-------------------------------------------\n";
        Output = Output + "Your ChatBots\n";
        for(int i = 0; i< bots.size();i++){
            Output = Output + "Bot Number: " + i + " " + bots.get(i).toString() + "\n";
        }
        Output = Output + "Total Messages Used: " + ChatBot.getTotalNumResponsesGenerated() + "\n";
        Output = Output + "Total Messages Remaining: " + ChatBot.getTotalNumMessagesRemaining() + "\n";
        Output = Output + "-------------------------------------------\n";
        return Output;
    }
    
    public String interactWithBot(int botNumber, String message){
        if((bots.size() - 1) < botNumber){
            return ("Incorrect Bot Number (" + botNumber + ") Selected. Try Again");
        }
        return bots.get(botNumber).prompt(message);
    }
}
