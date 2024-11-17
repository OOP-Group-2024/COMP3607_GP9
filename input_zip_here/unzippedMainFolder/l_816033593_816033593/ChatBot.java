/*
 * 816033593
 */
public class ChatBot{
    private String chatBotName;
    private int numResponsesGenerated;
    private static int messageLimit = 10;
    private static int messageNumber = 0;
    
    //constructors
    public ChatBot(){
        setChatBotName(ChatBotGenerator.generateChatBotLLM(0));
        setNumResponsesGenerated(0);
    }
    
    public ChatBot(int LLMCode){
        String LLMName = ChatBotGenerator.generateChatBotLLM(LLMCode);
        setChatBotName(LLMName);
        setNumResponsesGenerated(0);
    }
    
    //Accessors
    public String getChatBotName() { return chatBotName; }
    
    public int getNumResponsesGenerated() { return numResponsesGenerated; }
    
    public static int getTotalNumResponsesGenerated() {return messageNumber; }
    
    public static int getTotalNumMessagesRemaining() { 
        return (messageLimit-messageNumber);
    }
    
    //methods
    public static boolean limitReached(){
        if(messageNumber<messageLimit)
            return false;
        else
            return true;
    }
    
    private String generateResponses(){
        String response = "(Message# " +(getTotalNumResponsesGenerated()+1)+
                            ") Response from "+getChatBotName()+" >>generatedTextHere";
        
        messageNumber++;
        numResponsesGenerated++;
        
        return response;
    }
    
    public String prompt(String requestMessage){
        if(limitReached())
            return ("Daily Limit Reached. Wait 24 hours to resume chat bot usage");
        
        return(generateResponses());
    }
    
    public String toString(){
        return("ChatBot Name: "+getChatBotName()+"  Number Messages Used: "+getNumResponsesGenerated());
    }
    
    //Mutators
    public void setChatBotName(String chatBotName){ 
        this.chatBotName=chatBotName;
    }
    
    public void setNumResponsesGenerated(int numResponsesGenerated){
       this.numResponsesGenerated=numResponsesGenerated; 
    }
    
    private static void setMessageLimit(int newMessageLimit){
        messageLimit=newMessageLimit;
    }
    
    private static void setMessageNumber(int newMessageNumber){
        messageNumber=newMessageNumber;
    }
    
    
}