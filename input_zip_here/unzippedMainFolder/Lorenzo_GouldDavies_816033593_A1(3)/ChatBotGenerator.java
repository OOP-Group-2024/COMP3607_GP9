/*
 * 816033593
 */
public class ChatBotGenerator{
    
    public static String generateChatBotLLM(int LLMCodeNumber){
        String output;
        switch (LLMCodeNumber){
            case 1: 
                output="LLaMa";
                break;
            case 2:
                output="Mistral7B";
                break;
            case 3:
                output="Bard";
                break;
            case 4:
                output="Claude";
                break;
            case 5:
                output="Solar";
                break;
            default:
                output="ChatGPT-3.5";
                break;
        }
        
        return output;
    }
}

/*
 * Used Java documentation to learn about switch statements
 * https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
 */