//Student ID : 816041437
public class ChatBot {
	private String chatBotName;
	private int numResponsesGenerated;
	static int messageLimit = 10;
	static int messageNumber = 0;
	
	public ChatBot(){
		chatBotName = ChatBotGenerator.generateChatBotLLM(0);
		this.numResponsesGenerated = 0;
	}
	
	public ChatBot(int LLMCode) {
        	chatBotName = ChatBotGenerator.generateChatBotLLM(LLMCode);
        	this.numResponsesGenerated = 0;
	}
	
	public String getChatBotName() {
		return chatBotName;
	}
	
	public static void resetCounters(){
	    messageLimit = 10;
	    messageNumber = 0;
        }
	
	public int getNumResponsesGenerated() {
		return numResponsesGenerated;
	}

	public static int getTotalNumResponsesGenerated() {
		return messageNumber;
	}
	
	public static int getTotalNumMessagesRemaining() {
		return (messageLimit - messageNumber);
	}
	
	public static boolean limitReached() {
		return (messageLimit == messageNumber);
	}
	
	private String generateResponse() {
		String Response = ("Message# " + messageNumber + " Response from " + chatBotName +  "     >>generatedTextHere");
		numResponsesGenerated ++;
		messageNumber ++;
		return Response;
	}
	
	public String prompt(String requestMessage){
	    if (limitReached() == true){
	        return "Daily Limit Reached. Wait 24 hours to resume chatbot usage";
            }
            return generateResponse();
     
         }
         
        public String toString(){
            return ("ChatBot Name: " + chatBotName + " Number Messages Used: " + numResponsesGenerated);
        }
}
