/*
 * 816033593
 */
import java.util.ArrayList;

public class ChatBotPlatform{
    private ArrayList<ChatBot> bots;
    
    //constructor
    public ChatBotPlatform(){
        this.bots=new ArrayList<>();
    }
    
    public boolean addChatBot(int LLMcode){
        if(ChatBot.limitReached())
            return false;
        
        ChatBot newChatBot = new ChatBot(LLMcode);
        bots.add(newChatBot);
        return true;
    }
    
    public String getChatBotList(){
        String output =" Your ChatBots\n";
        
        for(int i=0; i<bots.size(); ++i){
            ChatBot temp = bots.get(i);
            output+="Bot Number: "+i+" "+temp.toString()+"\n";
        }
        output+="Total Messages Used: "+ChatBot.getTotalNumResponsesGenerated()+
                "\nTotal Messages Remaining: "+ChatBot.getTotalNumMessagesRemaining();
        return output;
    }
    
    public String interactWithBot(int botNumber, String message){
        if(botNumber<0||botNumber>=bots.size()){
            return ("Incorrect Bot Number ("+botNumber+") Selected. Try again");
        }
        
        ChatBot tempBot=bots.get(botNumber);
        return tempBot.prompt(message);
        
    }
    
    public int numOfBots(){
        return(bots.size());
    }
}
