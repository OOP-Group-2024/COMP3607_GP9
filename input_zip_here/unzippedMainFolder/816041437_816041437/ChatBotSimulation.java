//Student ID : 816041437
import java.util.Random;
public class ChatBotSimulation
{
   private Random Rand = new Random();
    public ChatBotSimulation()
    {
        System.out.println("Hello World!");
        ChatBotPlatform Platform = new ChatBotPlatform();
        for(int i = 0; i < 7; i++){
            Platform.addChatBot(i);
        }
        System.out.println(Platform.getChatBotList());
        //System.out.println(Platform.interactWithBot(10, "Hi I'm Alive")); //Used to test out of bounds bot
        for(int i = 0; i < 15; i++){
            String result = Platform.interactWithBot(Rand.nextInt(7), "Hi how are ya");
            System.out.println(result);
        }
        System.out.println("");
        System.out.println(Platform.getChatBotList());
    }

}
//Random number generated using code from https://www.geeksforgeeks.org/generating-random-numbers-in-java/