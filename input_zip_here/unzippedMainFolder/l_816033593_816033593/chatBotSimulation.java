/*
 * 816033593
 */
import java.util.Random; 
public class chatBotSimulation{
    public static void main(String[] args){
        System.out.println("Hello World!"+
                            "\n--------------");
        
        ChatBotPlatform chatBotPlat = new ChatBotPlatform();
        
        chatBotPlat.addChatBot(0);
        chatBotPlat.addChatBot(1);
        chatBotPlat.addChatBot(2);
        chatBotPlat.addChatBot(3);
        chatBotPlat.addChatBot(4);
        chatBotPlat.addChatBot(5);
        chatBotPlat.addChatBot(6);
        //made two unique instance of chat-GPT (will show as two different bots with the same LLM name)
        
        System.out.println(chatBotPlat.getChatBotList());
        System.out.println("--------------");
        
        Random r = new Random();
        for(int i=0; i<15; ++i){
            int LLMCode = r.nextInt(chatBotPlat.numOfBots());
            String output = chatBotPlat.interactWithBot(LLMCode, "message");
            System.out.println(output);
        }
        
        //added a instance where a bot that does not exists is being called, to show error handling
        //System.out.println(chatBotPlat.interactWithBot(7, "message"));
        
        System.out.println("--------------");
        System.out.println(chatBotPlat.getChatBotList());
        return;
    }
}
/*
 * used Week 1 lab for random number generator
 */